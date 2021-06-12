package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DepartureAirportInterface extends Remote {

    public void pilotParkAtTransferGate(String state) throws RemoteException;
    
    public void pilotInformPlaneReadyForBoarding(String state) throws RemoteException;

    public void hostessPrepareForPassBoarding(String state) throws RemoteException;

    public void hostessCheckDocuments(String state) throws RemoteException;

    public void hostessWaitForNextPassenger(String state) throws RemoteException;

    public void hostessWaitForNextFlight(String state) throws RemoteException;

    public void passengerTravelToAirport(int id, String state) throws RemoteException ;

    public void passengerWaitInQueue(int id, String state) throws RemoteException; 

    public void passengerShowDocuments(int id, String state) throws RemoteException;

    public boolean isPlaneBoarded() throws RemoteException;

    public boolean noMorePassengers(String state) throws RemoteException ;

    public void serverShutdown() throws RemoteException ;
    
} 


