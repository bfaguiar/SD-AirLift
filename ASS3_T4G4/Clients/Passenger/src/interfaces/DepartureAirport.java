package interfaces;

public interface DepartureAirport extends Remote{

    public void passengerTravelToAirport(int id, String state) throws RemoteException;
    
    public void passengerWaitInQueue(int id, String state) throws RemoteException;

    public void passengerShowDocuments(int id, String state) throws RemoteException;
} 