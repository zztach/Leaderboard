package org.zisist.leaderboard.inputanalyzer.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;
import org.zisist.leaderboard.inputanalyzer.InputAnalyzer;
import org.zisist.model.TopPlayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * We are assuming that the European server retrieves data concerning Top Players<br>
 * from a web service<br>
 *
 * Created by zis.tax@gmail.com on 03/10/2012 at 11:58 PM
 */
@Service("europeanServerAnalyzer")
public class EuropeanServerAnalyzer implements InputAnalyzer {

    private Gson gson = new Gson();

    @Override
    @SuppressWarnings("unchecked")
    public List<TopPlayer> getTopPlayersFromInputSource(InputStream inStream) {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
        Type listType = new TypeToken<List<TopPlayer>>() {}.getType();

        List<TopPlayer> topPlayerList = gson.fromJson(reader, listType);
        try {
            reader.close();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to close Buffered Reader inside EuropeanServerAnalyzer");
        }
        return topPlayerList;
    }
}
