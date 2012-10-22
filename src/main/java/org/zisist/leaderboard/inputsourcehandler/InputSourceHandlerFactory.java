package org.zisist.leaderboard.inputsourcehandler;

/**
 * Responsible for retrieving the appropriate InputSourceHandler implementation <br>
 * based on the provided <i>uri</i><br>
 * 
 * Created by zis.tax@gmail.com on 12/10/2012 at 1:10 AM
 */
public interface InputSourceHandlerFactory {
    InputSourceHandler getInputSourceHandler(String uri);
}
