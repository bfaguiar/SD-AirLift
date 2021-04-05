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
                    System.out.println(this.toString("state: ", this.state.name()));
                    EHostess.waitForFlight s1 = this.dp.waitForFlight();
                    System.out.println(this.toString("action: ", s1.name()));
                    this.state = EHostess.State.WAIT_FOR_PASSENGER;
                    break;

                case WAIT_FOR_PASSENGER:
                    System.out.println(this.toString("state: ", this.state.name()));
                    EHostess.waitForPassenger s2 = this.plane.waitForPassenger();
                    System.out.println(this.toString("action: ", s2.name()));
                    this.state = EHostess.State.CHECK_PASSENGER;
                    break;

                case CHECK_PASSENGER:
                    System.out.println(this.toString("state: ", this.state.name()));
                    EHostess.checkPassenger s3 = this.dp.checkPassenger();
                    System.out.println(this.toString("action: ", s3.name()));
                    this.state = EHostess.State.READY_TO_FLY;
                    break;

                case READY_TO_FLY:
                    System.out.println(this.toString("state: ", this.state.name()));
                    EHostess.readyToFly s4 = this.dp.readyToFly();
                    System.out.println(this.toString("action: ", s4.name()));
                    end = true;
                    break;
            }
        }
    }

    private String toString(String s1, String s2){
        return String.format("%-20s", "[Hostess]") +String.format("%-10s", s1) +String.format("%-30s", s2);
    }
}
