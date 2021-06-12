package repo;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

import Interface.RepositoryInterface;
import main.Initializer;


/**
 * General Repository of Information
* @author Bruno Aguiar, 80177
* @author David Rocha, 84807
 */
public class Repository implements RepositoryInterface {

    /**
     * Thread Hostess
     */
    private String hostessState;

    /**
     * Thread Pilot
     */
    private String pilotState;

    /**
     *  Array of Passenger threads
     */
    private String[] passengerListState;

    /**
     * Number of Passengers in Queue
     */
    private int numberInQueue;

    /**
     * Number of Passengers in the plane
     */
    private int numberInPlane;

    /**
     * Number of passengers arrived at Destination
     */
    private int numberAtDestination;

    /**
     * Number of flights taken in the simulation
     */
    private int flightNum = 0;

    /**
     * Declaration of a writer for the creation of a character file to register the simulation
     */
    private FileWriter repoWriter;

    /**
     * Latest State of the Simulation
     */
    private String lastStateString;

    /**
     * Logger to provide command-line information of the simulation state
     */
    private final Logger logger = Logger.getLogger(Repository.class.getName());

    /**
     * Instantiation of a thread syncronization mechanism 
     * @see ReentrantLock  
     */
    private ReentrantLock mutex = new ReentrantLock();

    /**
     * Constructor
     * @param num_passengers number of passengers
     */
    public Repository(int num_passengers, String logfile) {
        numberInQueue = 0;
        numberInPlane = 0;
        numberAtDestination = 0;
        hostessState = "WFNF";
        pilotState = "ATF";
        passengerListState = new String[num_passengers];
        for (int i = 0; i < passengerListState.length; i++) {
            passengerListState[i] = "GTA";
        }
        try {
            
            repoWriter = new FileWriter(new File(logfile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            logEntities();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
    * @param state Hostess state
    */
   @Override 
    public void setHostessState(String state) throws RemoteException {
        this.hostessState = state;
    }

    /**
     * 
     * @param state Pilot state
     */
    @Override
    public void setPilotState(String state)  throws RemoteException {
        this.pilotState = state;
    }

    /**
     * @param index Passenger Index
     * @param  passegerState Passenger State
     */
    @Override
    public void setPassengerListState(int index, String passegerState) throws RemoteException {
        this.passengerListState[index] = passegerState;
    }

    /**
     * Increments the number of passengers in queue
     */
    @Override
    public void incrementNumberInQueue() throws RemoteException {
        numberInQueue++;
    }

    /**
     * Decrements the number of passengers in queue
     */
    @Override
    public void decrementNumberInQueue() throws RemoteException {
        numberInQueue--;
    }

    /**
     * Decrements the number of passengers in the plane
     */
    @Override
    public void decrementNumberInPlane() throws RemoteException {
        numberInPlane--;
    }

    /**
     * Increments the number of passengers in the plane
     */
    @Override
    public void incrementNumberInPlane() throws RemoteException {
        numberInPlane++;
    } 

    /**
     * Increments the number of passengers arrived at Destination
     */
    @Override
    public void incrementNumberAtDestination() throws RemoteException {
        numberAtDestination++;
    }  

    /**
     * @return The number of flights taken in the simulation
     */
    @Override
    public int getFlightNum() throws RemoteException {
        return this.flightNum;
    }

    /**
     * @param flightNum Number of flights taken in the simulation
     */
    @Override
    public void setFlightNum(int flightNum) throws RemoteException {
        this.flightNum = flightNum;
    }

    /**
     * Increment the number of flights taken in the simulation
     */
    @Override
    public void incrementFlightNum() throws RemoteException {
        this.flightNum++;
    }

    /**
     * Close the writing session
     */
    @Override
    public void closelog() throws RemoteException {
        try {
            this.repoWriter.close();
            this.repoWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Initializer.end = true;
    }

    /**
     * Writes the header format in the simulation report
     * <p> Example: {@code PT   HT   P0   P1   P2   P3   P4   P5   P6   P7   P8   P9  P10  P11  P12  P13  P14  P15  P16  P17  P18  P19  P20  InQ  InF PTAL} </p>
     */
    @Override
    public void logEntities() throws RemoteException {
        String entities = String.format("%4s %4s", "PT", "HT");
        for(int i = 0; i < passengerListState.length; i++)
            entities += String.format(" %4s", ("P"+i));
        entities += String.format(" %4s %4s %4s%n", "InQ", "InF", "PTAL");
        logger.info(entities);
        try {
            this.repoWriter.write(entities);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes thread's state in the simulation report
     * <p> Example: {@code WFB   CP   IF   IF   IF   IF   IF   IQ   IQ   IQ   IQ   IQ  GTA  GTA  GTA  GTA  GTA  GTA  GTA  GTA  GTA  GTA  GTA    5    5    0} </p>
     */
    @Override
    public void log() throws RemoteException {
        mutex.lock();
        String states = String.format("%4s %4s", pilotState, hostessState);
        for(int i = 0; i < passengerListState.length; i++)
            states += String.format(" %4s", passengerListState[i]);
        states += String.format(" %4s %4s %4s%n", numberInQueue, numberInPlane, numberAtDestination);

        if(!states.equals(this.lastStateString)){ 
            logger.info(states);
        }

        try {
            if(!states.equals(this.lastStateString)){
                repoWriter.write(states);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        lastStateString = states;
        mutex.unlock();
    }

    /**
     * Writes the state "Boarding Started" in the simulation report and informs the current flight number
     * <p> Example: {@code Flight 1: boarding started.} </p>
     */
    @Override
    public void logFlightBoardingStarting() throws RemoteException {
        mutex.lock();
        String flightString = String.format("%nFlight %d: boarding started.%n", flightNum);
        logger.info(flightString);
        try {
            repoWriter.write(flightString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mutex.unlock();
    }

    /**
     * Writes the state "Passenger Checked" in the simulation report and informs the current flight number 
     * <p> Example: {@code Flight 1: passenger 0 checked.} </p>
     * @param id id of the passenger
     */
    @Override
    public void logPassengerCheck(int id) throws RemoteException {
        mutex.lock();
        String flightString = String.format("%nFlight %d: passenger %d checked.%n", flightNum, id);
        logger.info(flightString);
        try {
            repoWriter.write(flightString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mutex.unlock();
    }

    /**
     * Writes the state "Departed with N Passengers" in the simulation report and informs the current flight number 
     * <p> Example: {@code Flight 1: departed with 10 passengers.} </p>
     */
    @Override
    public void logDeparture() throws RemoteException {
        mutex.lock();
        String flightString = String.format("%nFlight %d: departed with %d passengers.%n", flightNum, numberInPlane);
        logger.info(flightString);
        try {
            repoWriter.write(flightString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mutex.unlock();
    }

    /**
     * Writes the state "Flight Arrived" in the simulation report and informs the current flight number
     * <p> Example: {@code Flight 1: arrived.} </p>
     */
    @Override
    public void logArriving() throws RemoteException {
        mutex.lock();
        String flightString = String.format("%nFlight %d: arrived.%n", flightNum);
        logger.info(flightString);
        try {
            repoWriter.write(flightString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mutex.unlock();
    }

    /**
     * Writes the state "Flight Returning" in the simulation report and informs the current flight number
     * <p> Example: {@code Flight 1: returning.} </p>
     */
    @Override
    public void logReturning() throws RemoteException {
        mutex.lock();
        String flightString = String.format("%nFlight %d: returning.%n", flightNum);
        logger.info(flightString);
        try {
            repoWriter.write(flightString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mutex.unlock();
    }
}

