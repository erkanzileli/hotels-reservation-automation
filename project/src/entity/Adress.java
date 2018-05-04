package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Adress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idAdress;

	private int idHotel;

	private int idCompany;

	private String country;

	private String city;

	private String street;

	private int postalCode;

	private int doorNumber;

	private long phoneNumber;

	private String type;

	public Adress() {
	}

	public Adress(int idHotel, int idCompany, String country, String city, String street, int postalCode,
			int doorNumber, long phoneNumber, String type) {
		this.idHotel = idHotel;
		this.idCompany = idCompany;
		this.country = country;
		this.city = city;
		this.street = street;
		this.postalCode = postalCode;
		this.doorNumber = doorNumber;
		this.phoneNumber = phoneNumber;
		this.type = type;
	}

	public int getIdAdress() {
		return idAdress;
	}

	public void setIdAdress(int idAdress) {
		this.idAdress = idAdress;
	}

	public int getIdHotel() {
		return idHotel;
	}

	public void setIdHotel(int idHotel) {
		this.idHotel = idHotel;
	}

	public int getIdCompany() {
		return idCompany;
	}

	public void setIdCompany(int idCompany) {
		this.idCompany = idCompany;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	public int getDoorNumber() {
		return doorNumber;
	}

	public void setDoorNumber(int doorNumber) {
		this.doorNumber = doorNumber;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
