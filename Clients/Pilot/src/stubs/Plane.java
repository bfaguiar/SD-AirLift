package stubs;

public class Plane {

    private String address;
    private int port;

    public Plane(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public int getNumberInPlane(){
        ClientCom com = new ClientCom (address, port);           // communication channel

        while(!com.open()) 
            try { Thread.currentThread().sleep((long) (10)); } 
            catch(InterruptExpection e) {}

        com.writeObject(new Message(MessageType.DP_NO_MORE_PASSENGERS)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.STATUS_OK;
        com.close();
        return fromServer.getRetInt(); 
    } 
    
    public void pilotWaitForAllInBoard(String state){
        ClientCom com = new ClientCom (address, port);           // communication channel

        while(!com.open()) 
            try { Thread.currentThread().sleep((long) (10)); } 
            catch(InterruptExpection e) {}

        com.writeObject(new Message(MessageType.PILOT_WAIT_ALL_INBOARD, state)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.STATUS_OK;
        com.close();
    }

    public void pilotFlyToDestinationPoint(String state){
        ClientCom com = new ClientCom (address, port);           // communication channel

        while(!com.open()) 
            try { Thread.currentThread().sleep((long) (10)); } 
            catch(InterruptExpection e) {}

        com.writeObject(new Message(MessageType.PILOT_FLY_DESTINATION_POINT, state)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.STATUS_OK;
        com.close();
    }

    public void pilotAnnounceArrival(String state){
        ClientCom com = new ClientCom (address, port);           // communication channel

        while(!com.open()) 
            try { Thread.currentThread().sleep((long) (10)); } 
            catch(InterruptExpection e) {}

        com.writeObject(new Message(MessageType.PILOT_ANNOUNCE_ARRIVAL, state)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.STATUS_OK;
        com.close();
    }
    
}