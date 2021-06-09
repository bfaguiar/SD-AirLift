package interfaces;

public interface Plane extends remote{

    public void hostessInformPlaneReadyToTakeOff(String state) throws RemoteException;
    
    public void serverShutdown() throws RemoteException;
}  