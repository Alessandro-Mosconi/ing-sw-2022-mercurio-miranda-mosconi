package it.polimi.ingsw.model;

import javafx.scene.paint.Color;

public enum TowerColor {
    black,
    white,
    grey;

    public static Color getColor(TowerColor color){
        return switch (color) {
            case black -> Color.BLACK;
            case white -> Color.WHITE;
            case grey -> Color.GREY;
        };

    }
}

