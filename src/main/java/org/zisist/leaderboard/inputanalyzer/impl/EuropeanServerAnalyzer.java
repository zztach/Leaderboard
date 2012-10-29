package org.zisist.leaderboard.inputanalyzer.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.zisist.leaderboard.inputanalyzer.InputAnalyzer;
import org.zisist.model.TopPlayer;
import org.zisist.model.xml.Leaderboard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * We are assuming that the European server retrieves data concerning Top Players<br>
 * from a web service<br>
 * This bean is a prototype (new instance for each thread) since there is a need for <br>
 * a separate configuration on each call<br>
 *
 * Created by zis.tax@gmail.com on 03/10/2012 at 11:58 PM
 */
@Service("europeanServerAnalyzer")
@Scope("prototype")
public class EuropeanServerAnalyzer implements InputAnalyzer {
    private static Logger log = Logger.getLogger(EuropeanServerAnalyzer.class);
    private Gson gson = new Gson();
    private InputStream inputStream;
    private Leaderboard board;

    @Override
    public void configure(InputStream inputStream, Leaderboard board) {
        this.inputStream = inputStream;
        this.board = board;
    }

    @Override
    public List<TopPlayer> call() throws Exception {
        log.info(String.format("Started analyzing input from %s using analyzer %s", board.getInputUri(), board.getInputAnalyzer()));
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        Type listType = new TypeToken<List<TopPlayer>>() {}.getType();

        List<TopPlayer> topPlayerList = gson.fromJson(reader, listType);
        try {
            reader.close();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to close Buffered Reader inside EuropeanServerAnalyzer");
        }
        log.info(String.format("Analyzed input from %s using analyzer %s", board.getInputUri(), board.getInputAnalyzer()));
        return topPlayerList;
    }
}
