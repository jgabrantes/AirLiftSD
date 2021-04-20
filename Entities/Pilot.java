package Entities;

import SharedRegions.DepartureAirport;
import SharedRegions.DestinationAirport;
import SharedRegions.Plane;

public class Pilot extends Thread {
    private   int id;
    private  DepartureAirport depAirport;
    private DestinationAirport destAirport;
    private Plane  plane;
    private  PilotState state;
    
    
    public Pilot(int id, DepartureAirport depAirport, DestinationAirport destAirport, Plane plane) {
        this.id = id;
        this.depAirport = depAirport;
        this.destAirport = destAirport;
        this.plane = plane;
        
    }

    /**
     *
     */
    @Override
    public void run() {
        //System.out.println("Pilot start:");--->Pass2MAIN
        
        depAirport.informPlaneReadyForBoarding();
        
        depAirport.waitForAllInBoard();
        
        plane.flyToDestinationPoint();
        
        destAirport.announceArrival();
        
        plane.flyToDeparturePoint();
        
        depAirport.parkAtTransfeGate();
            
        
    }
    
    /**
     *
     * @return
     */
    public int getPilotId() {
        return id;
    }
    public void setPilotId(int id) {
        this.id = id;
    }
    
    public PilotState getPilotState() {
        return state;
    }

    public void setPilotState(PilotState state) {
        this.state = state;
    }
    
    
    
}
