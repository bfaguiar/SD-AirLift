package main;
import interfaces.*;
import genclass.GenericIO;
import shared.DepartureAirport;

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
     * @param args args
     */
    public static void main(String [] args){
        /* get location of the registry service */
        String rmiRegHostName = args[0];
        int rmiRegPortNumb = Integer.parseInt(args[1]);
        
        String nameEntryBase = args[2];
        String nameEntryObject = args[3];
        String nameEntryRepo = args[4];
        int portDp = Integer.parseInt(args[5]);
        int capacityMin = Integer.parseInt(args[6]);
        int capacityMax = Integer.parseInt(args[7]);
        int totalPassengers = Integer.parseInt(args[8]);

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
      
        DepartureAirport dp = new DepartureAirport(repoInt, capacityMin, capacityMax, totalPassengers);
        DepartureAirportInterface dpInt = null;

 
        try
        { dpInt = (DepartureAirportInterface) UnicastRemoteObject.exportObject (dp, portDp);
        }
        catch (RemoteException e)
        { GenericIO.writelnString ("DepartureAirport stub generation exception: " + e.getMessage ());
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
      { registerInt.bind (nameEntryObject, dpInt);
      }
      catch (RemoteException e)
      { GenericIO.writelnString ("DepartureAirport registration exception: " + e.getMessage ());
        e.printStackTrace ();
        System.exit (1);
      }
      catch (AlreadyBoundException e)
      { GenericIO.writelnString ("DepartureAirport already bound exception: " + e.getMessage ());
        e.printStackTrace ();
        System.exit (1);
      }
      GenericIO.writelnString ("DepartureAirport object was registered!");

                              
      /* Wait for the service to end */
      while(!end){
          try {
              synchronized(dp){
                  dp.wait();
              }
          } catch (InterruptedException ex) {
              GenericIO.writelnString("Main thread of DepartureAirport was interrupted.");
              System.exit(1);
          } 
      }

      GenericIO.writelnString("DepartureAirport finished execution.");

          
      /* Unregister shared region */
      try
      { registerInt.unbind (nameEntryObject);
      }
      catch (RemoteException e)
      { GenericIO.writelnString ("DepartureAirport unregistration exception: " + e.getMessage ());
        e.printStackTrace ();
        System.exit (1);
      } catch (NotBoundException ex) {
        GenericIO.writelnString ("DepartureAirport unregistration exception: " + ex.getMessage ());
        ex.printStackTrace ();
        System.exit (1);
      }
      GenericIO.writelnString ("DepartureAirport object was unregistered!");
 
 
    /* Unexport shared region */
    try
    { UnicastRemoteObject.unexportObject (dp, false);
    }
    catch (RemoteException e)
    { GenericIO.writelnString ("DepartureAirport unexport exception: " + e.getMessage ());
      e.printStackTrace ();
      System.exit (1);
    }

    GenericIO.writelnString (" DepartureAirport object was unexported )successfully!");

  }
 
}   


 


