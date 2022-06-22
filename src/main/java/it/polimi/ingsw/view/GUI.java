package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.Message;

public class GUI extends View{
    private LoginController controller;
    private Object lock;

    @Override
    public void login() {
        //molto probabilmente qui bisognerà creare un thread
        JavaFXTest javaFXTest = new JavaFXTest();
        javaFXTest.main(null);
        //username = String.valueOf(controller.getUsername());

        System.out.println(username);
        player = new Player();
        Message msg_out = new Message();
        player.setNickName(username);
        setUsername(username);


    }

    @Override
    public void settings() {

    }

    @Override
    public void chooseAssistantCard() {

    }

    @Override
    public void choosePawnMove() {

    }

    @Override
    public void showTable() {

    }

    @Override
    public void chooseMNmovement() {

    }

    @Override
    public void chooseCT() {

    }

    @Override
    public void showUsedAssistantCards() {

    }
}
