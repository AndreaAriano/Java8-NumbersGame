package com.bira0003.numbersgame.services;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public interface SceneService {
    SceneService INSTANCE = new SceneServiceImpl();

    void setMainScene(Scene scene);

    void addScene(String name, AnchorPane pane);

    void removeScene(String name, AnchorPane pane);

    void setScene(String name);
}
