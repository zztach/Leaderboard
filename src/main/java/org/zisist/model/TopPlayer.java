package org.zisist.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents a player entity in a leaderboard
 * Created by zis.tax@gmail.com on 03/10/2012 at 1:38 AM
 */
@XmlRootElement
public class TopPlayer {
    private String name;
    private long points;
    private long kills;
    private String country;

    public TopPlayer() {}

    public TopPlayer(String name, long points, long kills, String country) {
        this.name = name;
        this.points = points;
        this.kills = kills;
        this.country = country;
    }


    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public long getPoints() {
        return points;
    }

    public long getKills() {
        return kills;
    }

    public void setKills(long kills) {
        this.kills = kills;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPoints(long points) {
        this.points = points;
    }
}
