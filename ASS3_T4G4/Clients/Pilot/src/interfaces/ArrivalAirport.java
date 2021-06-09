package interfaces;

public interface ArrivalAirport extends Remote{

    public void pilotFlyToDeparturePoint(int number, String state) throws RemoteException;
    
    public void serverShutdown() throws RemoteException;

