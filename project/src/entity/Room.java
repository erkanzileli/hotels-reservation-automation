package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Room {

	@Id
	@Column(name = "idRoom")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idRoom;

	@Column(name = "idHotel")
	private int idHotel;

	@Column(name = "type")
	private String type;

	@Column(name = "number")
	private int number;

	@Column(name = "phoneNumber")
	private long phoneNumber;

	@Column(name = "status")
	private String status;

	@Column(name = "amount")
	private double amount;

	public Room() {
	}

	public Room(int idHotel, String type, int number, long phoneNumber, String status, double amount) {
		this.idHotel = idHotel;
		this.type = type;
		this.number = number;
		this.phoneNumber = phoneNumber;
		this.status = status;
		this.amount = amount;
	}

	public Room(int idHotel, String type, int number, String status) {
		this.idHotel = idHotel;
		this.type = type;
		this.number = number;
		this.status = status;
	}

	public int getIdRoom() {
		return idRoom;
	}

	public void setIdRoom(int idRoom) {
		this.idRoom = idRoom;
	}

	public int getIdHotel() {
		return idHotel;
	}

	public void setIdHotel(int idHotel) {
		this.idHotel = idHotel;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}