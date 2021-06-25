package main;

import interfaces.DepartureAirportInterface;
import interfaces.PlaneInterface;
import interfaces.ArrivalAirportInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import genclass.GenericIO;
import threads.Passenger;

/**
 * Passenger's main
 * @author Bruno Aguiar 80177
 * @author David Rocha 84807
 */
public class Initializer {
    public static void main(String args[]){
        int id = Integer.parseInt(args[0]);
        String reg_address = args[1];
        int reg_port = Integer.parseInt(args[2]);
        String dp_name = args[3];
        String plane_name = args[4];
        String ap_name = args[5];

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

        ArrivalAirportInterface ap = null;

        try {
            ap = (ArrivalAirportInterface) registry.lookup(ap_name);
        }
        catch (NotBoundException ex) {
            System.out.println("ArrivalAirport is not registered: " + ex.getMessage () );
            ex.printStackTrace ();
            System.exit(1);
        } catch (RemoteException ex) {
            System.out.println("Exception thrown while locating ArrivalAirport: " + ex.getMessage () );
            ex.printStackTrace ();
        }
        
        /**
        * Instantiation of Passenger
        */        
        Passenger passenger = new Passenger(plane, dp, ap, id);

        /**
         * Thread's inicialization
         */
        passenger.start();
        try {
            passenger.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
