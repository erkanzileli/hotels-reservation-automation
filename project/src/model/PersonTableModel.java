package model;

import java.time.LocalDate;

public class PersonTableModel {

	private String name;
	private String surname;
	private long tc;
	private LocalDate birth;

	public PersonTableModel(String name, String surname, long tc, LocalDate birth) {
		this.setName(name);
		this.setSurname(surname);
		this.setTc(tc);
		this.setBirth(birth);
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

	public long getTc() {
		return tc;
	}

	public void setTc(long tc) {
		this.tc = tc;
	}

	public LocalDate getBirth() {
		return birth;
	}

	public void setBirth(LocalDate birth) {
		this.birth = birth;
	}

}
