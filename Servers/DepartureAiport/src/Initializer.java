import shared.DepartureAirport;
import shared.DepartureAirportProxy;
import stubs.Repository;

public class Initializer {
    public static void main(String args[]){
        Repository repo = new Repository();

        DepartureAirport dp = new DepartureAirport(repo, 0, 0, 0);
        DepartureAirportProxy proxy = new DepartureAirportProxy(dp);
    }
}
