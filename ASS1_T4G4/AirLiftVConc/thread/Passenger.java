package thread;

import monitor.ArrivalAirport;
import monitor.DepartureAirport;
import monitor.Plane;
import states.EPassenger;

/**
 *  Thread Passenger
 * @author Bruno Aguiar, 80177
 * @author David Rocha, 84807
 */
public class Passenger extends Thread {

    /**
     * Passenger's current state in the simulation
     */
    private EPassenger.State state;

     /**
     * Departure Airport's shared region
     * @see DepartureAirport
     */
    private DepartureAirport dp;

    /**
     * Arrival Airport's shared region
     * @see ArrivalAirport
     */
    private ArrivalAirport aa;

    /**
     * Passenger's ID
     */
    private int id;

    /**
     * Constructor
     * @param plane instance of Plane's shared region
     * @param dp instance of Departure Airport's shared region
     * @param ap instance of Arrival Airport's shared region
     * @param id Passenger's ID
     */
    public Passenger(Plane plane, DepartureAirport dp, ArrivalAirport aa, int i){
        this.state = EPassenger.State.GOING_TO_AIRPORT;
        this.dp = dp;
        this.aa = aa;
        this.id = i;
    }

    /**
     * Thread's life cycle
     */
    @Override
    public void run(){
        boolean end = false;
        while(!end){
            switch(this.state) {
                case GOING_TO_AIRPORT:
                    EPassenger.goingToAirport s1 = this.dp.goingToAirport(id);
                    if (s1 == EPassenger.goingToAirport.waitInQueue)
                        this.state = EPassenger.State.IN_QUEUE;
                    else if (s1 == EPassenger.goingToAirport.travelToAirport)
                        this.state = EPassenger.State.GOING_TO_AIRPORT;
                    break;

                case IN_QUEUE:
                    EPassenger.inQueue s2 = this.dp.inQueue(id);
                    if (s2 == EPassenger.inQueue.boardThePlane)
                        this.state = EPassenger.State.IN_FLIGHT;
                    else if (s2 == EPassenger.inQueue.showDocuments)
                        this.state = EPassenger.State.IN_QUEUE;
                    break;

                case IN_FLIGHT:
                    EPassenger.inFlight s3 = this.aa.inFlight();
                    if (s3 == EPassenger.inFlight.waitForEndOfFlight)
                        this.state = EPassenger.State.IN_FLIGHT;
                    else if (s3 == EPassenger.inFlight.leaveThePlane)
                        this.state = EPassenger.State.AT_DESTINATION;
                    break;

                case AT_DESTINATION:
                    end = true;
                    break;
            }
        }
    }

    /**
     * Given the state's current state, it returns the state in {@code String} format 
     * @return current state in {@code String} type
     */
    public String getStateString(){
        switch(this.state) {
            case GOING_TO_AIRPORT:
                return "GTA";

            case IN_QUEUE:
                return "IQ";

            case IN_FLIGHT:
                return "IF";

            case AT_DESTINATION:
                return "AD";
        }
        return "####";
    }
}