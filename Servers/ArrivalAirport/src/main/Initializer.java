package main;

import shared.ArrivalAirport;
import shared.ArrivalAirportProxy;
import shared.ArrivalAirportService;
import stubs.Repository;
import communication.ServerCom;

public class Initializer {
    public static boolean end = false;

    public static void main(String args[]){
        int server_port = Integer.parseInt(args[0]);

        String repo_address = args[1];
        int repo_port = Integer.parseInt(args[2]);
        
        Repository repo = new Repository(repo_address, repo_port);
        ArrivalAirport ap = new ArrivalAirport(repo);
        ArrivalAirportProxy proxy = new ArrivalAirportProxy(ap);

        ServerCom scon, sconi;
        ArrivalAirportService service;
        
        scon = new ServerCom(server_port);
        scon.start();
        while(!end){
            sconi = scon.accept();
            service = new ArrivalAirportService(sconi, proxy);
            service.start();
        }
    }
}
