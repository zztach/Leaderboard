package org.zisist.leaderboard.publisher;

import org.zisist.model.Configuration;
import org.zisist.model.TopPlayer;

import java.util.List;

/**
 * Created by zis.tax@gmail.com on 12/10/2012 at 1:14 AM
 */
public interface Publisher {
    /**
     *
     * Responsible for publishing to the outside world the results of our<br>
     * algorithm<br>
     *
     * @param topPlayerList the List<TopPlayers> that must be published
     * @param conf Configuration object with configuration needed by publishers
     */
    void publish(List<TopPlayer> topPlayerList, Configuration conf);
}
