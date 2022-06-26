package it.polimi.ingsw.model;

public enum WizardType {
    wizard1,
    wizard2,
    wizard3,
    wizard4;

    public static Integer getIndex(WizardType wizard){
        switch (wizard) {
            case wizard1: return 1;
            case wizard2: return 2;
            case wizard3: return 3;
            case wizard4: return 4;
        }

        return null;
    }
}
