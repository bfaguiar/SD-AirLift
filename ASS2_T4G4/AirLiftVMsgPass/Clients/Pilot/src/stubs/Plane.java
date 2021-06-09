package stubs;

import communication.ClientCom;
import communication.Message;
import communication.MessageType;

/**
 * Stub plane
 *  @author Bruno Aguiar 80177
 * @author David Rocha 84807
 */
public class Plane {

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
    public Plane(String address, int port) {
        this.address = address;
        this.port = port;
    }

    /**
     * Get number of passengers in plane
     * @return number of passengers in plane
     */
    public int getNumberInPlane(){
        ClientCom com = new ClientCom (address, port);           // communication channel

        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        com.writeObject(new Message(MessageType.PLANE_GET_NUMBER)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.STATUS_OK;
        com.close();
        return fromServer.getRetInt(); 
    } 
    
    /**
     * @param state Client's state
     */
    public void pilotWaitForAllInBoard(String state){
        ClientCom com = new ClientCom (address, port);           // communication channel

        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        com.writeObject(new Message(MessageType.PILOT_WAIT_ALL_INBOARD, state)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.STATUS_OK;
        com.close();
    }

    /**
     * 
     * @param state Client's state
     */
    public void pilotFlyToDestinationPoint(String state){
        ClientCom com = new ClientCom (address, port);           // communication channel

        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        com.writeObject(new Message(MessageType.PILOT_FLY_DESTINATION_POINT, state)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.STATUS_OK;
        com.close();
    }

    /**
     * 
     * @param state Client's state
     */
    public void pilotAnnounceArrival(String state){
        ClientCom com = new ClientCom (address, port);           // communication channel

        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        com.writeObject(new Message(MessageType.PILOT_ANNOUNCE_ARRIVAL, state)); 
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