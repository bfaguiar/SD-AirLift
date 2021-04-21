package monitor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import repo.Repository;
import states.EPassenger;
import states.EPilot;

public class ArrivalAirport { 
    private Repository repo;
    private ReentrantLock mutex = new ReentrantLock();

    private Condition conditionPassenger = mutex.newCondition();
    private Condition conditionPilot = mutex.newCondition();

    private boolean canExit;
    private boolean lastPassenger;

    private int passengersDeboarded;
    private int passengersInPlane;

    public ArrivalAirport(Repository repo){
        this.repo = repo;
    } 

    // --------------------------- PILOT ---------------------------
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

    public EPilot.flyingBack flyingBack() { 
        mutex.lock();
        repo.log();
        /*try {
            Thread.sleep((long) ((Math.random()*1000)+1));
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }*/
        mutex.unlock();
        return EPilot.flyingBack.parkAtTransferGate;
    }

    // --------------------------- PASSENGER ---------------------------
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