package monitor;

import states.EPilot;
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

    // --------------------------- HOSTESS ---------------------------
    public EHostess.readyToFly readyToFly() {
        rt.lock();
        repo.log();
        boardingComplete = true;
        condPilot.signal();
        rt.unlock();
        return EHostess.readyToFly.waitForNextFlight;
    }

    // --------------------------- PILOT ---------------------------
    public EPilot.waitingForBoarding waitingForBoarding() {
        rt.lock();
        repo.log();
        try{
            while(!boardingComplete)
                condPilot.await();
        } catch(InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        rt.unlock();
        return EPilot.waitingForBoarding.flyToDestinationPoint;
    }

    public EPilot.flyingForward flyingForward() {
        rt.lock();
        repo.log();
        /*try {
            Thread.sleep((long) ((Math.random() * 1000)+1));
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }*/
        rt.unlock();
        return EPilot.flyingForward.announceArrival;
    }

}