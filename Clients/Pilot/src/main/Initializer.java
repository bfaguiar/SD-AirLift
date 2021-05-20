package main;

import stubs.DepartureAirport;
import stubs.Plane;
import stubs.ArrivalAirport;
import threads.Pilot;

public class Initializer {
    public static void main(String args[]){
        String dp_address = args[0];
        int dp_port = Integer.parseInt(args[1]);
        String plane_address = args[2];
        int plane_port = Integer.parseInt(args[3]);
        String ap_address = args[4];
        int ap_port = Integer.parseInt(args[5]);
        
        DepartureAirport dp = new DepartureAirport(dp_address, dp_port);
        Plane plane = new Plane(plane_address, plane_port);
        ArrivalAirport ap = new ArrivalAirport(ap_address, ap_port);

        Pilot pilot = new Pilot(plane, dp, ap);

        pilot.start();
        try {
            pilot.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ap.serverShutdown();
        plane.serverShutdown();
        dp.serverShutdown();

    }
}
