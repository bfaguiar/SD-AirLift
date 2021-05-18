import stubs.DepartureAirport;
import stubs.Plane;
import threads.Hostess;

public class Initializer {
    public static void main(String args[]){
        DepartureAirport dp = new DepartureAirport();
        Plane plane = new Plane();

        Hostess hostess = new Hostess(plane, dp);

        hostess.start();
        try {
            hostess.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
