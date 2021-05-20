package threads;

import stubs.ArrivalAirport;
import stubs.DepartureAirport;
import stubs.Plane;
import states.PilotState;

/**
 * Thread Pilot
 * @author Bruno Aguiar, 80177
 * @author David Rocha, 84807
 */
public class Pilot extends Thread {

    /**
     * Pilot's current state in the simulation
     */
    private PilotState state;

    /**
     * Plane's shared region
     * @see Plane
     */
    private Plane plane;

    /**
     * Departure Airport's shared region
     * @see DepartureAirport
     */
    private DepartureAirport dp;

    /**
     * Arrival Airport's shared region
     */
    private ArrivalAirport ap;

    /**
     * Constructior
     * @param plane instance of Plane's shared region
     * @param dp instance of Departure Airport's shared region
     * @param ap instance of Arrival Airport's shared region
     */
    public Pilot(Plane plane, DepartureAirport dp, ArrivalAirport ap){
        this.plane = plane;
        this.dp = dp;
        this.ap = ap;
        this.state = PilotState.AT_TRANSFER_GATE;
    }

    /**
     * Thread's life cycle
     */
    @Override
    public void run(){
        boolean end = false;
        while(!end){
            switch(this.state) {
                case AT_TRANSFER_GATE:
                    if(this.dp.noMorePassengers()){
                        end = true;
                        break;
                    }
                    this.dp.pilotInformPlaneReadyForBoarding(this.getStateString());
                    this.state = PilotState.READY_FOR_BOARDING;
                    break;

                case READY_FOR_BOARDING:
                    this.plane.pilotWaitForAllInBoard(this.getStateString());
                    this.state = PilotState.WAIT_FOR_BOARDING;
                    break;

                case WAIT_FOR_BOARDING:
                    this.plane.pilotFlyToDestinationPoint(this.getStateString());
                    this.state = PilotState.FLYING_FORWARD;
                    break;

                case FLYING_FORWARD:
                    this.plane.pilotAnnounceArrival(this.getStateString());
                    this.state = PilotState.DEBOARDING;
                    break;
                    
                case DEBOARDING:
                    this.ap.pilotFlyToDeparturePoint(this.plane.getNumberInPlane(), this.getStateString());
                    this.state = PilotState.FLYING_BACK;
                    break;
                    
                case FLYING_BACK:
                    this.dp.pilotParkAtTransferGate(this.getStateString());
                    this.state = PilotState.AT_TRANSFER_GATE;
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
            case AT_TRANSFER_GATE:
                return "ATF";

            case READY_FOR_BOARDING:
                return "RBB";

            case WAIT_FOR_BOARDING:
                return "WFB";

            case FLYING_FORWARD:
                return "FF";
                
            case DEBOARDING:
                return "D";
                
            case FLYING_BACK:
                return "FB";
        }
        return "####";
    }
}
