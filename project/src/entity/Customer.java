package entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Customer {

	@Id
	private long tc;

	private int idAccount;

	public Customer() {
	}

	public Customer(long tc, int idAccount) {
		this.tc = tc;
		this.idAccount = idAccount;
	}

	public long getTc() {
		return tc;
	}

	public void setTc(long tc) {
		this.tc = tc;
	}

	public int getIdAccount() {
		return idAccount;
	}

	public void setIdAccount(int idAccount) {
		this.idAccount = idAccount;
	}

}
