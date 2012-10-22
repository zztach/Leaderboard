package org.zisist.conf;

import org.zisist.model.xml.LeaderboardConfiguration;

import java.util.List;

/**
 * Responsible for loading the cofigurations needed to execute the algorithm<br>
 * that handled the leaderbords<br>
 * Created by zis.tax@gmail.com on 07/10/2012 at 11:41 PM
 */
public interface ConfigurationLoader {
    /**
     * Loads the configuration by name
     * @param name the distinct name of the configuration
     * @return a LeaderboardConfiguration object holding the configuration
     * /
    LeaderboardConfiguration getConfiguration(String name);
    
    /**
     * Loads all available configurations     
     * @return a List of all available LeaderboardConfiguration objects
     * /
    List<LeaderboardConfiguration> getAvailableConfigurations();
}
