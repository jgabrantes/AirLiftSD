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
    private boolean readyToBoard,inQueue,checkDocs,docsShown,board;
    
    
    private RepositoryInterface repo;
    
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
        repo.boardingStarted();
        repo.updatePilotState(PilotState.READY_FOR_BOARDING);
        System.out.println("Pilot:plane ready to board");
        readyToBoard = true;
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
        repo.updateHostessState(HostessState.WAIT_FOR_PASSENGER);
             
        return passengerQueue.size();
    }
    
    public  synchronized void waitInQueue() 
    {
        Passenger passenger = ((Passenger)Thread.currentThread());
        System.out.println("Passenger:"+passenger.getPassengerId()+" has arrived");        
        System.out.println("Passanger:"+passenger.getPassengerId()+" at queue");
        
        passengerQueue.add(passenger);  
        passenger.setPassengerState(PassengerState.IN_QUEUE); 
        repo.inQueue();
        repo.updatePassengerState(passenger.getPassengerId(), PassengerState.IN_QUEUE);
           
        notifyAll();
    }    
    
    public synchronized void checkDocuments() 
    {
        while(passengerQueue.isEmpty()){
            try{
                wait();
            }catch(InterruptedException e){
               
                System.exit(1);
            }
        }
        System.out.println("Hostess:  ready to check documents ");
        
        Hostess hostess = ((Hostess)Thread.currentThread());  
        
        Passenger checkedPassenger =passengerQueue.remove(); 
        repo.outQueue();
        repo.passengerChecked(checkedPassenger.getPassengerId());
        hostess.setHostessState(HostessState.CHECK_PASSENGER);
        repo.updateHostessState(HostessState.CHECK_PASSENGER);
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
                
                System.exit(1);
            }
        }
        checkDocs = false; 
       
        System.out.println("Passanger:"+passenger.getPassengerId()+"showing documents");
        System.out.println("Passenger:"+passenger.getPassengerId()+"Waiting to have permission to board");
        docsShown = true;   
        notifyAll();
    }

    public synchronized int waitForNextPassenger() 
    {
        while(!docsShown){
            try{
                wait();
            }catch(InterruptedException e){
                System.exit(1);
            }
        }
        docsShown = false;
        
        Hostess hostess = ((Hostess)Thread.currentThread());
        hostess.setHostessState(HostessState.WAIT_FOR_PASSENGER);
        repo.updateHostessState(HostessState.WAIT_FOR_PASSENGER);
        System.out.println("Hostess:Passanger ready to board");
        System.out.println("PassengerQUEUE_SIZE-->:"+passengerQueue.size());
        board = true;
        notifyAll(); 
        return passengerQueue.size();
    }
    
    public synchronized void boardThePlane() 
    {
        Passenger passenger = ((Passenger)Thread.currentThread());
       
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
        board= false;  
        System.out.println("Passenger:"+passenger.getPassengerId()+" boarding the plane");
        repo.inPlane();
        passenger.setPassengerState(PassengerState.IN_FLIGHT);        
        repo.updatePassengerState(passenger.getPassengerId(), PassengerState.IN_FLIGHT);
     }
    
    
    
    public synchronized void waitForNextFlight() 
    {
        System.out.println("Hostess:Waiting for plane to arrive");
        Hostess hostess = (Hostess)Thread.currentThread();
        hostess.setHostessState(HostessState.WAIT_FOR_FLIGHT);
        repo.updateHostessState(HostessState.WAIT_FOR_FLIGHT);
    }

    public  synchronized  void parkAtTransfeGate() 
    {
        Pilot pilot = ((Pilot)Thread.currentThread());
        pilot.setPilotState(PilotState.AT_TRANFER_GATE);
        repo.updatePilotState(PilotState.AT_TRANFER_GATE);
        System.out.println("Pilot has parked at transfer gate");
    }

    public synchronized void setRepository(RepositoryInterface repository){
        this.repo = repository;
    }
}
