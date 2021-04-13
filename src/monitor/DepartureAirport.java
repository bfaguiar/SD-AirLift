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
    private boolean passenger_showed_documents = false;
    private boolean hostess_ask_documents = false;
    private boolean hostess_next_passenger = false;

    private int num_documents_checked = 0;
    private int num_passengers_in_queue = 0;
    private int num_passengers_in_plane = 0;

    private int plane_max_capacity;
    private int plane_min_capacity;

    public DepartureAirport(Repository repo, int capacity_max, int capacity_min){
        this.repo = repo;
        this.plane_max_capacity = capacity_max;
        this.plane_min_capacity = capacity_min;
    }

    // --------------------------- PILOT ---------------------------
    public EPilot.atTransferGate atTransferGate() {
        mutex.lock();
        num_passengers_in_plane = 0;
        plane_ready_boarding = false;
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
        num_documents_checked = 0;
        repo.log();
        mutex.unlock();
        return EHostess.waitForFlight.prepareForPassBoarding;
    }

    public EHostess.waitForPassenger waitForPassenger() {
        if((num_passengers_in_queue == 0 && num_passengers_in_plane >= plane_min_capacity) || num_passengers_in_plane == plane_max_capacity){
            mutex.lock();
            hostess_next_passenger = false;
            repo.log();
            mutex.unlock();
            return EHostess.waitForPassenger.informPlaneReadyToTakeOff;
        }
        else{
            mutex.lock();
            try {
                while(num_passengers_in_queue == 0)
                    this.condition.await();
            } catch(InterruptedException e) {
                System.out.print(e);
            }
            this.condition.signal();
            hostess_ask_documents = true;
            hostess_next_passenger = false;
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
        hostess_ask_documents = false;
        hostess_next_passenger = true;
        repo.log();
        mutex.unlock();
        return EHostess.checkPassenger.waitForNextPassenger;
    }

    // --------------------------- PASSENGER ---------------------------
    public EPassenger.goingToAirport goingToAirport() {
        if (num_passengers_in_queue + num_passengers_in_plane <= plane_max_capacity){
            mutex.lock();
            condition.signal();
            num_passengers_in_queue++;
            passenger_showed_documents = false;
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
        if (num_documents_checked > num_passengers_in_plane){
            mutex.lock();
            try {
                while(!this.hostess_next_passenger)
                    this.condition.await();
            } catch(InterruptedException e) {
                System.out.print(e);
            }
            num_passengers_in_queue--;
            num_passengers_in_plane++;
            repo.log();
            mutex.unlock();
            return EPassenger.inQueue.boardThePlane;
        }
        else{
            mutex.lock();
            try {
                while(!this.hostess_ask_documents)
                    this.condition.await();
            } catch(InterruptedException e) {
                System.out.print(e);
            }
            condition.signal();
            num_documents_checked++;
            passenger_showed_documents = true;
            repo.log();
            mutex.unlock();
            return EPassenger.inQueue.showDocuments;
        }
    }
}