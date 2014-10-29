package COMP182;

import java.util.LinkedList;

public class Session {
	private int capacity;
	private int startTime;
	private int endTime;
	private Instructor instructor;
	private int numStudents;
	private String courseName;
	public LinkedList<Student> studentList;

	public Session(int start, int end, Instructor instructor, int capacity) {
		//this.setID(id);
		this.setStartTime(start);
		this.setEndTime(end);
		this.setInstructor(instructor);
		this.capacity = capacity;
		studentList = new LinkedList<Student>();
	}
	
	///////////////////////
	//getters and setters//
	///////////////////////
	
	public int getCapacity() {
		return this.capacity;
	}
	
	public void setCapacity(int capacity) {
		this.capacity  = capacity;
	}
	
	public int getStartTime() {
		return this.startTime;
	}
	
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	
	public int getEndTime() {
		return this.endTime;
	}
	
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
	
	public Instructor getInstructor() {
		return this.instructor;
	}
	
	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}
	
	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	public int getNumStudents() {
		return this.numStudents;
	}
	
	///////////////////////
	//  Unique  Methods  //
	///////////////////////
	public void addStudent(Student sCall)
	{
		studentList.add(sCall);
		numStudents+=1;
	}
	
	public void removeStudent(Student sCall)
	{
		if(studentList.contains(sCall)) //checks for the case that the student is not on the roster
		{
			this.studentList.remove(sCall);
		}
		else
		{ 
			System.out.println("This student is not on the roster");
		}
	}
	
	public int findStudentID(int sid) 
	{
		
		for (int x = 0; x <= capacity; x+=1) //Traverses the roster
		{
			if(studentList.get(x) == null) //breaks if no student in roster
			{
				break;
			}
			if (sid == studentList.get(x).getID()) //if student found, return location in roster
			{
				return x;
			}
		}
		return 404404;
	}
	
	public boolean isSessionFull() {
		if (numStudents == capacity) {
			return true;
		}
		else {
			return false;
		}
	}
}
