package shared;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import main.Initializer;
import stubs.Repository;

/**
 * Arrival Airport's shared region
 * @author Bruno Aguiar, 80177
 * @author David Rocha, 84807
 */
public class ArrivalAirport { 

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
     * Instantiation of a Condition variable for the Pilot
     * @see Condition
     */
    private Condition conditionLastPassenger = mutex.newCondition();

    private Condition pilotLeave = mutex.newCondition();


    /**
     * Declaration of a boolean variable to inform the pilot that he's the last passenger exiting the plane
     */
    private boolean lastPassenger;

    private boolean passengersCanLeave;

    /**
     * Number of passengers deboarded
     */
    private int passengersDeboarded;

    /**
     * Number of passengers in the plane
     */
    private int passengersInPlane;

    /**
     * Constructor
     * @param repo Instance of the General Repository of Information
     */
    public ArrivalAirport(Repository repo){
        this.repo = repo;
    } 

    public void pilotFlyToDeparturePoint(int numberInPlane, String state) {
        mutex.lock();   
        repo.setPilotState(state);              
        repo.log();
        passengersDeboarded = 0;
        passengersInPlane = numberInPlane;
        pilotLeave.signal();
        passengersCanLeave = true;
        try {
            while(!lastPassenger)
                conditionLastPassenger.await();
            lastPassenger = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        repo.logReturning();
        mutex.unlock();
    } 

    public void passengerLeaveThePlane(int id, String state) {
        mutex.lock();
        try {
            while(!passengersCanLeave)
                pilotLeave.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        passengersDeboarded++;
        repo.incrementNumberAtDestination();
        if (passengersDeboarded == passengersInPlane) {
            lastPassenger = true;
            conditionLastPassenger.signal(); 
            passengersCanLeave = false;
        }  
        repo.decrementNumberInPlane();
        repo.setPassengerListState(id, state);
        repo.log();
        mutex.unlock();
    }  

    public void serverShutdown(){
        mutex.lock();
        Initializer.end = true;
        mutex.unlock();
    }
} 