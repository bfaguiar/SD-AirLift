package monitor;

import states.EHostess;
import states.EPassenger;
import states.EPilot;
import java.util.concurrent.locks.ReentrantLock;
import java.util.LinkedList;
import java.util.Queue;
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
    private Condition condition_passenger_left = mutex.newCondition();

    private boolean plane_ready_boarding;
    private boolean hostess_ask_documents;
    private boolean hostess_next_passenger;

    private int passengers_transported = 0;
    private int total_passengers;
    private int plane_max_capacity;
    private int plane_min_capacity;

    private Queue<Integer> passenger_queue = new LinkedList<Integer>();
    private Queue<Integer> passengers_in_plane = new LinkedList<Integer>();
    private Queue<Integer> passenger_documents_queue = new LinkedList<Integer>();

    public DepartureAirport(Repository repo, int capacity_min, int capacity_max, int total_passengers){
        this.repo = repo;
        this.plane_max_capacity = capacity_max;
        this.plane_min_capacity = capacity_min;
        this.total_passengers = total_passengers;
    }

    // --------------------------- PILOT ---------------------------
    public EPilot.atTransferGate atTransferGate() {
        if (passengers_transported == total_passengers){
            mutex.lock();
            repo.log();
            mutex.unlock();
            return EPilot.atTransferGate.endLife;
        }
        else{
            mutex.lock();
            repo.flight_num += 1;
            passengers_in_plane.clear();
            repo.log();
            repo.logFlightBoardingStarting();
            mutex.unlock();
            return EPilot.atTransferGate.informPlaneReadyForBoarding;
        }
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
        if (passengers_transported == total_passengers){
            mutex.lock();
            repo.log();
            mutex.unlock();
            return EHostess.waitForFlight.endLife;
        }
        else{
            mutex.lock();
            repo.log();
            try {
                while(!this.plane_ready_boarding)
                    this.condition_pilot.await();
            } catch(InterruptedException e) {
                System.out.print(e);
            }
            mutex.unlock();
            return EHostess.waitForFlight.prepareForPassBoarding;
        }
    }

    public EHostess.waitForPassenger waitForPassenger() {
        mutex.lock();
        repo.log();
        if((passenger_queue.size() == 0 && passengers_in_plane.size() >= plane_min_capacity) || 
           (passenger_queue.size() != 0 && passengers_in_plane.size() == plane_max_capacity) ||
           (passenger_queue.size() == 0 && passengers_transported == total_passengers)){
            plane_ready_boarding = false;
            mutex.unlock();
            return EHostess.waitForPassenger.informPlaneReadyToTakeOff;
        }
        else{
            try {
                while(passenger_queue.size() == 0)
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
            while(passenger_documents_queue.size() == 0)
                this.condition_passenger_documents.await();
        } catch(InterruptedException e) {
            System.out.print(e);
        }
        condition_hostess_next.signal();
        hostess_ask_documents = false;
        hostess_next_passenger = true;
        int checked_id = passenger_documents_queue.peek();
        try {
            while(!passengers_in_plane.contains(checked_id))
                this.condition_passenger_left.await();
        } catch(InterruptedException e) {
            System.out.print(e);
        }
        mutex.unlock();
        return EHostess.checkPassenger.waitForNextPassenger;
    }

    // --------------------------- PASSENGER ---------------------------
    public EPassenger.goingToAirport goingToAirport(int id) {
        mutex.lock();
        repo.log();
        if (passenger_queue.size() + passengers_in_plane.size() < plane_max_capacity && plane_ready_boarding){
            condition_passenger_queue.signal();
            passenger_queue.add(id);
            repo.number_in_queue++;
            mutex.unlock();
            return EPassenger.goingToAirport.waitInQueue;
        }
        else{
            mutex.unlock();
            return EPassenger.goingToAirport.travelToAirport;
        }
    }

    public EPassenger.inQueue inQueue(int id) {
        mutex.lock();
        repo.log();
        if (passenger_documents_queue.contains(id) && passenger_queue.size() != 0 && passenger_queue.peek() == id){
            condition_passenger_left.signal();
            repo.logPassengerCheck(id);
            passenger_documents_queue.remove(id);
            passengers_in_plane.add(id);
            repo.number_in_plane++;
            passengers_transported++;
            passenger_queue.remove(id);
            repo.number_in_queue--;
            mutex.unlock();
            return EPassenger.inQueue.boardThePlane;
        }
        else{
            try {
                while(!this.hostess_ask_documents)
                    this.condition_hostess_documents.await();
            } catch(InterruptedException e) {
                System.out.print(e);
            }
            condition_passenger_documents.signal();
            passenger_documents_queue.add(id);
            try {
                while(!this.hostess_next_passenger)
                    this.condition_hostess_next.await();
            } catch(InterruptedException e) {
                System.out.print(e);
            }
            mutex.unlock();
            return EPassenger.inQueue.showDocuments;
        }
    }
}