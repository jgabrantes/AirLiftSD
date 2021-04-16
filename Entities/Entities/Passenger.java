package Entities;
import SharedRegions.*;
public class Passenger {
    private int id;
    private String name;
    private DepartureAirport depAirport;
    private DestinationAirport destAirport;
    private Plane plane;

    public Passenger(int id, String name,  DepartureAirport depAirport, DestinationAirport destAirport, Plane plane) {
        this.id = id;
        this.name = name;
        this.depAirport = depAirport;
        this.destAirport = destAirport;
        this.plane = plane;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void Run(){}
    
}
