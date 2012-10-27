package org.zisist.leaderboard.merger.impl;

import org.springframework.stereotype.Service;
import org.zisist.leaderboard.merger.Merger;
import org.zisist.model.TopPlayer;
import org.zisist.model.TopPlayerComparatorByPoints;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Merges the results from all the leaderboard servers around the world and provides<br>
 * a list with the Top players of the world<br>
 * Created by zis.tax@gmail.com on 08/10/2012 at 2:53 AM
 */
@Service("topPointsPlayersMerger")
public class TopPointsPlayersMerger implements Merger<TopPlayer> {

    @Override
    public List<TopPlayer> merge(Map<String, List<TopPlayer>> data) {
        List<TopPlayer> finalList = new ArrayList<TopPlayer>();
        for(List<TopPlayer> topPlayerList : data.values()) {
            finalList.addAll(topPlayerList);
        }
        Collections.sort(finalList, new TopPlayerComparatorByPoints());

        return finalList.subList(0,Math.min(10, finalList.size()));
    }
}
