package main;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import genclass.GenericIO;
import interfaces.DepartureAirportInterface;
import interfaces.PlaneInterface;
import threads.Hostess;


/**
 * Hostess's main
 * @author Bruno Aguiar 80177
 * @author David Rocha 84807
 */
public class Initializer {
    public static void main(String args[]) {
        
        /**
         * argument fetch
         */        
        String reg_address = args[0];
        int reg_port = Integer.parseInt(args[1]);
        String dp_name = args[2];
        String plane_name = args[3];
        
        Registry registry = null;

        try {
            registry = LocateRegistry.getRegistry (reg_address, reg_port);
        }
        catch (RemoteException e){ 
            GenericIO.writelnString ("RMI registry locate exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }

        DepartureAirportInterface dp = null;
        try {
            dp = (DepartureAirportInterface) registry.lookup(dp_name);
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

        PlaneInterface plane = null;

        try {
            plane = (PlaneInterface) registry.lookup(plane_name);
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
        try {
            dp.serverShutdown();
            plane.serverShutdown();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
} 
