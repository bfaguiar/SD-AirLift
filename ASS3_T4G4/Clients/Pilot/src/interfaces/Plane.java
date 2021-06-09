package interfaces;

public interface Plane extends Remote{

    public int getNumberInPlane() throws RemoteException;
    
    public void pilotWaitForAllInBoard(String state) throws RemoteException;

    public void pilotFlyToDestinationPoint(String state) throws RemoteException;

    public void pilotAnnounceArrival(String state) throws RemoteException;
    
    public void serverShutdown() throws RemoteException;
} 