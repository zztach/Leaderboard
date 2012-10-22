package org.zisist.leaderboard.inputsourcehandler;

import java.io.InputStream;

/**
 * An interface providing InputStream to different types of source locations (e.g. file, http)<br>
 * 
 * Created by zis.tax@gmail.com on 12/10/2012 at 1:06 AM
 */
public interface InputSourceHandler {
    /**
     * Opens a stream for reading to the <i>sourceLocation</i><br>
     * 
     * @param sourceLocation the uri describing the location of the input source
     * @return an input stream for the <i>sourceLocation</i>
     */
    InputStream openStream(String sourceLocation);
}
