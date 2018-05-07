package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "generalmanager")
public class GeneralManager {

	@Id
	@Column(name = "tc")
	private long tc;

	@Column(name = "idAccount")
	private int idAccount;

	@Column(name = "idCompany")
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
