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
    private boolean readyToBoard,inQueue,checkDocs,docShown,board;

    public synchronized void waitForNextFlight() {
        Hostess hostess = (Hostess)Thread.currentThread();
        hostess.setHostessState(HostessState.WAIT_FOR_FLIGHT);
        while(!readyToBoard){
            try{
                wait();
            }catch(InterruptedException e){
                System.exit(1);
            }
        }
    }
    

    public synchronized void informPlaneReadyForBoarding() {
        Pilot pilot = ((Pilot)Thread.currentThread());
        pilot.setPilotState(PilotState.READY_FOR_BOARDING);
        readyToBoard = true;
        notifyAll();
        
    }

    public  synchronized  void waitForAllInBoard() {
        Pilot pilot = ((Pilot)Thread.currentThread());
        pilot.setPilotState(PilotState.WAIT_FOR_BOARDING);
        
        //while()
    }

    public  synchronized  void parkAtTransfeGate() {
        Pilot pilot = ((Pilot)Thread.currentThread());
        pilot.setPilotState(PilotState.AT_TRANFER_GATE);
    }

    public  synchronized void waitInQueue() {
        Passenger passenger = ((Passenger)Thread.currentThread());
        passenger.setPassengerState(PassengerState.IN_QUEUE);        
        inQueue = true;        
        
    }

    public  synchronized  int prepareForPassBoarding() {
        Hostess hostess = ((Hostess)Thread.currentThread());
        hostess.setHostessState(HostessState.WAIT_FOR_PASSENGER);
        while(!inQueue){
            try{
                wait();
            }catch(InterruptedException e){
              System.exit(1);
            }
        }
        return passengerQueue.size();
    }

    public synchronized void checkDocuments() {
        Hostess hostess = ((Hostess)Thread.currentThread());
        hostess.setHostessState(HostessState.CHECK_PASSENGER);
        checkDocs = true;
        while(!docShown){
            try{
                wait();
            }catch(InterruptedException e){
                System.exit(1);
            }
        }
    }

    public synchronized int waitForNextPassenger() {
        Hostess hostess = ((Hostess)Thread.currentThread());
        hostess.setHostessState(HostessState.WAIT_FOR_PASSENGER);
        board = true;
        while(!inQueue){
            try{
                wait();
            }catch(InterruptedException e){
                System.exit(1);
            }
        }
        board=true;
        return passengerQueue.size();
    }

    public synchronized void informPlaneReatyToTakeOff() {
        
    }

    public  synchronized void showDocuments() {
        Passenger passenger = ((Passenger)Thread.currentThread());
        passenger.setPassengerState(PassengerState.IN_QUEUE);
        while(!checkDocs){
            try{
                wait();
            }catch(InterruptedException e){
                System.exit(1);
            }
        }
        docShown=true;
        
        
    }

    public synchronized void boardThePlane() {
        
    }

    public synchronized void travelToAirport() {
        Passenger passenger = ((Passenger)Thread.currentThread());
        passenger.setPassengerState(PassengerState.GOING_TO_AIRPORT);
        try {
            passenger.sleep((long)(Math.random() * 1000));
        } catch (InterruptedException ex) {
            System.exit(1);
        }
        passengerQueue.add(passenger.getPassengerId());
    }

    
}
