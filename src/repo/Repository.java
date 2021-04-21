package repo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import thread.Hostess;
import thread.Passenger;
import thread.Pilot;
import java.util.logging.Logger;

public class Repository{
    private Hostess hostess;
    private Pilot pilot;
    private Passenger[] passengerList;
    private int numberInQueue;
    private int numberInPlane;
    private int numberAtDestination;
    private int flightNum = 0;

    private FileWriter repoWriter;

    private String lastStateString;

    private final Logger logger = Logger.getLogger(Repository.class.getName());

    public Repository(){
        numberInQueue = 0;
        numberInPlane = 0;
        numberAtDestination = 0;
        try {
            
            repoWriter = new FileWriter(new File("repo.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setHostess(Hostess hostess) {
        this.hostess = hostess;
    }

    public void setPilot(Pilot pilot) {
        this.pilot = pilot;
    }

    public void setPassengerList(Passenger[] passengerList) {
        this.passengerList = passengerList;
    }

    public void incrementNumberInQueue() {
        numberInQueue++;
    }

    public void decrementNumberInQueue() {
        numberInQueue--;
    }

    public int getNumberInPlane() {
        return this.numberInPlane;
    }

    public void decrementNumberInPlane() {
        numberInPlane--;
    }

    public void incrementNumberInPlane() {
        numberInPlane++;
    } 

    public void incrementNumberAtDestination() {
        numberAtDestination++;
    }  

    public int getFlightNum() {
        return this.flightNum;
    }

    public void setFlightNum(int flightNum) {
        this.flightNum = flightNum;
    }

    public void incrementFlightNum() {
        this.flightNum++;
    }
 


    public void closelog(){
        try {
            this.repoWriter.close();
            this.repoWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logEntities(){
        String entities = String.format("%4s %4s", "PT", "HT");
        for(int i = 0; i < passengerList.length; i++)
            entities += String.format(" %4s", ("P"+i));
        entities += String.format(" %4s %4s %4s%n", "InQ", "InF", "PTAL");
        logger.info(entities);
        try {
            this.repoWriter.write(entities);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void log(){
        String states = String.format("%4s %4s", pilot.getStateString(), hostess.getStateString());
        for(int i = 0; i < passengerList.length; i++)
            states += String.format(" %4s", passengerList[i].getStateString());
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
    }

    public synchronized void logFlightBoardingStarting(){
        String flightString = String.format("%nFlight %d: boarding started.%n", flightNum);
        logger.info(flightString);
        try {
            repoWriter.write(flightString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void logPassengerCheck(int id){
        String flightString = String.format("%nFlight %d: passenger %d checked.%n", flightNum, id);
        logger.info(flightString);
        try {
            repoWriter.write(flightString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void logDeparture(){
        String flightString = String.format("%nFlight %d: departed with %d passengers.%n", flightNum, numberInPlane);
        logger.info(flightString);
        try {
            repoWriter.write(flightString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void logArriving(){
        String flightString = String.format("%nFlight %d: arrived.%n", flightNum);
        logger.info(flightString);
        try {
            repoWriter.write(flightString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void logReturning(){
        String flightString = String.format("%nFlight %d: returning.%n", flightNum);
        logger.info(flightString);
        try {
            repoWriter.write(flightString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
