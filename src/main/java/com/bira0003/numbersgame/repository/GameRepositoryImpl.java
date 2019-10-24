package com.bira0003.numbersgame.repository;

import com.bira0003.numbersgame.models.GameState;
import com.bira0003.numbersgame.models.Scores;

import java.io.*;

public class GameRepositoryImpl implements GameRepository {

    private FileInputStream inputStream;
    private FileOutputStream outputStream;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private String player;


    public GameRepositoryImpl() {
        try {
            outputStream = new FileOutputStream("Scores.hs", true);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean saveGame(GameState gameState) throws Exception {
        outputStream = new FileOutputStream(this.getPlayer() + ".game");
        objectOutputStream = new ObjectOutputStream(outputStream);
        if (outputStream != null) {
            objectOutputStream.writeObject(gameState);

            this.cleanup();
            System.out.println("Game Saved");
            return true;
        }
        this.cleanup();
        return false;
    }

    @Override
    public boolean saveScore(Scores scores) throws Exception {
        outputStream = new FileOutputStream("Scores.hs");
        objectOutputStream = new ObjectOutputStream(outputStream);

        if (outputStream != null) {
            objectOutputStream.writeObject(scores);

            System.out.println("Score Saved");
            this.cleanup();
            return true;
        }
        this.cleanup();
        return false;
    }

    @Override
    public Scores getScores() throws Exception {
        inputStream = new FileInputStream("Scores.hs");

        try {
            objectInputStream = new ObjectInputStream(inputStream);
        } catch (IOException e) {

            System.out.println("No valid Save Game found");
            return new Scores();
        }

        if (inputStream != null) {

            Object readObject = objectInputStream.readObject();
            this.cleanup();
            return (Scores) readObject;

        }

        this.cleanup();
        return new Scores();
    }

    @Override
    public GameState loadGame() throws Exception {
        inputStream = new FileInputStream(this.getPlayer() + ".game");
        objectInputStream = new ObjectInputStream(inputStream);
        if (inputStream != null) {

            Object savedGame = objectInputStream.readObject();
            this.cleanup();
            return (GameState) savedGame;
        }
        this.cleanup();
        return new GameState();
    }

    @Override
    public void cleanup() throws Exception {
        if (inputStream != null)
            inputStream.close();
        if (outputStream != null)
            outputStream.close();
        if (objectInputStream != null)
            objectInputStream.close();
        if (objectOutputStream != null)
            objectOutputStream.close();
    }

    public String getPlayer() {
        return this.player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }
}
