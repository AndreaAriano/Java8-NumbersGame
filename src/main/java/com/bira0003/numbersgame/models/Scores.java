package com.bira0003.numbersgame.models;

import java.io.Serializable;
import java.util.HashMap;

public class Scores implements Serializable {
    private static final long serialVersionUID = -2595369126800750990L;

    private HashMap<String, PlayerScore> playerScore = new HashMap<>();

    public HashMap<String, PlayerScore> getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(HashMap<String, PlayerScore> playerScore) {
        this.playerScore = playerScore;
    }

    @Override
    public String toString() {
        return "Scores{" +
                "\n" + playerScore.toString() +
                '}';
    }
}
