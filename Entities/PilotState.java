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
    AT_TRANFER_GATE ("ATG"),
    READY_FOR_BOARDING("RFB"),
    WAIT_FOR_BOARDING("WFB"),
    FLYING_FORWARD("FF"),
    DEBOARDING("DB"),
    FLYING_BACK("FB");
    private String state;
    private PilotState(String description){
        this.state = description;
    }
}
