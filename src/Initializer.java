import monitor.ArrivalAirport;
import monitor.DepartureAirport;
import monitor.Plane;
import thread.Hostess;
import thread.Passenger;
import thread.Pilot;

public class Initializer {
    public static void main(String[] args) throws Exception {
        // Constants
        final int N_PASSENGERS = 1;
        final int N_CAPACITY_MIN = 1;
        final int N_CAPACITY_MAX = 10;

        // Instantiate Monitors
        ArrivalAirport aa = new ArrivalAirport();
        DepartureAirport dp = new DepartureAirport(N_CAPACITY_MIN, N_CAPACITY_MAX);
        Plane plane = new Plane();

        // Instantiate Threads
        Hostess hostess = new Hostess(plane, dp);
        Pilot pilot = new Pilot(plane, dp, aa);
        Passenger[] passenger_list = new Passenger[N_PASSENGERS];
        for(int i = 0; i < N_PASSENGERS; i++)
            passenger_list[i] = new Passenger(plane, dp, i);

        // Start Threads
        hostess.start();
        pilot.start();
        for(int i = 0; i < N_PASSENGERS; i++)
            passenger_list[i].start();

    }
}
