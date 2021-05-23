package repo;

import communication.Message;
import communication.ServerCom;

/**
 * Repositoty's Thread
 */
public class RepositoryService extends Thread {

    /**
     * Instantiation of the Server Connection
     * 
     */
    private final ServerCom com;
    
    /**
     * Instantiation of the Server's proxy
     */
    private final RepositoryProxy proxy;

    /**
     * Constructor
     * @param com instance of the Server Connection
     * @param proxy instance of the server's proxy
     */
    public RepositoryService(ServerCom com, RepositoryProxy proxy) {
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
