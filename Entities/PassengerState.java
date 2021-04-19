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
    GOING_TO_AIRPORT ("GTA"),
    IN_QUEUE ("IQ"),
    IN_FLIGHT ("IF"),
    AT_DESTINATION ("AD");

    private final String state;

    private PassengerState(String description){
        this.state = description;
    }

    @Override
    public String toString(){
        return this.state;
    }
}
