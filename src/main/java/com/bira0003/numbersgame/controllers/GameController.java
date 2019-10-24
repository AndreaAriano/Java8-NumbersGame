package com.bira0003.numbersgame.controllers;

import com.bira0003.numbersgame.listeners.LoadedEvent;
import com.bira0003.numbersgame.repository.GameRepository;
import com.bira0003.numbersgame.services.GridService;
import com.bira0003.numbersgame.services.SceneService;
import com.bira0003.numbersgame.models.names.SceneNames;
import com.bira0003.numbersgame.services.GameService;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    public Label timeLabel;
    //@TODO Remove Debug
    //Global variables
    private GameService gameService;
    private GridService gridService;
    private GameRepository gameRepository;
    private SceneService sceneService;

    @FXML
    private GridPane appGrid;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label pairsLabel;
    @FXML
    private Button saveGameButton;
    @FXML
    private Button loadGameButton;
    @FXML
    private Button repopulateButton;
    @FXML
    private Button startButton;
    @FXML
    private Button highScores;
    @FXML
    private Label playerNameLabel;

    public GameController() {
        this.gameService = GameService.INSTANCE;
        this.gridService = GridService.INSTANCE;
        this.gameRepository = GameRepository.INSTANCE;
        this.sceneService = SceneService.INSTANCE;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rootPane.addEventHandler(LoadedEvent.PANE_LOADED, event -> {
            setPlayerName();
        });

        gameService.setAppGrid(appGrid);
        scoreLabel.textProperty().bind(gameService.getState().scoreProperty);
        pairsLabel.textProperty().bind(gameService.getState().pairsProperty);
        gridService.resetGrid(appGrid);
        startButton.setText("Restart");

        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String date = format.format(new Date());

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            Calendar cal = Calendar.getInstance();
            int minute = cal.get(Calendar.MINUTE);
            int hour = cal.get(Calendar.HOUR);
            timeLabel.setText(date+ " - "+hour + ":" + (minute));
        }),
                new KeyFrame(Duration.minutes(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public void newGame() {
        gridService.resetGrid(appGrid);
        gameService.newGame();
    }

    public void saveGame() throws Exception {
        gameService.saveGame();
    }

    public void loadGame() throws Exception {
        if (gameService.loadGame()) {
            System.out.println("Game Loaded");
        } else {
            System.out.println("error Loading Game");
        }
    }

    public void repopulateGrid() {
        gridService.partiallyPopulateGrid(gameService.getAppGrid());
    }


    public void exitApplication() throws Exception {
        Platform.exit();
    }

    public void goToScoresPage() throws Exception{

        sceneService.setScene(SceneNames.SCORESPAGE);
    }

    private void setPlayerName() {
        playerNameLabel.setText(gameService.getState().getPlayer());
        gameRepository.setPlayer(gameService.getState().getPlayer());
    }
}
