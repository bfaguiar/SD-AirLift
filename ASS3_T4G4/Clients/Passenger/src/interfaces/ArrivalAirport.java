package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ArrivalAirport extends Remote{

    public void passengerLeaveThePlane(int id, String state) throws RemoteException;
} 