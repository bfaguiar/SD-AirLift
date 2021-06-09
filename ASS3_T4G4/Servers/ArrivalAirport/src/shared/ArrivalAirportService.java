package shared;

import communication.Message;
import communication.ServerCom;

/**
 * Arrival Airport's Thread 
 * @author Bruno Aguiar, 80177
 * @author David Rocha, 84807
 */
public class ArrivalAirportService extends Thread {

    /**
     * Instantiation of the Server connection
     */
    private final ServerCom com;

    /**
     * Instantiation of the Server proxy
     */
    private final ArrivalAirportProxy proxy;

    /**
     * Constructor 
     * @param com instance of the Server connection
     * @param proxy instance of the Server proxy
     */
    public ArrivalAirportService(ServerCom com, ArrivalAirportProxy proxy) {
        this.com = com;
        this.proxy = proxy;
    }
    
    /**
     * Server's life cycle
     */
    @Override
    public void run() {
        Message inMessage = (Message) com.readObject();
        Message outMessage = proxy.processAndReply(inMessage, com);
        com.writeObject(outMessage);
        com.close();
    }
}
