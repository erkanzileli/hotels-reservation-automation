package model;

import javafx.scene.layout.Pane;

public class PaneModel extends Pane {

	public int idRoom;

	public int idHotel;

	public PaneModel() {
	}

	public int getIdRoom() {
		return this.idRoom;
	}

	public void setIdRoom(int idRoom) {
		this.idRoom = idRoom;
	}

	public int getIdHotel() {
		return this.idHotel;
	}

	public void setIdHotel(int idHotel) {
		this.idHotel = idHotel;
	}

}
