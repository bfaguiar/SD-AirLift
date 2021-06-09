package main;

import java.net.SocketTimeoutException;

import communication.ServerCom;
import shared.DepartureAirport;
import shared.DepartureAirportProxy;
import shared.DepartureAirportService;
import stubs.Repository;

/**
 * Departure Airport's main
 * @author Bruno Aguiar 80177
 * @author David Rocha 84807
 */
public class Initializer {
    
    /**
     * Boolean to indicate the end of the server
     */
    public static boolean end = false;
    
     /**
     * @param args List of arguments: ServerPort, N_PASSENGERS, CAPACITY_MIN, CAPACITY_MAX, RepositoryAddress, RepositoryPort
     */
    public static void main(String args[]) {

        /**
         * Argument Fetching
         */
        int server_port = Integer.parseInt(args[0]);
        int N_PASSENGERS = Integer.parseInt(args[1]);
        int CAPACITY_MIN = Integer.parseInt(args[2]);
        int CAPACITY_MAX = Integer.parseInt(args[3]);
        String repo_address = args[4];
        int repo_port = Integer.parseInt(args[5]);
        
        /**
         * Instantiation of repository
         */
        Repository repo = new Repository(repo_address, repo_port);
        
        /**
         * Instantiation of Shared
         */
        DepartureAirport dp = new DepartureAirport(repo, CAPACITY_MIN, CAPACITY_MAX, N_PASSENGERS);
        DepartureAirportProxy proxy = new DepartureAirportProxy(dp);

        /**
         * Instantation of the Server
         */
        ServerCom scon, sconi;
        DepartureAirportService service;
        
        scon = new ServerCom(server_port);

        /**
         * Inicialization of the Server
         */
        scon.start();
        while(!end){
            try{
                sconi = scon.accept();
                service = new DepartureAirportService(sconi, proxy);
                service.start();
            } catch (SocketTimeoutException ex) {
            }
        }
    }
}
