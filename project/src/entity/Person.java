package entity;

import java.util.Date;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Person {

	@Id
	@Column(name = "idPerson")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idPerson;

	@Column(name = "tc")
	private long tc;

	@Column(name = "name")
	private String name;

	@Column(name = "surname")
	private String surname;

	@Column(name = "birthDate")
	private LocalDate birthDate;

	@Column(name = "idReservation")
	private int idReservation;

	private Date entryDate;

	private Date outDate;

	public Person() {
	}

	public Person(long tc, String name, String surname, LocalDate birthDate, int idReservation, Date entryDate,
			Date outDate) {
		this.tc = tc;
		this.name = name;
		this.surname = surname;
		this.birthDate = birthDate;
		this.setEntryDate(entryDate);
		this.setOutDate(outDate);
		this.setIdReservation(idReservation);
	}

	public int getIdPerson() {
		return idPerson;
	}

	public void setIdPerson(int idPerson) {
		this.idPerson = idPerson;
	}

	public long getTc() {
		return tc;
	}

	public void setTc(long tc) {
		this.tc = tc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public int getIdReservation() {
		return idReservation;
	}

	public void setIdReservation(int idReservation) {
		this.idReservation = idReservation;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public Date getOutDate() {
		return outDate;
	}

	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}

}
