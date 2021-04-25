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
public enum PilotState {
    AT_TRANFER_GATE ("ATRG"),
    READY_FOR_BOARDING("RDFB"),
    WAIT_FOR_BOARDING("WTFB"),
    FLYING_FORWARD("FLFW"),
    DEBOARDING("DRPP"),
    FLYING_BACK("FLBK");
    
    
    private final String state;
    private PilotState(String description){
        this.state = description;
    }

    @Override
    public String toString() {
        return this.state;
    }
    
}
