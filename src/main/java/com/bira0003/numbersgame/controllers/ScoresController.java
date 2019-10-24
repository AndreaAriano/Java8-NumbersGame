package com.bira0003.numbersgame.controllers;

import com.bira0003.numbersgame.listeners.LoadedEvent;
import com.bira0003.numbersgame.models.PlayerScore;
import com.bira0003.numbersgame.services.SceneService;
import com.bira0003.numbersgame.services.ScoreService;
import com.bira0003.numbersgame.models.Scores;
import com.bira0003.numbersgame.models.names.SceneNames;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ScoresController implements Initializable {

    public TableColumn<PlayerScore, String> scoreColumn;
    public TableColumn<PlayerScore, String> pairsColumn;
    public TableColumn<PlayerScore, String> playerNameColumn;
    public TableColumn<PlayerScore, String> dateColumn;
    @FXML
    public Button backButton;
    @FXML
    public TableView<PlayerScore> scoresTable;
    private SceneService sceneService;
    private ScoreService scoreService;
    @FXML
    private AnchorPane rootPane;

    public ScoresController() {
        this.sceneService = SceneService.INSTANCE;
        this.scoreService = ScoreService.INSTANCE;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rootPane.addEventHandler(LoadedEvent.PANE_LOADED, event -> {
            try {
                populateScores();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        playerNameColumn.setCellValueFactory(new PropertyValueFactory<>("playerInitials"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        pairsColumn.setCellValueFactory(new PropertyValueFactory<>("pairs"));
    }

    private void populateScores() throws Exception {
        ObservableList<PlayerScore> scoresTableItems = scoresTable.getItems();
        scoresTableItems.clear();
        Scores scores = scoreService.getAllScores();

        if (scores.getPlayerScore() != null && scores.getPlayerScore().size() > 0) {
            scores.getPlayerScore().values().stream()
                    .sorted((o1, o2) -> {

                        int iComp = Integer.compare(o2.getScore(), o1.getScore());
                        if(iComp != 0) {
                            return iComp;
                        }
                        int sComp = o2.getDate().compareTo(o1.getDate());
                        return sComp;
                    })
                    .limit(10)
                    .forEach(scoresTableItems::add);

            scoresTable.setItems(scoresTableItems);
            System.out.println(Arrays.toString(scoresTableItems.stream().map(PlayerScore::getScore).toArray()));
        }
    }

    public void navigateToGame() {
        sceneService.setScene(SceneNames.GAMEPAGE);
    }
}
