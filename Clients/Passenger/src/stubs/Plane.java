package stubs;

public class Plane {

    private String address;
    private int port;

    public Plane(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void passengerBoardThePlane(int id, String state){
        
        ClientCom com = new ClientCom (address, port);           // communication channel

        while(!com.open()) 
            try { Thread.currentThread().sleep((long) (10)); } 
            catch(InterruptExpection e) {}

        com.writeObject(new Message(MessageType.PASSENGER_BOARD_PLANE, id, state)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.STATUS_OK;
        com.close();
    
    } 

    public void passengerWaitForEndOfFlight(int id, String state){
        
        ClientCom com = new ClientCom (address, port);           // communication channel

        while(!com.open()) 
            try { Thread.currentThread().sleep((long) (10)); } 
            catch(InterruptExpection e) {}

        com.writeObject(new Message(MessageType.PASSENGER_WAIT_FOR_END, id, state)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.STATUS_OK;
        com.close();
    
    }

    public void passengerExit(int id){

        ClientCom com = new ClientCom (address, port);           // communication channel

        while(!com.open()) 
            try { Thread.currentThread().sleep((long) (10)); } 
            catch(InterruptExpection e) {}

        com.writeObject(new Message(MessageType.PASSENGER_EXIT, id)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.STATUS_OK;
        com.close();

    }
    
}   