package thread;

import monitor.ArrivalAirport;
import monitor.DepartureAirport;
import monitor.Plane;
import states.EPilot;

/**
 * Thread Pilot
 * @author Bruno Aguiar, 80177
 * @author David Rocha, 84807
 */
public class Pilot extends Thread {

    /**
     * Pilot's current state in the simulation
     */
    private EPilot.State state;

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
        this.state = EPilot.State.AT_TRANSFER_GATE;
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
                    EPilot.atTransferGate s1 = this.dp.atTransferGate();
                    if (s1 == EPilot.atTransferGate.informPlaneReadyForBoarding)
                        this.state = EPilot.State.READY_FOR_BOARDING;
                    else if (s1 == EPilot.atTransferGate.endLife)
                        end = true;
                    break;

                case READY_FOR_BOARDING:
                    EPilot.readyForBoarding s2 = this.dp.readyForBoarding();
                    assert s2 == EPilot.readyForBoarding.waitForAllInBoard;
                    this.state = EPilot.State.WAIT_FOR_BOARDING;
                    break;

                case WAIT_FOR_BOARDING:
                    EPilot.waitingForBoarding s3 = this.plane.waitingForBoarding();
                    assert s3 == EPilot.waitingForBoarding.flyToDestinationPoint;
                    this.state = EPilot.State.FLYING_FORWARD;
                    break;

                case FLYING_FORWARD:
                    EPilot.flyingForward s4 = this.plane.flyingForward();
                    assert s4 == EPilot.flyingForward.announceArrival;
                    this.state = EPilot.State.DEBOARDING;
                    break;
                    
                case DEBOARDING:
                    EPilot.deboarding s5 = this.ap.deboarding();
                    assert s5 == EPilot.deboarding.flyToDeparturePoint;
                    this.state = EPilot.State.FLYING_BACK;
                    break;
                    
                case FLYING_BACK:
                    EPilot.flyingBack s6 = this.ap.flyingBack();
                    assert s6 == EPilot.flyingBack.parkAtTransferGate;
                    this.state = EPilot.State.AT_TRANSFER_GATE;
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
