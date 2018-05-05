package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Address {

	@Id
	@Column(name="idAddress")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idAddress;

	@Column(name="idHotel")
	private int idHotel;

	@Column(name="country")
	private String country;

	@Column(name="province")
	private String province;

	@Column(name="district")
	private String district;

	@Column(name="phoneNumber")
	private long phoneNumber;

	public Address() {

	}

	public Address(int idHotel, String country, String province, String district, long phoneNumber) {
		this.idHotel = idHotel;
		this.country = country;
		this.province = province;
		this.district = district;
		this.phoneNumber = phoneNumber;
	}

	public int getIdAddress() {
		return idAddress;
	}

	public void setIdAddress(int idAddress) {
		this.idAddress = idAddress;
	}

	public int getIdHotel() {
		return idHotel;
	}

	public void setIdHotel(int idHotel) {
		this.idHotel = idHotel;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
