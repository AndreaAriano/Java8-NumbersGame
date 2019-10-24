package com.bira0003.numbersgame.listeners.helpers;

import javafx.scene.control.Label;

public class MouseEventHelper {

    public static MouseEventHelper INSTANCE = new MouseEventHelper();

    private Label clickedLabel;

    public Label getClickedLabel() {
        return clickedLabel;
    }

    public void setClickedLabel(Label clickedLabel) {
        this.clickedLabel = clickedLabel;
    }
}
