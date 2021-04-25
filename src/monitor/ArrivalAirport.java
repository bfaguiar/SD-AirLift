package monitor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import repo.Repository;
import states.EPassenger;
import states.EPilot;

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
     * Instantiation of a Condition variable for the Passenger
     * @see Condtition
     */
    private Condition conditionPassenger = mutex.newCondition();

    /**
     * Instantiation of a Condition variable for the Pilot
     * @see Condition
     */
    private Condition conditionPilot = mutex.newCondition();

    /**
     * Declaration of a boolean variable to inform the passenger that he can exit the plane
     */
    private boolean canExit;

    /**
     * Declaration of a boolean variable to inform the pilot that he's the last passenger exiting the plane
     */
    private boolean lastPassenger;

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

    /**
     * <b> Blocking state: </b> The pilot announces that the Plane is ready for deboarding and waits until all passengers exit the plane.
     * @return EPilot.deboarding enumerate to inform about what should be the next state
     */
    public EPilot.deboarding deboarding() {
        mutex.lock();                 
        repo.log();
        passengersInPlane = repo.getNumberInPlane();
        passengersDeboarded = 0;
        try {
            canExit = true;
            conditionPassenger.signalAll();
            while(!lastPassenger)
                conditionPilot.await();
            lastPassenger = false;
            canExit = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        repo.logReturning();
        mutex.unlock();
        return EPilot.deboarding.flyToDeparturePoint;
    } 

    /**
     * <b> independent state with blocking: </b> The pilot returns back to the Departure Airport.
     * @return EPilot.flyingBack enumerate to inform about what should be the next state
     */
    public EPilot.flyingBack flyingBack() { 
        mutex.lock();
        repo.log();
        try {
            Thread.sleep((long) ((Math.random()*1000)+1));
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        mutex.unlock();
        return EPilot.flyingBack.parkAtTransferGate;
    }

    /**
     * <b> Blocking state: </b> The passenger waits for the pilot to annouce the arrival and then leaves. If he's the last passenger, he informs the pilot. 
     * @return EPassenger.inFlight enumerate to inform about what should be the next state
     */
    public EPassenger.inFlight inFlight() {
        mutex.lock();
        try {
            while(!canExit){
                conditionPassenger.await();
            }
            passengersDeboarded++;
            repo.decrementNumberInPlane();
            repo.incrementNumberAtDestination();
            if (passengersDeboarded == passengersInPlane) {
                lastPassenger = true;
                conditionPilot.signal(); 
            }  
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        repo.log();
        mutex.unlock();
        return EPassenger.inFlight.leaveThePlane;
    }  
} 