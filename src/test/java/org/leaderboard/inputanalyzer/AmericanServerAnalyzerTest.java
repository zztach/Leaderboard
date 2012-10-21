package org.leaderboard.inputanalyzer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.zisist.leaderboard.inputanalyzer.impl.AmericanServerAnalyzer;
import org.zisist.model.TopPlayer;

import java.util.List;

/**
 * Created by zis.tax@gmail.com on 04/10/2012 at 12:11 AM
 */
public class AmericanServerAnalyzerTest {
    private AmericanServerAnalyzer americanServerAnalyzer;

    @Before
    public void init() {
        americanServerAnalyzer = new AmericanServerAnalyzer();
    }

    @Test
    public void testAmericanServer() {
        List<TopPlayer> topPlayerList = americanServerAnalyzer.getTopPlayersFromInputSource(this.getClass().getResourceAsStream("/leaders.txt"));
        Assert.assertTrue("The returned list must be of size 3!", topPlayerList.size() == 3);
    }
}
