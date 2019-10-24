package com.bira0003.numbersgame.services;

import javafx.scene.control.Label;

import java.util.Map;

public interface LabelService {

    LabelService INSTANCE = new LabelServiceImpl();

    Map<String, Label> getLabelMap();

    Label getLabel(String id);

    void addLabelToList(Label label);

    void updateLabelText(Label label, String text);

    void updateLabelStyle(Label label, String style);

    void resetLabelColor(Label label);

    boolean labelsAreNeighbours(Label label1, Label label2);
}
