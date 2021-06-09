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
     * @param state Client's state
     */
    public void hostessInformPlaneReadyToTakeOff(String state){
        
        ClientCom com = new ClientCom (address, port);           // communication channel
   
        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        com.writeObject(new Message(MessageType.HOSTESS_INFORM_PLANE_READY, state)); 
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