package entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class InRoom {

	@Id
	private long tc;

	private int idReservated;

	public InRoom() {
	}

	public InRoom(long tc, int idReservated) {
		this.tc = tc;
		this.idReservated = idReservated;
	}

	public long getTc() {
		return tc;
	}

	public void setTc(long tc) {
		this.tc = tc;
	}

	public int getIdReservated() {
		return idReservated;
	}

	public void setIdReservated(int idReservated) {
		this.idReservated = idReservated;
	}
}