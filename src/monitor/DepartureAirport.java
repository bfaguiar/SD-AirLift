package monitor;

import states.EHostess;
import states.EPassenger;
import states.EPilot;
import java.util.concurrent.locks.ReentrantLock;

public class DepartureAirport {
    private ReentrantLock rt = new ReentrantLock();
    private int plane_capacity_min;
    private int plane_capacity_max;
    private int plane_current_capacity;

    public DepartureAirport(int capacity_min, int capacity_max){
        plane_capacity_min = capacity_min;
        plane_capacity_max = capacity_max;
        plane_current_capacity = 0;
    }

    public EPilot.atTransferGate atTransferGate() {
        rt.lock();
        // something
        rt.unlock();
        return EPilot.atTransferGate.informPlaneReadyForBoarding;
    }

    public EHostess.waitForFlight waitForFlight() {
        rt.lock();
        // something
        rt.unlock();
        return EHostess.waitForFlight.prepareForPassBoarding;
    }

    public EHostess.checkPassenger checkPassenger() {
        rt.lock();
        // something
        rt.unlock();
        return EHostess.checkPassenger.waitForNextPassenger;
    }

    public EHostess.readyToFly readyToFly() {
        rt.lock();
        // something
        rt.unlock();
        return EHostess.readyToFly.waitForNextFlight;
    }

    public EPassenger.goingToAirport goingToAirport() {
        rt.lock();
        // something
        rt.unlock();
        // if plane is ready for boarding
        return EPassenger.goingToAirport.waitInQueue;
        // else
        // return EPassenger.goingToAirport.travelToAirport;
    }

    public EPassenger.inQueue inQueue() {
        rt.lock();
        // something
        rt.unlock();
        // if the hostess verified the documents
        return EPassenger.inQueue.boardThePlane;
        // else
        // return EPassenger.inQueue.showDocuments;
    }
}