package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ArrivalAirportInterface extends Remote{

    public void pilotFlyToDeparturePoint(int number, String state) throws RemoteException;
    
    public void serverShutdown() throws RemoteException;
}

