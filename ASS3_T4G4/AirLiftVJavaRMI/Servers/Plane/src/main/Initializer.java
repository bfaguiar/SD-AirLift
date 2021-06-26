package main;

import interfaces.*;
import genclass.GenericIO;
import shared.Plane;

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
        int portPlane = Integer.parseInt(args[5]);

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
      
        Plane plane = new Plane(repoInt);
        PlaneInterface planeInt = null;


        try
        { planeInt = (PlaneInterface) UnicastRemoteObject.exportObject (plane, portPlane);
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
      { registerInt.bind (nameEntryObject, planeInt);
      }
      catch (RemoteException e)
      { GenericIO.writelnString ("Plane registration exception: " + e.getMessage ());
        e.printStackTrace ();
        System.exit (1);
      }
      catch (AlreadyBoundException e)
      { GenericIO.writelnString ("Plane already bound exception: " + e.getMessage ());
        e.printStackTrace ();
        System.exit (1);
      }
      GenericIO.writelnString ("Plane object was registered!");

                              
      /* Wait for the service to end */
      while(!end){
          try {
              synchronized(plane){
                  plane.wait();
              }
          } catch (InterruptedException ex) {
              GenericIO.writelnString("Main thread of Plane was interrupted.");
              System.exit(1);
          }
      }

      GenericIO.writelnString("Plane finished execution.");

          
      /* Unregister shared region */
      try
      { registerInt.unbind (nameEntryObject);
      }
      catch (RemoteException e)
      { GenericIO.writelnString ("Plane unregistration exception: " + e.getMessage ());
        e.printStackTrace ();
        System.exit (1);
      } catch (NotBoundException ex) {
        GenericIO.writelnString ("Plane unregistration exception: " + ex.getMessage ());
        ex.printStackTrace ();
        System.exit (1);
      }
      GenericIO.writelnString ("Plane object was unregistered!");


    /* Unexport shared region */
    try
    { UnicastRemoteObject.unexportObject (plane, false);
    }
    catch (RemoteException e)
    { GenericIO.writelnString ("Plane unexport exception: " + e.getMessage ());
      e.printStackTrace ();
      System.exit (1);
    }

    GenericIO.writelnString ("Plane object was unexported successfully!");

  }

}





