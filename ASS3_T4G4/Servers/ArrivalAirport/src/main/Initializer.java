package main;
import Interface.*;
import genclass.GenericIO;
import shared.ArrivalAirport;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


/**
 * @author Bruno Aguiar
 * @author David Rocha
 */
public class Initializer {
    
    /**
     * Used to check if the service must terminate.
     */
    public static boolean end = false;
    
    /**
     * Main paddock launcher
     * @param args args 
     */
    public static void main(String [] args){
        
        /* get location of the registry service */
        String rmiRegHostName = args[0];
        int rmiRegPortNumb = Integer.parseInt(args[1]);
        
        String nameEntryBase = args[2];
        String nameEntryObject = args[3];
        String nameEntryRepo = args[4];
        int portAp = Integer.parseInt(args[5]);

        Registry registry = null;
        RegisterInterface registerInt = null;
        
        /* create and install the security manager */
        if (System.getSecurityManager () == null)
            System.setSecurityManager (new SecurityManager ());
        
        try
        { registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
        }
        catch (RemoteException e)
        { GenericIO.writelnString ("RMI registry locate exception: " + e.getMessage ());
          e.printStackTrace ();
          System.exit (1);
        }
        
        /* Localize the repo in the RMI server by its name */
        RepositoryInterface repoInt = null;
        
        try
        { 
            repoInt = (RepositoryInterface) registry.lookup (nameEntryRepo);
        }
        catch (RemoteException e)
        { 
            System.out.println("Repository lookup exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }
        catch (NotBoundException e)
        { 
            System.out.println("Repository not bound exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit(1);
        }
        
        /* Initialize the shared region */
        ArrivalAirport aa = new ArrivalAirport(repoInt);
        ArrivalAirportInterface aaInt = null;
        
        try
        { aaInt = (ArrivalAirportInterface) UnicastRemoteObject.exportObject (aa, portAp);
        }
        catch (RemoteException e)
        { GenericIO.writelnString ("ArrivalAirport stub generation exception: " + e.getMessage ());
          e.printStackTrace ();
          System.exit (1);
        }
        
        /* register it with the general registry service */
        try
        { registerInt = (RegisterInterface) registry.lookup (nameEntryBase);
        }
        catch (RemoteException e)
        { GenericIO.writelnString ("Register lookup exception: " + e.getMessage ());
          e.printStackTrace ();
          System.exit (1);
        }
        catch (NotBoundException e)
        { GenericIO.writelnString ("Register not bound exception: " + e.getMessage ());
          e.printStackTrace ();
          System.exit (1);
        }

        try
        { registerInt.bind (nameEntryObject, aaInt);
        }
        catch (RemoteException e)
        { GenericIO.writelnString ("ArrivalAirport registration exception: " + e.getMessage ());
          e.printStackTrace ();
          System.exit (1);
        }
        catch (AlreadyBoundException e)
        { GenericIO.writelnString ("ArrivalAirport already bound exception: " + e.getMessage ());
          e.printStackTrace ();
          System.exit (1);
        }
        GenericIO.writelnString ("ArrivalAirport object was registered!");
        
        /* Wait for the service to end */
        while(!end){
            try {
                synchronized(aa){
                    aa.wait();
                }
            } catch (InterruptedException ex) {
                GenericIO.writelnString("Main thread of ArrivalAirport was interrupted.");
                System.exit(1);
            }
        }
        
        GenericIO.writelnString("ArrivalAirport finished execution.");
        
        /* Unregister shared region */
        try
        { registerInt.unbind (nameEntryObject);
        }
        catch (RemoteException e)
        { GenericIO.writelnString ("ArrivalAirport unregistration exception: " + e.getMessage ());
          e.printStackTrace ();
          System.exit (1);
        } catch (NotBoundException ex) {
          GenericIO.writelnString ("ArrivalAirport unregistration exception: " + ex.getMessage ());
          ex.printStackTrace ();
          System.exit (1);
        }
        GenericIO.writelnString ("ArrivalAirport object was unregistered!");
        
        /* Unexport shared region */
        try
        { UnicastRemoteObject.unexportObject (aa, false);
        }
        catch (RemoteException e)
        { GenericIO.writelnString ("ArrivalAirport unexport exception: " + e.getMessage ());
          e.printStackTrace ();
          System.exit (1);
        }
        
        GenericIO.writelnString ("ArrivalAirport object was unexported successfully!");
        
    }
    
} 
