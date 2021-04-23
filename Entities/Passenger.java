package Entities;
import SharedRegions.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Passenger extends Thread {
    
    private int id;
    private final DestinationAirport destAirport;
    private final DepartureAirport depAirport;
    private final Plane plane;
    private PassengerState state;
    public Passenger(int id, DepartureAirport depAirport,  DestinationAirport destAirport, Plane plane) {
        this.id = id;
        this.destAirport = destAirport;
        this.depAirport = depAirport;
        this.plane = plane;
    }
    public int getPassengerId() {
        return id;
    }
    public void setPassengerId(int id) {
        this.id = id;
    }
     
    public PassengerState getPassengerState() {
        return state;
    }

    public void setPassengerState(PassengerState state) {
        this.state = state;
    }
    @Override
    public void run() {
       depAirport.travelToAirport();
        try {
            this.sleep((long)(Math.random() * 1000));
            } catch (InterruptedException ex) {}
       depAirport.waitInQueue();
       
       depAirport.showDocuments();
       
       depAirport.boardThePlane();
       
       plane.waitForEndOfFlight();
       
       destAirport.leaveThePlane();
    }
}
