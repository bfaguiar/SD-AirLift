package main;

import stubs.DepartureAirport;
import stubs.Plane;
import stubs.ArrivalAirport;
import threads.Passenger;

public class Initializer {
    public static void main(String args[]){
        int id = Integer.parseInt(args[0]);

        String dp_address = args[1];
        int dp_port = Integer.parseInt(args[2]);
        String plane_address = args[3];
        int plane_port = Integer.parseInt(args[4]);
        String ap_address = args[5];
        int ap_port = Integer.parseInt(args[6]);
        
        DepartureAirport dp = new DepartureAirport(dp_address, dp_port);
        Plane plane = new Plane(plane_address, plane_port);
        ArrivalAirport ap = new ArrivalAirport(ap_address, ap_port);

        Passenger passenger = new Passenger(plane, dp, ap, id);

        passenger.start();
        try {
            passenger.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
