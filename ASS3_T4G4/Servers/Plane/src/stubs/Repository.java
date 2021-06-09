package stubs;

import communication.ClientCom;
import communication.Message;
import communication.MessageType;

/**
 * Stub Repository 
 * @author Bruno Aguiar 80177
 * @author David Rocha 84807
 */
public class Repository {
   
   
    /**
     * Stub address
     */
    private String address;

    /**
     * Stub port
     */ 
    private int port;
    
    /**
     * Constructor
     * @param address Stub address
     * @param port Stub port
     */ 
    public Repository(String address, int port) {
        this.address = address;
        this.port = port;
    }

    /**
     * Increment number of passengers in the plane
     */
    public void incrementNumberInPlane(){
        ClientCom com = new ClientCom (address, port);
        while(!com.open()){  
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        } 
        Message msg = new Message(MessageType.REPO_INCREMENT_NUMBER_PLANE);
        com.writeObject(msg);
        Message inMessage = (Message) com.readObject();
        assert inMessage.getMessageType() == MessageType.STATUS_OK;
        com.close();
    }

    /**
     * Simulation's log
     */
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

    /**
     * Departure log
     */
    public void logDeparture(){
        ClientCom com = new ClientCom (address, port);
        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        Message msg = new Message(MessageType.REPO_LOG_DEPARTURE);
        com.writeObject(msg);
        Message inMessage = (Message) com.readObject();
        assert inMessage.getMessageType() == MessageType.STATUS_OK;
        com.close();
    }

    /**
     * Arriving Log
     */
    public void logArriving(){
        ClientCom com = new ClientCom (address, port);
        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        Message msg = new Message(MessageType.REPO_LOG_ARRIVAL);
        com.writeObject(msg);
        Message inMessage = (Message) com.readObject();
        assert inMessage.getMessageType() == MessageType.STATUS_OK;
        com.close();
    }

    /**
     * 
     * @param state Client's state
     */
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

    /**
     * @param state Client's state
     */
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

    /**
     * @param id Client's ID
     * @param state Client's state
     */ 
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