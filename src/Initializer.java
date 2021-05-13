import repo.Repository;
import monitor.ArrivalAirport;
import monitor.DepartureAirport;
import monitor.Plane;
import thread.Hostess;
import thread.Passenger;
import thread.Pilot;

/**
* <h1> Air Lift: Initializer </h1>
* The Air lift Program is implements a concurrent application that simulates activities of the passengers during an air lift from an origin town and a destination town.
* @author Bruno Aguiar, 80177
* @author David Rocha, 84807
*/

public class Initializer {
    /**
     * Main program,
     *  it is implemented by the main method of the data type. 
     * @throws Exception
     * @see Exception 
     */
    public static void main(String[] args) throws Exception {
        
        /**
         *  Number of passengers in the simulation
         */
        final int N_PASSENGERS = 21;

        /**
         *  Minimum capacity of the plane
         */
        final int N_CAPACITY_MIN = 5;

        /**
         * Maximum capacity of the plane
         */
        final int N_CAPACITY_MAX = 10;
        
        /**
         * Instantiation of the shared regions
         */
        Repository repository = new Repository();
        ArrivalAirport aa = new ArrivalAirport(repository);
        DepartureAirport dp = new DepartureAirport(repository, N_CAPACITY_MIN, N_CAPACITY_MAX, N_PASSENGERS);
        Plane plane = new Plane(repository);

        /**
         * Instantiation of Threads 
         */
        Hostess hostess = new Hostess(plane, dp);
        Pilot pilot = new Pilot(plane, dp, aa);
        Passenger[] passengerList = new Passenger[N_PASSENGERS];
        for(int i = 0; i < N_PASSENGERS; i++)
            passengerList[i] = new Passenger(plane, dp, aa, i);

        /**
         * General Repository of Information start up
         */
        repository.setPilot(pilot);
        repository.setHostess(hostess);
        repository.setPassengerList(passengerList);
        repository.logEntities();

        /**
         * Initialization of Threads
         */
        hostess.start();
        pilot.start();
        for(int i = 0; i < N_PASSENGERS; i++)
            passengerList[i].start();
        
        /**
         *  Wait for threads to end 
         */
        hostess.join();
        pilot.join();
        for(int i = 0; i < N_PASSENGERS; i++)
            passengerList[i].join();
        
        /**
         *  Close the writing session
         */
        repository.closelog();
    }
}
