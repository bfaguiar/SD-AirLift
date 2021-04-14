package repo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import thread.Hostess;
import thread.Passenger;
import thread.Pilot;

public class Repository{
    public Hostess hostess;
    public Pilot pilot;
    public Passenger[] passenger_list;
    public int number_in_queue;
    public int number_in_plane;
    public int number_at_destination;

    private File repo_txt = new File("repo.txt");
    private FileWriter repo_writer;

    public Repository(){
        number_in_queue = 0;
        number_in_plane = 0;
        number_at_destination = 0;
        try {
            repo_txt.createNewFile();
            repo_writer = new FileWriter(repo_txt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void log(){
        String entities = String.format("%4s %4s", "PT", "HT");
        for(int i = 0; i < passenger_list.length; i++)
            entities += String.format(" %4s", ("P"+i));
        entities += String.format(" %4s %4s %4s\n", "InQ", "InF", "PTAL");

        String states = String.format("%4s %4s", pilot.getStateString(), hostess.getStateString());
        for(int i = 0; i < passenger_list.length; i++)
            states += String.format(" %4s", passenger_list[i].getStateString());
        states += String.format(" %4s %4s %4s\n\n", number_in_queue, number_in_plane, number_at_destination);

        System.out.printf(entities);
        System.out.printf(states);

        try {
            repo_writer.write(entities);
            repo_writer.write(states);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}