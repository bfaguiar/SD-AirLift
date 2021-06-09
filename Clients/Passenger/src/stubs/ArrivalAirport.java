package stubs;

import communication.ClientCom;
import communication.Message;
import communication.MessageType;

/**
 * Stub ArrivalAirport
 * @author Bruno Aguiar 80177
 * @author David Rocha 84807
 */
public class ArrivalAirport {

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
    public ArrivalAirport(String address, int port) {
        this.address = address;
        this.port = port;    
    }

    /**
     * @param id Client's ID
     * @param state Client's state
     */
    public void passengerLeaveThePlane(int id, String state){
        
        ClientCom com = new ClientCom (address, port);           // communication channel
   
        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        com.writeObject(new Message(MessageType.PASSENGER_LEAVE_PLANE, id, state)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.STATUS_OK;
        com.close();
    
    } 
} 