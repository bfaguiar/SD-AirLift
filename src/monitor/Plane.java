package monitor;

import states.EPassenger;
import states.EPilot;
import states.EHostess;
import java.util.concurrent.locks.ReentrantLock;

public class Plane {
    private ReentrantLock rt = new ReentrantLock();
    
    public Plane(){

    }

     public EHostess.readyToFly readyToFly() {
        rt.lock();
        // something
        rt.unlock();
        return EHostess.readyToFly.waitForNextFlight;
    }


    public EPilot.waitingForBoarding waitingForBoarding() {
        rt.lock();
        // something
        rt.unlock();
        return EPilot.waitingForBoarding.flyToDestinationPoint;
    }

    public EPilot.flyingForward flyingForward() {
        rt.lock();
        // something
        rt.unlock();
        return EPilot.flyingForward.announceArrival;
    }

    public EPassenger.inFlight inFlight() {
        rt.lock();
        // something
        rt.unlock();
        // if flight is not over
        return EPassenger.inFlight.waitForEndOfFlight;
        // else
        // return EPassenger.inFlight.leaveThePlane;
    }
}
