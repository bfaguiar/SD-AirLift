import shared.ArrivalAirport;
import shared.ArrivalAirportProxy;
import stubs.Repository;

public class Initializer {
    public static void main(String args[]){
        String server_address = args[0];
        int server_port = Integer.parseInt(args[1]);

        Repository repo = new Repository();
        ArrivalAirport ap = new ArrivalAirport(repo);
        ArrivalAirportProxy proxy = new ArrivalAirportProxy(ap);
    }
}
