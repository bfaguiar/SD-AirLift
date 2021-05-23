package shared;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import main.Initializer;
import stubs.Repository;

/**
 * Plane's shared region
 * @author Bruno Aguiar, 80177
 * @author David Rocha, 84807
 */
public class Plane {

    /**
     * Declaration of the General Repository of Information
     */
    private Repository repo;

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
    public Plane(Repository repo) {
        this.repo = repo;
    } 

    /**
     * @param state Client's State
     */
    public void hostessInformPlaneReadyToTakeOff(String state) {
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
    public void pilotWaitForAllInBoard(String state) {
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
    public void pilotFlyToDestinationPoint(String state) {
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
    public void pilotAnnounceArrival(String state) {
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
    public void passengerWaitForEndOfFlight(int id, String state) {
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
    public void passengerBoardThePlane(int id, String state) {
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
    public void passengerExit(int id){
        mutex.lock();
        this.passengers.remove(passengers.indexOf(id));
        mutex.unlock();
    }

    /**
     * @return number of passengers in the plane
     */
    public int getNumberInPlane(){
        return this.passengers.size();
    }
    
    /**
     * Server shutdown ..
     */
    public void serverShutdown(){
        shutdown++;
        if(shutdown >= 2)
            Initializer.end = true;
    }
}  