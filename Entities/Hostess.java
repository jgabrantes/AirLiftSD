package Entities;
import MainProgram.Parameters;
import SharedRegions.*;

public class Hostess extends Thread {
    private int id;
    private final DepartureAirport depAirport;
    private final Plane  plane;
    private HostessState state;
    public Hostess(int id, DepartureAirport depAirport, Plane plane) {
        this.id = id;
        this.depAirport = depAirport;
        this.plane = plane;
    }
    public int getHostessId() {
        return id;
    }
    public void setHostessId(int id) {
        this.id = id;
    }

    public HostessState getHostessState() {
        return state;
    }

    public void setHostessState(HostessState state) {
        this.state = state;
    }
    
    int nPassenger = Parameters.N_PASSENGERS;
    int boarded = 0;
    int queued;
    
    public void Run(){
        while(nPassenger>= 0){
            queued = depAirport.prepareForPassBoarding();
            while(nPassenger > 0  && (queued > 0 && boarded < Parameters.MAX || nPassenger >= Parameters.MIN && boarded <Parameters.MIN || nPassenger > 0 && nPassenger < Parameters.MIN)){
                depAirport.checkDocuments();
                boarded++;
                queued = depAirport.waitForNextPassenger();
            } 
            nPassenger = nPassenger - boarded;
            plane.informPlaneReadyToTakeoff();
            depAirport.waitForNextFlight();
        
        }
 
    }
}
