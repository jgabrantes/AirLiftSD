package SharedRegions;

import Entities.Hostess;
import Entities.HostessState;
import Entities.Passenger;
import Entities.PassengerState;
import Entities.Pilot;
import Entities.PilotState;

import java.util.LinkedList;
import java.util.Queue;


public class DepartureAirport {
    private int passengerCounter;
    private Queue<Passenger> passengerQueue = new LinkedList<Passenger>();
    private Queue<Passenger> boardedQueue = new LinkedList<Passenger>();
    private boolean readyToBoard,inQueue,checkDocs,docShown,board, readyToFly;

    public synchronized void travelToAirport() 
    {
        Passenger passenger = ((Passenger)Thread.currentThread());
        passenger.setPassengerState(PassengerState.GOING_TO_AIRPORT);        
        System.out.println("Passenger:"+passenger.getPassengerId()+" is traveling to the airport");       
    }
    
    
    public synchronized void informPlaneReadyForBoarding() 
    {
        Pilot pilot = ((Pilot)Thread.currentThread());
        pilot.setPilotState(PilotState.READY_FOR_BOARDING);
        System.out.println("Pilot:plane ready to board");
        readyToBoard = true;
        notifyAll();
    }
    
    public  synchronized void waitInQueue() 
    {
        Passenger passenger = ((Passenger)Thread.currentThread());
        System.out.println("Passenger:"+passenger.getPassengerId()+" has arrived");        
        System.out.println("Passanger:"+passenger.getPassengerId()+" at queue");
        passenger.setPassengerState(PassengerState.IN_QUEUE);    
        passengerQueue.add(passenger);
        
        inQueue = true;        
        notifyAll();
    }
    
    public synchronized int prepareForPassBoarding() 
    {
        System.out.println("Hostess:Waiting for plane to be ready to board");
        while(!readyToBoard)
        {
            try
            {
                wait();
            }          
            catch(InterruptedException e)
            {
                System.out.println("Hostess:Waiting for passengers");
                System.exit(1);
            }
        }
        readyToBoard = false;       
       
        Hostess hostess = ((Hostess)Thread.currentThread());
        hostess.setHostessState(HostessState.WAIT_FOR_PASSENGER);
        
        return passengerQueue.size();
    }
    
    
    
    public synchronized void checkDocuments() 
    {
        while(!inQueue){
            try{
                wait();
            }catch(InterruptedException e){
                System.exit(1);
            }
        }
        inQueue = false;
        System.out.println("Hostess: Passenger has Arrived ready to show documents ");
        Hostess hostess = ((Hostess)Thread.currentThread());
        hostess.setHostessState(HostessState.CHECK_PASSENGER);
        checkDocs = true;
        notifyAll();        
    }
    
    public  synchronized void showDocuments() 
    {
        Passenger passenger = ((Passenger)Thread.currentThread());
        passenger.setPassengerState(PassengerState.IN_QUEUE);
        System.out.println("Passenger:"+passenger.getPassengerId()+" waiting to show documents");
        while(!checkDocs)
        {
            try
            {
                wait();
            }
            
            catch(InterruptedException e)
            {
                System.out.println("Passanger:"+passenger.getPassengerId()+"showing documents");
                System.exit(1);
            }
        }
        checkDocs = false;
        
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
        System.out.println("Hostess:Passanger ready to board");
        return passengerQueue.size();
    }
    
    public synchronized void boardThePlane() 
    {
        Passenger passenger = ((Passenger)Thread.currentThread());
        System.out.println("Passenger:"+passenger.getPassengerId()+"Waiting to have permission to board");
        while(!board)
        {
            try
            {
                wait();
            }
            
            catch(InterruptedException e)
            {
                System.out.println("Passenger:"+passenger.getPassengerId()+" boarding the plane");
                System.exit(1);
            }
        }
        boardedQueue.add(passenger);
        board= false;        
        passenger.setPassengerState(PassengerState.IN_FLIGHT);
     }
    
    
    
    public synchronized void waitForNextFlight() 
    {
        System.out.println("Hostess:Waiting for plane to arrive");
        Hostess hostess = (Hostess)Thread.currentThread();
        hostess.setHostessState(HostessState.WAIT_FOR_FLIGHT);
        
    }

    public  synchronized  void parkAtTransfeGate() 
    {
        Pilot pilot = ((Pilot)Thread.currentThread());
        pilot.setPilotState(PilotState.AT_TRANFER_GATE);
        System.out.print("Pilot has parked at transfer gate");
    }

   
}
