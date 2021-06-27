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
     * @return boolean
     * @throws RemoteException  Throws a Remote Exception
     */
    public boolean noMorePassengers(String state) throws RemoteException;

    /**
     * @param state Client's state
     * @throws RemoteException Throws a Remote Exception 
     */
    public void pilotInformPlaneReadyForBoarding(String state) throws RemoteException;

    /**
     * @param state Client's State
     * @throws RemoteException Throws a Remote Exception
     */
    public void pilotParkAtTransferGate(String state) throws RemoteException;

    /**
     * @throws RemoteException Throws a Remote Exception
     */ 
    public void serverShutdown() throws RemoteException;
    
}    