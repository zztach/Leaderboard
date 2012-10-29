package org.zisist.leaderboard.inputanalyzer.impl;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.zisist.leaderboard.inputanalyzer.InputAnalyzer;
import org.zisist.model.TopPlayer;
import org.zisist.model.xml.Leaderboard;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentMap;

/**
 * We are assuming that the AmericanServer receives data from a text file<br>
 * This bean is a prototype (new instance for each thread) since there is a need for <br>
 * a separate configuration on each call<br>
 *
 * Created by zis.tax@gmail.com on 03/10/2012 at 11:59 PM
 */
@Service("americanServerAnalyzer")
@Scope("prototype")
public class AmericanServerAnalyzer implements InputAnalyzer {
    private static Logger log = Logger.getLogger(AmericanServerAnalyzer.class);
    private InputStream inputStream;
    private Leaderboard board;

    @Override
    public void configure(InputStream inputStream, Leaderboard board) {
        this.inputStream = inputStream;
        this.board = board;
    }

    @Override
    public List<TopPlayer> call() throws Exception {
        StopWatch watch = new StopWatch();
        watch.start();
        log.info(String.format("Started analyzing input from %s using analyzer %s", board.getInputUri(), board.getInputAnalyzer()));

        List<TopPlayer> topPlayersList = new ArrayList<TopPlayer>();

        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNextLine()) {
            String name = scanner.next();
            long points = scanner.nextLong();
            long kills =  scanner.nextLong();
            String country = scanner.next();
            topPlayersList.add(new TopPlayer(name, points, kills, country));
        }
        scanner.close();
        watch.stop();
        log.info(String.format("Analyzed input from %s using analyzer %s in %s seconds", board.getInputUri(), board.getInputAnalyzer(), watch.getTotalTimeSeconds()));
        return topPlayersList;
    }
}
