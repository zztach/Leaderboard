package org.conf;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zisist.conf.impl.LeaderboardConfigurationLoader;
import org.zisist.model.xml.LeaderboardConfiguration;

import java.util.List;

/**
 * Created by zisis@gmail.com on 17/10/2012 at 12:39 AM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class LeaderboardConfigurationLoaderTest {

    @Autowired
    private LeaderboardConfigurationLoader leaderboardConfigurationLoader;

    @Test
    public void testConfigurationRetrieval() {
        LeaderboardConfiguration conf = leaderboardConfigurationLoader.getConfiguration("test1");
        Assert.assertEquals("Name should have been test1", "test1", conf.getName());
        Assert.assertEquals("Publisher should have been emailPublisher", "emailPublisher", conf.getPublisher());
    }

    @Test
    public void testAvailableConfigurations() {
        List<LeaderboardConfiguration> configurations = leaderboardConfigurationLoader.getAvailableConfigurations();
        Assert.assertEquals("The list of configurations should have been of size 2", 2, configurations.size());
    }
}
