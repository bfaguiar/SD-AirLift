package shared;

import communication.Message;
import communication.ServerCom;

public class ArrivalAirportService extends Thread{
    private final ServerCom com;
    private final ArrivalAirportProxy proxy;

    public ArrivalAirportService(ServerCom com, ArrivalAirportProxy proxy) {
        this.com = com;
        this.proxy = proxy;
    }
    
    @Override
    public void run(){
        Message inMessage = (Message) com.readObject();
        Message outMessage = proxy.processAndReply(inMessage, com);
        com.writeObject(outMessage);
        com.close();
    }
}
