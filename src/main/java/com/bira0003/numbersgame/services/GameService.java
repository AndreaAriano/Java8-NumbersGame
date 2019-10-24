package com.bira0003.numbersgame.services;

import com.bira0003.numbersgame.models.GameState;
import javafx.scene.layout.GridPane;

import java.util.Map;

public interface GameService {

    GameService INSTANCE = new GameServiceImpl();

    void newGame();

    boolean saveGame() throws Exception;

    boolean saveGameWithDialog() throws Exception;

    boolean loadGame() throws Exception;

    GameState getState();

    GridPane getAppGrid();

    void setAppGrid(GridPane gridPane);

    void updateBoard(int[][] board);

    void UpdateElement(Map<String, Integer> idMap, Integer value);

    void checkElementsAndUpdateScore();


}
