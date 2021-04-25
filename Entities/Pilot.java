package Entities;

import MainProgram.Parameters;
import SharedRegions.DepartureAirport;
import SharedRegions.DestinationAirport;
import SharedRegions.Plane;

public class Pilot extends Thread {
    private   int id;
    private  DepartureAirport depAirport;
    private DestinationAirport destAirport;
    private Plane  plane;
    private  PilotState state;
    private int nPassengers = Parameters.N_PASSENGERS,  passengersMoved = 0;
    
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
        
        while(passengersMoved < nPassengers){
            System.out.println("PassengersTransported::"+passengersMoved);

            depAirport.parkAtTransfeGate();
            depAirport.informPlaneReadyForBoarding();
                       
            int boardedPassengers = plane.waitForAllInBoard();
            System.out.println("BOARDED PASSENGERS:"+boardedPassengers);
            passengersMoved += boardedPassengers;
            plane.flyToDestinationPoint();
            try {
                this.sleep((long)(Math.random() * 1000));
            } catch (InterruptedException ex) {}
        
            plane.announceArrival();
        
            destAirport.waitForAllPassengersToLeave(boardedPassengers);
            boardedPassengers = 0;
            plane.flyToDeparturePoint();  
        }
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
