package Entities;

import SharedRegions.DepartureAirport;
import SharedRegions.DestinationAirport;
import SharedRegions.Plane;

public class Pilot {
    private int id;
    private DepartureAirport depAirport;
    private DestinationAirport destAirport;
    private Plane  plane;
    private PilotState state;
    public Pilot(int id, DepartureAirport depAirport, DestinationAirport destAirport, Plane plane) {
        this.id = id;
        this.depAirport = depAirport;
        this.destAirport = destAirport;
        this.plane = plane;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    public PilotState getState() {
        return state;
    }

    public void setState(PilotState state) {
        this.state = state;
    }
    
     public void run() {
        System.out.println("Pilot start:");
        while(depAirport.waitForNextFlight()){
            setState(state.AT_TRANFER_GATE);
            depAirport.informPlaneReadyForBoarding();
            setState(state.READY_FOR_BOARDING);
            depAirport.waitForAllInBoard();
            setState(state.WAIT_FOR_BOARDING);
            plane.flyToDestinationPoint();
            setState(state.FLYING_FORWARD);
            destAirport.announceArrival();
            setState(state.DEBOARDING);
            plane.flyToDeparturePoint();
            setState(state.FLYING_BACK);
            depAirport.parkAtTransfeGate();
            
        }
    }
}
