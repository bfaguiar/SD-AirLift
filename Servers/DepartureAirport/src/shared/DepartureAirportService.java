package shared;

import communication.Message;
import communication.ServerCom;

public class DepartureAirportService extends Thread {
    private final ServerCom com;
    private final DepartureAirportProxy proxy;

    public DepartureAirportService(ServerCom com, DepartureAirportProxy proxy) {
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
