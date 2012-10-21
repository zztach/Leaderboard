package org.zisist.conf;

import org.zisist.model.xml.LeaderboardConfiguration;

import java.util.List;

/**
 * Created by zis.tax@gmail.com on 07/10/2012 at 11:41 PM
 */
public interface ConfigurationLoader {
    LeaderboardConfiguration getConfiguration(String name);
    List<LeaderboardConfiguration> getAvailableConfigurations();
}
