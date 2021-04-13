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
        String entities = String.format("%4s %4s %4s %4s %4s %4s %4s %4s %4s %4s %4s %4s %4s %4s %4s %4s %4s %4s %4s %4s %4s %4s %4s %4s %4s\n", 
        "PT", "HT", "P00", "P01", "P02", "P03", "P04", "P05", "P06", "P07", "P08", "P09", "P10", "P11", "P12", "P13", 
        "P14", "P15", "P16", "P17", "P18", "P19", "InQ", "InF", "PTAL");

        String states = String.format("%4s %4s %4s %4s %4s %4s %4s %4s %4s %4s %4s %4s %4s %4s %4s %4s %4s %4s %4s %4s %4s %4s %4d %4d %4d \n\n", 
        pilot.getStateString(), hostess.getStateString(), passenger_list[0].getStateString(), passenger_list[1].getStateString(), 
        passenger_list[2].getStateString(), passenger_list[3].getStateString(), passenger_list[4].getStateString(),
        passenger_list[5].getStateString(), passenger_list[6].getStateString(), passenger_list[7].getStateString(),
        passenger_list[8].getStateString(), passenger_list[9].getStateString(), passenger_list[10].getStateString(),
        passenger_list[11].getStateString(), passenger_list[12].getStateString(), passenger_list[13].getStateString(),
        passenger_list[14].getStateString(), passenger_list[15].getStateString(), passenger_list[16].getStateString(),
        passenger_list[17].getStateString(), passenger_list[18].getStateString(), passenger_list[19].getStateString(),
        number_in_queue, number_in_plane, number_at_destination);

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