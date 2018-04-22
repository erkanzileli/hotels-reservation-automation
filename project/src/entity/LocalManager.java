package entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LocalManager {

	@Id
	private long tc;

	private int idAccount;

	private int idHotel;

	private int idCompany;

	public LocalManager() {
	}

	public LocalManager(long tc, int idAccount, int idHotel, int idCompany) {
		this.tc = tc;
		this.idAccount = idAccount;
		this.idHotel = idHotel;
		this.idCompany = idCompany;
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

}
