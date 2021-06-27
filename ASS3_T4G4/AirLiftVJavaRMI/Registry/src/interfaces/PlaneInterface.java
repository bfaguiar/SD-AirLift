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
     * @param state Client's state
     * @throws RemoteException Throws a Remote Exception
     */
    public void hostessInformPlaneReadyToTakeOff(String state) throws RemoteException;

    /**
     * @param state Client's state
     * @throws RemoteException Throws a Remote Exception
     */
    public void pilotWaitForAllInBoard(String state) throws RemoteException;

    /**
     * @param state Client's state
     * @throws RemoteException Throws a Remote Exception
     */
    public void pilotFlyToDestinationPoint(String state) throws RemoteException;

    /**
     * @param state Client's state
     * @throws RemoteException Throws a Remote Exception
     */
    public void pilotAnnounceArrival(String state) throws RemoteException;

    /**
     * @param id Client's ID
     * @param state Client's state
     * @throws RemoteException Throws a Remote Exception
     */
    public void passengerWaitForEndOfFlight(int id, String state) throws RemoteException;

    /**
     * @param id Client's ID
     * @param state Client's state
     * @throws RemoteException Throws a Remote Exception
     */
    public void passengerBoardThePlane(int id, String state) throws RemoteException;

    /**
     * @param id Client's ID
     * @throws RemoteException Throws a Remote Exception
     */
    public void passengerExit(int id) throws RemoteException;

    /**
     * @return Integer
     * @throws RemoteException Throws a Remote Exception
     */
     public int getNumberInPlane() throws RemoteException;

     /**
      * @throws RemoteException Throws a Remote Exception
      */
     public void serverShutdown() throws RemoteException;

}




