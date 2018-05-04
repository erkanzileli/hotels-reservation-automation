package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Reservated {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idReservated;

	private int idReservation;

	private int idRoom;

	private LocalDateTime beginDate;

	private LocalDateTime endDate;

	public Reservated() {
	}

	public Reservated(int idReservation, int idRoom, LocalDateTime beginDate, LocalDateTime endDate) {
		this.idReservation = idReservation;
		this.idRoom = idRoom;
		this.beginDate = beginDate;
		this.endDate = endDate;
	}

	public int getIdReservated() {
		return idReservated;
	}

	public void setIdReservated(int idReservated) {
		this.idReservated = idReservated;
	}

	public int getIdReservation() {
		return idReservation;
	}

	public void setIdReservation(int idReservation) {
		this.idReservation = idReservation;
	}

	public int getIdRoom() {
		return idRoom;
	}

	public void setIdRoom(int idRoom) {
		this.idRoom = idRoom;
	}

	public LocalDateTime getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(LocalDateTime beginDate) {
		this.beginDate = beginDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
}