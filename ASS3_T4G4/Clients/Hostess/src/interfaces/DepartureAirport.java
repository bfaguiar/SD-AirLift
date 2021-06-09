package interfaces;

public interface DepartureAirport extends Remote {

    public boolean noMorePassengers(String state) throws RemoteException;

    public boolean isPlaneBoarded() throws RemoteException;

    public void hostessPrepareForPassBoarding(String state) throws RemoteException;

    public void hostessCheckDocuments(String state) throws RemoteException;

    public void hostessWaitForNextPassenger(String state) throws RemoteException;

    public void hostessWaitForNextFlight(String state) throws RemoteException;

    public void serverShutdown() throws RemoteException;
}   