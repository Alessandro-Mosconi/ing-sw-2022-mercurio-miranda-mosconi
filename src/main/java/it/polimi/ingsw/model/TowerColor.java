package it.polimi.ingsw.model;

import javafx.scene.paint.Color;

public enum TowerColor {
    black,
    white,
    grey;

    public static Color getColor(TowerColor color){
        switch (color) {
            case black: return Color.BLACK;
            case white: return Color.WHITE;
            case grey: return Color.GREY;
        }

        return null;
    }
}

