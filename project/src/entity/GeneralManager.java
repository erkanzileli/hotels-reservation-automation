package entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class GeneralManager {

	@Id
	private long tc;

	private int idAccount;

	private int idCompany;

	public GeneralManager() {
	}

	public GeneralManager(long tc, int idAccount, int idCompany) {
		this.tc = tc;
		this.idAccount = idAccount;
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

	public int getIdCompany() {
		return idCompany;
	}

	public void setIdCompany(int idCompany) {
		this.idCompany = idCompany;
	}

}
