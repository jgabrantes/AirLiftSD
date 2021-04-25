/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SharedRegions;

import Entities.Passenger;
import Entities.PassengerState;


/**
 *
 * @author jgabrantes
 */
public class DestinationAirport {
    
    private int passengersLeft= 0;
    
    private RepositoryInterface repo;
        
    public synchronized void leaveThePlane() {
        Passenger passenger =((Passenger)Thread.currentThread());
        passengersLeft +=1;
        System.out.println("Passenger:"+passenger.getPassengerId()+" left the plane");
        passenger.setPassengerState(PassengerState.AT_DESTINATION);
        repo.outPlane();
        repo.updatePassengerState(passenger.getPassengerId(), PassengerState.AT_DESTINATION);
        System.out.println("PassengersLeft---------->"+passengersLeft+"<---------------------------------");
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
        
         passengersLeft = 0;
     }
     
     public synchronized void setRepository(RepositoryInterface repository){
        this.repo = repository;
    }
}
