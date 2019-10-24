package com.bira0003.numbersgame.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GameState implements Serializable {


    private static final long serialVersionUID = 4479298173730470850L;

    public transient StringProperty scoreProperty = new SimpleStringProperty(this, "score", "0");
    public transient StringProperty pairsProperty = new SimpleStringProperty(this, "pairs", "0");
    /*
     * This is the game board. it is a 2 dimensional array representing the Nodes in the game.
     * its structure looks like this:
     *          9 COLUMNS
     * [  ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑
     *   [0,1,2,3,4,5,6,7,8], →
     *   [1,0,0,0,0,0,0,0,0], →
     *   [2,0,0,0,0,0,0,0,0], →
     *   [3,0,0,0,0,0,0,0,0], →
     *   [4,0,0,0,0,0,0,0,0], →
     *   [5,0,0,0,0,0,0,0,0], → 12 ROWS
     *   [6,0,0,0,0,0,0,0,0], →
     *   [7,0,0,0,0,0,0,0,0], →
     *   [8,0,0,0,0,0,0,0,0], →
     *   [9,0,0,0,0,0,0,0,0], →
     *   [10,0,0,0,0,0,0,0,0], →
     *   [11,0,0,0,0,0,0,0,0]  →
     * ]
     * it represents the board state
     * */
    private int[][] board;
    //This is the current user score
    private int score;
    //This is the number of pairs achieved
    private int pairs;
    //This is a Map of clicked nodes ID and Values
    private Map<String, Integer> clickedValue = new HashMap<>();
    private String player;

    public GameState() {
        board = new int[12][9];
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        this.setScorePropertyValue("" + score);
    }

    public int getPairs() {
        return pairs;
    }

    public void setPairs(int pairs) {
        this.pairs = pairs;
        this.setPairsPropertyValue("" + pairs);
    }

    public Map<String, Integer> getClickedValue() {
        return clickedValue;
    }

    public void setClickedValues(Map<String, Integer> clickedValue) {
        this.clickedValue = clickedValue;
    }

    public void addClickedValue(String key, Integer value) {
        this.clickedValue.put(key, value);
    }

    public void clearClickedValue() {
        this.clickedValue.clear();
    }

    public String getScoreProperty() {
        return scoreProperty.get();
    }

    public StringProperty scoreProperty() {
        return scoreProperty;
    }

    public void setScorePropertyValue(String score) {
        this.scoreProperty.set(score);
    }

    public String getPairsProperty() {
        return pairsProperty.get();
    }

    public StringProperty pairsProperty() {
        return pairsProperty;
    }

    public void setPairsPropertyValue(String pairs) {
        this.pairsProperty.set(pairs);
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return "GameState{" +
                "board=" + Arrays.deepToString(board) +
                ", score=" + score +
                ", pairs=" + pairs +
                ", player='" + player + '\'' +
                '}';
    }
}
