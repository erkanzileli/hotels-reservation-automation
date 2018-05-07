package model;

public class RoomListModel {

	private int bedCount;
	private int capacity;
	private double amount;
	private int idRoom;
	private int roomNumber;
	private String roomType;

	public RoomListModel(int bedCount, int capacity, double amount, int idRoom, int roomNumber, String roomType) {
		this.setIdRoom(idRoom);
		this.setBedCount(bedCount);
		this.setCapacity(capacity);
		this.setAmount(amount);
		this.setRoomNumber(roomNumber);
		this.setRoomType(roomType);
	}

	public int getBedCount() {
		return bedCount;
	}

	public void setBedCount(int bedCount) {
		this.bedCount = bedCount;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getIdRoom() {
		return idRoom;
	}

	public void setIdRoom(int idRoom) {
		this.idRoom = idRoom;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

}
