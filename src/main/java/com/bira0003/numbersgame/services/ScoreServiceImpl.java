package com.bira0003.numbersgame.services;


import com.bira0003.numbersgame.models.PlayerScore;
import com.bira0003.numbersgame.repository.GameRepository;
import com.bira0003.numbersgame.models.Scores;

import java.util.Objects;
import java.util.UUID;

public class ScoreServiceImpl implements ScoreService { //use game service

    private int playerScore = 0;
    private GameRepository gameRepository = GameRepository.INSTANCE;

    @Override
    public int getScore() {
        return playerScore;
    }

    @Override
    public void setScore(int score) {
        this.playerScore = score;
        this.updateGameScore();
    }

    @Override
    public void addScore(int score) {
        this.playerScore += score;
        this.updateGameScore();
    }

    @Override
    public void calculateScoreWithGap(int gap) {
        if (gap == 0) {
            this.addScore(10);
        } else {
            int score = 10 + ((int) Math.pow(2, gap));
            this.addScore(score);
        }
    }

    @Override
    public void deductScore(int penalty) {
        this.playerScore -= penalty;
        this.updateGameScore();
    }

    @Override
    public void updateGameScore() {
        if (this.getScore() < 0) {
            this.playerScore = 0;
        }
    }

    @Override
    public void saveScore(String player, PlayerScore playerScore) throws Exception {
        Scores scores = gameRepository.getScores();
        if (scores != null) {
            UUID uuid = UUID.randomUUID();
            System.out.println(uuid);
            String scoreId = uuid.toString();
            scores.getPlayerScore().put(scoreId, playerScore);
            gameRepository.saveScore(scores);
        }
        System.out.println(Objects.requireNonNull(gameRepository.getScores()).toString());
    }

    @Override
    public Scores getAllScores() throws Exception{

        return this.gameRepository.getScores();
    }
}
