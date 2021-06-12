package main;

import interfaces.DepartureAirport;
import interfaces.Plane;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import genclass.GenericIO;
import interfaces.ArrivalAirport;
import threads.Pilot;

/**
 * Pilot's main
 * @author Bruno Aguiar, 80177
 * @author David Rocha, 84807
 */
public class Initializer {
    public static void main(String args[]){
        String reg_address = args[0];
        int reg_port = Integer.parseInt(args[1]);
        String dp_name = args[2];
        String plane_name = args[3];
        String ap_name = args[4];

        Registry registry = null;

        try {
            registry = LocateRegistry.getRegistry (reg_address, reg_port);
        }
        catch (RemoteException e){ 
            GenericIO.writelnString ("RMI registry locate exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }
        
        DepartureAirport dp = null;
        try {
            dp = (DepartureAirport) registry.lookup(dp_name);
        }
        catch (NotBoundException ex) {
            System.out.println("DepartureAirport is not registered: " + ex.getMessage () );
            ex.printStackTrace ();
            System.exit(1);
        } catch (RemoteException ex) {
            System.out.println("Exception thrown while locating DepartureAirport: " + ex.getMessage () );
            ex.printStackTrace ();
            System.exit (1);
        }
        
        Plane plane = null;
        try {
            plane = (Plane) registry.lookup(plane_name);
        }
        catch (NotBoundException ex) {
            System.out.println("Plane is not registered: " + ex.getMessage () );
            ex.printStackTrace ();
            System.exit(1);
        } catch (RemoteException ex) {
            System.out.println("Exception thrown while locating Plane: " + ex.getMessage () );
            ex.printStackTrace ();
            System.exit (1);
        }

        ArrivalAirport ap = null;
        try {
            ap = (ArrivalAirport) registry.lookup(ap_name);
        }
        catch (NotBoundException ex) {
            System.out.println("ArrivalAirport is not registered: " + ex.getMessage () );
            ex.printStackTrace ();
            System.exit(1);
        } catch (RemoteException ex) {
            System.out.println("Exception thrown while locating ArrivalAirport: " + ex.getMessage () );
            ex.printStackTrace ();
            System.exit (1);
        }

        /**
         * Instantiation of Pilot
         */
        Pilot pilot = new Pilot(plane, dp, ap);

        /**
         * Thread's inicialization
         */
        pilot.start();
        try {
            pilot.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /**
         * Server shutdown.
         */
        try {
            ap.serverShutdown();
            plane.serverShutdown();
            dp.serverShutdown();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
