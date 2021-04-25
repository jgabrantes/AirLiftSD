/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author jgabrantes
 */
public enum PassengerState {
    GOING_TO_AIRPORT ("GTAP"),
    IN_QUEUE ("INQE"),
    IN_FLIGHT ("INFL"),
    AT_DESTINATION ("ATDS");

    private final String state;

    private PassengerState(String description){
        this.state = description;
    }

    @Override
    public String toString(){
        return this.state;
    }
}
