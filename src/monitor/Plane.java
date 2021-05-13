package monitor;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import repo.Repository;

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

    private boolean flightComplete;

    private List<Integer> passengers = new LinkedList<Integer>();

    /**
     * Constructor
     * @param repo Instance of the General Repository of Information
     */
    public Plane(Repository repo) {
        this.repo = repo;
    } 

    public void hostessInformPlaneReadyToTakeOff(String state) {
        mutex.lock();
        repo.setHostessState(state);
        repo.log();
        boardingComplete = true;
        conditionHostessBoardingComplete.signal();
        mutex.unlock();
    }

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

    public void pilotAnnounceArrival(String state) {
        mutex.lock();
        repo.setPilotState(state); 
        repo.log();
        conditionPilotFlightComplete.signalAll();
        flightComplete = true;
        repo.logArriving(); 
        mutex.unlock(); 
    }

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

    public void passengerBoardThePlane(int id, String state) {
        mutex.lock();
        repo.setPassengerListState(id, state);
        repo.log();
        this.passengers.add(id);
        repo.incrementNumberInPlane();
        mutex.unlock();
    }

    public void passengerExit(int id){
        mutex.lock();
        this.passengers.remove(passengers.indexOf(id));
        mutex.unlock();
    }

    public int getNumberInPlane(){
        return this.passengers.size();
    }
}  