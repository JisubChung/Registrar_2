package COMP182;

import java.util.ArrayList;

public class Building {
	
	private String buildingName;
	private int openHour;
	private int closeHour;
	public ArrayList<Room> roomList= new ArrayList<Room>();
	
	Building (String name, int openHr, int closeHr) {
		//I don't have number of rooms here because everything will be passed in from Registrar
		// from files outside of the program
		this.buildingName = name;
		this.openHour = openHr;
		this.closeHour = closeHr;
	}

	public int getOpenHour() {
		return openHour;
	}

	public void setOpenHour(int openHour) {
		this.openHour = openHour;
	}
	
	public int getCloseHour() {
		return closeHour;
	}

	public void setCloseHour(int closeHour) {
		this.closeHour = closeHour;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	
	public void addRoom(String roomName, int roomCapacity) {
		this.roomList.add(new Room(roomName, roomCapacity, openHour, closeHour));
	}
	
	public void throwup() {
		System.out.println(this.buildingName);
		for (int x = 0; x < this.roomList.size(); x++) {
			roomList.get(x).throwup();
		}
	}
}
