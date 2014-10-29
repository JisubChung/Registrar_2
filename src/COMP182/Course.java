package COMP182;

import java.util.LinkedList;


public class Course 
{
	private String name;
	private int units;
	private int duration;
	private int totalNumOfStudents;
	public LinkedList<Session> sessionList = new LinkedList<Session>();
	public LinkedList<Student> studentList = new LinkedList<Student>();
	
	//Constructor
	public Course(String initCrsName, int durration, int units) 
	{
		name = initCrsName;
		this.duration = durration;
		this.units = units;
	}
	
	public String getCrsName()
	{
		return name;
	}
	
	public int getUnits()
	{
		return units;
	}
	
	public int getDuration()
	{
		return duration;
	}
		
	public void setUnits(int inputUnits)
	{
		units = inputUnits;
	}
	
	public void setDuration(int inputDuration)
	{
		duration = inputDuration;
	}
	
	public int getTotalNumOfStudents() {
		return this.totalNumOfStudents;
	}
	
	public void incrementTotalNumOfStudents() {
		this.totalNumOfStudents+=1;
	}
}
