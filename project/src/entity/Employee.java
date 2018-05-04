package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Employee {

	@Id
	private long tc;

	private int idHotel;

	private int idAccount;

	@Column(name = "EmployeeType")
	private String type;

	public Employee() {
	}

	public Employee(long tc, int idHotel, int idAccount, String type) {
		this.tc = tc;
		this.idHotel = idHotel;
		this.idAccount = idAccount;
		this.setType(type);
	}

	public long getTc() {
		return tc;
	}

	public void setTc(long tc) {
		this.tc = tc;
	}

	public int getIdHotel() {
		return idHotel;
	}

	public void setIdHotel(int idHotel) {
		this.idHotel = idHotel;
	}

	public int getIdAccount() {
		return idAccount;
	}

	public void setIdAccount(int idAccount) {
		this.idAccount = idAccount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
