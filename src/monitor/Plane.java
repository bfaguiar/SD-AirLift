package monitor;

import states.EPassenger;
import states.EPilot;
import thread.Passenger;
import states.EHostess;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import repo.Repository;

public class Plane {
    private Repository repo;
    private ReentrantLock rt = new ReentrantLock();
    private Condition condPilot = rt.newCondition();
    private boolean boardingComplete;

    public Plane(Repository repo){
        this.repo = repo;
        boardingComplete = false;
    }

    public EHostess.readyToFly readyToFly() {
        rt.lock();
        boardingComplete = true;
        condPilot.signal();
       repo.log();
        rt.unlock();
        return EHostess.readyToFly.waitForNextFlight;
    }

    public EPilot.waitingForBoarding waitingForBoarding() {
        rt.lock();
        try{
            while(!boardingComplete)
                condPilot.await();
        } catch(InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            rt.unlock();
        }
        repo.log();
        return EPilot.waitingForBoarding.flyToDestinationPoint;
    }

    public EPilot.flyingForward flyingForward() {
        rt.lock();
        try {
            Thread.sleep((long) ((Math.random() * 1000)+1));
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            rt.unlock();
        }
        repo.log();
        return EPilot.flyingForward.announceArrival;
    }

}