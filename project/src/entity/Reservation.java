package entity;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "reservation")
public class Reservation {

	@Id
	@Column(name = "idReservation")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idReservation;

	@Column(name = "idHotel")
	private int idHotel;

	@Column(name = "idRoom")
	private int idRoom;

	@Column(name = "startDate")
	private Date startDate;

	@Column(name = "amount")
	private double amount;

	@Column(name = "tcCustomer")
	private long tcCustomer;

	public Reservation() {
	}

	public Reservation(int idHotel, int idRoom, LocalDate localDate, double amount, long tcCustomer) {
		this.setIdCustomer(tcCustomer);
		this.setIdHotel(idHotel);
		this.setIdRoom(idRoom);
		this.setDate(localDate);
		this.setAmount(amount);
	}

	public Reservation(int idHotel, int idRoom, LocalDate date, double amount) {
		this.setIdHotel(idHotel);
		this.setIdRoom(idRoom);
		this.setDate(date);
		this.setAmount(amount);
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

	public Date getDate() {
		return startDate;
	}

	public void setDate(LocalDate date) {
		this.startDate = Date.valueOf(date);
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public long getIdCustomer() {
		return tcCustomer;
	}

	public void setIdCustomer(long tcCustomer) {
		this.tcCustomer = tcCustomer;
	}

}