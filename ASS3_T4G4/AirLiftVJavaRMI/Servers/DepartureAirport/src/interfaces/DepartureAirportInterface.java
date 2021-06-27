package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Departure Airport Interface
 * @author Bruno Aguiar, 80177
 * @author David Rocha, 84807
 */
public interface DepartureAirportInterface extends Remote {

    /**
     * @param state Client's state
     * @throws RemoteException Throws a Remote Exception
     */
    public void pilotParkAtTransferGate(String state) throws RemoteException;
    
    /**
     * @param state Client's state
     * @throws RemoteException Throws a Remote Exception
     */
    public void pilotInformPlaneReadyForBoarding(String state) throws RemoteException;

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
     * @param id Client's ID
     * @param state Client's ID 
     * @throws RemoteException Throws a Remote Exception
     */
    public void passengerTravelToAirport(int id, String state) throws RemoteException ;

    /**
     * @param id Client's ID
     * @param state Client's ID
     * @throws RemoteException Throws a Remote Exception
     */
    public void passengerWaitInQueue(int id, String state) throws RemoteException; 

    /**
     * @param id Client's ID
     * @param state Client's ID
     * @throws RemoteException Throws a Remote Exception
     */
    public void passengerShowDocuments(int id, String state) throws RemoteException;

    /**
     * @return boolean...
     * @throws RemoteException Throws a Remote Exception
     */
    public boolean isPlaneBoarded() throws RemoteException;

    /**
     * @param state Client's state
     * @return Boolean.
     * @throws RemoteException Throws a Remote Exception
     */
    public boolean noMorePassengers(String state) throws RemoteException ;

    /**
     * @throws RemoteException Throws a Remote Exception
     */
    public void serverShutdown() throws RemoteException ;
    
}  


