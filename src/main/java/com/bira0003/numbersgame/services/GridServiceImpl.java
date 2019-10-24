package com.bira0003.numbersgame.services;

import com.bira0003.numbersgame.listeners.EventMouseClickedInGame;
import com.bira0003.numbersgame.listeners.EventMouseHoverInGame;
import com.bira0003.numbersgame.listeners.EventMouseLeavesInGame;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class GridServiceImpl implements GridService {

    private Random random;
    private GameService gameService;
    private ConverterService converterService;
    private LabelService labelService;
    private ScoreService scoreService;

    public GridServiceImpl() {
        gameService = GameService.INSTANCE;
        converterService = ConverterService.INSTANCE;
        random = new Random();
        labelService = LabelService.INSTANCE;
        scoreService = ScoreService.INSTANCE;
    }

    /* This function resets the grid by adding random numbers to every label
     * The Grid pane is passed into the function. the function loops through all nodes in the GridPane
     * and dives it an id (example r10c2 which means Row 10 Column 2.
     * this ID will be used throughout the application to match nodes to the Game Board array in the Game State Class.
     * it also adds all labels to the GameLabelList Class
     */
    @Override
    public void resetGrid(GridPane gridPane) {
        int[][] board = gameService.getState().getBoard();
        ObservableList<Node> gridChildren = gridPane.getChildren(); //Get all children

        //This method sets the ID of every grid label
        gridChildren.forEach(node -> {
            Integer row = Integer.valueOf(node.getProperties().get("gridpane-row").toString().trim()); //get the node row and store it in a variable
            Integer column = Integer.valueOf(node.getProperties().get("gridpane-column").toString().trim()); //get the node column and store it in a variable

            node.setId("r" + row + "c" + column); //create id with pattern rNUMcNUM
            Integer nodeText = (random.nextInt(9) + 1);

            this.updateNode(node, nodeText);//This updates a node and then updates it on the board


            node.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventMouseClickedInGame());
            node.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventMouseHoverInGame());
            node.addEventHandler(MouseEvent.MOUSE_EXITED, new EventMouseLeavesInGame());

            labelService.addLabelToList(((Label) node));
            labelService.resetLabelColor(((Label) node));
        });
        gameService.updateBoard(board); //This line updates the board in memory
    }

    /*
     * This method updates the value on a node
     * NOTE: the node is what you see in the application. Each red square containing a number is a single node.
     * this method updates the number on a node with the given value
     * then it calls the update element method in the game service to update corresponding element in the Game Board array
     */
    @Override
    public void updateNode(Node node, Integer value) {
        ((Label) node).setText("" + value + "");
        gameService.UpdateElement(converterService.nodeToGameStateMap(node), value);
    }

    @Override
    public void partiallyPopulateGrid(GridPane gridPane) {

        List<Node> listOfEmptyNodes = gridPane.getChildren().stream()
                .filter(node -> ((Label) node).getText().equals(""))
                .collect(Collectors.toList()); //gets the list of empty nodes

        if (listOfEmptyNodes.size() != 0) { //if list of empty nodes is not empty, repopulate a random number of nodes
            listOfEmptyNodes
                    .forEach(node -> {
                        Label label = ((Label) node);
                        Integer randomNumber = (random.nextInt(6) + 1);
                        if (randomNumber % 2 == 0) {
                            labelService.updateLabelText(label, "" + (random.nextInt(9) + 1));
                            labelService.resetLabelColor(label);
                        }
                    });
            scoreService.deductScore(20);
            gameService.getState().setScore(scoreService.getScore());
        }
    }

    /*
    * This method takes the Board Map of nodes in the game and changes the values
    * by getting the id of the node and replacing it with the corresponding element in a board list
    * this is used in the load game functionality
    * */
    @Override
    public boolean loadGridFromBoard(Map<String, Integer> board) {
        Map<String, Label> gameBoard = labelService.getLabelMap();

        board.forEach((id, value) -> {
            String text = ""+value;
            if(value==0){
                text = "";
            labelService.updateLabelStyle(gameBoard.get(id), "-fx-background-color: yellow;"); //change the background color
            }
            gameBoard.get(id).setText(text);
        });
        return true;
    }
}
