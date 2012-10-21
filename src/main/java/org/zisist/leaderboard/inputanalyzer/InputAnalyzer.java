package org.zisist.leaderboard.inputanalyzer;

import org.zisist.model.TopPlayer;

import java.io.InputStream;
import java.util.List;

/**
 * Created by zis.tax@gmail.com on 03/10/2012 at 11:56 PM
 */
public interface InputAnalyzer {
    /**
     * Responsible for parsing the data coming from the <i>inStream</i><br>
     * and producing a List of TopPlayer objects for further processing<br>
     *
     * @param inStream the inputStream which contains the list of top players
     * @return a List of TopPlayer objects
     */
    List<TopPlayer> getTopPlayersFromInputSource(final InputStream inStream);
}
