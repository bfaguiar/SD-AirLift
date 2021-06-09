package stubs;

import communication.ClientCom;
import communication.Message;
import communication.MessageType;

/**
 * Stub Repository 
 * @author Bruno Aguiar 80177
 * @author David Rocha 84807
 */
public class Repository{
    
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
     * Increment number of flights
     */
    public void incrementFlightNum(){
        ClientCom com = new ClientCom (address, port);
        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        Message msg = new Message(MessageType.REPO_INCREMENT_FLIGHT_NUM);
        com.writeObject(msg);
        Message inMessage = (Message) com.readObject();
        assert inMessage.getMessageType() == MessageType.STATUS_OK;
        com.close();
    }

    /**
     * Increment number of passengers in queue
     */
    public void incrementNumberInQueue(){
        ClientCom com = new ClientCom (address, port);
        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        Message msg = new Message(MessageType.REPO_INCREMENT_NUMBER_QUEUE);
        com.writeObject(msg);
        Message inMessage = (Message) com.readObject();
        assert inMessage.getMessageType() == MessageType.STATUS_OK;
        com.close();
    }

    /**
     * Decrement number of passengers in queue
     */
    public void decrementNumberInQueue(){
        ClientCom com = new ClientCom (address, port);
        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        Message msg = new Message(MessageType.REPO_DECREMENT_NUMBER_QUEUE);
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
     * Log for starting the boarding
     */
    public void logFlightBoardingStarting(){
        ClientCom com = new ClientCom (address, port);
        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        Message msg = new Message(MessageType.REPO_LOG_BOARDING);
        com.writeObject(msg);
        Message inMessage = (Message) com.readObject();
        assert inMessage.getMessageType() == MessageType.STATUS_OK;
        com.close();
    }

    /**
     * Log for Passenger's check
     * @param id Client's ID
     */
    public void logPassengerCheck(int id){
        ClientCom com = new ClientCom (address, port);
        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        Message msg = new Message(MessageType.REPO_LOG_PASSENGER_CHECK, id);
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
     * @param state Client's state
     * @param id Client's id
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

    /**
     * Close the logging
     */
    public void closeLog(){
        ClientCom com = new ClientCom (address, port);
        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        Message msg = new Message(MessageType.REPO_CLOSE_LOG);
        com.writeObject(msg);
        Message inMessage = (Message) com.readObject();
        assert inMessage.getMessageType() == MessageType.STATUS_OK;
        com.close();
    }
}