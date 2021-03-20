package monitor;

import states.EHostess;
import states.EPassenger;
import thread.Hostess;
import thread.Passenger;
import java.util.LinkedList;

public class DepartureAirport {
    private Hostess hostess;
    private LinkedList<Passenger> passenger_queue = new LinkedList<Passenger>();

    public EHostess.waitForFlight waitForFlight() {
        return EHostess.waitForFlight.prepareForPassBoarding;
    }

    public EHostess.waitForPassenger waitForPassenger() {
        // if there are no passengers in queue
        return EHostess.waitForPassenger.informPlaneReadyToTakeOff;
        // else
        // return EHostess.waitForPassenger.checkDocuments;
    }

    public EHostess.checkPassenger checkPassenger() {
        return EHostess.checkPassenger.waitForNextPassenger;
    }

    public EHostess.readyToFly readyToFly() {
        return EHostess.readyToFly.waitForNextFlight;
    }

    public EPassenger.goingToAirport goingToAirport() {
        // if plane is ready for boarding
        return EPassenger.goingToAirport.waitInQueue;
        // else
        // return EPassenger.goingToAirport.travelToAirport;
    }

    public EPassenger.inQueue inQueue() {
        // if the hostess verified the documents
        return EPassenger.inQueue.boardThePlane;
        // else
        // return EPassenger.inQueue.showDocuments;
    }
}