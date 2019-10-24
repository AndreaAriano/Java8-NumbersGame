package com.bira0003.numbersgame.services;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import java.util.Map;

public interface GridService {
    GridService INSTANCE = new GridServiceImpl();

    void resetGrid(GridPane gridPane);

    void updateNode(Node node, Integer value);

    void partiallyPopulateGrid(GridPane gridPane);

    boolean loadGridFromBoard(Map<String, Integer> board);
}
