import stubs.DepartureAirport;
import stubs.Plane;
import threads.Hostess;

public class Initializer {
    public static void main(String args[]){
        String dp_address = args[0];
        int dp_port = Integer.parseInt(args[1]);
        String plane_address = args[2];
        int plane_port = Integer.parseInt(args[3]);

        DepartureAirport dp = new DepartureAirport(dp_address, dp_port);
        Plane plane = new Plane(plane_address, plane_port);

        Hostess hostess = new Hostess(plane, dp);

        hostess.start();
        try {
            hostess.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
