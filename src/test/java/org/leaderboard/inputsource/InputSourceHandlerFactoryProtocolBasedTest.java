package org.leaderboard.inputsource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.zisist.leaderboard.inputsourcehandler.InputSourceHandler;
import org.zisist.leaderboard.inputsourcehandler.impl.FileInputSourceHandler;
import org.zisist.leaderboard.inputsourcehandler.impl.HttpInputSourceHandler;
import org.zisist.leaderboard.inputsourcehandler.impl.InputSourceHandlerFactoryProtocolBased;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zis.tax@gmail.com on 17/10/2012 at 12:17 AM
 */
public class InputSourceHandlerFactoryProtocolBasedTest {

    private InputSourceHandlerFactoryProtocolBased inputSourceHandlerFactory;
    private Map<String, InputSourceHandler> inputSourceHandlersMap;

    @Before
    public void init() {
        inputSourceHandlersMap = new HashMap<String, InputSourceHandler>();
        inputSourceHandlersMap.put("httpInputSourceHandler", new HttpInputSourceHandler());
        inputSourceHandlersMap.put("fileInputSourceHandler", new FileInputSourceHandler());
        inputSourceHandlerFactory = new InputSourceHandlerFactoryProtocolBased();
        inputSourceHandlerFactory.setInputSourceHandlers(inputSourceHandlersMap);
    }

    @Test
    public void testHttpProtocolHandler() {
        InputSourceHandler inputSourceHandler = inputSourceHandlerFactory.getInputSourceHandler("http://google.com");
        Assert.assertTrue("InputSourceHandler should have been HttpInputSourceHandler", inputSourceHandler instanceof HttpInputSourceHandler);
    }

    @Test
    public void testFileProtocolHandler() {
        InputSourceHandler inputSourceHandler = inputSourceHandlerFactory.getInputSourceHandler("C:\\leaders.txt");
        Assert.assertTrue("InputSourceHandler should have been FileInputSourceHandler", inputSourceHandler instanceof FileInputSourceHandler);
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void testHttpsProtocolHandler() {
        inputSourceHandlerFactory.getInputSourceHandler("https://google.com");
        Assert.fail("Method should have thrown an UnsupportedOperationException");
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void testFtpProtocolHandler() {
        inputSourceHandlerFactory.getInputSourceHandler("ftp://server.gr/pub");
        Assert.fail("Method should have thrown an UnsupportedOperationException");
    }
}
