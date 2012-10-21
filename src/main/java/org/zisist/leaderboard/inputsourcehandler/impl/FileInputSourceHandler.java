package org.zisist.leaderboard.inputsourcehandler.impl;

import org.springframework.stereotype.Service;
import org.zisist.leaderboard.inputsourcehandler.InputSourceHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

/**
 * Responsible for opening a stream to FILE location
 * Created by zis.tax@gmail.com on 12/10/2012 at 1:07 AM
 */
@Service("fileInputSourceHandler")
public class FileInputSourceHandler implements InputSourceHandler {
    @Override
    public InputStream openStream(String sourceLocation) {
        if (!sourceLocation.startsWith("/")) {
            URL url = this.getClass().getResource("/"+sourceLocation);
            if (url == null) {
                throw new IllegalArgumentException(String.format("'%s' is not a valid file path!", sourceLocation));
            }
            sourceLocation = url.getPath();
        }

        File file = new File(sourceLocation);
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(String.format("'%s' is not a valid file path!", sourceLocation), e);
        }
    }
}
