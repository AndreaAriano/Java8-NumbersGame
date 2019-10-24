package com.bira0003.numbersgame.listeners;


import com.bira0003.numbersgame.listeners.helpers.MouseEventHelper;
import com.bira0003.numbersgame.services.LabelService;
import com.bira0003.numbersgame.models.GameState;
import com.bira0003.numbersgame.services.GameService;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class EventMouseClickedInGame implements EventHandler<MouseEvent> {

    private GameService gameService;
    private GameState gameState;
    private LabelService labelService;
    private MouseEventHelper mouseEvent;

    public EventMouseClickedInGame() {
        gameService = GameService.INSTANCE;
        gameState = gameService.getState();
        labelService = LabelService.INSTANCE;
        mouseEvent = MouseEventHelper.INSTANCE;
    }

    /*
     *  This function takes the clicked node, gets its ID, its content,
     *  Changes its color
     *  and adds it to the clicked nodes Map in the GameState class
     */
    @Override
    public void handle(MouseEvent event) {
        Label label = ((Label) event.getSource());
        mouseEvent.setClickedLabel(label);

        String id = label.getId();
        if (!label.getText().equals("")) { //if the content of the label is not empty
            labelService.updateLabelStyle(label, "-fx-background-color: green;");
            Integer text = Integer.valueOf(label.getText());
            this.gameState.addClickedValue(id, text);
            System.out.println("Clicked value " + this.gameState.getClickedValue().toString()); //TODO remove debug
            gameService.checkElementsAndUpdateScore();
        }
    }
}
