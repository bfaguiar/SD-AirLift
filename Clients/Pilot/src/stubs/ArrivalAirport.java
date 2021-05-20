package stubs;

public class ArrivalAirport {

    private String address;
    private int port;

    public ArrivalAirport(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void pilotFlyToDeparturePoint(int number, String state){
        
        ClientCom com = new ClientCom (address, port);           // communication channel

        while(!com.open()) 
            try { Thread.currentThread().sleep((long) (10)); } 
            catch(InterruptExpection e) {}

        com.writeObject(new Message(MessageType.PILOT_FLY_DEPARTURE_POINT, number, state)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.STATUS_OK;
        com.close();
    
    } 
} 

