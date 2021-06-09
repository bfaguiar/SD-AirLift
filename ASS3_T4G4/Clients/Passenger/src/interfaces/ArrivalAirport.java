package interfaces;

public interface ArrivalAirport extends Remote{

    public void passengerLeaveThePlane(int id, String state) throws RemoteException;
} 