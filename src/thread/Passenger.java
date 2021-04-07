package thread;

import monitor.DepartureAirport;
import monitor.Plane;
import states.EPassenger;

public class Passenger extends Thread{
    private int number;
    private EPassenger.State state;
    private Plane plane;
    private DepartureAirport dp;

    public Passenger(Plane plane, DepartureAirport dp, int i){
        this.state = EPassenger.State.GOING_TO_AIRPORT;
        this.plane = plane;
        this.dp = dp;
        this.number = i;
    }

    @Override
    public void run(){
        boolean end = false;
        while(!end){
            switch(this.state) {
                case GOING_TO_AIRPORT:
                    EPassenger.goingToAirport s1 = this.dp.goingToAirport();
                    this.state = EPassenger.State.IN_QUEUE;
                    break;

                case IN_QUEUE:
                    EPassenger.inQueue s2 = this.dp.inQueue();
                    this.state = EPassenger.State.IN_FLIGHT;
                    break;

                case IN_FLIGHT:
                    EPassenger.inFlight s3 = this.plane.inFlight();
                    this.state = EPassenger.State.AT_DESTINATION;
                    break;

                case AT_DESTINATION:
                    end = true;
                    break;
            }
        }
    }

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