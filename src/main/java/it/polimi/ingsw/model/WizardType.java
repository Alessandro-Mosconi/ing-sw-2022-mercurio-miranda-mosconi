package it.polimi.ingsw.model;

public enum WizardType {
    wizard1,
    wizard2,
    wizard3,
    wizard4;

    public static Integer getIndex(WizardType wizard){
        return switch (wizard) {
            case wizard1 -> 1;
            case wizard2 -> 2;
            case wizard3 -> 3;
            case wizard4 -> 4;
        };

    }
}
