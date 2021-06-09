package shared;

import communication.Message;
import communication.ServerCom;

/**
 * Plane's Thread
 * @author Bruno Aguiar, 80177
 * @author David Rocha, 84807
 */
public class PlaneService extends Thread {

    /**
     * Instantiation of the Server connection
     */
    private final ServerCom com;
    
    /**
     * Instantiation of the Server proxy
     */
    private final PlaneProxy proxy;

    /**
     * 
     * @param com instance of the Server connection
     * @param proxy instance of the Server proxy
     */
    public PlaneService(ServerCom com, PlaneProxy proxy) {
        this.com = com;
        this.proxy = proxy;
    }

    /**
     * Server's Life Cycle
     */
    @Override
    public void run(){
        Message inMessage = (Message) com.readObject();
        Message outMessage = proxy.processAndReply(inMessage, com);
        com.writeObject(outMessage);
        com.close();
    }
}
