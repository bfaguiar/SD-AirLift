package interfaces;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Register Interface
 * @author Bruno Aguiar, 80177
 * @author David Rocha, 84807
 */
public interface RegisterInterface extends Remote {

    /**
     * @param name name
     * @param ref reference
     * @throws RemoteException Throws a Remote Exception
     * @throws AlreadyBoundException Throws an Exception
     */
    public void bind(String name, Remote ref) throws RemoteException, AlreadyBoundException;

    /**
     * @param name name
     * @throws RemoteException Throws a Remote Exception
     * @throws NotBoundException Throws an  Exception
     */
    public void unbind(String name) throws RemoteException, NotBoundException;

    /**
     * 
     * @param name Name
     * @param ref Reference
     * @throws RemoteException Throws a Remote Exception
     */
    public void rebind(String name, Remote ref) throws RemoteException;
    
}
 

