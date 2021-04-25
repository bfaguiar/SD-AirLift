package thread;

import monitor.DepartureAirport;
import monitor.Plane;
import states.EHostess;

/**
 * Thread Hostess
 * @author Bruno Aguiar, 80177
 * @author David Rocha, 84807
 */
public class Hostess extends Thread {

    /**
     * Hostess' current state in the simulation
     */
    private EHostess.State state;
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
        this.state = EHostess.State.WAIT_FOR_NEXT_FLIGHT;
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
                case WAIT_FOR_NEXT_FLIGHT:
                    EHostess.waitForFlight s1 = this.dp.waitForFlight();
                    if(s1 == EHostess.waitForFlight.prepareForPassBoarding)
                        this.state = EHostess.State.WAIT_FOR_PASSENGER;
                    else if (s1 == EHostess.waitForFlight.endLife)
                        end = true;
                    break;

                case WAIT_FOR_PASSENGER:
                    EHostess.waitForPassenger s2 = this.dp.waitForPassenger();
                    if (s2 == EHostess.waitForPassenger.informPlaneReadyToTakeOff)
                        this.state = EHostess.State.READY_TO_FLY;
                    else if (s2 == EHostess.waitForPassenger.checkDocuments)
                        this.state = EHostess.State.CHECK_PASSENGER;
                    break;

                case CHECK_PASSENGER:
                    EHostess.checkPassenger s3 = this.dp.checkPassenger();
                    assert s3 == EHostess.checkPassenger.waitForNextPassenger;
                    this.state = EHostess.State.WAIT_FOR_PASSENGER;
                    break;

                case READY_TO_FLY:
                    EHostess.readyToFly s4 = this.plane.readyToFly();
                    assert s4 == EHostess.readyToFly.waitForNextFlight;
                    this.state = EHostess.State.WAIT_FOR_NEXT_FLIGHT;
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
            case WAIT_FOR_NEXT_FLIGHT:
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
