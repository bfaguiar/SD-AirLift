package monitor;

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
    private Condition conditionPilot = mutex.newCondition();  

    /**
     * Declaration of a boolean variable to inform the pilot that the boarding is complete
     */
    private boolean boardingComplete;

    /**
     * Constructor
     * @param repo Instance of the General Repository of Information
     */
    public Plane(Repository repo) {
        this.repo = repo;
    } 

    public void hostessInformPlaneReadyToTakeOff() {
        mutex.lock();
        repo.log();
        boardingComplete = true;
        conditionPilot.signal();
        mutex.unlock();
    }

    public void pilotWaitForAllInBoard() {
        mutex.lock();
        repo.log();
        try{
            while(!boardingComplete)
                conditionPilot.await();
        } catch(InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        boardingComplete = false;
        repo.logDeparture();    
        mutex.unlock();
    }

    public void pilotFlyToDestinationPoint() {
        mutex.lock();
        repo.log();
        try {
            Thread.sleep((long) ((Math.random()*1000)+1));
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        mutex.unlock();
    }

    public void pilotAnnounceArrival() {
        mutex.lock();
        repo.log();
        repo.logArriving(); 
        mutex.unlock(); 
    }

    public void passengerWaitForEndOfFlight(int id) {
        mutex.lock();
        repo.log();
        mutex.unlock();
    } 
}  