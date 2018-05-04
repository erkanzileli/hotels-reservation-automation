package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class FeedBack {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idFeedBack;

	private int idHotel;

	private long tc;

	private int point;

	private String comment;

	private LocalDateTime date;

	public FeedBack() {
	}

	public FeedBack(int idHotel, long tc, int point, String comment, LocalDateTime date) {
		this.idHotel = idHotel;
		this.tc = tc;
		this.point = point;
		this.comment = comment;
		this.date = date;
	}

	public int getIdFeedBack() {
		return idFeedBack;
	}

	public void setIdFeedBack(int idFeedBack) {
		this.idFeedBack = idFeedBack;
	}

	public int getIdHotel() {
		return idHotel;
	}

	public void setIdHotel(int idHotel) {
		this.idHotel = idHotel;
	}

	public long getTc() {
		return tc;
	}

	public void setTc(long tc) {
		this.tc = tc;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}
}
