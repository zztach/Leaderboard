package org.leaderboard;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zisist.leaderboard.LeaderboardsHandlerImpl;
import org.zisist.model.Configuration;

/**
 * Created by zisis@gmail.com on 17/10/2012 at 12:51 AM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@Ignore
public class LeaderboardsHandlerImplIntegrationTest {

    @Autowired
    private LeaderboardsHandlerImpl leaderboardsHandler;
    private Configuration configuration;

    @Before
    public void init() {
        configuration = new Configuration("test1", "leaderboard.zt@gmail.com");
    }

    @Test
    public void testConfiguration_1() {
        leaderboardsHandler.handleLeaderboards(configuration);
    }

    @Test
    public void testConfiguration_2() {
        configuration = new Configuration("test2", "leaderboard.zt@gmail.com");
        leaderboardsHandler.handleLeaderboards(configuration);
    }
}
