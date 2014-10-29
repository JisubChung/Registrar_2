package COMP182;

public class Instructor extends Person
{
	private String department;
	 
	//constructor
	public Instructor(String givenName,	int givenId	) { 
		super(givenName, givenId);	
	}
	
	//Methods
	public String getDepartment() 
	{
		return department;
	}
	public void setDepartment(String initDepartment)
	{
		department = initDepartment;
	}
	
}