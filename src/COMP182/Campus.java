package COMP182;

import java.util.ArrayList;

public class Campus {
	
	private String campusName;
	public ArrayList<Building> listOfBuildings = new ArrayList<Building>();// = new LinkedList<String>();	

	public Campus(String name) {
		this.setCampusName(name);
	}

	public String getCampusName() {
		return campusName;
	}

	public void setCampusName(String campusName) {
		this.campusName = campusName;
	}
	
	public void addBuilding(Building bldg) {
		this.listOfBuildings.add(bldg);
	}
	
	public void throwup() {
		System.out.println(this.campusName);
		for (int x = 0; x < this.listOfBuildings.size(); x++) {
			this.listOfBuildings.get(x).throwup();
		}
	}
}
