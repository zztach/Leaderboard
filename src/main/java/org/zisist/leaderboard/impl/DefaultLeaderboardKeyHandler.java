package org.zisist.leaderboard.impl;

import org.springframework.stereotype.Service;
import org.zisist.leaderboard.LeaderboardKeyHandler;
import org.zisist.model.xml.Leaderboard;

import java.util.StringTokenizer;

/**
 * Created by zis.tax@gmail.com on 28/10/2012 at 2:51 AM
 */
@Service("defaultLeaderboardKeyHandler")
public class DefaultLeaderboardKeyHandler implements LeaderboardKeyHandler {
    private final String SEPARATOR = "_:_";

    @Override
    public String encodeKey(Leaderboard leaderboard) {
        return leaderboard.getInputAnalyzer() + SEPARATOR + leaderboard.getInputUri();
    }

    @Override
    public Leaderboard decodeKey(String key) {
        if (!key.contains(SEPARATOR)) {
            throw new IllegalArgumentException(String.format("%s is not a valid key since it does not contain %s separator!", key, SEPARATOR));
        }

        String[] tokens = key.split(SEPARATOR);
        String inputAnalyzer = tokens[0];
        String inputURI = tokens[1];
        return new Leaderboard(inputAnalyzer, inputURI);
    }
}
