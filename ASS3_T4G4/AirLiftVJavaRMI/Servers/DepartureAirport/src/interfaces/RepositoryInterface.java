package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Repository Interface
 * @author Bruno Aguiar, 80177
 * @author David Rocha, 84807
 */
public interface RepositoryInterface extends Remote {

    /**
     * @param state Client's state
     * @throws RemoteException Throws a Remote Exception
     */
    public void setHostessState(String state) throws RemoteException; 

    /**
     * @param state Client's state
     * @throws RemoteException Throws a Remote Exception
     */
    public void setPilotState(String state) throws RemoteException; 

    /**
     * @param index index
     * @param passegerState Passenger's State 
     * @throws RemoteException Throws a Remote Exception
     */
    public void setPassengerListState(int index, String passegerState) throws RemoteException;

    /**
     * @throws RemoteException Throws a Remote Exception
     */
    public void incrementNumberInQueue() throws RemoteException;

    /**
     * @throws RemoteException Throws a Remote Exception
     */
    public void decrementNumberInQueue() throws RemoteException;
    
    /**
     * @throws RemoteException Throws a Remote Exception
     */
    public void decrementNumberInPlane() throws RemoteException;

    /**
     * @throws RemoteException Throws a Remote Exception
     */
    public void incrementNumberInPlane() throws RemoteException;

    /**
     * @throws RemoteException Throws a Remote Exception
     */
    public void incrementNumberAtDestination() throws RemoteException;

    /**
     * @return integer
     * @throws RemoteException Throws a Remote Exception
     */
    public int getFlightNum() throws RemoteException;

    /**
     * @param flightNum Flight Number.
     * @throws RemoteException Throws a Remote Exception
     */
    public void setFlightNum(int flightNum) throws RemoteException;

    /**
     * @throws RemoteException Throws a Remote Exception
     */
    public void incrementFlightNum() throws RemoteException;

    /**
     * @throws RemoteException Throws a Remote Exception
     */
    public void closelog() throws RemoteException;  

    /**
     * @throws RemoteException Throws a Remote Exception
     */
    public void logEntities() throws RemoteException;

    /**
     * @throws RemoteException Throws a Remote Exception
     */
    public void log() throws RemoteException;

    /**
     * @throws RemoteException Throws a Remote Exception
     */
    public void logFlightBoardingStarting() throws RemoteException;

    /**
     * @param id Client's ID
     * @throws RemoteException Throws a Remote Exception
     */
    public void logPassengerCheck(int id) throws RemoteException;

    /**
     * @throws RemoteException Throws a Remote Exception
     */
    public void logDeparture() throws RemoteException;

    /**
     * @throws RemoteException Throws a Remote Exception
     */
    public void logArriving() throws RemoteException;

    /**
     * @throws RemoteException Throws a Remote Exception
     */
    public void logReturning() throws RemoteException; 
}

