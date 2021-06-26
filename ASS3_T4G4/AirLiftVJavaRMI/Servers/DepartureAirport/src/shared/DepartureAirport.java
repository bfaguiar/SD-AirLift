package shared;

import java.util.concurrent.locks.ReentrantLock;

import interfaces.DepartureAirportInterface;
import interfaces.RepositoryInterface;
import main.Initializer;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;

/** Departure Airport's shared region
 * @author Bruno Aguiar, 80177
 * @author David Rocha, 84807
 */ 
public class DepartureAirport implements DepartureAirportInterface {

    /**
     * Declaration of the General Repository of Information
     */
    private RepositoryInterface repo;

     /**
     * Instantiation of a thread syncrhonization mechanism 
     * @see ReentrantLock
     */
    private ReentrantLock mutex = new ReentrantLock();

    /**
     * Instantiation of a Condition Variable for the Pilot
     * @see Condition
     */
    private Condition conditionPilotBoarding = mutex.newCondition();

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

    private int shutdown = 0;

    /**
     * Constructor 
     * @param repo Instance of the General Repository of Information
     * @param capacityMin Minimum capacity of the plane
     * @param capacityMax Maximum capacity of the plane
     * @param totalPassengers Total number of passengers in the simulation
     */
    public DepartureAirport(RepositoryInterface repo, int capacityMin, int capacityMax, int totalPassengers){
        this.repo = repo;
        this.planeMaxCapacity = capacityMax;
        this.planeMinCapacity = capacityMin;
        this.totalPassengers = totalPassengers;
    }

    /**
     * 
     * @param state Client's state
     */
    @Override
    public void pilotParkAtTransferGate(String state) throws RemoteException {
        mutex.lock();
        try {
            Thread.sleep((long) ((Math.random() * 1000)+1));
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        repo.incrementFlightNum();
        passengersInPlane.clear();
        repo.setPilotState(state); 
        repo.log();
        mutex.unlock();
    }

    /**
     * @param state Client's state
     */
    @Override
    public void pilotInformPlaneReadyForBoarding(String state) throws RemoteException {
        mutex.lock();
        repo.setPilotState(state); 
        repo.log();
        this.conditionPilotBoarding.signal();
        repo.logFlightBoardingStarting();
        planeReadyBoarding = true;
        mutex.unlock();
    }

    /**
     * 
     * @param state Client's state
     */
    @Override
    public void hostessPrepareForPassBoarding(String state) throws RemoteException {
        mutex.lock();
        repo.setHostessState(state);
        repo.log();
        try {
            while(!this.planeReadyBoarding)
                this.conditionPilotBoarding.await();
        } catch(InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        mutex.unlock();
    }

    /**
     * 
     * @param state Client's state
     */
    @Override
     public void hostessCheckDocuments(String state) throws RemoteException {
        mutex.lock();
        repo.setHostessState(state); //asda 
        repo.log();
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
     }

     /**
      * 
      * @param state Client's state
      */
    @Override
    public void hostessWaitForNextPassenger(String state) throws RemoteException {
        mutex.lock();
        repo.setHostessState(state);
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
    }

    /**
     * 
     * @param state Client's state
     */
    @Override
    public void hostessWaitForNextFlight(String state) throws RemoteException {
        mutex.lock();
        repo.setHostessState(state);
        repo.log();
        planeReadyBoarding = false;
        mutex.unlock();
     }
    
     /**
      * 
      * @param id Client's ID
      * @param state Client's State
      */
    @Override
    public void passengerTravelToAirport(int id, String state) throws RemoteException {
        repo.setPassengerListState(id, state);
        repo.log();
        try {
            Thread.sleep((long) ((Math.random()*1000)+1));
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    } 

    /**
     * 
     * @param id Client's ID
     * @param state Client's state
     */
     @Override
     public void passengerWaitInQueue(int id, String state) throws RemoteException {
        mutex.lock();
        repo.setPassengerListState(id, state);
        repo.log();
        conditionPassengerQueue.signal();
        passengerQueue.add(id);
        repo.incrementNumberInQueue();
        mutex.unlock();
    } 

    /**
     * 
     * @param id Client's ID
     * @param state Client's state
     */
    @Override
    public void passengerShowDocuments(int id, String state) throws RemoteException {
        mutex.lock();
        repo.setPassengerListState(id, state);
        repo.log();
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
        conditionPassengerLeft.signal();
        repo.logPassengerCheck(id);
        passengerDocumentsQueue.remove(id);
        passengersInPlane.add(id);
        passengersTransported++;
        passengerQueue.remove(id);
        repo.decrementNumberInQueue();
        mutex.unlock();
    }

    /**
     * 
     * @return boolean to check if the plane is ready to go or not
     */
    @Override
    public boolean isPlaneBoarded() throws RemoteException{
        if((passengerQueue.isEmpty() && passengersInPlane.size() >= planeMinCapacity) || 
           (!passengerQueue.isEmpty() && passengersInPlane.size() == planeMaxCapacity) ||
           (passengerQueue.isEmpty() && passengersTransported == totalPassengers))
            return true;
        else
            return false;
    }

    /**
     * 
     * @param state Client's state
     * @return boolean to check if there are more passengers left to flight or not.
     */
    @Override
    public boolean noMorePassengers(String state)throws RemoteException{
        mutex.lock();
        if (state.equals("WFNF"))
            repo.setHostessState("WFNF");
        else
            repo.setPilotState("ATF");
        mutex.unlock();
        if (passengersTransported == totalPassengers)
            return true;
        else
            return false;
    }

    /**
     * Server shutdown
     */
    @Override
    public synchronized void serverShutdown()throws RemoteException {
        repo.log();
        shutdown++;
        if (shutdown >= 2){
            Initializer.end = true;
            this.repo.closelog();
            notifyAll();
        }
    }
}