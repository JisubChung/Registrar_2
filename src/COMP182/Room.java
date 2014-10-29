package COMP182;

import java.util.*;

public class Room {
	private String roomName;
	private int roomCapacity;
	private int openHour; //Must be 24-Hour time
	private int closeHour; //Must be 24-Hour time
	private int remainingTime;
	private int numOfSessions;
	public LinkedList<Session> listOfSessions = new LinkedList<Session>();
	
	public Room(String roomName, int roomCapacity, int openHour, int closeHour)
	{
		this.roomName = roomName;
		this.roomCapacity = roomCapacity;
		this.openHour = openHour;
		this.closeHour = closeHour;
		this.remainingTime= closeHour - openHour;
	}

	///////////////////////
	//getters and setters//
	///////////////////////
	public String getRoomName(){
		return this.roomName;
	}
	public int getRoomCapacity() {
		return this.roomCapacity;
	}
	public int getOpenHour() {
		return this.openHour;
	}
	public int getCloseHour() {
		return this.closeHour;
	}
	public int getRemainingTime() {
		return this.remainingTime;
	}
	
	///////////////////////
	//  Unique  Methods  //
	///////////////////////
	
	public void setRemainingTime(int time) {
		remainingTime = time;
	}
	
	public void throwup() {
		System.out.println(this.roomName);
		for(int x = 0; x < numOfSessions; x++) {
			System.out.println(this.listOfSessions.get(x).getStartTime());
		}
	}
}
