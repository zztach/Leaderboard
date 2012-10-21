package org.zisist.model;

import java.util.Comparator;

/**
 * Created by zis.tax@gmail.com on 08/10/2012 at 2:58 AM
 */
public class TopPlayerComparatorByPoints implements Comparator<TopPlayer> {
    @Override
    public int compare(TopPlayer topPlayer, TopPlayer topPlayer1) {
        if (topPlayer.getPoints() < topPlayer1.getPoints()) {
            return 1;
        } else if (topPlayer.getPoints() > topPlayer1.getPoints()) {
            return -1;
        }
        return 0;
    }
}
