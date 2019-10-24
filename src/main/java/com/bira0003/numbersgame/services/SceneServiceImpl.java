package com.bira0003.numbersgame.services;

import com.bira0003.numbersgame.listeners.LoadedEvent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.util.HashMap;

public class SceneServiceImpl implements SceneService {

    private HashMap<String, AnchorPane> screeMap = new HashMap<>();
    private Scene mainScene;


    public HashMap<String, AnchorPane> getScreeMap() {
        return screeMap;
    }

    public void setMainScene(Scene scene) {
        this.mainScene = scene;
    }

    public void addScene(String name, AnchorPane pane){
        screeMap.put(name, pane);
    }

    public void removeScene(String name, AnchorPane pane){
        screeMap.remove(name, pane);
    }
    public void setScene(String name){
        AnchorPane anchorPane = this.getScreeMap().get(name);
        anchorPane.fireEvent(new LoadedEvent(LoadedEvent.PANE_LOADED));
        mainScene.setRoot(anchorPane);
    }
}
