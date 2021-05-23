package stubs;

import communication.ClientCom;
import communication.Message;
import communication.MessageType;

/**
 * Stub DepartureAirport
 * @author Bruno Aguiar 80177
 * @author David Rocha 84807
 */
public class DepartureAirport {

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
    public DepartureAirport(String address, int port) {
        this.address = address;
        this.port = port;
    }

    /**
     * @param state Client's state
     * @return boolean to check if there are more passengers left to flight
     */
    public boolean noMorePassengers(String state){
        
        ClientCom com = new ClientCom (address, port);           // communication channel

        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        com.writeObject(new Message(MessageType.DP_NO_MORE_PASSENGERS, state)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.STATUS_OK;
        com.close();
        return fromServer.getRetBool();
    }

    /**
     * @param state Client's state
     */
    public void pilotInformPlaneReadyForBoarding(String state){
        ClientCom com = new ClientCom (address, port);           // communication channel

        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        com.writeObject(new Message(MessageType.PILOT_INFORM_TAKEOFF, state)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.STATUS_OK;
        com.close();
    } 

    /**
     * 
     * @param state Client's state
     */
    public void pilotParkAtTransferGate(String state){
        ClientCom com = new ClientCom (address, port);           // communication channel

        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        com.writeObject(new Message(MessageType.PILOT_PARK_TRANSFER_GATE, state)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.STATUS_OK;
        com.close();
    }

    /**
     * Server shutdown
     */
    public void serverShutdown(){
        ClientCom com = new ClientCom (address, port);           // communication channel

        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        com.writeObject(new Message(MessageType.SERVER_SHUTDOWN)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.STATUS_OK;
        com.close();
    }
} 