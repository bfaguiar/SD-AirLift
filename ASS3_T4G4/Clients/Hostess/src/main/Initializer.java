package main;

import stubs.DepartureAirport;
import stubs.Plane;
import threads.Hostess;

/**
 * Hostess's main
 * @author Bruno Aguiar 80177
 * @author David Rocha 84807
 */
public class Initializer {
    public static void main(String args[]) {

        /**
         * argument fetching
         */
        String dp_address = args[0];
        int dp_port = Integer.parseInt(args[1]);
        String plane_address = args[2];
        int plane_port = Integer.parseInt(args[3]);

        /**
         * Instantiation of stubs
         */
        DepartureAirport dp = new DepartureAirport(dp_address, dp_port);
        Plane plane = new Plane(plane_address, plane_port);

        /**
         * Instantiation of Hostess
         */
        Hostess hostess = new Hostess(plane, dp);

        /**
         * Thread's inicialization
         */
        hostess.start();
        try {
            hostess.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /**
         * Server shutdown
         */
        dp.serverShutdown();
        plane.serverShutdown();
    }
}
