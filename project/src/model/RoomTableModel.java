package model;

public class RoomTableModel {

	private int idRoom;

	private int idHotel;

	private String type;

	private int number;

	private long phoneNumber;

	private String status;

	private int bedCount;

	public RoomTableModel() {
	}

	public RoomTableModel(int idHotel, String type, int number, long phoneNumber, String status, int bedCount) {
		this.idHotel = idHotel;
		this.type = type;
		this.number = number;
		this.phoneNumber = phoneNumber;
		this.status = status;
		this.bedCount = bedCount;
	}

	public int getIdRoom() {
		return idRoom;
	}

	public void setIdRoom(int idRoom) {
		this.idRoom = idRoom;
	}

	public int getIdHotel() {
		return idHotel;
	}

	public void setIdHotel(int idHotel) {
		this.idHotel = idHotel;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getBedCount() {
		return bedCount;
	}

	public void setBedCount(int bedCount) {
		this.bedCount = bedCount;
	}

}
