package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Departure Airport Interface 
 * @author Bruno Aguiar 80177
 * @author David Rocha 84807
 */
public interface DepartureAirportInterface extends Remote {
    
    /**
     * @param state Client's state
     * @return boolean 
     * @throws RemoteException  Throws a Remote Exception
     */
    public boolean noMorePassengers(String state) throws RemoteException;
    
    /**
     * @return boolean
     * @throws RemoteException Throws a Remote Exception
     */
    public boolean isPlaneBoarded() throws RemoteException;

    /**
     * @param state Client's state
     * @throws RemoteException Throws a Remote Exception
     */
    public void hostessPrepareForPassBoarding(String state) throws RemoteException;

    /**
     * @param state Client's state
     * @throws RemoteException Throws a Remote Exception
     */
    public void hostessCheckDocuments(String state) throws RemoteException;

    /**
     * @param state Client's state
     * @throws RemoteException Throws a Remote Exception
     */
    public void hostessWaitForNextPassenger(String state) throws RemoteException;

    /**
     * @param state Client's state
     * @throws RemoteException Throws a Remote Exception
     */
    public void hostessWaitForNextFlight(String state) throws RemoteException;

    /**
     * @throws RemoteException Throws a Remote Exception
     */
    public void serverShutdown() throws RemoteException;
}   