package Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PlaneInterface extends Remote {

    public void hostessInformPlaneReadyToTakeOff(String state) throws RemoteException;

    public void pilotWaitForAllInBoard(String state) throws RemoteException;

    public void pilotFlyToDestinationPoint(String state) throws RemoteException;

    public void pilotAnnounceArrival(String state) throws RemoteException;

    public void passengerWaitForEndOfFlight(int id, String state) throws RemoteException;

    public void passengerBoardThePlane(int id, String state) throws RemoteException;

    public void passengerExit(int id) throws RemoteException;

     public int getNumberInPlane() throws RemoteException;

     public void serverShutdown() throws RemoteException;

}

