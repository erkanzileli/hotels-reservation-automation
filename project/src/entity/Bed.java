package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Bed {

	@Id
	@Column(name = "idBed")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idBed;

	@Column(name = "idRoom")
	private int idRoom;

	@Column(name = "capacity")
	private int capacity;

	public Bed() {
	}

	public Bed(int idRoom, int bedType) {
		this.idRoom = idRoom;
		capacity = bedType;
	}

	public int getIdBed() {
		return idBed;
	}

	public void setIdBed(int idBed) {
		this.idBed = idBed;
	}

	public int getIdRoom() {
		return idRoom;
	}

	public void setIdRoom(int idRoom) {
		this.idRoom = idRoom;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
}
