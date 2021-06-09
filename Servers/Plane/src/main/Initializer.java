package main;

import java.net.SocketTimeoutException;

import communication.ServerCom;
import shared.Plane;
import shared.PlaneProxy;
import shared.PlaneService;
import stubs.Repository;

/**
 * Plane's main 
 * @author Bruno Aguiar 80177
 * @author David Rocha 84807
 */
public class Initializer {

    /**
     * Boolean to indicate the end of the server
     */
    public static boolean end = false;

     /**
     * @param args List of arguments: ServerPort, RepositoryAddress, RepositoryPort
     */ 
    public static void main(String args[]){
       
        /**
         * Argument Fetching
         */
        int server_port = Integer.parseInt(args[0]);
        String repo_address = args[1];
        int repo_port = Integer.parseInt(args[2]);
        
        /**
         * Instantiation of repository
         */

        Repository repo = new Repository(repo_address, repo_port);

        /**
         * Instantiation of Shared
         */
        Plane plane = new Plane(repo);
        PlaneProxy proxy = new PlaneProxy(plane);

        /**
         * Instantation of the Server
         */
        ServerCom scon, sconi;
        PlaneService service;
        
        /**
         * Inicialization of the Server
         */
        scon = new ServerCom(server_port);
        scon.start();
        while(!end){
            try{
                sconi = scon.accept();
                service = new PlaneService(sconi, proxy);
                service.start();
            } catch (SocketTimeoutException ex) {
            }
        }
    }  
}
