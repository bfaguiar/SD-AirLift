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
        this.plane = plane;
        this.dp = dp;
        this.number = i;
    }

    @Override
    public void run(){
        this.state = EPassenger.State.GOING_TO_AIRPORT;
        System.out.println(this.toString("state: ", this.state.name()));
        EPassenger.goingToAirport s1 = this.dp.goingToAirport();
        System.out.println(this.toString("action: ", s1.name()));
        
        this.state = EPassenger.State.IN_QUEUE;
        System.out.println(this.toString("state: ", this.state.name()));
        EPassenger.inQueue s2 = this.dp.inQueue();
        System.out.println(this.toString("action: ", s2.name()));

        this.state = EPassenger.State.IN_FLIGHT;
        System.out.println(this.toString("state: ", this.state.name()));
        EPassenger.inFlight s3 = this.plane.inFlight();
        System.out.println(this.toString("action: ", s3.name()));

        this.state = EPassenger.State.AT_DESTINATION;
        System.out.println(this.toString("state: ", this.state.name()));
    }

    private String toString(String s1, String s2){
        return String.format("%-20s", "[Passenger" +this.number +"]") +String.format("%-10s", s1) +String.format("%-30s", s2);
    }
}