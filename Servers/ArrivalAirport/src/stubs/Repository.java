package stubs;

import communication.ClientCom;
import communication.Message;
import communication.MessageType;

public class Repository{
    private String address;
    private int port;

    public Repository(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void incrementNumberAtDestination(){
        ClientCom com = new ClientCom (address, port);
        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        Message msg = new Message(MessageType.REPO_INCREMENT_NUMBER_DESTINATION);
        com.writeObject(msg);
        Message inMessage = (Message) com.readObject();
        assert inMessage.getMessageType() == MessageType.STATUS_OK;
        com.close ();
    }

    public void decrementNumberInPlane(){
        ClientCom com = new ClientCom (address, port);
        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        Message msg = new Message(MessageType.REPO_DECREMENT_NUMBER_PLANE);
        com.writeObject(msg);
        Message inMessage = (Message) com.readObject();
        assert inMessage.getMessageType() == MessageType.STATUS_OK;
        com.close();
    }

    public void log(){
        ClientCom com = new ClientCom (address, port);
        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        Message msg = new Message(MessageType.REPO_LOG);
        com.writeObject(msg);
        Message inMessage = (Message) com.readObject();
        assert inMessage.getMessageType() == MessageType.STATUS_OK;
        com.close();
    }

    public void logReturning(){
        ClientCom com = new ClientCom (address, port);
        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        Message msg = new Message(MessageType.REPO_LOG_RETURN);
        com.writeObject(msg);
        Message inMessage = (Message) com.readObject();
        assert inMessage.getMessageType() == MessageType.STATUS_OK;
        com.close();
    }

    public void setPilotState(String state){
        ClientCom com = new ClientCom (address, port);
        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        Message msg = new Message(MessageType.REPO_SET_PILOT_STATE, state);
        com.writeObject(msg);
        Message inMessage = (Message) com.readObject();
        assert inMessage.getMessageType() == MessageType.STATUS_OK;
        com.close();
    }

    public void setHostessState(String state){
        ClientCom com = new ClientCom (address, port);
        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        Message msg = new Message(MessageType.REPO_SET_HOSTESS_STATE, state);
        com.writeObject(msg);
        Message inMessage = (Message) com.readObject();
        assert inMessage.getMessageType() == MessageType.STATUS_OK;
        com.close();
    }

    public void setPassengerListState(int id, String state){
        ClientCom com = new ClientCom (address, port);
        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        Message msg = new Message(MessageType.REPO_SET_PASSENGER_STATE, id, state);
        com.writeObject(msg);
        Message inMessage = (Message) com.readObject();
        assert inMessage.getMessageType() == MessageType.STATUS_OK;
        com.close();
    }
}