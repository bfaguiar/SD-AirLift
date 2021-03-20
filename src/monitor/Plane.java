package monitor;

import states.EPassenger;
import states.EPilot;
import thread.Passenger;
import thread.Pilot;
import java.util.LinkedList;

public class Plane {
    private LinkedList<Passenger> passenger_list = new LinkedList<Passenger>();
    private Pilot pilot;

    public EPilot.atTransferGate atTransferGate() {
        return EPilot.atTransferGate.informPlaneReadyForBoarding;
    }

    public EPilot.readyForBoarding readyForBoarding() {
        return EPilot.readyForBoarding.waitForAllInBoard;
    }

    public EPilot.waitingForBoarding waitingForBoarding() {
        return EPilot.waitingForBoarding.flyToDestinationPoint;
    }

    public EPilot.flyingForward flyingForward() {
        return EPilot.flyingForward.announceArrival;
    }

    public EPassenger.inFlight inFlight() {
        // if flight is not over
        return EPassenger.inFlight.waitForEndOfFlight;
        // else
        // return EPassenger.inFlight.leaveThePlane;
    }
}