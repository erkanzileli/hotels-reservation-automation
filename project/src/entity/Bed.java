package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Bed {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idBed;

	private int idRoom;

	private int BedType;

	public Bed() {
	}

	public Bed(int idRoom, int bedType) {
		this.idRoom = idRoom;
		BedType = bedType;
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

	public int getBedType() {
		return BedType;
	}

	public void setBedType(int bedType) {
		BedType = bedType;
	}
}
