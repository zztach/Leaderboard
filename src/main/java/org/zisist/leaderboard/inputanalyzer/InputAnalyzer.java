package org.zisist.leaderboard.inputanalyzer;

import org.zisist.model.TopPlayer;
import org.zisist.model.xml.Leaderboard;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by zis.tax@gmail.com on 03/10/2012 at 11:56 PM
 */
public interface InputAnalyzer extends Callable<List<TopPlayer>> {

    /**
     * Responsible for parsing the data from inputStream passed through the configure function
     * and producing a List of TopPlayer objects for further processing<br>
     *
     * @return a List of TopPlayer objects
     */
    @Override
    List<TopPlayer> call() throws Exception;

    /**
     * Set the initial configuration that is needed for <i>InputAnalyzer</i> in order
     * to perform the analysis on the data<br>
     *
     * @param inputStream the inputStream which contains the list of top players
     * @param board the configuration
     */
    void configure(InputStream inputStream, Leaderboard board);
}
