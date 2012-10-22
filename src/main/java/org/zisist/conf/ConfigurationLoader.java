package org.zisist.conf;

import org.zisist.model.xml.LeaderboardConfiguration;

import java.util.List;

/**
 * Responsible for loading the cofigurations needed to execute the algorithm<br>
 * that handled the leaderbords<br>
 * Created by zis.tax@gmail.com on 07/10/2012 at 11:41 PM
 */
public interface ConfigurationLoader {
    LeaderboardConfiguration getConfiguration(String name);
    List<LeaderboardConfiguration> getAvailableConfigurations();
}
