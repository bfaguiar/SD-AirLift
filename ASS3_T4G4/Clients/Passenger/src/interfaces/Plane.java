package interfaces;

public interface Plane extends Remote{

    public void passengerBoardThePlane(int id, String state) throws RemoteException;

    public void passengerWaitForEndOfFlight(int id, String state) throws RemoteException;

    public void passengerExit(int id) throws RemoteException;
    
}   