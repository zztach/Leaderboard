package org.leaderboard.inputsource;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.zisist.leaderboard.inputsourcehandler.impl.HttpInputSourceHandler;
import org.zisist.model.TopPlayer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by zis.tax@gmail.com on 03/10/2012 at 2:03 AM
 */
@Ignore
public class HttpInpuSourceRESTTest {
    private HttpInputSourceHandler httpInputSource;
    private Gson gson = new Gson();

    @Before
    public void init() {
        httpInputSource = new HttpInputSourceHandler();
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void testRESTAPI() {
        InputStream inStream = httpInputSource.openStream("http://localhost:8080/leaderboard/api/players/leaders");
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));

//        List<TopPlayer> topPlayerList = gson.fromJson(reader, newpackage TypeToken<List<TopPlayer>>(){}.getType());
        List<TopPlayer> topPlayerList = gson.fromJson(reader, List.class);
        System.out.println("sadasd");
    }
}
