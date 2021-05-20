package stubs;

import communication.ClientCom;
import communication.Message;
import communication.MessageType;

public class ArrivalAirport {

    private String address;
    private int port;

    public ArrivalAirport(String address, int port) {
        this.address = address;
        this.port = port;    
    }

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