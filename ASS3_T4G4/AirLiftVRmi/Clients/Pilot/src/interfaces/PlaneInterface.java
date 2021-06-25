package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PlaneInterface extends Remote{

    public int getNumberInPlane() throws RemoteException;
    
    public void pilotWaitForAllInBoard(String state) throws RemoteException;

    public void pilotFlyToDestinationPoint(String state) throws RemoteException;

    public void pilotAnnounceArrival(String state) throws RemoteException;
    
    public void serverShutdown() throws RemoteException;
} 