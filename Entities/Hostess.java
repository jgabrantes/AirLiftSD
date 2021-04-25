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
    
    @Override
    public void run(){
        System.out.println("Hostess:Started coming");
        while(nPassenger> 0){            
            queued = depAirport.prepareForPassBoarding();
            
            while((queued > 0 && boarded < Parameters.MAX || nPassenger >= Parameters.MIN && boarded <Parameters.MIN || nPassenger > 0 && nPassenger < Parameters.MIN) && nPassenger>0){
                depAirport.checkDocuments();                
                queued = depAirport.waitForNextPassenger();  
                boarded++;
                nPassenger--;
            }        
            
            //nPassenger = nPassenger - boarded;
            System.out.println("QUANTOS FALTAM???????_________------->"+nPassenger);
            plane.informPlaneReadyToTakeoff(boarded);
            boarded = 0;
            depAirport.waitForNextFlight();         
        }
        System.out.println("Hostess::->FIM");
    }
}
