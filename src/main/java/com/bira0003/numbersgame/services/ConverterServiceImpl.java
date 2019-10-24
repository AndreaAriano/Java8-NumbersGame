package com.bira0003.numbersgame.services;

import javafx.scene.Node;

import java.util.HashMap;
import java.util.Map;

public class ConverterServiceImpl implements ConverterService {

    /*
     * The purpose of this class is to get the id of a label
     * and return a map with its corresponding row and column on the grid
     * */
    @Override
    public Map<String, Integer> idToGameStateMap(String id) {
        Map<String, Integer> idMap = new HashMap<>();

        String[] idArray = id.trim()
                .toLowerCase()
                .replace('r', ' ')
                .trim()
                .replace('c', ',')
                .split(",");

        idMap.put("row", Integer.valueOf(idArray[0]));
        idMap.put("column", Integer.valueOf(idArray[1]));

        return idMap;
    }

    @Override
    public Map<String, Integer> nodeToGameStateMap(Node node) {
        return this.idToGameStateMap(node.getId());
    }

    /*
     * This methods takes a board array of int[12][9] and
     * converts it to a HashMap<String, Integer>
     * which can be used to replace existing data on the board
     * it is used for the load data functionality
     * it will iterate through the array and create a board id
     * the board IDs are structured rNcN which stand for row[N}col[N]
     * e.g. r10c3 stands for Row 10 Column 13
     */
    @Override
    public Map<String, Integer> boardToGameStateMap(int[][] board) {
        Map<String, Integer> boardMap = new HashMap<>();

        for (int row = 0; row < 12; row++) {
            for (int col = 0; col < 9; col++) { //for each row and column
                String id = "r" + row + "c" + col;

                int value = board[row][col];//set the corresponding value in board

                boardMap.put(id, value);
            }

        }
        return boardMap;
    }
}
