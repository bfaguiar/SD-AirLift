package Interface;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RegisterInterface extends Remote {

    public void bind(String name, Remote ref) throws RemoteException, AlreadyBoundException;

    public void unbind(String name) throws RemoteException, NotBoundException;

    public void rebind(String name, Remote ref) throws RemoteException;
    
}



