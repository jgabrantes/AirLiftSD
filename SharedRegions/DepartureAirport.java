package SharedRegions;

import Entities.Hostess;
import Entities.HostessState;
import Entities.Passenger;
import Entities.PassengerState;
import Entities.Pilot;
import Entities.PilotState;
import MainProgram.Parameters;
import java.util.Queue;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DepartureAirport {
    private int passengerCounter;
    private Queue<Integer> passengerQueue;
    private boolean readyToBoard,inQueue,checkDocs,docShown,board, readyToFly;

    public synchronized void travelToAirport() 
    {
        Passenger passenger = ((Passenger)Thread.currentThread());
        passenger.setPassengerState(PassengerState.GOING_TO_AIRPORT);
        
        System.out.println("Passenger"+passenger.getPassengerId()+"is traveling to the airport");
        
        try 
        {
        	passenger.sleep((long)(Math.random() * 1000));
        } 
        
        catch (InterruptedException ex) 
        {
            System.exit(1);
        }
        
        System.out.println("Passenger"+passenger.getPassengerId()+"arrived");
    }
    
    public synchronized void informPlaneReadyForBoarding() 
    {
        Pilot pilot = ((Pilot)Thread.currentThread());
        pilot.setPilotState(PilotState.READY_FOR_BOARDING);
        readyToBoard = true;
        notifyAll();
    }
    
    public  synchronized void waitInQueue() 
    {
        Passenger passenger = ((Passenger)Thread.currentThread());
        passenger.setPassengerState(PassengerState.IN_QUEUE);    
        passengerQueue.add(passenger.getPassengerId());
        inQueue = true;        
        notifyAll();
    }
    
    public synchronized int prepareForPassBoarding() 
    {
        while(!inQueue)
        {
            try
            {
                wait();
            }
            
            catch(InterruptedException e)
            {
            	System.exit(1);
            }
        }
        
        Hostess hostess = ((Hostess)Thread.currentThread());
        hostess.setHostessState(HostessState.WAIT_FOR_PASSENGER);
        
        return passengerQueue.size();
    }
    
    public  synchronized  void waitForAllInBoard() 
    {
        Pilot pilot = ((Pilot)Thread.currentThread());
        pilot.setPilotState(PilotState.WAIT_FOR_BOARDING);
        
        while(!readyToFly)
        {
            try
            {
                wait();
            }
            
            catch(InterruptedException e)
            {
                System.exit(1);
            }
        }
    }
    
    public synchronized void checkDocuments() 
    {
        Hostess hostess = ((Hostess)Thread.currentThread());
        hostess.setHostessState(HostessState.CHECK_PASSENGER);
        checkDocs = true;
        notifyAll();
        
        while(!docShown)
        {
            try
            {
                wait();
            }
            
            catch(InterruptedException e)
            {
                System.exit(1);
            }
        }
    }
    
    public  synchronized void showDocuments() 
    {
        Passenger passenger = ((Passenger)Thread.currentThread());
        passenger.setPassengerState(PassengerState.IN_QUEUE);
        
        while(!checkDocs)
        {
            try
            {
                wait();
            }
            
            catch(InterruptedException e)
            {
                System.exit(1);
            }
        }
        
        docShown=true;
        notifyAll();
    }

    public synchronized int waitForNextPassenger() 
    {
        Hostess hostess = ((Hostess)Thread.currentThread());
        hostess.setHostessState(HostessState.WAIT_FOR_PASSENGER);
        board = true;
        passengerQueue.remove();
        notifyAll();
        
        while(!inQueue)
        {
            try
            {
                wait();
            }
            
            catch(InterruptedException e)
            {
                System.exit(1);
            }
        }
        
        
        return passengerQueue.size();
    }
    
    public synchronized void boardThePlane() 
    {
        while(!board)
        {
            try
            {
                wait();
            }
            
            catch(InterruptedException e)
            {
                System.exit(1);
            }
        }
        
        Passenger passenger = ((Passenger)Thread.currentThread());
        passenger.setPassengerState(PassengerState.IN_FLIGHT);
     }
    
    public synchronized void informPlaneReatyToTakeOff() 
    {
        Hostess hostess = ((Hostess)Thread.currentThread());
        hostess.setHostessState(HostessState.READY_TO_FLY);
        readyToFly= true;
        notifyAll();
    }
    
    public synchronized void waitForNextFlight() 
    {
        Hostess hostess = (Hostess)Thread.currentThread();
        hostess.setHostessState(HostessState.WAIT_FOR_FLIGHT);
        
        while(!readyToBoard)
        {
            try
            {
                wait();
            }
            
            catch(InterruptedException e)
            {
                System.exit(1);
            }
        }
    }

    public  synchronized  void parkAtTransfeGate() 
    {
        Pilot pilot = ((Pilot)Thread.currentThread());
        pilot.setPilotState(PilotState.AT_TRANFER_GATE);
    }
}
