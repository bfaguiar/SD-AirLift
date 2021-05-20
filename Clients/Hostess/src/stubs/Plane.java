package stubs;

public class Plane{

    private String address;
    private int port;

    public Plane(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void hostessInformPlaneReadyToTakeOff(String state){
        
        ClientCom com = new ClientCom (address, port);           // communication channel
   
        while(!com.open()) 
            try { Thread.currentThread().sleep((long) (10)); } 
            catch(InterruptExpection e) {}

        com.writeObject(new Message(MessageType.HOSTESS_INFORM_PLANE_READY, state)); 
        Message fromServer = (Message) com.readObject(); 
        assert fromServer.getMessageType() == MessageType.STATUS_OK;
        com.close();    
                                             // HOSTESS_WAIT_FOR_NEXT_FLIGHT
    }
    
}  