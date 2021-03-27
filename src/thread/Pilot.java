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
                    /*try {
                        Thread.sleep(5000);
                    } catch(InterruptedException e) {}*/
                    System.out.println(this.toString("state: ", this.state.name()));
                    EPilot.atTransferGate s1 = this.dp.atTransferGate();
                    System.out.println(this.toString("action: ", s1.name()));
                    this.state = EPilot.State.READY_FOR_BOARDING;
                    break;

                case READY_FOR_BOARDING:
                    System.out.println(this.toString("state: ", this.state.name()));
                    EPilot.readyForBoarding s2 = this.plane.readyForBoarding();
                    System.out.println(this.toString("action: ", s2.name()));
                    this.state = EPilot.State.WAIT_FOR_BOARDING;
                    break;

                case WAIT_FOR_BOARDING:
                    System.out.println(this.toString("state: ", this.state.name()));
                    EPilot.waitingForBoarding s3 = this.plane.waitingForBoarding();
                    System.out.println(this.toString("action: ", s3.name()));
                    this.state = EPilot.State.FLYING_FORWARD;
                    break;

                case FLYING_FORWARD:
                    System.out.println(this.toString("state: ", this.state.name()));
                    EPilot.flyingForward s4 = this.plane.flyingForward();
                    System.out.println(this.toString("action: ",s4.name()));
                    this.state = EPilot.State.DEBOARDING;
                    break;
                    
                case DEBOARDING:
                    System.out.println(this.toString("state: ", this.state.name()));
                    EPilot.deboarding s5 = this.ap.deboarding();
                    System.out.println(this.toString("action: ", s5.name()));
                    this.state = EPilot.State.FLYING_BACK;
                    break;
                    
                case FLYING_BACK:
                    System.out.println(this.toString("state: ", this.state.name()));
                    EPilot.flyingBack s6 = this.ap.flyingBack();
                    System.out.println(this.toString("action: ", s6.name()));
                    end = true;
                    break;
            }
        }
    }

    private String toString(String s1, String s2){
        return String.format("%-20s", "[Pilot]") +String.format("%-10s", s1) +String.format("%-30s", s2);
    }
}
