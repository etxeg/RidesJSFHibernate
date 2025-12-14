package beans;

import java.io.Serializable;
import java.util.Date;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import businessLogic.BLFacade;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

@Named("createRideBean")
@ViewScoped
public class CreateRideBean  implements Serializable{

	private String from;
	private String to;
	private Date date;
	private int nSeats;
	private float price;

	
	private final String driverEmail = "driver1@gmail.com";

	// Getters & setters required by JSF
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getnSeats() {
		return nSeats;
	}

	public void setnSeats(int nSeats) {
		this.nSeats = nSeats;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	
	public String createRide() {
		BLFacade facade = FacadeBean.getBusinessLogic();

		try {
			facade.createRide(from, to, date, nSeats, price, driverEmail);

			
			return "MainMenu?faces-redirect=true";

		} catch (RideAlreadyExistException | RideMustBeLaterThanTodayException e) {
			e.printStackTrace();
			return null; 
		}
	}
}
