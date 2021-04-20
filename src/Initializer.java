import repo.Repository;
import monitor.ArrivalAirport;
import monitor.DepartureAirport;
import monitor.Plane;
import thread.Hostess;
import thread.Passenger;
import thread.Pilot;

public class Initializer {
    public static void main(String[] args) throws Exception {
        // Constants
        final int N_PASSENGERS = 20;
        final int N_CAPACITY_MIN = 10;
        final int N_CAPACITY_MAX = 10;

        // Instantiate Monitors
        Repository repository = new Repository();
        ArrivalAirport aa = new ArrivalAirport(repository);
        DepartureAirport dp = new DepartureAirport(repository, N_CAPACITY_MIN, N_CAPACITY_MAX, N_PASSENGERS);
        Plane plane = new Plane(repository);

        // Instantiate Threads
        Hostess hostess = new Hostess(plane, dp);
        Pilot pilot = new Pilot(plane, dp, aa);
        Passenger[] passenger_list = new Passenger[N_PASSENGERS];
        for(int i = 0; i < N_PASSENGERS; i++)
            passenger_list[i] = new Passenger(plane, dp, aa, i);

        repository.pilot = pilot;
        repository.hostess = hostess;
        repository.passenger_list = passenger_list;

        // Start Threads
        repository.logEntities();
        hostess.start();
        pilot.start();
        for(int i = 0; i < N_PASSENGERS; i++)
            passenger_list[i].start();
        
        // Wait for threads to end
        hostess.join();
        pilot.join();
        for(int i = 0; i < N_PASSENGERS; i++)
            passenger_list[i].join();
        
        repository.closelog();
    }
}
