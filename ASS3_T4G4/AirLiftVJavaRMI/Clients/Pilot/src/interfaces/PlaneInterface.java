package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Plane Interface 
 * @author Bruno Aguiar, 80177
 * @author David Rocha, 84807
 */
public interface PlaneInterface extends Remote {

    /**
     * @return Integer 
     * @throws RemoteException Throws a Remote Exception
     */
    public int getNumberInPlane() throws RemoteException;
    
    /**
     * @param state Client's State
     * @throws RemoteException Throws a Remote Exception
     */
    public void pilotWaitForAllInBoard(String state) throws RemoteException;

    /**
     * @param state Client's State
     * @throws RemoteException Throws a Remote Exception
     */
    public void pilotFlyToDestinationPoint(String state) throws RemoteException;

    /**
     * @param state Client's state
     * @throws RemoteException Throws a Remote Exception
     */
    public void pilotAnnounceArrival(String state) throws RemoteException;
    
    /**
     * @throws RemoteException Throws a Remote Exception
     */
    public void serverShutdown() throws RemoteException;

} 

 