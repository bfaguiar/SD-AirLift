package monitor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import repo.Repository;
import states.EPassenger;
import states.EPilot;

public class ArrivalAirport { 
    private Repository repo;
    private ReentrantLock mutex = new ReentrantLock();

    private Condition cond_passenger = mutex.newCondition();
    private Condition cond_pilot = mutex.newCondition();

    private boolean can_exit;
    private boolean last_passenger;

    private int passengers_deboarded;

    public ArrivalAirport(Repository repo){
        this.repo = repo;
    } 

    // --------------------------- PILOT ---------------------------
    public EPilot.deboarding deboarding() {
        mutex.lock();                 
        repo.log();
        passengers_deboarded = 0;
        try {
            can_exit = true;
            cond_passenger.signalAll();
            while(!last_passenger)
                cond_pilot.await();
            last_passenger = false;
            can_exit = false;
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
            while(!can_exit){
                cond_passenger.await();
            }
            passengers_deboarded++;
            repo.number_in_plane--;
            repo.number_at_destination++;
            if (passengers_deboarded == repo.number_in_plane) {
                last_passenger = true;
                cond_pilot.signal(); 
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