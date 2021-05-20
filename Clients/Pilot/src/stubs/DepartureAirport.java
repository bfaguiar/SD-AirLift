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

    public boolean noMorePassengers(){
        
        ClientCom com = new ClientCom (address, port);           // communication channel

        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        com.writeObject(new Message(MessageType.DP_NO_MORE_PASSENGERS)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.STATUS_OK;
        com.close();
        return fromServer.getRetBool();
    }

    public void pilotInformPlaneReadyForBoarding(String state){
        ClientCom com = new ClientCom (address, port);           // communication channel

        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        com.writeObject(new Message(MessageType.PILOT_INFORM_TAKEOFF, state)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.STATUS_OK;
        com.close();
    } 

    public void pilotParkAtTransferGate(String state){
        ClientCom com = new ClientCom (address, port);           // communication channel

        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        com.writeObject(new Message(MessageType.PILOT_PARK_TRANSFER_GATE, state)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.STATUS_OK;
        com.close();
    }

    public void serviceEnd(){
        ClientCom com = new ClientCom (address, port);           // communication channel

        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        com.writeObject(new Message(MessageType.SERVICE_END)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.STATUS_OK;
        com.close();
    }
} 