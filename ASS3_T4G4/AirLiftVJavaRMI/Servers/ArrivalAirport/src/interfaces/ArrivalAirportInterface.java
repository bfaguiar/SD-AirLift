package interfaces;
import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * Arrival Airport Interface
 * @author Bruno Aguiar, 80177
 * @author David Rocha, 84807
 */
public interface ArrivalAirportInterface extends Remote {

    /**
     * @param numberInPlane Number of Passengers in the Plane
     * @param state Client's state
     * @throws RemoteException Throws a Remote Exception
     */
    public void pilotFlyToDeparturePoint(int numberInPlane, String state) throws RemoteException;

    /**
     * @param id Client;s ID
     * @param state Client's state
     * @throws RemoteException Throws a Remote Exception
     */
    public void passengerLeaveThePlane(int id, String state) throws RemoteException;

    /**
     * @throws RemoteException Throws a Remote Exception
     */ 
    public void serverShutdown() throws RemoteException;
    
}





