package se.yrgo.deb.entity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "highscores")
public class Highscores {
    private List<Highscore> scores;

    public Highscores() {
        this.scores = new ArrayList<>();
    }

    public void add(Highscore hs) {
        scores.add(hs);
    }

    @XmlElementRef
    public List<Highscore> getHighscores() {
        return List.copyOf(scores);
    }
}
