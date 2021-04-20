package monitor;

import states.EPilot;
import states.EHostess;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import repo.Repository;

public class Plane {
    private Repository repo;
    private ReentrantLock mutex = new ReentrantLock();
    private Condition cond_pilot = mutex.newCondition();
    private boolean boarding_complete;

    public Plane(Repository repo){
        this.repo = repo;
    }

    // --------------------------- HOSTESS ---------------------------
    public EHostess.readyToFly readyToFly() {
        mutex.lock();
        repo.log();
        boarding_complete = true;
        cond_pilot.signal();
        mutex.unlock();
        return EHostess.readyToFly.waitForNextFlight;
    }

    // --------------------------- PILOT ---------------------------
    public EPilot.waitingForBoarding waitingForBoarding() {
        mutex.lock();
        repo.log();
        try{
            while(!boarding_complete)
            cond_pilot.await();
        } catch(InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        boarding_complete = false;
        repo.logDeparture();    
        mutex.unlock();
        return EPilot.waitingForBoarding.flyToDestinationPoint;
    }

    public EPilot.flyingForward flyingForward() {
        mutex.lock();
        repo.log();
        /*try {
            Thread.sleep((long) ((Math.random() * 1000)+1));
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }*/
        repo.logArriving();
        mutex.unlock();
        return EPilot.flyingForward.announceArrival;
    }

}