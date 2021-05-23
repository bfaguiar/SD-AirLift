package main;

import shared.ArrivalAirport;
import shared.ArrivalAirportProxy;
import shared.ArrivalAirportService;
import stubs.Repository;

import java.net.SocketTimeoutException;

import communication.ServerCom;

/**
 * ArrivalAirport's main
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
    public static void main(String args[]) {

        /**
         * Argument fetching
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
        ArrivalAirport ap = new ArrivalAirport(repo);
        ArrivalAirportProxy proxy = new ArrivalAirportProxy(ap);

        /**
         * Instantation of the Server
         */
        ServerCom scon, sconi;
        ArrivalAirportService service;
        scon = new ServerCom(server_port);
        
        /**
         * Inicialization of the Server
         */
        scon.start();
        while(!end) {
            try{
                sconi = scon.accept();
                service = new ArrivalAirportService(sconi, proxy);
                service.start();
            } catch (SocketTimeoutException ex) {
            }
        }
    }
}
