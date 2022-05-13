package it.polimi.ingsw.network;

import com.google.gson.Gson;
import it.polimi.ingsw.model.Player;

public class Message {
    private String user;
    private MessageType type;
    private String payload;

    public Message(String user) {
        this.user = user;
        this.type = null;
        this.payload = null;
    }


    public Message(String user, MessageType type) {
        this.user = user;
        this.type = type;
        this.payload = null;
    }

    public Message() {
        this.user = null;
        this.type = null;
        this.payload = null;
    }

    public Message(String user, MessageType type, String payload) {
        this.user = user;
        this.type = type;
        this.payload = payload;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String fill (Object obj){
        Gson gson = new Gson();
        this.payload = gson.toJson(obj);
        return payload;
    }

    public String toSend (){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
