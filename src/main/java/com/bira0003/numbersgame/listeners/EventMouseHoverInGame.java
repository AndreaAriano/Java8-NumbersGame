package com.bira0003.numbersgame.listeners;

import com.bira0003.numbersgame.listeners.helpers.MouseEventHelper;
import com.bira0003.numbersgame.services.LabelService;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class EventMouseHoverInGame implements EventHandler<MouseEvent> {

    private LabelService labelService;
    private MouseEventHelper mouseEvent;

    public EventMouseHoverInGame() {
        labelService = LabelService.INSTANCE;
        mouseEvent = MouseEventHelper.INSTANCE;
    }

    @Override
    public void handle(MouseEvent event) {
        Label label = ((Label) event.getSource());
        if (mouseEvent.getClickedLabel() != label) {
            if (!label.getText().equals("")) { //if the content of the label is not empty
                labelService.updateLabelStyle(label, "-fx-background-color: white;");
            }
        }
    }
}
