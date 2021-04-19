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
    public int flight_num = 1;

    private File repo_txt = new File("repo.txt");
    private FileWriter repo_writer;

    private String last_state_string;

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

    public void closelog(){
        try {
            this.repo_writer.close();
            this.repo_writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logEntities(){
        String entities = String.format("%4s %4s", "PT", "HT");
        for(int i = 0; i < passenger_list.length; i++)
            entities += String.format(" %4s", ("P"+i));
        entities += String.format(" %4s %4s %4s\n", "InQ", "InF", "PTAL");
        System.out.printf(entities);
        try {
            this.repo_writer.write(entities);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void log(){
        String states = String.format("%4s %4s", pilot.getStateString(), hostess.getStateString());
        for(int i = 0; i < passenger_list.length; i++)
            states += String.format(" %4s", passenger_list[i].getStateString());
        states += String.format(" %4s %4s %4s\n", number_in_queue, number_in_plane, number_at_destination);

        if(!states.equals(this.last_state_string)){
            System.out.printf(states);
        }

        try {
            if(!states.equals(this.last_state_string)){
                repo_writer.write(states);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        last_state_string = states;
    }

    public synchronized void logFlightBoardingStarting(){
        String flightString = String.format("\nFlight %d: boarding started.\n", flight_num);
        System.out.printf(flightString);
        try {
            repo_writer.write(flightString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void logPassengerCheck(int id){
        String flightString = String.format("\nFlight %d: passenger %d checked.\n", flight_num, id);
        System.out.printf(flightString);
        try {
            repo_writer.write(flightString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void logDeparture(){
        String flightString = String.format("\nFlight %d: departed with %d passengers.\n", flight_num, number_in_plane);
        System.out.printf(flightString);
        try {
            repo_writer.write(flightString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void logArriving(){
        String flightString = String.format("\nFlight %d: arrived.\n", flight_num);
        System.out.printf(flightString);
        try {
            repo_writer.write(flightString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void logReturning(){
        String flightString = String.format("\nFlight %d: returning.\n", flight_num);
        System.out.printf(flightString);
        try {
            repo_writer.write(flightString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}