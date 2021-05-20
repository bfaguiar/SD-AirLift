package main;

import communication.ServerCom;
import shared.Plane;
import shared.PlaneProxy;
import shared.PlaneService;
import stubs.Repository;

public class Initializer {
    public static boolean end = false;
    public static void main(String args[]){
        int server_port = Integer.parseInt(args[0]);

        String repo_address = args[1];
        int repo_port = Integer.parseInt(args[2]);
        
        Repository repo = new Repository(repo_address, repo_port);
        Plane plane = new Plane(repo);
        PlaneProxy proxy = new PlaneProxy(plane);

        ServerCom scon, sconi;
        PlaneService service;
        
        scon = new ServerCom(server_port);
        scon.start();
        while(!end){
            sconi = scon.accept();
            service = new PlaneService(sconi, proxy);
            service.start();
        }
    }  
}
