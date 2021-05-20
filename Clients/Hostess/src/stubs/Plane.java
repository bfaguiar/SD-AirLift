package stubs;

import communication.ClientCom;
import communication.Message;
import communication.MessageType;

public class Plane{

    private String address;
    private int port;

    public Plane(String address, int port) {
        this.address = address;
        this.port = port;
    }

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