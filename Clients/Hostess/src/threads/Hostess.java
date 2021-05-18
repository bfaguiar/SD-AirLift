package threads;

import stubs.DepartureAirport;
import stubs.Plane;
import states.HostessState;

/**
 * Thread Hostess
 * @author Bruno Aguiar, 80177
 * @author David Rocha, 84807
 */
public class Hostess extends Thread {
    /**
     * Hostess' current state in the simulation
     */
    private HostessState state;
    /**
     * Departure Airport's shared region
     * @see DepartureAirport
     */
    private DepartureAirport dp;

    /**
     * Plane's shared region
     */
    private Plane plane;

    /**
     * Constructor 
     * @param plane Plane's shared region
     * @param dp Departure Airport's shared region
     */
    public Hostess(Plane plane, DepartureAirport dp){
        this.state = HostessState.WAIT_FOR_FLIGHT;
        this.dp = dp;
        this.plane = plane;
    }

    /**
     * Thread's life cycle.
     */
    @Override
    public void run(){
        boolean end = false;
        while(!end){
            switch(this.state) {
                case WAIT_FOR_FLIGHT:
                    if(this.dp.noMorePassengers()){
                        end = true;
                        break;
                    }
                    this.dp.hostessPrepareForPassBoarding(this.getStateString());
                    this.state = HostessState.WAIT_FOR_PASSENGER;
                    break;

                case WAIT_FOR_PASSENGER:
                    if(this.dp.isPlaneBoarded()){
                        this.plane.hostessInformPlaneReadyToTakeOff(this.getStateString());
                        this.state = HostessState.READY_TO_FLY;
                    }
                    else{
                        this.dp.hostessCheckDocuments(this.getStateString());
                        this.state = HostessState.CHECK_PASSENGER;
                    }
                    break;
                case CHECK_PASSENGER:
                    this.dp.hostessWaitForNextPassenger(this.getStateString());
                    this.state = HostessState.WAIT_FOR_PASSENGER;
                    break;

                case READY_TO_FLY:
                    this.dp.hostessWaitForNextFlight(this.getStateString());
                    this.state = HostessState.WAIT_FOR_FLIGHT;
                    break;
            }
        }
    }

    /**
     * Given the thread's current state, it returns the state in {@code String} format.
     * @return current state in {@code String} type
     */
    public String getStateString(){
        switch(this.state) {
            case WAIT_FOR_FLIGHT:
                return "WFNF";

            case WAIT_FOR_PASSENGER:
                return "WFP";

            case CHECK_PASSENGER:
                return "CP";

            case READY_TO_FLY:
                return "RF";
        }
        return "####";
    }
}
