package stubs;

import communication.ClientCom;
import communication.Message;
import communication.MessageType;

public class DepartureAirport {

    private String address;
    private int port ;

    public DepartureAirport(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void passengerTravelToAirport(int id, String state){

        ClientCom com = new ClientCom (address, port);           // communication channel

        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        com.writeObject(new Message(MessageType.PASSENGER_TRAVEL_AIRPORT, id, state)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.STATUS_OK;
        com.close();

    }

    public void passengerWaitInQueue(int id, String state){
        ClientCom com = new ClientCom (address, port);           // communication channel

        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        com.writeObject(new Message(MessageType.PASSENGER_WAIT_QUEUE, id, state)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.STATUS_OK;
        com.close();
    }

    public void passengerShowDocuments(int id, String state){
        ClientCom com = new ClientCom (address, port);           // communication channel

        while(!com.open()){
            try {
                Thread.currentThread().sleep ((long) (10));
            } catch (InterruptedException ex) {
            }
        }
        com.writeObject(new Message(MessageType.PASSENGER_SHOW_DOCUMENTS, id, state)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.STATUS_OK;
        com.close();
    }
} 