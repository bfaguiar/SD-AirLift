package monitor;

import states.EHostess;
import states.EPassenger;
import states.EPilot;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class DepartureAirport {
    private ReentrantLock rt = new ReentrantLock();
    private Condition pilot_at_gate = rt.newCondition();
    private boolean hostess_ready_boarding = false;

    public DepartureAirport(int capacity_max, int capacity_min){

    }

    public EPilot.atTransferGate atTransferGate() {
        rt.lock();
        this.pilot_at_gate.signal();
        this.hostess_ready_boarding = true;
        rt.unlock();
        return EPilot.atTransferGate.informPlaneReadyForBoarding;
    }

    public EHostess.waitForFlight waitForFlight() {
        rt.lock();
        try {
            while(!this.hostess_ready_boarding)
                this.pilot_at_gate.await();
        } catch(InterruptedException e) {
            System.out.print(e);
        } finally {
            rt.unlock();
        }
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