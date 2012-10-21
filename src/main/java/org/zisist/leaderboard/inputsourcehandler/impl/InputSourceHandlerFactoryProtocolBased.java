package org.zisist.leaderboard.inputsourcehandler.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zisist.leaderboard.inputsourcehandler.InputSourceHandler;
import org.zisist.leaderboard.inputsourcehandler.InputSourceHandlerFactory;

import java.util.Map;

/**
 * Selects the proper InputSourceHandler implementation based on the protocol<br>
 * defined at the beginning of the URI<br>
 *
 * Created by zis.tax@gmail.com on 12/10/2012 at 1:11 AM
 */
@Service("inputSourceHandlerFactoryProtocolBased")
public class InputSourceHandlerFactoryProtocolBased implements InputSourceHandlerFactory {
    private final String PROTOCOL_HTTP = "http://";
    private final String PROTOCOL_HTTPS = "https://";
    private final String PROTOCOL_FTP = "ftp://";

    @Autowired
    private Map<String, InputSourceHandler> inputSourceHandlers;

    public void setInputSourceHandlers(Map<String, InputSourceHandler> inputSourceHandlers) {
        this.inputSourceHandlers = inputSourceHandlers;
    }

    @Override
    public InputSourceHandler getInputSourceHandler(String uri) {
        if (uri.toLowerCase().trim().startsWith(PROTOCOL_HTTP)) {
            return inputSourceHandlers.get("httpInputSourceHandler");
        } else if (uri.toLowerCase().trim().startsWith(PROTOCOL_HTTPS)) {
            throw new UnsupportedOperationException("HTTPS Protocol is currently not supported. You need to provide an additional implementation of InputSource interface");
        } else if (uri.toLowerCase().trim().startsWith(PROTOCOL_FTP)) {
            throw new UnsupportedOperationException("FTP Protocol is currently not supported. You need to provide an additional implementation of InputSource interface");
        } else {
            return inputSourceHandlers.get("fileInputSourceHandler");
        }
    }

}
