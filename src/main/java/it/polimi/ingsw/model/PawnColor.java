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
        return switch (i) {
            case 0 -> Color.GREEN;
            case 1 -> Color.RED;
            case 2 -> Color.YELLOW;
            case 3 -> Color.PINK;
            case 4 -> Color.BLUE;
            default -> Color.TRANSPARENT;
        };

    }
    public static Color getColorFromPawnColor(PawnColor i){
        return switch (i) {
            case green -> Color.GREEN;
            case red -> Color.RED;
            case yellow -> Color.YELLOW;
            case pink -> Color.PINK;
            case blue -> Color.BLUE;
        };

    }
    public static PawnColor getIndex(Integer i){
        return switch (i) {
            case 0 -> green;
            case 1 -> red;
            case 2 -> yellow;
            case 3 -> pink;
            case 4 -> blue;
            default -> null;
        };

    }

    public static Integer getColorIndex(PawnColor color){
        return switch (color) {
            case green -> 0;
            case red -> 1;
            case yellow -> 2;
            case pink -> 3;
            case blue -> 4;
        };

    }

    /**
     * Chooses a random color among this enum values.
     * @return randomically chosen color.
     */
    public static PawnColor randomColor() {
        int pick = new Random().nextInt(PawnColor.values().length);
        return PawnColor.values()[pick];
    }
}
