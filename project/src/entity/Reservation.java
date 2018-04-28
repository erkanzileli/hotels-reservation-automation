package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idReservation;

	private int idHotel;

	private int idRoom;

	private long tcCustomer;

	private long tcEmployee;

	private LocalDateTime date;

	private double amount;

	public Reservation() {
	}

	public Reservation(int idHotel, int idRoom, long tcCustomer, long tcEmployee, LocalDateTime date, double amount) {
		this.idHotel = idHotel;
		this.idRoom = idRoom;
		this.tcCustomer = tcCustomer;
		this.tcEmployee = tcEmployee;
		this.date = date;
		this.amount = amount;
	}

	public int getIdReservation() {
		return idReservation;
	}

	public void setIdReservation(int idReservation) {
		this.idReservation = idReservation;
	}

	public int getIdHotel() {
		return idHotel;
	}

	public void setIdHotel(int idHotel) {
		this.idHotel = idHotel;
	}

	public int getIdRoom() {
		return idRoom;
	}

	public void setIdRoom(int idRoom) {
		this.idRoom = idRoom;
	}

	public long getTcCustomer() {
		return tcCustomer;
	}

	public void setTcCustomer(long tcCustomer) {
		this.tcCustomer = tcCustomer;
	}

	public long getTcEmployee() {
		return tcEmployee;
	}

	public void setTcEmployee(long tcEmployee) {
		this.tcEmployee = tcEmployee;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}