package shared;

import java.rmi.RemoteException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import interfaces.ArrivalAirportInterface;
import interfaces.RepositoryInterface;
import main.Initializer;
/**
 * Arrival Airport's shared region
 * @author Bruno Aguiar, 80177
 * @author David Rocha, 84807
 */
public class ArrivalAirport implements ArrivalAirportInterface { 

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
     * Instantiation of a Condition variable for the Pilot
     * @see Condition
     */
    private Condition conditionLastPassenger = mutex.newCondition();


    /**
     * Declaration of a boolean variable to inform the pilot that he's the last passenger exiting the plane
     */
    private boolean lastPassenger;

    /**
     * Number of passengers deboarded
     */
    private int passengersDeboarded;

    /**
     * Number of passengers in the plane
     */
    private int passengersInPlane;

    /**
     * Constructor
     * @param repo Instance of the General Repository of Information
     */
    public ArrivalAirport(RepositoryInterface repo) {
        this.repo = repo;
    } 

    @Override
    public void pilotFlyToDeparturePoint(int numberInPlane, String state)throws RemoteException {
        mutex.lock();   
        repo.setPilotState(state);              
        repo.log();
        passengersInPlane = numberInPlane;
        passengersDeboarded = 0;
        try {
            while(!lastPassenger)
                conditionLastPassenger.await();
            lastPassenger = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        repo.logReturning();
        mutex.unlock();
    } 

    @Override
    public void passengerLeaveThePlane(int id, String state) throws RemoteException {
        mutex.lock();
        passengersDeboarded++;
        repo.incrementNumberAtDestination();
        if (passengersDeboarded == passengersInPlane) {
            lastPassenger = true;
            conditionLastPassenger.signal(); 
        }  
        repo.decrementNumberInPlane();
        repo.setPassengerListState(id, state);
        repo.log();
        mutex.unlock();
    }  

     /**
     * Server shutdown
     */
    @Override
    public synchronized void serverShutdown() throws RemoteException {
        Initializer.end = true;
        notifyAll();
    }
} 

