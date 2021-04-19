package Entities;
import SharedRegions.*;

public class Hostess {
    private int id;
    private DepartureAirport depAirport;
    private DestinationAirport destAirport;
    private Plane  plane;
    private HostessState state;
    public Hostess(int id, DepartureAirport depAirport, DestinationAirport destAirport, Plane plane) {
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

    public HostessState getState() {
        return state;
    }

    public void setState(HostessState state) {
        this.state = state;
    }
    
    public void Run(){
        while(depAirport.waitForNextFlight()){
            setState(state.WAIT_FOR_FLIGHT);
            depAirport.prepareForPassBoarding();
            
            while(depAirport.waitInQueue()){
                setState(state.WAIT_FOR_PASSENGER);
                depAirport.checkDocuments();
                setState(state.CHECK_PASSENGER);
                depAirport.waitForNextPassenger();
            }
            depAirport.informPlaneReatyToTakeOff();
            setState(state.READY_TO_FLY);
            depAirport.waitForNextFlight();
        }
        
    }
 
}
