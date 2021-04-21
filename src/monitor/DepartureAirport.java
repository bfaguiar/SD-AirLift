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

    private Condition conditionPilot = mutex.newCondition();
    private Condition conditionHostessDocuments = mutex.newCondition();
    private Condition conditionHostessNext = mutex.newCondition();
    private Condition conditionPassengerQueue = mutex.newCondition();
    private Condition conditionPassengerDocuments = mutex.newCondition();
    private Condition conditionPassengerLeft = mutex.newCondition();

    private boolean planeReadyBoarding;
    private boolean hostessAskDocuments;
    private boolean hostessNextPassenger;

    private int passengersTransported = 0;
    private int totalPassengers;
    private int planeMaxCapacity;
    private int planeMinCapacity;

    private Queue<Integer> passengerQueue = new LinkedList<>();
    private Queue<Integer> passengersInPlane = new LinkedList<>();
    private Queue<Integer> passengerDocumentsQueue = new LinkedList<>();

    public DepartureAirport(Repository repo, int capacityMin, int capacityMax, int totalPassengers){
        this.repo = repo;
        this.planeMaxCapacity = capacityMax;
        this.planeMinCapacity = capacityMin;
        this.totalPassengers = totalPassengers;
    }

    // --------------------------- PILOT ---------------------------
    public EPilot.atTransferGate atTransferGate() {
        if (passengersTransported == totalPassengers){
            mutex.lock();
            repo.log();
            mutex.unlock();
            return EPilot.atTransferGate.endLife;
        }
        else{
            mutex.lock();
            repo.incrementFlightNum();
            passengersInPlane.clear();
            repo.log();
            repo.logFlightBoardingStarting();
            mutex.unlock();
            return EPilot.atTransferGate.informPlaneReadyForBoarding;
        }
    }

    public EPilot.readyForBoarding readyForBoarding() {
        mutex.lock();
        repo.log();
        this.conditionPilot.signal();
        planeReadyBoarding = true;
        mutex.unlock();
        return EPilot.readyForBoarding.waitForAllInBoard;
    }

    // --------------------------- HOSTESS ---------------------------
    public EHostess.waitForFlight waitForFlight() {
        if (passengersTransported == totalPassengers){
            mutex.lock();
            repo.log();
            mutex.unlock();
            return EHostess.waitForFlight.endLife;
        }
        else{
            mutex.lock();
            repo.log();
            try {
                while(!this.planeReadyBoarding)
                    this.conditionPilot.await();
            } catch(InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
            mutex.unlock();
            return EHostess.waitForFlight.prepareForPassBoarding;
        }
    }

    public EHostess.waitForPassenger waitForPassenger() {
        mutex.lock();
        repo.log();
        if((passengerQueue.isEmpty() && passengersInPlane.size() >= planeMinCapacity) || 
           (!passengerQueue.isEmpty() && passengersInPlane.size() == planeMaxCapacity) ||
           (passengerQueue.isEmpty() && passengersTransported == totalPassengers)){
            planeReadyBoarding = false;
            mutex.unlock();
            return EHostess.waitForPassenger.informPlaneReadyToTakeOff;
        }
        else{
            try {
                while(passengerQueue.isEmpty())
                    this.conditionPassengerQueue.await();
            } catch(InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
            this.conditionHostessDocuments.signalAll();
            hostessAskDocuments = true;
            hostessNextPassenger = false;
            mutex.unlock();
            return EHostess.waitForPassenger.checkDocuments;
        }
     }

    public EHostess.checkPassenger checkPassenger() {
        mutex.lock();
        repo.log();
        try {
            while(passengerDocumentsQueue.isEmpty())
                this.conditionPassengerDocuments.await();
        } catch(InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        int checkedID = passengerDocumentsQueue.peek();
        conditionHostessNext.signal();
        hostessAskDocuments = false;
        hostessNextPassenger = true;
        try {
            while(!passengersInPlane.contains(checkedID))
                this.conditionPassengerLeft.await();
        } catch(InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        mutex.unlock();
        return EHostess.checkPassenger.waitForNextPassenger;
    }

    // --------------------------- PASSENGER ---------------------------
    public EPassenger.goingToAirport goingToAirport(int id) {
        mutex.lock();
        repo.log();
        if (passengerQueue.size() + passengersInPlane.size() < planeMaxCapacity && planeReadyBoarding){
            conditionPassengerQueue.signal();
            passengerQueue.add(id);
            repo.incrementNumberInQueue();
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
        if ((!passengerQueue.isEmpty() && passengerQueue.peek() == id && passengerDocumentsQueue.contains(id))){
            conditionPassengerLeft.signal();
            repo.logPassengerCheck(id);
            passengerDocumentsQueue.remove(id);
            passengersInPlane.add(id);
            repo.incrementNumberInPlane();
            passengersTransported++;
            passengerQueue.remove(id);
            repo.decrementNumberInQueue();
            mutex.unlock();
            return EPassenger.inQueue.boardThePlane;
        }
        else{
            try {
                while(!(this.hostessAskDocuments && passengerQueue.peek() == id))
                    this.conditionHostessDocuments.await();
            } catch(InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt(); 
            }
            passengerDocumentsQueue.add(id);
            conditionPassengerDocuments.signal();
            try {
                while(!(this.hostessNextPassenger && passengerQueue.peek() == id))
                    this.conditionHostessNext.await();
            } catch(InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt(); 
            }
            mutex.unlock();
            return EPassenger.inQueue.showDocuments;
        }
    }
}