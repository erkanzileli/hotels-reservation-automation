package model;

public class CustomerResultListModel {

	private String hotelName;
	private String companyName;
	private String province;
	private String district;
	private int freeRoomCount;
	private double minimumAmount;
	private int idHotel;

	public CustomerResultListModel(String hotelName, String companyName, String province, String district,
			int freeRoomCount, double minimumAmount, int idHotel) {
		this.setHotelName(hotelName);
		this.setCompanyName(companyName);
		this.setProvince(province);
		this.setDistrict(district);
		this.setFreeRoomCount(freeRoomCount);
		this.setMinimumAmount(minimumAmount);
		this.setIdHotel(idHotel);
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public int getFreeRoomCount() {
		return freeRoomCount;
	}

	public void setFreeRoomCount(int freeRoomCount) {
		this.freeRoomCount = freeRoomCount;
	}

	public double getMinimumAmount() {
		return minimumAmount;
	}

	public void setMinimumAmount(double minimumAmount) {
		this.minimumAmount = minimumAmount;
	}

	public int getIdHotel() {
		return idHotel;
	}

	public void setIdHotel(int idHotel) {
		this.idHotel = idHotel;
	}
}
