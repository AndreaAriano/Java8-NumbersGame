package com.bira0003.numbersgame.models.UIElements;

import javafx.scene.control.Label;

import java.util.HashMap;
import java.util.Map;

/*
 * This is a class which holds all Labels in the Application
 * It will be used to maintain state
 * */
public class GameLabelList {
    public static GameLabelList INSTANCE = new GameLabelList();

    private Map<String, Label> labelList = new HashMap<>();
    private int numberOfRows = 12;
    private int numberOfColumns = 9;

    public Map<String, Label> getLabelList() {
        return labelList;
    }

    public void setLabelList(Map<String, Label> labelList) {
        this.labelList = labelList;
    }

    public void addLabel(String id, Label label) {
        this.labelList.put(label.getId(), label);
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    private void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public void resetNumberOfRowsAndColumns() {
        this.setNumberOfRows(12);
        this.setNumberOfColumns(9);
    }

    public int getNumberOfcolumns() {
        return numberOfColumns;
    }

    private void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    public void decrementRows() {
        this.setNumberOfRows(numberOfRows - 1);
    }

    public void incrementRows() {
        this.setNumberOfRows(numberOfRows + 1);
    }
}
