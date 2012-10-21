package org.zisist.leaderboard.inputanalyzer.impl;

import org.springframework.stereotype.Service;
import org.zisist.leaderboard.inputanalyzer.InputAnalyzer;
import org.zisist.model.TopPlayer;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * We are assuming that the AmericanServer receives data from a text file<br>
 * Created by zis.tax@gmail.com on 03/10/2012 at 11:59 PM
 */
@Service("americanServerAnalyzer")
public class AmericanServerAnalyzer implements InputAnalyzer {

    @Override
    public List<TopPlayer> getTopPlayersFromInputSource(InputStream inStream) {
        List<TopPlayer> topPlayersList = new ArrayList<TopPlayer>();

        Scanner scanner = new Scanner(inStream);
        while (scanner.hasNextLine()) {
            String name = scanner.next();
            long points = scanner.nextLong();
            long kills =  scanner.nextLong();
            String country = scanner.next();
            topPlayersList.add(new TopPlayer(name, points, kills, country));
        }
        scanner.close();

        return topPlayersList;
    }
}
