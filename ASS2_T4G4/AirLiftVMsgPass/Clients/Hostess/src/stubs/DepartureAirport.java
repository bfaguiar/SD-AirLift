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
     * 
     * @param state Client's state
     * @return boolean to indicate if there are more passengers waiting to flight 
     */
    public boolean noMorePassengers(String state) {

        ClientCom com = new ClientCom (address, port);           // communication channel
   
        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        com.writeObject(new Message(MessageType.DP_NO_MORE_PASSENGERS, state)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.RETURN_NO_PASSENGERS;
        com.close();
        return fromServer.getRetBool();
         // return
    } 

    /**
     * @return boolean to indicate if the plane is boarded
     */
    public boolean isPlaneBoarded(){ 
        
        ClientCom com = new ClientCom (address, port);           // communication channel
   
        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        com.writeObject(new Message(MessageType.DP_IS_PLANE_BOARDED)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.RETURN_PLANE_BOARDED;
        com.close();
        return fromServer.getRetBool();
    
    }

    /**
     * @param state Client's state
     */
    public void hostessPrepareForPassBoarding(String state) {

        ClientCom com = new ClientCom (address, port);           // communication channel
   
        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        com.writeObject(new Message(MessageType.HOSTESS_PREPARE_FOR_BOARDING, state)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.STATUS_OK;
        com.close();

    }

    /**
     * 
     * @param state client's state
     */
    public void hostessCheckDocuments(String state) {

        ClientCom com = new ClientCom (address, port);           // communication channel
   
        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        com.writeObject(new Message(MessageType.HOSTESS_CHECK_DOCUMENTS, state)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.STATUS_OK;
        com.close();

    }

    /**
     * 
     * @param state Client's state
     */
    public void hostessWaitForNextPassenger(String state) {

        ClientCom com = new ClientCom (address, port);           // communication channel
   
        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        com.writeObject(new Message(MessageType.HOSTESS_WAIT_FOR_NEXT_PASSENGER, state)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.STATUS_OK;
        com.close();

    }

    /**
     * @param state Client's state
     */
    public void hostessWaitForNextFlight(String state) {

        ClientCom com = new ClientCom (address, port);           // communication channel
   
        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        com.writeObject(new Message(MessageType.HOSTESS_WAIT_FOR_NEXT_FLIGHT, state)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.STATUS_OK;
        com.close();
        
    }

    /**
     * Server shutdown
     */
    public void serverShutdown() {
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