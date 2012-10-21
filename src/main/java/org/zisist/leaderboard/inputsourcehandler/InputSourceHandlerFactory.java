package org.zisist.leaderboard.inputsourcehandler;

/**
 * Created by zis.tax@gmail.com on 12/10/2012 at 1:10 AM
 */
public interface InputSourceHandlerFactory {
    InputSourceHandler getInputSourceHandler(String uri);
}
