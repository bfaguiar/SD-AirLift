package monitor;

import java.util.concurrent.locks.ReentrantLock;

import repo.Repository;
import states.EPilot;

public class ArrivalAirport { 
    private Repository repo;
    private ReentrantLock rt = new ReentrantLock();

    public ArrivalAirport(Repository repo){
        this.repo = repo;
    }

    public EPilot.deboarding deboarding() {
        rt.lock();

        repo.log();
        rt.unlock();
        return EPilot.deboarding.flyToDeparturePoint;
    }

    public EPilot.flyingBack flyingBack() {
        rt.lock();

        repo.log();
        rt.unlock();
        return EPilot.flyingBack.parkAtTransferGate;
    }
}