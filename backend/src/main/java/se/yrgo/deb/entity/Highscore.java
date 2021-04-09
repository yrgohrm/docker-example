package se.yrgo.deb.entity;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "highscore")
public class Highscore {
    private int id;
    private String player;
    private int score;

    public Highscore() {}

    public Highscore(int id, String player, int score) {
        this.id = id;
        this.player = player;
        this.score = score;
    }

    @XmlAttribute
    public void setId(int id) {
        this.id = id;
    }

    @XmlAttribute(name = "score")
    public void setScore(int score) {
        this.score = score;
    }

    @XmlAttribute(name = "player")
    public void setPlayer(String player) {
        this.player = player;
    }

    public int getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public String getPlayer() {
        return player;
    }
}
