package org.zisist.leaderboard.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.zisist.SpringApplicationContext;
import org.zisist.conf.ConfigurationLoader;
import org.zisist.leaderboard.LeaderboardHandler;
import org.zisist.leaderboard.LeaderboardKeyHandler;
import org.zisist.leaderboard.inputanalyzer.InputAnalyzer;
import org.zisist.leaderboard.inputsourcehandler.InputSourceHandler;
import org.zisist.leaderboard.inputsourcehandler.InputSourceHandlerFactory;
import org.zisist.leaderboard.merger.Merger;
import org.zisist.leaderboard.publisher.Publisher;
import org.zisist.model.Configuration;
import org.zisist.model.TopPlayer;
import org.zisist.model.xml.Leaderboard;
import org.zisist.model.xml.LeaderboardConfiguration;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * This is a multi-threaded implementation of the algorithm.<br>
 * The analysis of the sources (reading of input data and generation of TopPlayer lists) <br>
 * is the step that is handed over to multiple threads.<br>
 * The best performance improvement that we can hope for is to limit time needed to parse
 * the data from all sourced to the time needed by the slowest source.<br><br>
 * Fluctuations in the results might be observed, since response times from servers might vary.<br><br>
 * Created by zis.tax@gmail.com on 26/10/2012 at 12:35 AM
 */
@Service("multiThreadedLeaderboardHandler")
public final class MultiThreadedLeaderboardHandler implements LeaderboardHandler {
    private static Logger log = Logger.getLogger(MultiThreadedLeaderboardHandler.class);


    @Autowired
    private ConfigurationLoader configurationLoader;
    @Autowired
    private Map<String, Publisher> publishersMap;
    @Autowired
    private Map<String, Merger<TopPlayer>> mergerMap;
    @Autowired
    private InputSourceHandlerFactory inputSourceHandlerFactory;
    @Autowired
    private LeaderboardKeyHandler leaderboardKeyHandler;

    @Override
    public final void handleLeaderboards(Configuration configuration) {
        StopWatch watch = new StopWatch();
        watch.start();

        LeaderboardConfiguration leaderboardConfiguration = configurationLoader.getConfiguration(configuration.getConfigurationName());
        int NUM_OF_THREADS = Math.min(Math.max(2, Runtime.getRuntime().availableProcessors()), leaderboardConfiguration.getLeaderboards().getLeaderboard().size());
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_OF_THREADS);
        log.info(String.format("Using %s threads to gather results...", NUM_OF_THREADS));
        Map<String, Future<List<TopPlayer>>> futureMap = new HashMap<String, Future<List<TopPlayer>>>();

        log.info(String.format("Loaded configuration %s...", leaderboardConfiguration.getName()));

        for (Leaderboard board : leaderboardConfiguration.getLeaderboards().getLeaderboard()) {
            // open stream to source
            String inputSourceURI = board.getInputUri();
            InputSourceHandler source = inputSourceHandlerFactory.getInputSourceHandler(inputSourceURI);
            InputStream inputStream = source.openStream(inputSourceURI);
            log.info(String.format("Read input from %s using handler %s", inputSourceURI, source));

            // read from source and generate meaningful representation of data
            InputAnalyzer analyzer = SpringApplicationContext.getBean(board.getInputAnalyzer(), InputAnalyzer.class);
            analyzer.configure(inputStream, board);
            futureMap.put(leaderboardKeyHandler.encodeKey(board), executorService.submit(analyzer));
        }

        // shutdown executor service gracefully
        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }

        // gather all results from the invoked threads
        long numOfTopPlayersProcessed = 0;
        ConcurrentMap<String, List<TopPlayer>> topPlayersPerRegion = new ConcurrentHashMap<String, List<TopPlayer>>();
        for (Map.Entry<String, Future<List<TopPlayer>>> topPlayer : futureMap.entrySet()) {
            Leaderboard board = leaderboardKeyHandler.decodeKey(topPlayer.getKey());
            try {
                List<TopPlayer> topPlayerList = topPlayer.getValue().get();
                topPlayersPerRegion.put(topPlayer.getKey(), topPlayerList);
                numOfTopPlayersProcessed += topPlayerList.size();
            } catch (InterruptedException e) {
                throw new IllegalStateException(String.format("Thread performing data analysis from input source %s using analyzer %s was interrupted!!!", board.getInputUri(), board.getInputAnalyzer()), e);
            } catch (ExecutionException e) {
                throw new IllegalStateException(String.format("Failure during data analysis from input source %s using analyzer %s",board.getInputUri(), board.getInputAnalyzer()), e);
            }
        }
        watch.stop();

        log.info(String.format("Accumulated total %s Top players from %s different sources in %s seconds", numOfTopPlayersProcessed, leaderboardConfiguration.getLeaderboards().getLeaderboard().size(), watch.getTotalTimeSeconds()));

        // handle the data from all sources
        Merger<TopPlayer> merger = mergerMap.get(leaderboardConfiguration.getMerger());
        List<TopPlayer> finalList = merger.merge(topPlayersPerRegion);
        log.info(String.format("Handled data from all sources using merger %s", leaderboardConfiguration.getMerger()));

        // publish the final results
        Publisher publisher = publishersMap.get(leaderboardConfiguration.getPublisher());
        publisher.publish(finalList, configuration);
        log.info(String.format("Published results using publisher %s", leaderboardConfiguration.getPublisher()));
    }
}
