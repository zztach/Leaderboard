package org.zisist.leaderboard;

import org.zisist.model.xml.Leaderboard;

/**
 * Service tha generates a unique key for a Leaderboard object (that can be used as a key to a Map for example)<br>
 * and vice-versa<br>
 * Created by zis.tax@gmail.com on 28/10/2012 at 2:49 AM
 */
public interface LeaderboardKeyHandler {
    /**
     * Given a Leaderboard object it returns a key as a String<br>
     *
     * @param leaderboard the Leaderboard object
     * @return the key that is generated based on the <i>leaderboard</i> object
     */
    String encodeKey(Leaderboard leaderboard);

    /**
     * Given a key it generates a Leaderboard object. This is the reverse operation of <i>encodeKey</i> function<br>
     *
     * @param key the key from which the Leaderboard object will be created
     * @return a Leaderboard object based on <i>key</i>
     */
    Leaderboard decodeKey(String key);
}
