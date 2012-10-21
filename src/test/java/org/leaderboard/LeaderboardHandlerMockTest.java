package org.leaderboard;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.zisist.conf.ConfigurationLoader;
import org.zisist.leaderboard.LeaderboardsHandlerImpl;
import org.zisist.leaderboard.inputanalyzer.InputAnalyzer;
import org.zisist.leaderboard.inputanalyzer.impl.EuropeanServerAnalyzer;
import org.zisist.leaderboard.inputsourcehandler.InputSourceHandler;
import org.zisist.leaderboard.inputsourcehandler.InputSourceHandlerFactory;
import org.zisist.leaderboard.inputsourcehandler.impl.FileInputSourceHandler;
import org.zisist.leaderboard.merger.Merger;
import org.zisist.leaderboard.merger.impl.TopPointsPlayersMerger;
import org.zisist.leaderboard.publisher.Publisher;
import org.zisist.leaderboard.publisher.impl.EmailPublisher;
import org.zisist.model.Configuration;
import org.zisist.model.TopPlayer;
import org.zisist.model.xml.Leaderboard;
import org.zisist.model.xml.LeaderboardConfiguration;
import org.zisist.model.xml.Leaderboards;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zisis@gmail.com on 21/10/2012 at 7:21 PM
 */
public class LeaderboardHandlerMockTest {
    // service under test
    private LeaderboardsHandlerImpl leaderboardsHandler;

    // Mock services
    private ConfigurationLoader configurationLoaderMock;
    private InputAnalyzer inputAnalyzerMock;
    private Map<String, InputAnalyzer> inputAnalyzersMapMock = new HashMap<String, InputAnalyzer>();
    private InputSourceHandlerFactory inputSourceHandlerFactoryMock;
    private Merger<TopPlayer> topPlayerMergerMock;
    private Map<String, Merger<TopPlayer>> mergerMapMock = new HashMap<String, Merger<TopPlayer>>();
    private EmailPublisher emailPublisherMock;
    private Map<String, Publisher> publishersMapMock = new HashMap<String, Publisher>();

    // helper objects
    private Configuration configuration;
    private InputSourceHandler inputSourceHandlerMock;


    @Before
    public void init() {
        inputSourceHandlerMock = EasyMock.createMock(FileInputSourceHandler.class);
        configurationLoaderMock = EasyMock.createMock(ConfigurationLoader.class);
        inputAnalyzerMock = EasyMock.createMock(EuropeanServerAnalyzer.class);        
        inputAnalyzersMapMock.put("europeanServerAnalyzer", inputAnalyzerMock);
        inputSourceHandlerFactoryMock = EasyMock.createMock(InputSourceHandlerFactory.class);
        topPlayerMergerMock = EasyMock.createMock(TopPointsPlayersMerger.class);
        mergerMapMock.put("topPointsPlayersMerger", topPlayerMergerMock);
        emailPublisherMock = EasyMock.createMock(EmailPublisher.class);
        publishersMapMock.put("emailPublisher", emailPublisherMock);
        
        leaderboardsHandler = new LeaderboardsHandlerImpl();
        
        leaderboardsHandler.setConfigurationLoader(configurationLoaderMock);
        leaderboardsHandler.setInputAnalyzersMap(inputAnalyzersMapMock);
        leaderboardsHandler.setInputSourceHandlerFactory(inputSourceHandlerFactoryMock);
        leaderboardsHandler.setMergerMap(mergerMapMock);
        leaderboardsHandler.setPublishersMap(publishersMapMock);

        // helper objects
        configuration = new Configuration("test1", "leaderboard.zt@gmail.com");
    }

    @Test
    public void testSimpleConfigurationTest1UsingMocks() throws Exception {
        final String INPUT_URI = this.getClass().getResource("/leaders.txt").getPath();

        // setup the XML configuration in terms of objects
        LeaderboardConfiguration leaderboardConfiguration = new LeaderboardConfiguration();
        leaderboardConfiguration.setName("test1");
        leaderboardConfiguration.setMerger("topPointsPlayersMerger");
        leaderboardConfiguration.setPublisher("emailPublisher");
        Leaderboards leaderboards = new Leaderboards();
        List<Leaderboard> leaderboardsList = new ArrayList<Leaderboard>();
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.setInputAnalyzer("europeanServerAnalyzer");
        leaderboard.setInputUri(INPUT_URI);
        leaderboardsList.add(leaderboard);
        leaderboards.setLeaderboard(leaderboardsList);
        leaderboardConfiguration.setLeaderboards(leaderboards);

        InputStream inputStream = new FileInputStream(INPUT_URI);

        // setup list of players
        List<TopPlayer> topPlayersList = new ArrayList<TopPlayer>();
        topPlayersList.add(new TopPlayer("JOHN", 100, 10, "GR"));
        topPlayersList.add(new TopPlayer("TIM", 200, 15, "NE"));
        Map<String, List<TopPlayer>> topPlayersMap = new HashMap<String, List<TopPlayer>>();
        topPlayersMap.put("europeanServerAnalyzer", topPlayersList);

        // setup ordered list of players, the expected result of the merger
        List<TopPlayer> orderedTopPlayers = new ArrayList<TopPlayer>();
        topPlayersList.add(new TopPlayer("TIM", 200, 15, "NE"));
        topPlayersList.add(new TopPlayer("JOHN", 100, 10, "GR"));

        EasyMock.expect(configurationLoaderMock.getConfiguration("test1")).andReturn(leaderboardConfiguration);
        EasyMock.expect(inputSourceHandlerFactoryMock.getInputSourceHandler(INPUT_URI)).andReturn(inputSourceHandlerMock);
        EasyMock.expect(inputSourceHandlerMock.openStream(INPUT_URI)).andReturn(inputStream);
        EasyMock.expect(inputAnalyzerMock.getTopPlayersFromInputSource(inputStream)).andReturn(topPlayersList);        
        EasyMock.expect(topPlayerMergerMock.merge(topPlayersMap)).andReturn(orderedTopPlayers);
        emailPublisherMock.publish(orderedTopPlayers, configuration);
        EasyMock.expectLastCall();

        EasyMock.replay(configurationLoaderMock, inputAnalyzerMock, inputSourceHandlerFactoryMock,
                        inputSourceHandlerMock, topPlayerMergerMock, emailPublisherMock);
        
        leaderboardsHandler.handleLeaderboards(configuration);

        EasyMock.verify(configurationLoaderMock, inputAnalyzerMock, inputSourceHandlerFactoryMock,
                        inputSourceHandlerMock, topPlayerMergerMock, emailPublisherMock);
    }
}
