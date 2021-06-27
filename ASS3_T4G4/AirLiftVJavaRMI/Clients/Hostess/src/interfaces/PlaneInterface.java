package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Plane Interface
 * @author Bruno Aguiar 80177
 * @author David Rocha 84807
 */
public interface PlaneInterface extends Remote{

    /**
     * @param state Client's state
     * @throws RemoteException Throws a Remote Exception
     */
    public void hostessInformPlaneReadyToTakeOff(String state) throws RemoteException;
    
    /**
     * @throws RemoteException Throws a Remote Exception
     */
    public void serverShutdown() throws RemoteException;
}   