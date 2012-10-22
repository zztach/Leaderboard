package org.leaderboard.merger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.zisist.leaderboard.merger.impl.TopPointsPlayersMerger;
import org.zisist.model.TopPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zis.tax@gmail.com on 17/10/2012 at 1:07 AM
 */
public class TopPointsPlayerMergerTest {
    private TopPointsPlayersMerger topPointsPlayersMerger;
    private Map<String, List<TopPlayer>> topPlayersMap;
    
    @Before
    public void init() {
        topPointsPlayersMerger = new TopPointsPlayersMerger();
        topPlayersMap = new HashMap<String, List<TopPlayer>>();
        List<TopPlayer> europeTopPlayers = new ArrayList<TopPlayer>();
        europeTopPlayers.add(new TopPlayer("Adam", 100, 40, "GB"));
        europeTopPlayers.add(new TopPlayer("Seb", 200, 110, "FR"));
        List<TopPlayer> americaTopPlayers = new ArrayList<TopPlayer>();
        americaTopPlayers.add(new TopPlayer("Tom", 300, 200, "US"));
        americaTopPlayers.add(new TopPlayer("Neithan", 150, 80, "US"));
        topPlayersMap.put("EUROPE", europeTopPlayers);
        topPlayersMap.put("AMERICA", americaTopPlayers);
    }
    
    @Test
    public void testTopPlayer() {
        List<TopPlayer> topPlayers = topPointsPlayersMerger.merge(topPlayersMap);
        Assert.assertEquals("First should have been Tom", "Tom", topPlayers.get(0).getName());
        Assert.assertEquals("Last should have been Adam", "Adam", topPlayers.get(3).getName());
    }
}
