package thread;

import monitor.DepartureAirport;
import monitor.Plane;
import states.EHostess;

public class Hostess extends Thread{
    private EHostess.State state;
    private DepartureAirport dp;
    private Plane plane;

    public Hostess(Plane plane, DepartureAirport dp){
        this.state = EHostess.State.WAIT_FOR_NEXT_FLIGHT;
        this.dp = dp;
        this.plane = plane;
    }

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
