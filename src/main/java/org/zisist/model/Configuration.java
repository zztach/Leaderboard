package org.zisist.model;

/**
 * This class holds the configuration needed by the algorithm<br>
 * inside {@link org.zisist.leaderboard.LeaderboardHandler}
 *
 * Created by zis.tax@gmail.com on 12/10/2012 at 2:09 AM
 */
public class Configuration {
    private final String configurationName;
    private final String email;

    public Configuration(String configurationName, String email) {
        this.configurationName = configurationName;
        this.email = email;
    }

    public String getConfigurationName() {
        return configurationName;
    }

    public String getEmail() {
        return email;
    }
}
