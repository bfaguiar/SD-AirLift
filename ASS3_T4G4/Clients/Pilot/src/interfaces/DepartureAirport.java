package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DepartureAirport extends Remote{

    public boolean noMorePassengers(String state) throws RemoteException;

    public void pilotInformPlaneReadyForBoarding(String state) throws RemoteException;

    public void pilotParkAtTransferGate(String state) throws RemoteException;

    public void serverShutdown() throws RemoteException;
} 