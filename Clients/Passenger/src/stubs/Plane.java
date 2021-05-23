package stubs;

import communication.ClientCom;
import communication.Message;
import communication.MessageType;

/**
 * Stub Plane
 * @author Bruno Aguiar 80177
 * @author David Rocha 84807
 */
public class Plane {

    /**
     * Stub Address
     */
    private String address;

    /**
     * Stub Port
     */
    private int port;

    /**
     * Constructor
     * @param address Stub address
     * @param port Stub port
     */
    public Plane(String address, int port) {
        this.address = address;
        this.port = port;
    }

    /**
     * 
     * @param id Client's ID
     * @param state Client's state
     */
    public void passengerBoardThePlane(int id, String state){
        
        ClientCom com = new ClientCom (address, port);           // communication channel

        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        com.writeObject(new Message(MessageType.PASSENGER_BOARD_PLANE, id, state)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.STATUS_OK;
        com.close();
    
    } 

    /**
     * 
     * @param id Client's ID
     * @param state Client's state
     */
    public void passengerWaitForEndOfFlight(int id, String state) {
        
        ClientCom com = new ClientCom (address, port);           // communication channel

        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        com.writeObject(new Message(MessageType.PASSENGER_WAIT_FOR_END, id, state)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.STATUS_OK;
        com.close();
    
    }

    /** 
     * 
     * @param id Client's ID
     */
    public void passengerExit(int id) {

        ClientCom com = new ClientCom (address, port);           // communication channel

        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        com.writeObject(new Message(MessageType.PASSENGER_EXIT, id)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.STATUS_OK;
        com.close();

    }
    
}   