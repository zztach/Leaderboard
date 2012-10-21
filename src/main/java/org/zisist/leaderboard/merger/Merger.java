package org.zisist.leaderboard.merger;

import java.util.List;
import java.util.Map;

/**
 * Responsible for merging the results gathered from multiple Leaderboards
 *
 * Created by zis.tax@gmail.com on 08/10/2012 at 2:52 AM
 */
public interface Merger<E> {
    List<E> merge(Map<String, List<E>> data);
}
