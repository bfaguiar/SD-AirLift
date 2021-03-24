package monitor;

import java.util.concurrent.locks.ReentrantLock;
import states.EPilot;

public class ArrivalAirport { 
    private ReentrantLock rt = new ReentrantLock();

    public ArrivalAirport(){
        
    }

    public EPilot.deboarding deboarding() {
        rt.lock();
        // something
        rt.unlock();
        return EPilot.deboarding.flyToDeparturePoint;
    }

    public EPilot.flyingBack flyingBack() {
        rt.lock();
        // something
        rt.unlock();
        return EPilot.flyingBack.parkAtTransferGate;
    }
}