package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import businessLogic.BLFacade;
import domain.Ride;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named("findRideBean")
@SessionScoped
public class FindRideBean implements Serializable {

    private String from;  // selected origin
    private String to;    // selected destination
    private Date date;    // selected date

    private List<Ride> rides;          // rides to display
    private List<String> origins;      // available departure cities
    private List<String> destinations; // available arrival cities

    private final BLFacade facade = FacadeBean.getBusinessLogic();

    public FindRideBean() {
        // leave empty, lazy initialization in getters
    }

    // --- Lazy initialization for origins ---
    public List<String> getOrigins() {
        if (origins == null) {
            origins = new ArrayList<>();
            if (facade != null) {
                origins = facade.getDepartCities();
                System.out.println("Origins loaded: " + origins);
            } else {
                System.out.println("BLFacade is null! Cannot load origins.");
            }
        }
        return origins;
    }

    // --- Lazy initialization for destinations based on selected origin ---
    public List<String> getDestinations() {
        if (to != null && (destinations == null || destinations.isEmpty())) {
            destinations = new ArrayList<>();
            if (facade != null && from != null) {
                destinations = facade.getDestinationCities(from);
                System.out.println("Destinations for '" + from + "': " + destinations);
            }
        }
        return destinations;
    }
    
    public void onOriginChange() {
        if (from != null && !from.isEmpty()) {
            destinations = facade.getDestinationCities(from);
            System.out.println("Destinations for '" + from + "': " + destinations);
            // Clear previously selected destination
            to = null;
        } else {
            destinations = new ArrayList<>();
            to = null;
        }
    }
    
    private Date normalizeDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }



    // --- Method to search rides ---
    public void findRides() {
        rides = new ArrayList<>();
        
        if (from != null && to != null && date != null) {
        	 Date normalizedDate = normalizeDate(date);
            rides = facade.getRides(from, to, date);
            System.out.println("Rides found: " + rides.size());
        } else {
            System.out.println("Cannot search rides: from/to/date not set");
        }
    }

    

    // --- Getters & Setters ---
    public String getFrom() { return from; }
    public void setFrom(String from) { 
        this.from = from; 
        // clear previous selection when origin changes
        destinations = null;
        to = null;
    }

    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public List<Ride> getRides() { return rides; }

}
