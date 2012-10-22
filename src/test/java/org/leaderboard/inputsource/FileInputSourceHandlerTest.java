package org.leaderboard.inputsource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.zisist.leaderboard.inputsourcehandler.impl.FileInputSourceHandler;

import java.io.InputStream;

/**
 * Created by zis.tax@gmail.com on 17/10/2012 at 12:57 AM
 */
public class FileInputSourceHandlerTest {
    private FileInputSourceHandler fileInputSourceHandler;
    
    @Before
    public void init() {
        fileInputSourceHandler = new FileInputSourceHandler();
    }
    
    @Test
    public void testExistingFileOpenSuccess() {
        InputStream inStream = fileInputSourceHandler.openStream("leaders.txt");
        Assert.assertTrue("inStream should be != null", inStream != null);
    }

   @Test(expected = IllegalArgumentException.class)
    public void testNonExistingFileOpenFail() {
        fileInputSourceHandler.openStream("no_such_file.txt");
        Assert.fail("File does not exist!!!");
    }

   @Test(expected = IllegalArgumentException.class)
    public void testNonExistingFileAbsolutePathOpenFail() {
        fileInputSourceHandler.openStream("/no_such_file.txt");
        Assert.fail("File does not exist!!!");
    }
}
