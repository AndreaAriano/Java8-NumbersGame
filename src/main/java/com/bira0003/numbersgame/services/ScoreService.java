package com.bira0003.numbersgame.services;

import com.bira0003.numbersgame.models.PlayerScore;
import com.bira0003.numbersgame.models.Scores;

public interface ScoreService {
    ScoreService INSTANCE = new ScoreServiceImpl();

    int getScore();

    void setScore(int score);

    void addScore(int score);

    void deductScore(int penalty);

    void calculateScoreWithGap(int gap);

    void updateGameScore();

    void saveScore(String player, PlayerScore playerScore) throws Exception;

    Scores getAllScores() throws Exception;
}
