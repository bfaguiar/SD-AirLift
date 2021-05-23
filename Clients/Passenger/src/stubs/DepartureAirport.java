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
     * Stub Address
     */
    private String address;
    
    /**
     * Stub port
     */
    private int port ;

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
     * 
     * @param id Client's ID
     * @param state Client's state
     */
    public void passengerTravelToAirport(int id, String state){

        ClientCom com = new ClientCom (address, port);           // communication channel

        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        com.writeObject(new Message(MessageType.PASSENGER_TRAVEL_AIRPORT, id, state)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.STATUS_OK;
        com.close();

    }
    
    /**
     * 
     * @param id Client's ID
     * @param state Client's state
     */
    public void passengerWaitInQueue(int id, String state){
        ClientCom com = new ClientCom (address, port);           // communication channel

        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        com.writeObject(new Message(MessageType.PASSENGER_WAIT_QUEUE, id, state)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.STATUS_OK;
        com.close();
    }

    /**
     * 
     * @param id Client's ID
     * @param state Client's state
     */
    public void passengerShowDocuments(int id, String state){
        ClientCom com = new ClientCom (address, port);           // communication channel

        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        com.writeObject(new Message(MessageType.PASSENGER_SHOW_DOCUMENTS, id, state)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.STATUS_OK;
        com.close();
    }
} 