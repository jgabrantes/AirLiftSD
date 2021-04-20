/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainProgram;

import Entities.*;
import SharedRegions.*;

/**
 *
 * @author jgabrantes
 */
public class Main {
    public static void main(String[] args) {
        DepartureAirport depAirport = new DepartureAirport();
        DestinationAirport destAirport = new DestinationAirport();
        Plane plane = new Plane();
        
        Pilot pilot = new Pilot(0,depAirport, destAirport, plane);
        Hostess hostess = new Hostess(0, depAirport, plane);
        
        Passenger []passengers = new Passenger[Parameters.N_PASSENGERS];
        pilot.start();
        hostess.start();
        
        for(int i = 0; i<Parameters.N_PASSENGERS;i++){
            passengers[i] = new Passenger(i,depAirport, destAirport, plane);
            passengers[i].start();
        }
        
        try{
            pilot.join();
            hostess.join();
            for(int i = 0; i<Parameters.N_PASSENGERS;i++){
                passengers[i].join();
            }
        }catch (Exception e){
            
        }
    }

}
