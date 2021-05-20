package main;

import communication.ServerCom;
import repo.Repository;
import repo.RepositoryProxy;
import repo.RepositoryService;

public class Initializer {
    public static boolean end = false;
    public static void main(String args[]){
        int server_port = Integer.parseInt(args[0]);

        int N_PASSENGERS = Integer.parseInt(args[1]);

        Repository repo = new Repository(N_PASSENGERS);
        RepositoryProxy proxy = new RepositoryProxy(repo);

        ServerCom scon, sconi;
        RepositoryService service;
        
        scon = new ServerCom(server_port);
        scon.start();
        while(!end){
            sconi = scon.accept();
            service = new RepositoryService(sconi, proxy);
            service.start();
        }
    }
}
