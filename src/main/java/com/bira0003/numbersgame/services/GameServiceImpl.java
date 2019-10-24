package com.bira0003.numbersgame.services;

import com.bira0003.numbersgame.models.PlayerScore;
import com.bira0003.numbersgame.repository.GameRepository;
import com.bira0003.numbersgame.models.GameState;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.*;

public class GameServiceImpl implements GameService {

    private GameState gameState;
    private LabelService labelService;
    private ConverterService converterService;
    private GameRepository gameRepository;
    private ScoreService scoreService;
    private GridService gridService;
    private GridPane appGrid;
    private PlayerScore playerScore;
    private int pairs = 0;

    public GameServiceImpl() {
        this.gameState = new GameState();
        this.labelService = LabelService.INSTANCE;
        converterService = ConverterService.INSTANCE;
        scoreService = ScoreService.INSTANCE;
        this.gameRepository = GameRepository.INSTANCE;
        playerScore = new PlayerScore();
        playerScore.setDate(new Date());
    }

    @Override
    public void newGame() {
        initSharedServices();
        gameState.setPairs(0);
        gameState.setScore(0);
        this.pairs = 0;
        scoreService.setScore(0);
        playerScore.setDate(new Date());
    }

    @Override
    public boolean loadGame() throws Exception {
        if (gridService == null)
            initSharedServices();
        GameState savedGameState = gameRepository.loadGame();
        if (savedGameState.getPlayer() != null) {
            System.out.println(savedGameState.toString());
            this.gameState.setScore(savedGameState.getScore());
            this.gameState.setPairs(savedGameState.getPairs());
            this.gameState.setBoard(savedGameState.getBoard());
            this.gameState.setClickedValues(savedGameState.getClickedValue());
            this.gameState.setPlayer(savedGameState.getPlayer());
            scoreService.setScore(savedGameState.getScore());

            Map<String, Integer> boardMap = converterService.boardToGameStateMap(this.getState().getBoard());
            gridService.loadGridFromBoard(boardMap);
            return true;
        }
        return false;
    }

    @Override
    public boolean saveGame() throws Exception {
        scoreService.saveScore(this.getState().getPlayer(), this.getPlayerScore());
        return this.gameRepository.saveGame(this.getState());
    }

    @Override
    public boolean saveGameWithDialog() throws Exception {
        if (this.getState().getScore() == 0) {
            return false; //if score is 0 return false
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save Game");
        alert.setContentText("Do you want to save game?");

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.out.println(this.getState().getPlayer());
            this.saveGame();

        } else {
            alert.close();
            // ... user chose CANCEL or closed the dialog
        }
        return true;
    }

    @Override
    public GameState getState() {
        return gameState;
    }

    public GridPane getAppGrid() {
        return this.appGrid;
    }

    @Override
    public void setAppGrid(GridPane appGrid) {
        this.appGrid = appGrid;
    }

    @Override
    public void updateBoard(int[][] board) {
        this.getState().setBoard(board);
    }

    /* This updates an element in the game array using the @updateBoardElement() method below */
    @Override
    public void UpdateElement(Map<String, Integer> idMap, Integer value) {
        Integer row = idMap.get("row");
        Integer column = idMap.get("column");

        this.updateBoardElement(row, column, value);

    }

    public void checkElementsAndUpdateScore() {
        Label firstLabel;
        Label secondLabel;

        if (this.getState().getClickedValue().size() >= 2) {//checks if 2 nodes were clicked
            List<String> keys = new ArrayList<>(this.getState().getClickedValue().keySet());// Make a new array list from the set of clicked keys.
            firstLabel = labelService.getLabel(keys.get(0)); //set first label to the first clicked label
            secondLabel = labelService.getLabel(keys.get(1));// set second label to the second clicked label

            int sum = this.getState().getClickedValue()
                    .values() //gets the values of the clicked nodes
                    .stream() //turns it to a @Stream
                    .mapToInt(Integer::intValue) //Gets the integer values
                    .sum(); //adds them up

            if (sum == 10 && labelService.labelsAreNeighbours(firstLabel, secondLabel)) { //if the sum is 10 {{this is core logic}} and elements are neighbours
                System.out.println("sum is 10!!"); //@TODO remove debug


                /*
                 * This method gets the keys of the elements, and updates the board in memory to 0
                 * the value is 0 so cleared nodes can be detected during game load
                 * */
                keys.forEach(key -> {
                    Map<String, Integer> stringIntegerMap = converterService.idToGameStateMap(key);
                    int row = stringIntegerMap.get("row");
                    int column = stringIntegerMap.get("column");
                    this.updateBoardElement(row, column, 0);//Set value of elements to 0
                });

                this.increasePairs(); //increment pairs
                labelService.updateLabelText(firstLabel, ""); //updates the text of the gotten label
                labelService.updateLabelStyle(firstLabel, "-fx-background-color: yellow;"); //updates the text background of the gotten label

                labelService.updateLabelText(secondLabel, ""); //updates the text of the gotten label
                labelService.updateLabelStyle(secondLabel, "-fx-background-color: yellow;"); //updates the text background of the gotten label

                gameState.setScore(scoreService.getScore());


            } else {// Else reset label colors
                labelService.resetLabelColor(firstLabel);
                labelService.resetLabelColor(secondLabel);
            }

            this.getState().clearClickedValue();
            keys.clear();
        }
    }


    /* This updates an element in the game array
     * This is not what changes the grid. it updates the game state data
     * */
    private void updateBoardElement(int row, int column, int value) {
        int[][] board = this.getState().getBoard();
        board[row][column] = value;
        this.getState().setBoard(board);
    }

    private void increasePairs() {
        this.pairs++;
        this.getState().setPairs(this.pairs);
    }

    private void initSharedServices() {
        this.gridService = GridService.INSTANCE;
    }


    private PlayerScore getPlayerScore() {
        playerScore.setPairs(this.gameState.getPairs());
        playerScore.setPlayerInitials(this.getState().getPlayer().toLowerCase());
        playerScore.setScore(this.getState().getScore());
        return playerScore;
    }

    public void setPlayerScore(PlayerScore playerScore) {
        this.playerScore = playerScore;
    }
}
