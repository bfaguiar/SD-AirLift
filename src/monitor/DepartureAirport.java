package monitor;

import states.EHostess;
import states.EPassenger;
import states.EPilot;
import java.util.concurrent.locks.ReentrantLock;
import repo.Repository;
import java.util.concurrent.locks.Condition;

public class DepartureAirport {
    private Repository repo;
    private ReentrantLock mutex = new ReentrantLock();
    private Condition plane = mutex.newCondition();
    private boolean hostess_ready_boarding = false;

    private Condition passenger = mutex.newCondition();


    public DepartureAirport(Repository repo, int capacity_max, int capacity_min){
        this.repo = repo;
    }

    public EPilot.atTransferGate atTransferGate() {
        mutex.lock();
        repo.log();
        mutex.unlock();
        return EPilot.atTransferGate.informPlaneReadyForBoarding;
    }

    public EPilot.readyForBoarding readyForBoarding() {
        mutex.lock();
        this.plane.signal();
        this.hostess_ready_boarding = true;
        repo.log();
        mutex.unlock();
        return EPilot.readyForBoarding.waitForAllInBoard;
    }

    public EHostess.waitForFlight waitForFlight() {
        mutex.lock();
        try {
            while(!this.hostess_ready_boarding)
                this.plane.await();
        } catch(InterruptedException e) {
            System.out.print(e);
        } finally {
            repo.log();
            mutex.unlock();
        }
        return EHostess.waitForFlight.prepareForPassBoarding;
    }

    public EHostess.waitForPassenger waitForPassenger() {
        mutex.lock();
        try {
            while(!this.hostess_ready_boarding)
                this.passenger.await();
        } catch(InterruptedException e) {
            System.out.print(e);
        } finally {
            repo.log();
            mutex.unlock();
        }
        // if there are no passengers in queue
        return EHostess.waitForPassenger.informPlaneReadyToTakeOff;
        // else
         // return EHostess.waitForPassenger.checkDocuments;
     }

    public EHostess.checkPassenger checkPassenger() {
        mutex.lock();
        
        repo.log();
        mutex.unlock();
        return EHostess.checkPassenger.waitForNextPassenger;
    }

    public EPassenger.goingToAirport goingToAirport() {
        mutex.lock();
        passenger.signal();
        repo.log();
        mutex.unlock();
        // if plane is ready for boarding
        return EPassenger.goingToAirport.waitInQueue;
        // else
        // return EPassenger.goingToAirport.travelToAirport;
    }

    public EPassenger.inQueue inQueue() {
        mutex.lock();
        
        repo.log();
        mutex.unlock();
        // if the hostess verified the documents
        return EPassenger.inQueue.boardThePlane;
        // else
        // return EPassenger.inQueue.showDocuments;
    }
}