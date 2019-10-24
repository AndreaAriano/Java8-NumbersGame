package com.bira0003.numbersgame;


import com.bira0003.numbersgame.services.SceneService;
import com.bira0003.numbersgame.models.names.SceneNames;
import com.bira0003.numbersgame.services.GameService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NumbersGameApplication extends Application {

    private SceneService sceneService;
    private GameService gameService = GameService.INSTANCE;

    public NumbersGameApplication() {
        sceneService = SceneService.INSTANCE;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("HomeWindow.fxml"));
        primaryStage.setTitle("Tombari Numbers");
        Scene mainScene = new Scene(root);
        primaryStage.setScene(mainScene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            gameService.checkElementsAndUpdateScore();
            primaryStage.close();
        });

        sceneService.setMainScene(mainScene);
        sceneService.addScene(SceneNames.HOMEPAGE, FXMLLoader.load(getClass().getResource("HomeWindow.fxml")));
        sceneService.addScene(SceneNames.GAMEPAGE,FXMLLoader.load(getClass().getResource("GameWindow.fxml")));
        sceneService.addScene(SceneNames.SCORESPAGE,FXMLLoader.load(getClass().getResource("ScoresWindow.fxml")));
    }
    //TODO implement blank rows collapse
    /*
     * TODO add README.TXT
     *
     *
     * */

    @Override
    public void stop() throws Exception {
        gameService.saveGameWithDialog();
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
