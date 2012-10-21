package org.zisist.leaderboard;

import org.zisist.model.Configuration;

/**
 * Created by zis.tax@gmail.com on 04/10/2012 at 12:31 AM
 */
public interface LeaderboardsHandler {
    /**
     * This is the heart of the application. It defines the algorithm used to produce the results<br><br>
     *
     * 1. Loads the proper configuration from leaderboard_conf.xml<br>
     * 2. Parses all available leaderboards and gathers the results in a common data structure<br>
     * 3. Merges all the results gathered from step 2<br>
     * 4. Publishes the results calculated in step 3<br>
     *
     * @param configuration object holding configuration
     */
    void handleLeaderboards(Configuration configuration);
}
