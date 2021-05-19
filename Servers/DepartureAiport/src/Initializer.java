import communication.ServerCom;
import shared.DepartureAirport;
import shared.DepartureAirportProxy;
import shared.DepartureAirportService;
import stubs.Repository;

public class Initializer {
    public static boolean end = false;
    public static void main(String args[]){
        int server_port = Integer.parseInt(args[0]);

        int N_PASSENGERS = Integer.parseInt(args[1]);
        int CAPACITY_MIN = Integer.parseInt(args[2]);
        int CAPACITY_MAX = Integer.parseInt(args[3]);

        String repo_address = args[4];
        int repo_port = Integer.parseInt(args[5]);
        
        Repository repo = new Repository(repo_address, repo_port);
        DepartureAirport dp = new DepartureAirport(repo, CAPACITY_MIN, CAPACITY_MAX, N_PASSENGERS);
        DepartureAirportProxy proxy = new DepartureAirportProxy(dp);

        ServerCom scon, sconi;
        DepartureAirportService service;
        
        scon = new ServerCom(server_port);
        scon.start();
        while(!end){
            sconi = scon.accept();
            service = new DepartureAirportService(sconi, proxy);
            service.start();
        }
    }
}
