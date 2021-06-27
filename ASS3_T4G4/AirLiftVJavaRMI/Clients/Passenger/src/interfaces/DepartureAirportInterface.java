package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Departure airport Interface
 * @author Bruno Aguiar, 80177
 * @author David Rocha, 84807
 */ 
public interface DepartureAirportInterface extends Remote{

    /**
     * @param id Client's ID
     * @param state Client's state
     * @throws RemoteException  Throws a Remote Exception
     */
    public void passengerTravelToAirport(int id, String state) throws RemoteException;
    
    /**
     * @param id Client's ID
     * @param state Client's state
     * @throws RemoteException Throws a Remote Exception
     */
    public void passengerWaitInQueue(int id, String state) throws RemoteException;

    /**
     * @param id Client's ID
     * @param state Client's state
     * @throws RemoteException Throws a Remote Exception
     */
    public void passengerShowDocuments(int id, String state) throws RemoteException;
} 