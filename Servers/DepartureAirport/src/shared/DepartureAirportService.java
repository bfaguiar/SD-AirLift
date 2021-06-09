package shared;

import communication.Message;
import communication.ServerCom;

/**
 * Departure Airport's thread
 * @author Bruno Aguiar 80177
 * @author David Rocha 84807 
 */
public class DepartureAirportService extends Thread {
    
    /**
     * Instantiation of the Server connection
     */
    private final ServerCom com;
    
    /**
     * Instantiation of the Server proxy
     */
    private final DepartureAirportProxy proxy;

    /**
     * 
     * @param com instance of the Server connection
     * @param proxy instance of the Server proxy
     */
    public DepartureAirportService(ServerCom com, DepartureAirportProxy proxy) {
        this.com = com;
        this.proxy = proxy;
    }

    /**
     * Server's life cycle.
     */
    @Override
    public void run(){
        Message inMessage = (Message) com.readObject();
        Message outMessage = proxy.processAndReply(inMessage, com);
        com.writeObject(outMessage);
        com.close();
    }
}
