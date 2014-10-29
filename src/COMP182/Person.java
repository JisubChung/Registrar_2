package COMP182;

public class Person 
{
	//elements of all persons
	public String name;
	public int id;
	
	//Constructors
	public Person(String givenName, int givenID){
		name = givenName;
		id = givenID;
	}
	
	//Methods
	public boolean isDuplicate(Student checkAgainst)
	{
		if (id == checkAgainst.getID())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public void setName(String newName){
		name = newName;
	}
	public void setID(int newID){
		id = newID;
	}
	public String getName() 
	{
		return name;
	}
	public int getID() 
	{
		return id;
	}
	public String toString(){
		return (name + ", " + id);
	}

}
