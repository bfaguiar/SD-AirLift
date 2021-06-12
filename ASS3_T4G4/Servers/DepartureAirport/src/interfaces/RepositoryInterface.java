package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RepositoryInterface extends Remote{


    public void setHostessState(String state) throws RemoteException; 

    public void setPilotState(String state) throws RemoteException; 

    public void setPassengerListState(int index, String passegerState) throws RemoteException;

    public void incrementNumberInQueue() throws RemoteException;

    public void decrementNumberInQueue() throws RemoteException;
    
    public void decrementNumberInPlane() throws RemoteException;

    public void incrementNumberInPlane() throws RemoteException;

    public void incrementNumberAtDestination() throws RemoteException;

    public int getFlightNum() throws RemoteException;

    public void setFlightNum(int flightNum) throws RemoteException;

    public void incrementFlightNum() throws RemoteException;

    public void closelog() throws RemoteException;  

    public void logEntities() throws RemoteException;

    public void log() throws RemoteException;

    public void logFlightBoardingStarting() throws RemoteException;

    public void logPassengerCheck(int id) throws RemoteException;

    public void logDeparture() throws RemoteException;

    public void logArriving() throws RemoteException;

    public void logReturning() throws RemoteException;

    //#public void serverShutdown() throws RemoteException;

    
}

