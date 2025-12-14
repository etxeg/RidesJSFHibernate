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

@Named("findRideSeatBean")
@SessionScoped
public class FindRideSeatBean implements Serializable {

	private int nSeats;

	private List<Ride> rides; // rides to display

	private final BLFacade facade = FacadeBean.getBusinessLogic();

	public FindRideSeatBean() {
		// leave empty, lazy initialization in getters
	}


	// --- Method to search rides ---
	public void findRides() {
		rides = new ArrayList<>();

		rides = facade.getRidesByPlaces(nSeats);
		System.out.println("Rides found: " + rides.size());
	}
	
	public String showRides() {
		findRides();
		return "ShowRide?faces-redirect=true";
	}

	// --- Getters & Setters ---
	public void setRides(List<Ride> rides) {
		this.rides=rides;
	}
	public List<Ride>getRides(){
		return this.rides;
	}
	
	public void setnSeats(int nSeats) {
		this.nSeats = nSeats;
	}

	public int getnSeats() {
		return this.nSeats;
	}

}
