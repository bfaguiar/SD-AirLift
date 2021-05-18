import stubs.DepartureAirport;
import stubs.Plane;
import stubs.ArrivalAirport;
import threads.Passenger;

public class Initializer {
    public static void main(String args[]){
        int id = Integer.parseInt(args[0]);
        
        DepartureAirport dp = new DepartureAirport();
        Plane plane = new Plane();
        ArrivalAirport ap = new ArrivalAirport();

        Passenger passenger = new Passenger(plane, dp, ap, id);

        passenger.start();
        try {
            passenger.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
