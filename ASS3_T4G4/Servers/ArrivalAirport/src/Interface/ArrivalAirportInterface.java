package Interface;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ArrivalAirportInterface extends Remote {

    public void pilotFlyToDeparturePoint(int numberInPlane, String state) throws RemoteException;

    public void passengerLeaveThePlane(int id, String state) throws RemoteException;

    public void serverShutdown() throws RemoteException;
    
}

