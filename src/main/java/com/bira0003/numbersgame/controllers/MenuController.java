package com.bira0003.numbersgame.controllers;

import com.bira0003.numbersgame.services.SceneService;
import com.bira0003.numbersgame.models.names.SceneNames;
import com.bira0003.numbersgame.services.GameService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    private GameService gameService;
    private SceneService sceneService;

    @FXML
    public TextField playerNameInput;
    @FXML
    private Label messageLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameService = GameService.INSTANCE;
        sceneService = SceneService.INSTANCE;
    }

    public void navigateToGame() throws Exception{
        String player = playerNameInput.getText().toLowerCase();
        if (!player.contains(" ")) {
            if (player.length() > 3) {
                gameService.getState().setPlayer(player);
                this.navigateToGameWindow();

            } else {
                messageLabel.setText("Player name must be more than 3 characters");
            }
        } else {
            messageLabel.setText("Player name must not contain spaces");
        }
    }

    private void navigateToGameWindow() throws Exception{
        sceneService.setScene(SceneNames.GAMEPAGE);
    }
}
