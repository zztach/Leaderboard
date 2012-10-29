package org.leaderboard;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.zisist.leaderboard.LeaderboardHandler;
import org.zisist.leaderboard.LeaderboardKeyHandler;
import org.zisist.leaderboard.impl.DefaultLeaderboardKeyHandler;
import org.zisist.model.xml.Leaderboard;

/**
 * Created by zisis@gmail.com on 30/10/2012 at 1:14 AM
 */
public class LeaderboardKeyHandlerTest {
    private LeaderboardKeyHandler leaderboardKeyHandler;
    
    @Before
    public void init() {
        leaderboardKeyHandler = new DefaultLeaderboardKeyHandler();
    }
    
    @Test
    public void testEncoding() {
        Leaderboard leaderboard = new Leaderboard("testAnalyzer", "http://inputdata.com");
        String key = leaderboardKeyHandler.encodeKey(leaderboard);
        Assert.assertTrue("Key should have been testAnalyzer_:_http://inputdata.com" , key.equals("testAnalyzer_:_http://inputdata.com"));
        
    }

    @Test
    public void testDecoding() {
        Leaderboard leaderboard = leaderboardKeyHandler.decodeKey("testAnalyzer_:_http://inputdata.com");
        Assert.assertTrue("InputAnalyzer should have been 'testAnalyzer'" , leaderboard.getInputAnalyzer().equals("testAnalyzer"));
        Assert.assertTrue("InputUri should have been 'http://inputdata.com'" , leaderboard.getInputUri().equals("http://inputdata.com"));

    }

    @Test(expected = IllegalArgumentException.class)
    public void testDecodingFailure() {
        Leaderboard leaderboard = leaderboardKeyHandler.decodeKey("testAnalyzer_http://inputdata.com");

    }
}
