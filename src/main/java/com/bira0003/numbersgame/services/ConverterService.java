package com.bira0003.numbersgame.services;

import javafx.scene.Node;

import java.util.Map;

public interface ConverterService {

    ConverterService INSTANCE = new ConverterServiceImpl();

    Map<String, Integer> idToGameStateMap(String id);
    Map<String, Integer> nodeToGameStateMap(Node node);
    Map<String, Integer> boardToGameStateMap(int[][] board);

}
