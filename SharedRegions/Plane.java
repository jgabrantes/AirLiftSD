/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SharedRegions;

import Entities.Hostess;
import Entities.HostessState;
import Entities.Passenger;
import Entities.PassengerState;
import Entities.Pilot;
import Entities.PilotState;

/**
 *
 * @author jgabrantes
 */
public class Plane {
    private boolean  arrivalAnnounced, readyToFly;
    private int passengersBoarded;    
    
    public synchronized void informPlaneReadyToTakeoff(int boarded){
        System.out.println("Hostess: Informing pilot that the plane is ready to take off");
        Hostess hostess = ((Hostess)Thread.currentThread());
        hostess.setHostessState(HostessState.READY_TO_FLY);
        passengersBoarded  = boarded;
        readyToFly= true;        
        notifyAll();
    }
    
    public  synchronized  int waitForAllInBoard() 
    {
        Pilot pilot = ((Pilot)Thread.currentThread());
        pilot.setPilotState(PilotState.WAIT_FOR_BOARDING);
        System.out.println("Pilot:waiting  For all in board");
        while(!readyToFly)
        {
            try
            {
                wait();
            }
            
            catch(InterruptedException e)
            {
                System.out.print("Ready to fly");
                System.exit(1);
            }
        }
        readyToFly = false;
        return passengersBoarded;
        
    }
    
    public synchronized void flyToDestinationPoint() {
        System.out.println("Pilot: LIIIFT OOOOF");           
        Pilot pilot =((Pilot)Thread.currentThread());
        pilot.setPilotState(PilotState.FLYING_FORWARD);
    }
    public  synchronized void  announceArrival() {
        System.out.println("Passangers able to deboard");
        Pilot pilot =((Pilot)Thread.currentThread());
        pilot.setPilotState(PilotState.DEBOARDING);
        arrivalAnnounced = true;
        notifyAll();      
    } 
    
    public synchronized void waitForEndOfFlight() {
       Passenger passenger = ((Passenger)Thread.currentThread());
       System.out.println("Passenger:"+ passenger.getPassengerId()+" waiting for the end of flight");        
       while(!arrivalAnnounced){
           try{
                wait();
           }catch(InterruptedException ex){
               System.exit(1);
               
           }
       }
       arrivalAnnounced = false;
       
    }
     
    public synchronized void flyToDeparturePoint() {
        System.out.println("Pilot:flying back");
        Pilot pilot = ((Pilot)Thread.currentThread());
        pilot.setPilotState(PilotState.FLYING_BACK);      
            
    }     
}
