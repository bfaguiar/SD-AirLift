package shared;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import main.Initializer;
import interfaces.RepositoryInterface;
import interfaces.PlaneInterface;

/**
 * Plane's shared region
 * @author Bruno Aguiar, 80177
 * @author David Rocha, 84807
 */
public class Plane implements PlaneInterface {

    /**
     * Declaration of the General Repository of Information
     */
    private RepositoryInterface repo;

    /**
     * Instantiation of a thread syncrhonization mechanism 
     * @see ReentrantLock
     */
    private ReentrantLock mutex = new ReentrantLock();   

    /**
     * Instantiation of a Condition Variable for the Pilot
     * @see Condition
     */
    private Condition conditionHostessBoardingComplete = mutex.newCondition();  

    private Condition conditionPilotFlightComplete= mutex.newCondition();  

    /**
     * Declaration of a boolean variable to inform the pilot that the boarding is complete
     */
    private boolean boardingComplete; 

    /**
     * Boolean to indicate the flight completion
     */
    private boolean flightComplete;

    /**
     * List of passengers
     */
    private List<Integer> passengers = new LinkedList<Integer>();

    /**
     * Shutdown status 
     */
    int shutdown = 0;

    /**
     * Constructor
     * @param repo Instance of the General Repository of Information
     */
    public Plane(RepositoryInterface repo) {
        this.repo = repo;
    } 

    /**
     * @param state Client's State
     */
    @Override
    public void hostessInformPlaneReadyToTakeOff(String state) throws RemoteException {
        mutex.lock();
        repo.setHostessState(state);
        repo.log();
        boardingComplete = true;
        conditionHostessBoardingComplete.signal();
        mutex.unlock();
    }

    /**
     * 
     * @param state Client's state
     */
    @Override
    public void pilotWaitForAllInBoard(String state) throws RemoteException {
        mutex.lock();
        repo.setPilotState(state); 
        repo.log();
        flightComplete = false;
        try{
            while(!boardingComplete)
                conditionHostessBoardingComplete.await();
        } catch(InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        boardingComplete = false;
        repo.logDeparture();    
        mutex.unlock();
    }

    /**
     * 
     * @param state Client's state
     */
    @Override
    public void pilotFlyToDestinationPoint(String state) throws RemoteException {
        mutex.lock();
        repo.setPilotState(state); 
        repo.log();
        try {
            Thread.sleep((long) ((Math.random()*1000)+1));
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        mutex.unlock();
    }

    /**
     * 
     * @param state Client's state
     */
    @Override
    public void pilotAnnounceArrival(String state) throws RemoteException {
        mutex.lock();
        repo.setPilotState(state); 
        repo.log();
        conditionPilotFlightComplete.signalAll();
        flightComplete = true;
        repo.logArriving(); 
        mutex.unlock(); 
    } 

    /**
     * 
     * @param id Client's ID
     * @param state Client's state
     */
    @Override
    public void passengerWaitForEndOfFlight(int id, String state) throws RemoteException {
        mutex.lock();
        try {
            while(!flightComplete)
                conditionPilotFlightComplete.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        repo.setPassengerListState(id, state);
        repo.log();
        mutex.unlock();
    } 

    /**
     * 
     * @param id Client's ID
     * @param state Client's state
     */
    @Override
    public void passengerBoardThePlane(int id, String state) throws RemoteException {
        mutex.lock();
        repo.setPassengerListState(id, state);
        repo.log();
        this.passengers.add(id);
        repo.incrementNumberInPlane();
        mutex.unlock();
    }

    /**
     * 
     * @param id Client's ID
     */
    @Override
    public void passengerExit(int id) throws RemoteException {
        mutex.lock();
        this.passengers.remove(passengers.indexOf(id));
        mutex.unlock();
    }

    /**
     * @return number of passengers in the plane
     */
    @Override
    public int getNumberInPlane() throws RemoteException {
        return this.passengers.size();
    }
    
    /**
     * Server shutdown ..
     */
    @Override
    public void serverShutdown() throws RemoteException {
        shutdown++;
        if(shutdown >= 2)
            Initializer.end = true;
    }
}  