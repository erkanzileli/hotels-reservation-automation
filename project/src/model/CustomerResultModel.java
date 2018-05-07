package model;

public class CustomerResultModel {

	private int idRoom;
	private int idHotel;
	private int idCompany;
	private String hotelName;

	public CustomerResultModel(int idRoom, int idHotel, int idCompany, String hotelName) {
		this.setIdRoom(idRoom);
		this.setIdHotel(idHotel);
		this.setIdCompany(idCompany);
		this.setHotelName(hotelName);
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

	public int getIdCompany() {
		return idCompany;
	}

	public void setIdCompany(int idCompany) {
		this.idCompany = idCompany;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

}
