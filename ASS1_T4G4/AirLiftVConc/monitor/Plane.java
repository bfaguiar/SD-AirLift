package monitor;

import states.EPilot;
import states.EHostess;
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

    /**
     * <b> Transition state: </b> Informs the Pilot that the boarding was complete
     * @return EHostess.readyToFly enumerate to inform about what should be the next state
     */ 
    public EHostess.readyToFly readyToFly() {
        mutex.lock();
        repo.log();
        boardingComplete = true;
        conditionPilot.signal();
        mutex.unlock();
        return EHostess.readyToFly.waitForNextFlight;
    }

    /**
     * <b> Blocking State: </b> Pilot is waken up by the hostess when boarding is complete
     * @return EPilot.waitingForBoarding enumerate to inform about what should be the next state
     */
    public EPilot.waitingForBoarding waitingForBoarding() {
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
        return EPilot.waitingForBoarding.flyToDestinationPoint;
    }

    /**
     * <b> Independent state with blocking: </b> The pilot sleeps for a random period of time in the simulation 
     * @return EPilot.flyingForward enumerate to inform about what should be the next state 
     */
    public EPilot.flyingForward flyingForward() {
        mutex.lock();
        repo.log();
        try {
            Thread.sleep((long) ((Math.random() * 1000)+1));
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        repo.logArriving(); 
        mutex.unlock(); 
        return EPilot.flyingForward.announceArrival;
    }

}  