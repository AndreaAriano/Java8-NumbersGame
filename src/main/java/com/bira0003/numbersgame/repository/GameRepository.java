package com.bira0003.numbersgame.repository;

import com.bira0003.numbersgame.models.GameState;
import com.bira0003.numbersgame.models.Scores;

public interface GameRepository {

    GameRepository INSTANCE = new GameRepositoryImpl();

    boolean saveGame(GameState gameState) throws Exception;
    boolean saveScore(Scores scores) throws Exception;
    Scores getScores() throws Exception;
    GameState loadGame() throws Exception;
    void cleanup() throws Exception;
    void setPlayer(String player);
}
