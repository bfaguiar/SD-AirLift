package threads;

import stubs.ArrivalAirport;
import stubs.DepartureAirport;
import stubs.Plane;
import states.PassengerState;

/**
 *  Thread Passenger
 * @author Bruno Aguiar, 80177
 * @author David Rocha, 84807
 */
public class Passenger extends Thread {

    /**
     * Passenger's current state in the simulation
     */
    private PassengerState state;

     /**
     * Departure Airport's shared region
     * @see DepartureAirport
     */
    private DepartureAirport dp;

    /**
     * Arrival Airport's shared region
     * @see ArrivalAirport
     */
    private ArrivalAirport ap;

    /**
     * Plane shared region
     * @see Plane
     */
    private Plane plane;

    /**
     * Passenger's ID
     */
    private int id;

    /**
     * Constructor
     * @param plane instance of Plane's shared region
     * @param dp instance of Departure Airport's shared region
     * @param ap instance of Arrival Airport's shared region
     * @param i Passenger's ID
     */
    public Passenger(Plane plane, DepartureAirport dp, ArrivalAirport ap, int i){
        this.state = PassengerState.GOING_TO_AIRPORT;
        this.dp = dp;
        this.plane = plane;
        this.ap = ap;
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
                    this.dp.passengerTravelToAirport(this.id, this.getStateString());
                    this.dp.passengerWaitInQueue(this.id, this.getStateString());
                    this.state = PassengerState.IN_QUEUE;
                    break;

                case IN_QUEUE:
                    this.dp.passengerShowDocuments(this.id, this.getStateString());
                    this.plane.passengerBoardThePlane(this.id, this.getStateString());
                    this.state = PassengerState.IN_FLIGHT;
                    break;

                case IN_FLIGHT:
                    this.plane.passengerWaitForEndOfFlight(this.id, this.getStateString());
                    this.state = PassengerState.AT_DESTINATION;
                    break;

                case AT_DESTINATION:
                    this.plane.passengerExit(this.id);
                    this.ap.passengerLeaveThePlane(this.id, this.getStateString());
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