/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SharedRegions;

import Entities.*;

/**
 *
 * @author jgabrantes
 */
public interface RepositoryInterface {   
    
    public void initLogging();    
    public void  updatePilotState(PilotState pilotState);
    public void  updateHostessState(HostessState hostessState);
    public void  updatePassengerState(int id, PassengerState passengerState);
    public void inQueue();   
    public void outQueue();
    public void inPlane();     
    public void outPlane();
    public void boardingStarted();
    public void passengerChecked(int passengerID);
    public void flightDeparted();
    public void flightArrived();
    public void flyingBack();
}
