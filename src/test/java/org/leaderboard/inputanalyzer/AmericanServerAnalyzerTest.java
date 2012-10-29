package org.leaderboard.inputanalyzer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.zisist.leaderboard.inputanalyzer.impl.AmericanServerAnalyzer;
import org.zisist.model.TopPlayer;
import org.zisist.model.xml.Leaderboard;

import java.io.InputStream;
import java.util.List;

/**
 * Created by zis.tax@gmail.com on 04/10/2012 at 12:11 AM
 */
public class AmericanServerAnalyzerTest {
    private AmericanServerAnalyzer americanServerAnalyzer;
    private Leaderboard leaderboard;

    @Before
    public void init() {
        americanServerAnalyzer = new AmericanServerAnalyzer();
        leaderboard = new Leaderboard();
        leaderboard.setInputAnalyzer("americanServerAnalyzer");
        leaderboard.setInputUri("http://america.server.com/leaders.txt");
    }

    @Test
    public void testAmericanServer() throws Exception {
        InputStream inputStream = this.getClass().getResourceAsStream("/leaders.txt");
        americanServerAnalyzer.configure(inputStream, leaderboard);
        List<TopPlayer> topPlayerList = americanServerAnalyzer.call();
        Assert.assertTrue("The returned list must be of size 3!", topPlayerList.size() == 3);
    }
}
