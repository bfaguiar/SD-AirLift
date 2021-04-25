package monitor;

import states.EHostess;
import states.EPassenger;
import states.EPilot;
import java.util.concurrent.locks.ReentrantLock;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import repo.Repository;

/** Departure Airport's shared region
 * @author Bruno Aguiar, 80177
 * @author David Rocha, 84807
 */ 
public class DepartureAirport {

    /**
     * Declaration of the General Repository of Information
     */
    private Repository repo;

     /**
     * Instantiation of a thread syncrhonization mechanism 
     * @see ReentrantLock
     */
    private ReentrantLock mutex = new ReentrantLock();

    /**
     * Instantiation of a Condition Variable for the Pilot
     * @see Condition
     */
    private Condition conditionPilot = mutex.newCondition();

     /**
     * Instantiation of a Condition Variable for the Hostess
     * @see Condition
     */
    private Condition conditionHostessDocuments = mutex.newCondition();

    /**
     * Instantiation of a Condition Variable for the Hostess
     * @see Condition
     */
    private Condition conditionHostessNext = mutex.newCondition();

    /**
     * Instantiation of a Condition Variable for the Passenger
     * @see Condition
     */
    private Condition conditionPassengerQueue = mutex.newCondition();

    /**
     * Instantiation of a Condition Variable for the Passenger
     * @see Condition
     */
    private Condition conditionPassengerDocuments = mutex.newCondition();

    /**
     * Instantiation of a Condition Variable for the Passenger
     * @see Condition
     */
    private Condition conditionPassengerLeft = mutex.newCondition();

    /**
     * Declaration of a boolean variable to inform the Hostess that the plane is ready for boarding and used later by her to inform the passengers 
     */
    private boolean planeReadyBoarding;

    /**
     * Declaration of a boolean variable to inform the passengers to show their documents
     */
    private boolean hostessAskDocuments;

    /**
     * Declaration of a boolean variable to inform the passengers to check if they are the next passenger in the head of the queue
     */
    private boolean hostessNextPassenger;

    /**
     * Total number of passengers already carried to the Destination Airport in the simulation
     */
    private int passengersTransported = 0;

    /**
     * Total number of passengers in the simulation
     */
    private int totalPassengers;

    /**
     * Plane's maximum capacity 
     */
    private int planeMaxCapacity;

    /**
     * Plane's minimum capacity
     */
    private int planeMinCapacity;

    /**
     * Passenger's waiting queue
     */
    private Queue<Integer> passengerQueue = new LinkedList<>();

    /**
     * List of passengers in the plane
     */
    private Queue<Integer> passengersInPlane = new LinkedList<>();

    /**
     * List of passenger's documents 
     */
    private Queue<Integer> passengerDocumentsQueue = new LinkedList<>();

    /**
     * Constructor 
     * @param repo Instance of the General Repository of Information
     * @param capacityMin Minimum capacity of the plane
     * @param capacityMax Maximum capacity of the plane
     * @param totalPassengers Total number of passengers in the simulation
     */
    public DepartureAirport(Repository repo, int capacityMin, int capacityMax, int totalPassengers){
        this.repo = repo;
        this.planeMaxCapacity = capacityMax;
        this.planeMinCapacity = capacityMin;
        this.totalPassengers = totalPassengers;
    }

    /**
     * <b> Transition state: </b> The pilot is at the Transfer Gate and if not all passengers were already transported, He increments the number of flights, clears the  {@code passengersInPlane} queue and now he's ready for a new boarding. 
     * @return EPilot.atTransferGate enumerate to inform about what should be the next state
     */
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

    /**
     * <b> Transition state: </b> The pilot informs the Hostess that the plane is ready for a new boarding
     * @return  EPilot.readyForBoarding enumerate to inform what should be the next state
     */
    public EPilot.readyForBoarding readyForBoarding() {
        mutex.lock();
        repo.log();
        this.conditionPilot.signal();
        planeReadyBoarding = true;
        mutex.unlock();
        return EPilot.readyForBoarding.waitForAllInBoard;
    }

    /**
     * <b> Blocking state: </b> If not all passengers were already transported, the Hostess waits for a signal from Pilot to start waiting for passengers
     * @return EHostess.waitForFlight enumerate to inform what should be the next state
     */
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

    /**
     * <b> Blocking state: </b> If there are no passengers left in the queue and the plane's capacity was reached or there are no more passengers  in the simulation to transport, the Hostess will inform that the plane is ready to take off, if not, the Hostess will check the Documents of the passengers in the queue if there are any.
     * @return EHostess.waitForPassenger enumerate to inform what should be the next state
     */
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

     /**
      * <b> Blocking state: </b> The hostess will wait for the passenger in the head of the queue (if there are any) to enter the plane 
      * @return EHostess.checkPassenger enumerate to inform what should be the next state
      */
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

    /**
     * <b> independent state with blocking: </b> the passenger must wait for a place in the queue
     * @param id Passenger's id
     * @return EPassenger.goingToAirport enumerate to inform what should be the next state
     */
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

    /**
     * <b> Double blocking state </b>: The passenger waits in the queue until he's called to show the documents to board the plane. 
     * @param id Passenger's id
     * @return EPassenger.inQueue enumerate to inform what should be the next state
     */
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