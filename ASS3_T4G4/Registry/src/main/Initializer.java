package main;

import genclass.GenericIO;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import interfaces.RegisterInterface;
import java.rmi.NotBoundException;

/**
 *  This data type instantiates and registers a remote object that enables the registration of other remote objects
 *  located in the same or other processing nodes in the local registry service.
 *  Communication is based in Java RMI.
 */
public class Initializer{
    /**
     * Used to check if the service must terminate.
     */
    public static boolean serviceEnd = false;
    
   /**
    * Main task.
    * @param args
    */
   public static void main(String[] args){
    /* get location of the registry service */

    String reg_address = args[0];
    int reg_port = Integer.parseInt(args[1]);
    String reg_name = args[2];
    int reg_listening_port = Integer.parseInt(args[3]);
    int unbinds = Integer.parseInt(args[4]);

    /* create and install the security manager */

     if (System.getSecurityManager () == null)
        System.setSecurityManager (new SecurityManager ());

    /* instantiate a registration remote object and generate a stub for it */
     RegisterRemoteObject regEngine = new RegisterRemoteObject (reg_address, reg_port, unbinds);
     RegisterInterface registerInt = null;

     try{ 
        registerInt = (RegisterInterface) UnicastRemoteObject.exportObject (regEngine, reg_listening_port);
     }
     catch (RemoteException e){ 
        GenericIO.writelnString ("RegisterRemoteObject stub generation exception: " + e.getMessage ());
        System.exit (1);
     }

    /* register it with the local registry service */
     Registry registry = null;

     try{ 
        registry = LocateRegistry.getRegistry (reg_address, reg_port);
     }
     catch (RemoteException e){ 
        GenericIO.writelnString ("RMI registry creation exception: " + e.getMessage ());
        System.exit (1);
     }

     try{ 
        registry.rebind (reg_name, registerInt);
     }
     catch (RemoteException e){ 
        GenericIO.writelnString ("RegisterRemoteObject remote exception on registration: " + e.getMessage ());
       System.exit (1);
     }
     GenericIO.writelnString ("RegisterRemoteObject object was registered!");
     
     /* Wait for the service to end */
        while(!serviceEnd){
            try {
                synchronized(regEngine){
                    regEngine.wait();
                }
            } catch (InterruptedException ex) {
                GenericIO.writelnString("Main thread of registry was interrupted.");
                System.exit(1);
            }
        }
        
        GenericIO.writelnString("Registry finished execution.");
        
        /* Unregister shared region */
        try{ 
            registry.unbind (reg_name);
        }
        catch (RemoteException e){ 
            GenericIO.writelnString ("RegisterRemoteObject unregistration exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        } catch (NotBoundException ex) {
            GenericIO.writelnString ("RegisterRemoteObject unregistration exception: " + ex.getMessage ());
            ex.printStackTrace ();
            System.exit (1);
        }
        GenericIO.writelnString ("RegisterRemoteObject object was unregistered!");
        
        /* Unexport shared region */
        try{ 
            UnicastRemoteObject.unexportObject (regEngine, false);
        }
        catch (RemoteException e){ 
            GenericIO.writelnString ("RegisterRemoteObject unexport exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }
        
        GenericIO.writelnString ("RegisterRemoteObject object was unexported successfully!");
   }
}
