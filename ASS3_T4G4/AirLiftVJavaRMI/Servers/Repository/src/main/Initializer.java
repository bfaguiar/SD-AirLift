package main;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import genclass.GenericIO;
import interfaces.RegisterInterface;
import interfaces.RepositoryInterface;
import repo.Repository;

public class Initializer { 

    public static boolean end = false;

    public static void main(String args[]) {

        /* get location of the registry service */
        String rmiRegHostName = args[0];
        int rmiRegPortNumb = Integer.parseInt(args[1]);
        
        String nameEntryBase = args[2];
        String nameEntryObject = args[3];
        int portRepo = Integer.parseInt(args[4]);
        int N_PASSENGERS = Integer.parseInt(args[5]);
        String file = args[6];

        Registry registry = null;
        RegisterInterface registerInt = null;

        /* create and install the security manager */
        if (System.getSecurityManager () == null)
            System.setSecurityManager (new SecurityManager ()); 

        /* Get the RMI server registry */
        try
        { registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
        }
        catch (RemoteException e)
        { GenericIO.writelnString ("RMI registry locate exception: " + e.getMessage ());
          e.printStackTrace ();
          System.exit (1);
        }

        Repository repo = new Repository(N_PASSENGERS, file);
        RepositoryInterface repoInt = null;

        /**
         * Export repo to the registry
         */
        try
        { repoInt = (RepositoryInterface) UnicastRemoteObject.exportObject (repo, portRepo);
        }
        catch (RemoteException e)
        { GenericIO.writelnString ("Logger stub generation exception: " + e.getMessage ());
          e.printStackTrace ();
          System.exit (1);
        }

        /* register it with the general registry service */
        try
        {   
            registerInt = (RegisterInterface) registry.lookup (nameEntryBase);
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
        {   
            registerInt.bind (nameEntryObject, repoInt);
        }
        catch (RemoteException e)
        { GenericIO.writelnString ("Repository registration exception: " + e.getMessage ());
          e.printStackTrace ();
          System.exit (1);
        }
        catch (AlreadyBoundException e)
        { GenericIO.writelnString ("Repository already bound exception: " + e.getMessage ());
          e.printStackTrace ();
          System.exit (1);
        }
        GenericIO.writelnString ("Repository object was registered!");

        /* Wait for the service to end */
      while(!end){
        try {
            synchronized(repo){
              repo.wait();
            }
        } catch (InterruptedException ex) {
            GenericIO.writelnString("Main thread of Repository was interrupted.");
            System.exit(1);
        }
      }

      GenericIO.writelnString("Repository finished execution.");

         /* Unregister shared region */
         try
         { registerInt.unbind (nameEntryObject);
         }
         catch (RemoteException e)
         { GenericIO.writelnString ("Repository unregistration exception: " + e.getMessage ());
           e.printStackTrace ();
           System.exit (1);
         } catch (NotBoundException ex) {
           GenericIO.writelnString ("Repository unregistration exception: " + ex.getMessage ());
           ex.printStackTrace ();
           System.exit (1);
         }
         GenericIO.writelnString ("Repository object was unregistered!");

         /* Unexport shared region */
        try
        { UnicastRemoteObject.unexportObject (repo, false);
        }
        catch (RemoteException e)
        { GenericIO.writelnString ("Repository unexport exception: " + e.getMessage ());
          e.printStackTrace ();
          System.exit (1);
        }
        
        GenericIO.writelnString ("Repository object was unexported successfully!");
        
    } 
    
}
