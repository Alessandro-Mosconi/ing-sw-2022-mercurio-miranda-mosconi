package it.polimi.ingsw.model;
import javafx.scene.paint.Color;
import java.util.Random;

public enum PawnColor {

    green,
    red,
    yellow,
    pink,
    blue;

    public static Color getColor(Integer i){
        switch (i) {
            case 0: return Color.GREEN;
            case 1: return Color.RED;
            case 2: return Color.YELLOW;
            case 3: return Color.PINK;
            case 4: return Color.BLUE;
        }

        return null;
    }
    public static Color getColorFromPawnColor(PawnColor i){
        switch (i) {
            case green: return Color.GREEN;
            case red: return Color.RED;
            case yellow: return Color.YELLOW;
            case pink: return Color.PINK;
            case blue: return Color.BLUE;
        }

        return null;
    }
    public static PawnColor getIndex(Integer i){
        switch (i) {
            case 0: return green;
            case 1: return red;
            case 2: return yellow;
            case 3: return pink;
            case 4: return blue;
        }

        return null;
    }

    public static Integer getColorIndex(PawnColor color){
        switch (color) {
            case green: return 0;
            case red: return 1;
            case yellow: return 2;
            case pink: return 3;
            case blue: return 4;
        }

        return null;
    }

    public static PawnColor randomColor() {
        int pick = new Random().nextInt(PawnColor.values().length);
        return PawnColor.values()[pick];
    }
}
