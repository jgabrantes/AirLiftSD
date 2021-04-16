package Entities;
import SharedRegions.*;

public class Hostess {
    private int id;
    private DepartureAirport depAirport;
    private DestinationAirport destAirport;
    private Plane  plane;

    public Hostess(int id, DepartureAirport depAirport, DestinationAirport destAirport, Plane plane) {
        this.id = id;
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
}
