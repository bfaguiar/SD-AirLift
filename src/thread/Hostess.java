package thread;

import monitor.DepartureAirport;
import monitor.Plane;
import states.EHostess;

public class Hostess extends Thread{
    
    private EHostess.State state;
    private DepartureAirport dp;
    private Plane plane;

    public Hostess(Plane plane, DepartureAirport dp){
        state = EHostess.State.WAIT_FOR_NEXT_FLIGHT;
        this.dp = dp;
        this.plane = plane;
    }

    @Override
    public void run(){
        switch(state) {
            case WAIT_FOR_NEXT_FLIGHT:
                dp.waitForFlight();
                break;
        }
    }
}
