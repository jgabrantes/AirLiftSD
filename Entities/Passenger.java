package Entities;
import SharedRegions.*;

public class Passenger {
    
    private int id;
    private String name;
    private DestinationAirport destAirport;
    private DepartureAirport depAirport;
    private Plane plane;
    public Passenger(int id, String name, DestinationAirport destAirport, DepartureAirport depAirport, Plane plane) {
        this.id = id;
        this.name = name;
        this.destAirport = destAirport;
        this.depAirport = depAirport;
        this.plane = plane;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public PassengerState getState() {
        return state;
    }

    public void setState(PassengerState state) {
        this.state = state;
    }
    
    public void run() {
        System.out.println("Passenger start:");
    	
        setState(state.GOING_TO_AIRPORT);
        depAirport.waitInQueue();
        setState(state.IN_QUEUE);
        depAirport.showDocuments();
        depAirport.boardThePlane();
        setState(state.IN_FLIGHT);
        plane.waitForEnfOfFlight();
        plane.leaveThePlane();
        setState(state.AT_DESTINATION);
    }
}
