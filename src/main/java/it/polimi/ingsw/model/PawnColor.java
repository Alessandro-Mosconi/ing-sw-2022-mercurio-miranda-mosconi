package it.polimi.ingsw.model;
import java.util.Random;

public enum PawnColor {
    yellow,
    blue,
    red,
    green,
    pink;

    public static PawnColor randomColor() {
        int pick = new Random().nextInt(PawnColor.values().length);
        return PawnColor.values()[pick];
    }
}
