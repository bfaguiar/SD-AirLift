import shared.ArrivalAirport;
import shared.ArrivalAirportProxy;
import stubs.Repository;

public class Initializer {
    public static void main(String args[]){
        Repository repo = new Repository();

        ArrivalAirport ap = new ArrivalAirport(repo);
        ArrivalAirportProxy proxy = new ArrivalAirportProxy(ap);
    }
}
