package communication;

import java.io.Serializable;

/**
 * This data type define the Message Structure that is exchanged between the clients and the servers. 
 * @author Bruno Aguiar 80177
 * @author David Rocha 84807
 */
public class Message implements Serializable {

    /**
     * Serial key
     */
    private static final long serialVersionUID = 040302L;

    /**
     * Enumerate to indicate to the receiver about the type of messages
     */
    private MessageType type;

    /**
     * Client's state
     */
    private String state;
    
    private int id;

    /**
     * Message with an integer returned from the Server side
     */
    private int retInt;

    /**
     * Message with a boolean returned from the Server side
     */
    private boolean retBool;

    /**
     * 
     * @param type Message Type Enumerate to indicate to the receiver about the type of messages
     */
    public Message(MessageType type){
        this.type = type;
    }

     /**
     * @param type Message Type Enumerate to indicate to the receiver about the type of messages
     * @param retBool Boolean that is returned from th Server side
     */
    public Message(MessageType type, boolean retBool){
        this.type = type;
        this.retBool = retBool;
    }

    /**
     * @param type Message Type Enumerate to indicate to the receiver about the type of messages
     * @param state Client's state
     */
    public Message(MessageType type, String state){
        this.type = type;
        this.state = state;
    }

    /**
     * @param type Message Type Enumerate to indicate to the receiver about the type of messages
     * @param arg1 boolean returned from the Server side
     */
    public Message(MessageType type, int arg1){
        this.type = type;
        if (type == MessageType.RETURN_PLANE_NUMBER)
            this.retInt = arg1;
        else
            this.id = arg1;
    }

    /**
     * @param type Message Type Enumerate to indicate to the receiver about the type of messages
     * @param id Client's ID
     * @param state Client's state
     */
    public Message(MessageType type, int id, String state){
        this.type = type;
        this.state = state;
        this.id = id;
    }

    /**
     * 
     * @return Enumerate type to indicate to the receiver about the type of messages
     */
    public MessageType getMessageType(){
        return this.type;
    }

    /**
     * 
     * @return Client's state
     */
    public String getState(){
        return this.state;
    }

    /**
     * @return Client's ID
     */
    public int getID(){
        return this.id;
    }

    /**
     * 
     * @return integer returned from the Server side
     */
    public int getRetInt(){
        return this.retInt;
    }

    /**
     * @return boolean returned from the Server side
     */
    public boolean getRetBool(){
        return this.retBool;
    }
}