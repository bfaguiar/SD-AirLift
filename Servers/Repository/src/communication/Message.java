package communication;

import java.io.Serializable;

public class Message implements Serializable{
    private static final long serialVersionUID = 040302L;

    private MessageType type;
    private String state;
    private int id;
    private int retInt;
    private boolean retBool;

    public Message(MessageType type){
        this.type = type;
    }

    public Message(MessageType type, boolean retBool){
        this.type = type;
        this.retBool = retBool;
    }

    public Message(MessageType type, String state){
        this.type = type;
        this.state = state;
    }

    public Message(MessageType type, int arg1){
        this.type = type;
        if (type == MessageType.RETURN_PLANE_NUMBER)
            this.retInt = arg1;
        else
            this.id = arg1;
    }

    public Message(MessageType type, int id, String state){
        this.type = type;
        this.state = state;
        this.id = id;
    }

    public MessageType getMessageType(){
        return this.type;
    }

    public String getState(){
        return this.state;
    }

    public int getID(){
        return this.id;
    }

    public int getRetInt(){
        return this.retInt;
    }

    public boolean getRetBool(){
        return this.retBool;
    }
}