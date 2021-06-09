package main;

import java.net.SocketTimeoutException;

import communication.ServerCom;
import repo.Repository;
import repo.RepositoryProxy;
import repo.RepositoryService;

/**
 * Repository Main
 * @author Bruno Aguiar 80177
 * @author David Rocha 84807
 */
public class Initializer {

    /**
     * Boolean to indicate the end of the server
     */
    public static boolean end = false;
    
    /**
     * 
     * @param args List of arguments: ServerPort, N_PASSENGERS
     */
    public static void main(String args[]) {

        /**
         * Argument Fetching
         */
        int server_port = Integer.parseInt(args[0]);
        int N_PASSENGERS = Integer.parseInt(args[1]);

        /**
         * Instantiation of repository
         */

        Repository repo = new Repository(N_PASSENGERS);

         /**
         * Instantiation of Shared
         */
        RepositoryProxy proxy = new RepositoryProxy(repo);

        /**
         * Instantation of the Server
         */
        ServerCom scon, sconi;
        RepositoryService service;
        
        /**
         * Inicialization of the Server
         */
        scon = new ServerCom(server_port);
        scon.start();
        while(!end){
            try{
                sconi = scon.accept();
                service = new RepositoryService(sconi, proxy);
                service.start();
            } catch (SocketTimeoutException ex) {
            }
        }
    }
}
