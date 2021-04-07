package thread;

import monitor.ArrivalAirport;
import monitor.DepartureAirport;
import monitor.Plane;
import states.EPilot;

public class Pilot extends Thread{
    private EPilot.State state;
    private Plane plane;
    private DepartureAirport dp;
    private ArrivalAirport ap;

    public Pilot(Plane plane, DepartureAirport dp, ArrivalAirport ap){
        this.plane = plane;
        this.dp = dp;
        this.ap = ap;
        this.state = EPilot.State.AT_TRANSFER_GATE;
    }

    @Override
    public void run(){
        boolean end = false;
        while(!end){
            switch(this.state) {
                case AT_TRANSFER_GATE:
                    EPilot.atTransferGate s1 = this.dp.atTransferGate();
                    this.state = EPilot.State.READY_FOR_BOARDING;
                    break;

                case READY_FOR_BOARDING:
                    EPilot.readyForBoarding s2 = this.dp.readyForBoarding();
                    this.state = EPilot.State.WAIT_FOR_BOARDING;
                    break;

                case WAIT_FOR_BOARDING:
                    EPilot.waitingForBoarding s3 = this.plane.waitingForBoarding();
                    this.state = EPilot.State.FLYING_FORWARD;
                    break;

                case FLYING_FORWARD:
                    EPilot.flyingForward s4 = this.plane.flyingForward();
                    this.state = EPilot.State.DEBOARDING;
                    break;
                    
                case DEBOARDING:
                    EPilot.deboarding s5 = this.ap.deboarding();
                    this.state = EPilot.State.FLYING_BACK;
                    break;
                    
                case FLYING_BACK:
                    EPilot.flyingBack s6 = this.ap.flyingBack();
                    end = true;
                    break;
            }
        }
    }

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
