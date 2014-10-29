package COMP182;

import java.util.ArrayList;

public class Student extends Person
{
	private int credits;
	private int gradePoints;
	private String gradeLevel;
	public ArrayList<String> enrolledCourses = new ArrayList<String>();
	public ArrayList<String> desiredCourses = new ArrayList<String>();
	
	 
	//Constructor 
	public Student(String givenName, int givenId, String gradeLevel) 
	{
		super(givenName, givenId);
		this.gradeLevel = gradeLevel;
	}
	
	//Methods
	public int getCredits() 
	{
		return credits;
	}
	public int getGradePoint() 
	{
		return gradePoints;
	}
	public double getGPA() 
	{ //GPA is calculated as gradepoints/credits
		double x = gradePoints;
		double y = credits;
		double z;
		z = x/y;
		return z;
	}
	public String getGradeLevel()
	{
		return gradeLevel;
	}
	public void setCredits(int inputCredits) 
	{
		credits = inputCredits;
	}
	public void setGradePoint(int inputGradePoints) 
	{
		gradePoints = inputGradePoints;
	}
	public void setGradeLevel(String gradeLevel)
	{
		this.gradeLevel = gradeLevel;
	}

	public void enrollIn(String courseName) {
		this.enrolledCourses.add(courseName);
	}
	
	public boolean isEnrolled(String courseName) {
		if(enrolledCourses.contains(courseName) == true) {
			return true;
		}
		else {
			return false;
		}
	}

	public void desiredCourseAdd(String courseName) {
		this.desiredCourses.add(courseName);
	}
	
	public String listEnrolledCourses() {
		String yes = "";
		for(int x = 0; x < enrolledCourses.size(); x++) {
			yes+= " " + enrolledCourses.get(x);
		}
		return yes;
	}
	
	public String listDesiredCourses() {
		String yes = "";
		for(int x = 0; x < desiredCourses.size(); x++) {
			yes+= " " + desiredCourses.get(x);
		}
		return yes;
	}
}