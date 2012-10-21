package org.zisist.conf.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zisist.conf.ConfigurationLoader;
import org.zisist.conf.XMLConverter;
import org.zisist.model.xml.LeaderboardConfiguration;
import org.zisist.model.xml.LeaderboardConfigurations;

import java.io.IOException;
import java.util.List;

/**
 * Responsible for loading the available configurations found <br>
 * inside leaderboard_conf.xml<br><br>
 * Created by zis.tax@gmail.com on 07/10/2012 at 11:43 PM
 */
@Service("leaderboardConfigurationLoader")
public class LeaderboardConfigurationLoader implements ConfigurationLoader {

    private final String LEADERBOARD_CONF_FILE = "/leaderboard_conf.xml";
    private LeaderboardConfigurations leaderboardconfigurations;

    /**
     * Loads all available leaderboard configurations only once at startup
     *
     * @param xmlConverter service needed to unmarshal the XML to an object
     */
    @Autowired
    public LeaderboardConfigurationLoader(XMLConverter xmlConverter) {
        try {
            String path = this.getClass().getResource(LEADERBOARD_CONF_FILE).getPath();
            leaderboardconfigurations = (LeaderboardConfigurations) xmlConverter.convertFromXMLToObject(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a specific LeaderboardConfiguration by its <i>name</i><br>
     *
     * @param name the name of the configuration
     * @return the specific LeaderboardConfiguration object identified by <i>name</i>
     */
    @Override
    public LeaderboardConfiguration getConfiguration(String name) {
        for (LeaderboardConfiguration conf : leaderboardconfigurations.getLeaderboardConfiguration()) {
            if (conf.getName().equals(name)) {
                return conf;
            }
        }
        return null;
    }

    /**
     * Returns all available configurations from leaderboard_conf.xml
     *
     * @return a List of all the available configurations
     */
    @Override
    public List<LeaderboardConfiguration> getAvailableConfigurations() {
        return leaderboardconfigurations.getLeaderboardConfiguration();
    }
}
