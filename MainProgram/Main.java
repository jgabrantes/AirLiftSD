/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainProgram;

import Entities.*;
import SharedRegions.*;
import java.io.FileNotFoundException;

/**
 *
 * @author jgabrantes
 */
public class Main {

    private  int[] passengerIds ;
    private   PassengerState[] passengersState;
    public static void main(String[] args) throws FileNotFoundException { 
        
        int passengerIds[] = new int[Parameters.N_PASSENGERS];       
        
        PassengerState passengersState[] = new PassengerState[Parameters.N_PASSENGERS];
        
        //instantiation of entitites and shared regions
        DepartureAirport depAirport = new DepartureAirport();
        
        DestinationAirport destAirport = new DestinationAirport();
        
        Plane plane = new Plane();
        
        Pilot pilot = new Pilot(0,depAirport, destAirport, plane);
        PilotState pilotState = PilotState.AT_TRANFER_GATE;
        
        Hostess hostess = new Hostess(0, depAirport, plane);        
        HostessState hostessState = HostessState.WAIT_FOR_FLIGHT;
        
        Passenger []passengers = new Passenger[Parameters.N_PASSENGERS];  
        
        
        
        for(int i = 0; i<Parameters.N_PASSENGERS;i++){
            passengers[i] = new Passenger(i,depAirport, destAirport, plane);
            passengerIds[i] = i;
            passengersState[i] = PassengerState.GOING_TO_AIRPORT;
        }     
        
        //instantiation an initialization of repository
        Repository repository = new Repository(passengerIds, pilotState, hostessState, passengersState);
        repository.initLogging();
        
        depAirport.setRepository(repository);
        destAirport.setRepository(repository);        
        plane.setRepository(repository);
        //initialization of the entities's threads
        for(Passenger passenger : passengers){
            passenger.start();
        }
        pilot.start();
        hostess.start();
        
        
        //closing of the entitites threadss
        for(int i = 0; i<Parameters.N_PASSENGERS;i++){
            try{
                passengers[i].join();
            }catch(InterruptedException e){}
        }  
        
        try{
            pilot.join();
            hostess.join();
            
        }catch (InterruptedException e){}
        
        
        repository.sumUp();
    }

}
