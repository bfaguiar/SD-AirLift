import shared.DepartureAirport;
import shared.DepartureAirportProxy;
import stubs.Repository;

public class Initializer {
    public static void main(String args[]){
        String server_address = args[0];
        int server_port = Integer.parseInt(args[1]);

        int N_PASSENGERS = Integer.parseInt(args[1]);
        int CAPACITY_MIN = Integer.parseInt(args[2]);
        int CAPACITY_MAX = Integer.parseInt(args[3]);

        Repository repo = new Repository();
        DepartureAirport dp = new DepartureAirport(repo, CAPACITY_MIN, CAPACITY_MAX, N_PASSENGERS);
        DepartureAirportProxy proxy = new DepartureAirportProxy(dp);
    }
}
