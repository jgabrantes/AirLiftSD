/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SharedRegions;

import Entities.Passenger;
import Entities.PassengerState;
import Entities.Pilot;
import Entities.PilotState;

/**
 *
 * @author jgabrantes
 */
public class DestinationAirport {
    
    private int passengersLeft= 0;
    
       
        
    public synchronized void leaveThePlane() {
        Passenger passenger =((Passenger)Thread.currentThread());
        passengersLeft +=1;
        System.out.println("Passenger:"+passenger.getPassengerId()+" left the plane");
        passenger.setPassengerState(PassengerState.AT_DESTINATION);
        notifyAll();
        
    }
     public synchronized void waitForAllPassengersToLeave(int numPassengers){
         System.out.println("Pilot:waiting for all passengers to leave");
         while(numPassengers != passengersLeft){
             try{
                 wait();
             }catch(InterruptedException e){
                 System.exit(1);
             }
         }
     }
}
