package stubs;

import communication.ClientCom;
import communication.Message;
import communication.MessageType;

public class DepartureAirport {

    private String address;
    private int port;
    
    public DepartureAirport(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public boolean noMorePassengers() {

        ClientCom com = new ClientCom (address, port);           // communication channel
   
        while(!com.open()) 
            try { Thread.currentThread().sleep((long) (10)); } 
            catch(InterruptExpection e) {}

        com.writeObject(new Message(MessageType.DP_NO_MORE_PASSENGERS)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.RETURN_NO_PASSENGERS;
        com.close();
        return fromServer.getRetBool();
         // return
    } 

    public boolean isPlaneBoarded(){ 
        
        ClientCom com = new ClientCom (address, port);           // communication channel
   
        while(!com.open()) 
            try { Thread.currentThread().sleep((long) (10)); } 
            catch(InterruptExpection e) {}

        com.writeObject(new Message(MessageType.DP_IS_PLANE_BOARDED)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.RETURN_PLANE_BOARDED;
        com.close();
        return fromServer.getRetBool();
    
    }

    public void hostessPrepareForPassBoarding(String state){

        ClientCom com = new ClientCom (address, port);           // communication channel
   
        while(!com.open()) 
            try { Thread.currentThread().sleep((long) (10)); } 
            catch(InterruptExpection e) {}

        com.writeObject(new Message(MessageType.HOSTESS_PREPARE_FOR_BOARDING, state)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.STATUS_OK;
        com.close();

    }

    public void hostessCheckDocuments(String state){

        ClientCom com = new ClientCom (address, port);           // communication channel
   
        while(!com.open()) 
            try { Thread.currentThread().sleep((long) (10)); } 
            catch(InterruptExpection e) {}

        com.writeObject(new Message(MessageType.HOSTESS_CHECK_DOCUMENTS, state)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.STATUS_OK;
        com.close();

    }

    public void hostessWaitForNextPassenger(String state){

        ClientCom com = new ClientCom (address, port);           // communication channel
   
        while(!com.open()) 
            try { Thread.currentThread().sleep((long) (10)); } 
            catch(InterruptExpection e) {}

        com.writeObject(new Message(MessageType.HOSTESS_WAIT_FOR_NEXT_PASSENGER, state)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.STATUS_OK;
        com.close();

    }

    public void hostessWaitForNextFlight(String state){

        ClientCom com = new ClientCom (address, port);           // communication channel
   
        while(!com.open()) 
            try { Thread.currentThread().sleep((long) (10)); } 
            catch(InterruptExpection e) {}

        com.writeObject(new Message(MessageType.HOSTESS_WAIT_FOR_NEXT_FLIGHT, state)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.STATUS_OK;
        com.close();
        
    }
}  