package monitor;

import states.EHostess;
import states.EPassenger;
import states.EPilot;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import repo.Repository;

public class DepartureAirport {
    private Repository repo;
    private ReentrantLock mutex = new ReentrantLock();
    private Condition condition = mutex.newCondition();

    private boolean plane_ready_boarding = false;
    private boolean passenger_in_queue = false;
    private boolean passenger_showed_documents = false;
    private boolean hostess_verified_documents = false;

    private int num_passengers_in_queue = 0;

    public DepartureAirport(Repository repo, int capacity_max, int capacity_min){
        this.repo = repo;
    }

    // --------------------------- PILOT ---------------------------
    public EPilot.atTransferGate atTransferGate() {
        mutex.lock();
        repo.log();
        mutex.unlock();
        return EPilot.atTransferGate.informPlaneReadyForBoarding;
    }

    public EPilot.readyForBoarding readyForBoarding() {
        mutex.lock();
        this.condition.signal();
        this.plane_ready_boarding = true;
        repo.log();
        mutex.unlock();
        return EPilot.readyForBoarding.waitForAllInBoard;
    }

    // --------------------------- HOSTESS ---------------------------
    public EHostess.waitForFlight waitForFlight() {
        mutex.lock();
        try {
            while(!this.plane_ready_boarding)
                this.condition.await();
        } catch(InterruptedException e) {
            System.out.print(e);
        }
        repo.log();
        mutex.unlock();
        return EHostess.waitForFlight.prepareForPassBoarding;
    }

    public EHostess.waitForPassenger waitForPassenger() {
        if(num_passengers_in_queue == 0){
            mutex.lock();
            repo.log();
            mutex.unlock();
            return EHostess.waitForPassenger.informPlaneReadyToTakeOff;
        }
        else{
            mutex.lock();
            try {
                while(!this.passenger_in_queue)
                    this.condition.await();
            } catch(InterruptedException e) {
                System.out.print(e);
            }
            repo.log();
            mutex.unlock();
            return EHostess.waitForPassenger.checkDocuments;
        }
     }

    public EHostess.checkPassenger checkPassenger() {
        mutex.lock();
        try {
            while(!this.passenger_showed_documents)
                this.condition.await();
        } catch(InterruptedException e) {
            System.out.print(e);
        }
        hostess_verified_documents = true;
        repo.log();
        mutex.unlock();
        return EHostess.checkPassenger.waitForNextPassenger;
    }

    // --------------------------- PASSENGER ---------------------------
    public EPassenger.goingToAirport goingToAirport() {
        if (plane_ready_boarding){
            mutex.lock();
            condition.signal();
            passenger_in_queue = true;
            num_passengers_in_queue++;
            repo.log();
            mutex.unlock();
            return EPassenger.goingToAirport.waitInQueue;
        }
        else{
            mutex.lock();
            repo.log();
            mutex.unlock();
            return EPassenger.goingToAirport.travelToAirport;
        }
    }

    public EPassenger.inQueue inQueue() {
        if (hostess_verified_documents){
            mutex.lock();
            num_passengers_in_queue--;
            repo.log();
            mutex.unlock();
            return EPassenger.inQueue.boardThePlane;
        }
        else{
            mutex.lock();
            condition.signal();
            passenger_showed_documents = true;
            repo.log();
            mutex.unlock();
            return EPassenger.inQueue.showDocuments;
        }
    }
}