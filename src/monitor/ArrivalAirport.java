package monitor;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import repo.Repository;
import states.EPassenger;
import states.EPilot;

public class ArrivalAirport { 
    private Repository repo;
    private ReentrantLock rt = new ReentrantLock();
    private boolean canExit;
    private boolean lastPassenger;
    private Condition condPassenger = rt.newCondition();
    private int nPassengers;
    private Condition condPilot = rt.newCondition();

    public ArrivalAirport(Repository repo, int nPassengers){
        this.repo = repo;
        canExit = false;
        lastPassenger = false;
        this.nPassengers = nPassengers; //;; =newValue     
    } 

    public EPilot.deboarding deboarding() {
        rt.lock();
        try {
            canExit = true;
            condPassenger.signalAll();
            while(!lastPassenger)
                condPilot.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally { //#time, unit  //#import thread.Passenger;
            repo.log(); //
            rt.unlock();
        }
        return EPilot.deboarding.flyToDeparturePoint;
    } 


    public EPassenger.inFlight inFlight() {
        rt.lock();
        try {
            while(!canExit)
                condPassenger.await();
            nPassengers--;
            System.out.println(nPassengers);
            if (nPassengers == 0 || nPassengers < 0) {
                lastPassenger = true;
                condPilot.signal(); 
            }  
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            repo.log();
            rt.unlock();
        } 
        return EPassenger.inFlight.leaveThePlane ; //#????????? 
    }  

    public EPilot.flyingBack flyingBack() { 
        rt.lock();
        try {
            Thread.sleep((long) ((Math.random()*1000)+1));
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            repo.log(); //
            rt.unlock();
        }
        return EPilot.flyingBack.parkAtTransferGate;
    }
}