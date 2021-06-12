package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PlaneInterface extends Remote{

    public void hostessInformPlaneReadyToTakeOff(String state) throws RemoteException;
    
    public void serverShutdown() throws RemoteException;
}  