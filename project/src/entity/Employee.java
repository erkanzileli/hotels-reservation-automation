package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Employee {

	@Id
	@Column(name = "tc")
	private long tc;

	@Column(name = "idAccount")
	private int idAccount;

	@Column(name = "idHotel")
	private int idHotel;

	@Column(name = "type")
	private String type;

	@Column(name = "firstname")
	private String firstname;

	@Column(name = "lastname")
	private String lastname;

	public Employee() {
	}

	public Employee(long tc, int idAccount, int idHotel, String type) {
		this.tc = tc;
		this.idAccount = idAccount;
		this.idHotel = idHotel;
		this.setType(type);
	}

	public Employee(long tc, int idHotel, String type, String firstname, String lastname) {
		this.tc = tc;
		this.idHotel = idHotel;
		this.setType(type);
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public long getTc() {
		return tc;
	}

	public void setTc(long tc) {
		this.tc = tc;
	}

	public int getIdAccount() {
		return this.idAccount;
	}

	public void setIdAccount(int idAccount) {
		this.idAccount = idAccount;
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

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

}
