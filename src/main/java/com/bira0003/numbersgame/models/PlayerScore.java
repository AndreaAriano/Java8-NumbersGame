package com.bira0003.numbersgame.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayerScore implements Serializable {
    private String date;

    private String playerInitials;

    private int pairs;

    private int score;


    public PlayerScore() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        this.date = dateFormat.format(date);
    }

    public String getPlayerInitials() {
        return playerInitials;
    }

    public void setPlayerInitials(String playerInitials) {
        this.playerInitials = playerInitials;
    }

    public int getPairs() {
        return pairs;
    }

    public void setPairs(int pairs) {
        this.pairs = pairs;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "PlayerScore{" +
                "date=" + date +
                ", playerInitials='" + playerInitials + '\'' +
                ", pairs=" + pairs +
                ", score=" + score +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerScore that = (PlayerScore) o;

        if (pairs != that.pairs) return false;
        if (score != that.score) return false;
        if (!date.equals(that.date)) return false;
        return playerInitials.equals(that.playerInitials);
    }

    @Override
    public int hashCode() {
        int result = date.hashCode();
        result = 31 * result + playerInitials.hashCode();
        result = 31 * result + pairs;
        result = 31 * result + score;
        return result;
    }
}
