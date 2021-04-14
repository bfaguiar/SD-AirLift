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
    private Condition condition_pilot = mutex.newCondition();
    private Condition condition_hostess_documents = mutex.newCondition();
    private Condition condition_hostess_next = mutex.newCondition();
    private Condition condition_passenger_queue = mutex.newCondition();
    private Condition condition_passenger_documents = mutex.newCondition();

    private boolean plane_ready_boarding = false;
    private boolean passenger_showed_documents = false;
    private boolean hostess_ask_documents = false;
    private boolean hostess_next_passenger = false;

    private int num_documents_checked = 0;
    private int num_passengers_in_queue = 0;
    private int num_passengers_in_plane = 0;

    private int plane_max_capacity;
    private int plane_min_capacity;

    public DepartureAirport(Repository repo, int capacity_min, int capacity_max){
        this.repo = repo;
        this.plane_max_capacity = capacity_max;
        this.plane_min_capacity = capacity_min;
    }

    // --------------------------- PILOT ---------------------------
    public EPilot.atTransferGate atTransferGate() {
        mutex.lock();
        repo.log();
        num_passengers_in_plane = 0;
        plane_ready_boarding = false;
        mutex.unlock();
        return EPilot.atTransferGate.informPlaneReadyForBoarding;
    }

    public EPilot.readyForBoarding readyForBoarding() {
        mutex.lock();
        repo.log();
        this.condition_pilot.signal();
        plane_ready_boarding = true;
        mutex.unlock();
        return EPilot.readyForBoarding.waitForAllInBoard;
    }

    // --------------------------- HOSTESS ---------------------------
    public EHostess.waitForFlight waitForFlight() {
        mutex.lock();
        repo.log();
        try {
            while(!this.plane_ready_boarding)
                this.condition_pilot.await();
        } catch(InterruptedException e) {
            System.out.print(e);
        }
        num_documents_checked = 0;
        mutex.unlock();
        return EHostess.waitForFlight.prepareForPassBoarding;
    }

    public EHostess.waitForPassenger waitForPassenger() {
        mutex.lock();
        if(num_passengers_in_plane >= plane_min_capacity){
            repo.log();
            mutex.unlock();
            return EHostess.waitForPassenger.informPlaneReadyToTakeOff;
        }
        else{
            repo.log();
            try {
                while(num_passengers_in_queue == 0)
                    this.condition_passenger_queue.await();
            } catch(InterruptedException e) {
                System.out.print(e);
            }
            this.condition_hostess_documents.signal();
            hostess_ask_documents = true;
            hostess_next_passenger = false;
            mutex.unlock();
            return EHostess.waitForPassenger.checkDocuments;
        }
     }

    public EHostess.checkPassenger checkPassenger() {
        mutex.lock();
        repo.log();
        try {
            while(!this.passenger_showed_documents)
                this.condition_passenger_documents.await();
        } catch(InterruptedException e) {
            System.out.print(e);
        }
        condition_hostess_next.signal();
        hostess_next_passenger = true;
        hostess_ask_documents = false;
        mutex.unlock();
        return EHostess.checkPassenger.waitForNextPassenger;
    }

    // --------------------------- PASSENGER ---------------------------
    public EPassenger.goingToAirport goingToAirport() {
        mutex.lock();
        if (num_passengers_in_queue + num_passengers_in_plane < plane_max_capacity){
            repo.log();
            condition_passenger_queue.signal();
            num_passengers_in_queue++;
            repo.number_in_queue++;
            mutex.unlock();
            return EPassenger.goingToAirport.waitInQueue;
        }
        else{
            repo.log();
            mutex.unlock();
            return EPassenger.goingToAirport.travelToAirport;
        }
    }

    public EPassenger.inQueue inQueue() {
        mutex.lock();
        if (num_documents_checked > num_passengers_in_plane){
            repo.log();
            try {
                while(!this.hostess_next_passenger)
                    this.condition_hostess_next.await();
            } catch(InterruptedException e) {
                System.out.print(e);
            }
            passenger_showed_documents = false;
            repo.number_in_queue--;
            num_passengers_in_queue--;
            repo.number_in_plane++;
            num_passengers_in_plane++;
            mutex.unlock();
            return EPassenger.inQueue.boardThePlane;
        }
        else{
            repo.log();
            try {
                while(!this.hostess_ask_documents)
                    this.condition_hostess_documents.await();
            } catch(InterruptedException e) {
                System.out.print(e);
            }
            condition_passenger_documents.signal();
            num_documents_checked++;
            passenger_showed_documents = true;
            mutex.unlock();
            return EPassenger.inQueue.showDocuments;
        }
    }
}