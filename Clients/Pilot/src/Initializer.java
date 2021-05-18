import stubs.DepartureAirport;
import stubs.Plane;
import stubs.ArrivalAirport;
import threads.Pilot;

public class Initializer {
    public static void main(String args[]){
        DepartureAirport dp = new DepartureAirport();
        Plane plane = new Plane();
        ArrivalAirport ap = new ArrivalAirport();

        Pilot pilot = new Pilot(plane, dp, ap);

        pilot.start();
        try {
            pilot.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
