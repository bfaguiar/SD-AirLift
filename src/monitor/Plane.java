package monitor;

import states.EPassenger;
import states.EPilot;
import states.EHostess;
import java.util.concurrent.locks.ReentrantLock;
import repo.Repository;

public class Plane {
    private Repository repo;
    private ReentrantLock rt = new ReentrantLock();
    
    public Plane(Repository repo){
        this.repo = repo;
    }

    public EHostess.readyToFly readyToFly() {
        rt.lock();
        
        repo.log();
        rt.unlock();
        return EHostess.readyToFly.waitForNextFlight;
    }

    public EPilot.waitingForBoarding waitingForBoarding() {
        rt.lock();
        
        repo.log();
        rt.unlock();
        return EPilot.waitingForBoarding.flyToDestinationPoint;
    }

    public EPilot.flyingForward flyingForward() {
        rt.lock();
        
        repo.log();
        rt.unlock();
        return EPilot.flyingForward.announceArrival;
    }

    public EPassenger.inFlight inFlight() {
        rt.lock();
        
        repo.log();
        rt.unlock();
        // if flight is not over
        return EPassenger.inFlight.waitForEndOfFlight;
        // else
        // return EPassenger.inFlight.leaveThePlane;
    }
}