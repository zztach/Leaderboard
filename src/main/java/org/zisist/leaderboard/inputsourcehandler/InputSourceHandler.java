package org.zisist.leaderboard.inputsourcehandler;

import java.io.InputStream;

/**
 * Created by zis.tax@gmail.com on 12/10/2012 at 1:06 AM
 */
public interface InputSourceHandler {
    InputStream openStream(String sourceLocation);
}
