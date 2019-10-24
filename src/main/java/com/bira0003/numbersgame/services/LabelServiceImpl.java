package com.bira0003.numbersgame.services;

import com.bira0003.numbersgame.models.UIElements.GameLabelList;
import javafx.scene.control.Label;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LabelServiceImpl implements LabelService {

    private GameLabelList gameLabelList = GameLabelList.INSTANCE;
    private ConverterService converterService = ConverterService.INSTANCE;
    private ScoreService scoreService = ScoreService.INSTANCE;

    @Override
    public Map<String, Label> getLabelMap() {
        return gameLabelList.getLabelList();
    }

    @Override
    public Label getLabel(String id) {
        return gameLabelList.getLabelList().get(id);
    }

    @Override
    public void addLabelToList(Label label) {
        gameLabelList.addLabel(label.getId(), label);
    }

    @Override
    public void updateLabelText(Label label, String text) {
        this.getLabel(label.getId()).setText(text);
    }

    @Override
    public void updateLabelStyle(Label label, String style) {
        this.getLabel(label.getId()).setStyle(style);
    }

    @Override
    public void resetLabelColor(Label label) {
        label.setStyle("-fx-background-color: #FD1A20; -fx-text-fill: #3B3B3B");
    }

    @Override
    public boolean labelsAreNeighbours(Label label1, Label label2) {
        Integer firstLabelRow = converterService.nodeToGameStateMap(label1).get("row");
        Integer firstLabelColumn = converterService.nodeToGameStateMap(label1).get("column");

        Integer secondLabelRow = converterService.nodeToGameStateMap(label2).get("row");
        Integer secondLabelColumn = converterService.nodeToGameStateMap(label2).get("column");
        List<String> includedNodes = gameLabelList.getLabelList().keySet()
                .stream()
                .filter(key -> !gameLabelList.getLabelList().get(key).getText().equals(""))
                .collect(Collectors.toList());

        List<String> sameRowElements = includedNodes
                .stream()
                .sorted()
                .filter(key -> (key.matches("r" + firstLabelRow + "c\\d+"))) //Regular expression to match the current row r[n]c[dd]// i had to use a regular expression because a .contains method will match r1, r10 and r11 [ they all contain r1]
                .filter(key -> !gameLabelList.getLabelList().get(key).getText().equals(""))
                .collect(Collectors.toList());

        List<String> sameColumnElements = gameLabelList.getLabelList().keySet()
                .stream()
                .sorted(Comparator.comparingInt(o -> converterService.idToGameStateMap(o).get("row")))
                .filter(key -> (key.contains("c" + firstLabelColumn)))
                .filter(key -> !gameLabelList.getLabelList().get(key).getText().equals(""))//columns are easier hence no regular expressions
                .collect(Collectors.toList());

        //@TODO remove debug
        System.out.println("Same Row" + sameRowElements);
        System.out.println("Same Column" + sameColumnElements);

        int label1RowIndex = sameRowElements.indexOf(label1.getId());
        int sameRowElementsListSize = sameRowElements.size();
        int label2RowIndex = sameRowElements.indexOf(label2.getId());

        int label1ColumnIndex = sameColumnElements.indexOf(label1.getId());
        int sameColumnElementListSize = sameColumnElements.size();
        int label2ColumnIndex = sameColumnElements.indexOf(label2.getId());
        int gap = Math.abs((firstLabelRow - secondLabelRow) + (firstLabelColumn - secondLabelColumn)) - 1;

        if (!label1.getId().equals(label2.getId())) {
            if (((sameColumnElements.contains(label1.getId())) && sameColumnElements.contains(label2.getId())) || ((sameRowElements.contains(label1.getId())) && sameRowElements.contains(label2.getId()))) {

                if (label1RowIndex == 0 && (label2RowIndex == sameRowElementsListSize - 1 || label2RowIndex == 1)) { //for all row 0 elements
                    calculateScoreForEdgeNodes(label2RowIndex, firstLabelColumn, secondLabelColumn, 8, gap);
                    return true;
                }

                if (label1RowIndex == sameRowElementsListSize - 1 && (label2RowIndex == 0 || label2RowIndex == sameRowElementsListSize - 2)) { //for elements next to edge elements e.g all row 12 elements
                    this.calculateScoreForMaxEdgeNodes(firstLabelColumn, secondLabelColumn, 8, gap);
                    return true;
                }

                if (label1RowIndex + 1 == label2RowIndex || label1RowIndex - 1 == label2RowIndex) { //for elements next to each other which are not at the grid edges
                    gap = Math.abs(firstLabelColumn - secondLabelColumn) - 1;
                    scoreService.calculateScoreWithGap(gap);
                    return true;
                }

                if (label1ColumnIndex == 0 && (label2ColumnIndex == sameColumnElementListSize - 1 || label2ColumnIndex == 1)) { //for all column 0 elements
                    calculateScoreForEdgeNodes(label2ColumnIndex, firstLabelRow, secondLabelRow, 11, gap);
                    return true;
                }

                if (label1ColumnIndex == sameColumnElementListSize - 1 && (label2ColumnIndex == 0 || label2ColumnIndex == sameColumnElementListSize - 2)) {
                    calculateScoreForMaxEdgeNodes(firstLabelColumn, secondLabelColumn, 8, gap); //for elements next to edge elements e.g all column 9 elements
                    return true;
                }


                if (label1ColumnIndex + 1 == label2ColumnIndex || label1ColumnIndex - 1 == label2ColumnIndex) { //for elements next to each other which are not at the grid edges

                    gap = Math.abs(firstLabelRow - secondLabelRow) - 1;
                    scoreService.calculateScoreWithGap(gap);
                    return true;
                }
            }
        }
        return false;
    }

    private void calculateScoreForEdgeNodes(int index, int firstPosition, int secondPosition, int size, int gap) {
        int lowerPosition = firstPosition > secondPosition ? firstPosition : secondPosition;
        int higherPosition = firstPosition > secondPosition ? secondPosition : firstPosition;

        if (index != 1) {
            gap = (higherPosition - size) + lowerPosition;
        }
        scoreService.calculateScoreWithGap(gap);
    }

    private void calculateScoreForMaxEdgeNodes(int firstPosition, int secondPosition, int size, int gap) {
        int lowerPosition = firstPosition > secondPosition ? firstPosition : secondPosition;
        int higherPosition = firstPosition > secondPosition ? secondPosition : firstPosition;

        gap = (higherPosition - size) + lowerPosition;

        scoreService.calculateScoreWithGap(gap);
    }
}
