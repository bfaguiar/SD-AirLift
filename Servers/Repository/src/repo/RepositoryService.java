package repo;

import communication.Message;
import communication.ServerCom;

public class RepositoryService extends Thread{
    private final ServerCom com;
    private final RepositoryProxy proxy;

    public RepositoryService(ServerCom com, RepositoryProxy proxy) {
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
