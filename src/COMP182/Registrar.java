package COMP182;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

public class Registrar {
	private Campus campus;
	private Hashtable<String, Student> masterStudentList;
	//Key = ID
	//Value = Student Object
	private ArrayList<Course> courseList = new ArrayList<Course>();
	private int shortestCourse = 999999999;
	private LinkedList<String> isCompletelyFull = new LinkedList<String>();
	private ArrayList<String[]> errorList = new ArrayList<String[]>();
	private ArrayList<Student> noCourseStudentsList = new ArrayList<Student>();
	public Instructor first = new Instructor("Professor George", 123);
	public Instructor second = new Instructor("Professor John", 456);
	
	Registrar(String nameOfCampus) {
		campus = new Campus(nameOfCampus);
	}
	
	public void createCampus(String locationOfFile) {
		String csvFile = locationOfFile; 
		BufferedReader br = null; 
		String line = "";
		String splitBy = ",";
		try {
			br = new BufferedReader(new FileReader(csvFile));
			String campusName = null;
			int index = 0;
			line = br.readLine(); //assumes that line 1 of the file is headers
			while ((line = br.readLine()) != null) {
				String[] roomInformation = line.split(splitBy);
				if (roomInformation[0].equals(campusName)) {
					//add to the building
					campus.listOfBuildings.get(index).addRoom(roomInformation[1], Integer.parseInt(roomInformation[2]));
				}
				else if (campusName == null) {
					//create initial building
					campus.addBuilding(new Building(roomInformation[0],800,2200));
					campus.listOfBuildings.get(index).addRoom(roomInformation[1], Integer.parseInt(roomInformation[2]));
					campusName = roomInformation[0];
				}
				else if (roomInformation[0].equals(campusName) == false) {
					//create a new building
					index+=1;
					campus.addBuilding(new Building(roomInformation[0],800,2200));
					campus.listOfBuildings.get(index).addRoom(roomInformation[1], Integer.parseInt(roomInformation[2]));
					campusName = roomInformation[0];
				}
			}
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		finally {
			if (br != null) {
				try {
					br.close();
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void createCourses(String locationOfFile) {
		//this method needs to create the courses
		String csvFile = locationOfFile; 
		BufferedReader br = null; 
		String line = "";
		String splitBy = ",";
		try {
			br = new BufferedReader(new FileReader(csvFile));
			line = br.readLine(); //assumes that line 1 of the file is headers
			while ((line = br.readLine()) != null) {
				String[] courseInformation = line.split(splitBy);
				courseList.add(new Course(
						courseInformation[0], 
						Integer.parseInt(courseInformation[2]), 
						Integer.parseInt(courseInformation[1])));
				if (Integer.parseInt(courseInformation[2]) < shortestCourse) {
					shortestCourse = Integer.parseInt(courseInformation[2]);
				}
			}
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		finally {
			if (br != null) {
				try {
					br.close();
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		//it needs to initialize the session list as well
	}

	public Session createSession(String courseName) {
		int duration = getCourse(courseName).getDuration();
		for(int x = 0; x < campus.listOfBuildings.size(); x++){
			for(int y = 0; y < campus.listOfBuildings.get(x).roomList.size(); y++) {
				if(campus.listOfBuildings.get(x).roomList.get(y).getRemainingTime() >= duration) {
					//If there is space to add the course... 
					campus.listOfBuildings.get(x).roomList.get(y).listOfSessions.add(
							new Session(
									(subTimeHour(campus.listOfBuildings.get(x).roomList.get(y).getCloseHour(),campus.listOfBuildings.get(x).roomList.get(y).getRemainingTime())),
									(addTime((subTimeHour(campus.listOfBuildings.get(x).roomList.get(y).getCloseHour(),campus.listOfBuildings.get(x).roomList.get(y).getRemainingTime())),duration)),
									assignInstructor(x),
									(campus.listOfBuildings.get(x).roomList.get(y).getRoomCapacity())));
					//session created
					campus.listOfBuildings.get(x).roomList.get(y).setRemainingTime(subTimeMin(campus.listOfBuildings.get(x).roomList.get(y).getRemainingTime(),duration));
					//set remaining time in room
					campus.listOfBuildings.get(x).roomList.get(y).listOfSessions.getLast().setCourseName(courseName);
					//sets the Course name in the Session
					getCourse(courseName).sessionList.add(campus.listOfBuildings.get(x).roomList.get(y).listOfSessions.getLast());
					//Adds this session to Course's Session List
					return campus.listOfBuildings.get(x).roomList.get(y).listOfSessions.getLast();
				}
				else {
					continue;
				}
			}
		}
		return null;
	}
	
	public int addTime(int startTime, int addTime) {
		int hour = (startTime/100)*100;
		int mins = (startTime-hour)%60;
		int hourAdd = (addTime/60)*100;
		int minsAdd = (addTime)%60;
		int extraHour = ((mins + minsAdd)/60)*100;
		int minsAddReal = (mins + minsAdd)%60;
		
		int newTime = hour + hourAdd + extraHour + minsAddReal;
		return (newTime);
	}

	public int subTimeMin(int millitaryTime, int minutes) {
		int time;
		if(millitaryTime%100 >= minutes%100) {
			time = millitaryTime-minutes;
			return time;
		}
		else {
			int justMinutes = minutes%60;
			int minuteHours = (minutes/60)*100;
			time = millitaryTime - minuteHours;
			if ((time-((time/100)*100)) < justMinutes) {
				time -= 40;
				time = time - justMinutes;
				return time;
			}
			time = time - justMinutes;
			return time;
		}
	}
	
	public int subTimeHour(int millitaryTime, int hour) {
		int time;
		if ((millitaryTime-((millitaryTime/100)*100)) != (hour-((hour/100)*100))) {
			millitaryTime-=40;
			time = millitaryTime-hour;
			return time;
		}
		else {
			time = millitaryTime - hour;
			return time;
		}			
	}
	
	public Session getSession(String courseName) {
		Course x = getCourse(courseName);
		if(x.sessionList.isEmpty() == true) {
			return createSession(courseName);
		}
		if(x.sessionList.getLast().isSessionFull() != true) {
			return x.sessionList.getLast();
		}
		else {
			return null;
		}
	}
	
	public Instructor assignInstructor(int buildingIterator) {
		if (buildingIterator == 0) {
			return first;
		}
		else {
			return second;
		}
	}
	
	public Course getCourse(String courseName) {
		for (int x = 0; x <= courseList.size(); x++) {
			if(courseName.equals(courseList.get(x).getCrsName()) == true) {
				return courseList.get(x);
			}
		}
		return null;
		//if programmed correctly, should never reach this point.
	}
	
	public void addStudent(String studentID, String courseName) {
		getSession(courseName).addStudent(masterStudentList.get(studentID));
		//added Student to the Session
		getCourse(courseName).studentList.add(masterStudentList.get(studentID));
		//update Course's Student record
		masterStudentList.get(studentID).enrolledCourses.add(courseName);
		//updated Student's enrolled list
		getCourse(courseName).incrementTotalNumOfStudents();
		//update Course's tally of students
	}
	
	public void enrollStudents(String locationOfFile) {
		masterStudentList = new Hashtable<String, Student>();
		String csvFile = locationOfFile; 
		BufferedReader br = null; 
		String line = "";
		String splitBy = ",";
		try {
			br = new BufferedReader(new FileReader(csvFile));
			line = br.readLine(); //assumes that line 1 of the file is headers
			while ((line = br.readLine()) != null) {
				String[] studentInformation = line.split(splitBy);
				//studentInformation[0] = id
				//studentInformation[1] = fname
				//studentInformation[2] = lname
				//studentInformation[3] = grade
				//studentInformation[4] = course
				if (studentInformation.length < 5) {
					String[] error = {"Missing field, Name of offender: ", studentInformation[1] + " " + studentInformation[2]};
					errorList.add(error);
					continue;
				}
				
				if (masterStudentList.containsKey(studentInformation[0])==false) {
				//create Student if not already created
					masterStudentList.put(studentInformation[0], new Student(studentInformation[1] + " " + studentInformation[2], Integer.parseInt(studentInformation[0]), studentInformation[3]));
					//created Student, added to masterStudentList
				}
				
				if(isCompletelyFull.size() != 0) {
				//In case the whole campus is full
					boolean courseIsFull = isCompletelyFull.contains(studentInformation[4]);
					if(courseIsFull == true) {
						masterStudentList.get(studentInformation[0]).desiredCourseAdd(studentInformation[4]);
						noCourseStudentsList.add(masterStudentList.get(studentInformation[0]));
						//add courses they desire
						continue;
					}
				}
				
				if (masterStudentList.get(studentInformation[0]).isEnrolled(studentInformation[4])==true) {
					String[] error = {"Student already enrolled in " + studentInformation[4], masterStudentList.get(studentInformation[0]).getName()};
					errorList.add(error);
					continue;
				}
				
				//Student exists, Campus is not Full, Student not already enrolled
				masterStudentList.get(studentInformation[0]).desiredCourseAdd(studentInformation[4]);
				//updated Student's desired list
				if (getSession(studentInformation[4]) != null) {
				//Session is NOT full
					addStudent(studentInformation[0],studentInformation[4]);
				}
				else {
					Session created = createSession(studentInformation[4]);
					if(created == null) {
					//There is NO MORE SPACE for this course in all of Campus
						if(getCourse(studentInformation[4]).getDuration() == shortestCourse) {
							isCompletelyFull.add(studentInformation[4]);
							masterStudentList.get(studentInformation[0]).desiredCourses.add(studentInformation[4]);
							noCourseStudentsList.add(masterStudentList.get(studentInformation[0]));
							continue;
						}
					}
					else {
					//There is space in Campus to add this student to this Course
						addStudent(studentInformation[0],studentInformation[4]);
					}		
				}
			}
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		finally {
			if (br != null) {
				try {
					br.close();
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void errorWriteUp(String locationOfFile) {
		BufferedWriter writer = null;
        try {
        	String yes = locationOfFile + "errorLog.txt";
            File logFile = new File(yes);
            writer = new BufferedWriter(new FileWriter(logFile));
            writer.write("Reason for Error, ------ Error causing student");
            writer.newLine();
            for(int x = 0; x < errorList.size();x++) {
                writer.write(errorList.get(x)[0]+ " ------ " +errorList.get(x)[1]);
                writer.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
            }
        }
	}

	public void sessionAndStudentsEnrolledWriteUp(String locationOfFile) {
		BufferedWriter writer = null;
        try {
        	String yes = locationOfFile + "Log of students in sessions.txt";
            File logFile = new File(yes);
            writer = new BufferedWriter(new FileWriter(logFile));
            for(int x = 0; x < campus.listOfBuildings.size(); x++){
            	String campusName = campus.getCampusName();
    			for(int y = 0; y < campus.listOfBuildings.get(x).roomList.size(); y++) {
        			String buildingName = campus.listOfBuildings.get(x).getBuildingName();
        			if(y != 0) {
        				writer.newLine();
        				writer.newLine();
					}
    				for(int z = 0; z < campus.listOfBuildings.get(x).roomList.get(y).listOfSessions.size(); z++) {
    					String combined = "";
    					String roomName = campus.listOfBuildings.get(x).roomList.get(y).getRoomName();
    					combined = "Directory: " + campusName + " " + buildingName + " " + roomName;
    					if(z != 0) {
            				writer.newLine();
            				writer.newLine();
    					}
    					writer.write(combined);
        				for(int a = 0; a < campus.listOfBuildings.get(x).roomList.get(y).listOfSessions.get(z).getNumStudents(); a++) {
        					if (a == 0) {
        						writer.write(", " + campus.listOfBuildings.get(x).roomList.get(y).listOfSessions.get(z).getCourseName()); 
        						writer.newLine();
        						writer.write("Instructor: " + campus.listOfBuildings.get(x).roomList.get(y).listOfSessions.get(z).getInstructor().getName()); 
        						writer.newLine();
        						writer.write("Capacity: " +campus.listOfBuildings.get(x).roomList.get(y).listOfSessions.get(z).getNumStudents()); 
        						writer.write("/" + campus.listOfBuildings.get(x).roomList.get(y).getRoomCapacity());
                				writer.newLine(); 
        						writer.write("Start-End: " +campus.listOfBuildings.get(x).roomList.get(y).listOfSessions.get(z).getStartTime());
        						writer.write("-" +campus.listOfBuildings.get(x).roomList.get(y).listOfSessions.get(z).getEndTime());
        						writer.newLine();
                				writer.newLine();
                				writer.write("Students Enrolled: ");
        					}
        					writer.write(campus.listOfBuildings.get(x).roomList.get(y).listOfSessions.get(z).studentList.get(a).getName() + " ");
        				}
        			}
        		}
    		}
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
            }
        }
	}
	
	public void courseAndStudentsEnrolledWriteUp(String locationOfFile) {
		BufferedWriter writer = null;
        try {
        	String yes = locationOfFile + "Log of courses and students enrolled in them.txt";
            File logFile = new File(yes);
            writer = new BufferedWriter(new FileWriter(logFile));
            for(int x = 0; x < courseList.size();x++) {
				if(x != 0) {
    				writer.newLine();
    				writer.newLine();
				}
                writer.write("Course Name: " + courseList.get(x).getCrsName());
				writer.newLine();
                writer.write("Duration: " + courseList.get(x).getDuration());
				writer.newLine();
                writer.write("Units: " + courseList.get(x).getUnits());
				writer.newLine();
                writer.write("Total Number of Enrolled Students: " + courseList.get(x).getTotalNumOfStudents());
				writer.newLine();
                writer.newLine();
                writer.newLine();
            	for(int y  = 0; y < courseList.get(x).getTotalNumOfStudents(); y++) {
            		writer.write(courseList.get(x).studentList.get(y).getName() + " ");
            	}
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
            }
        }
	}
	
	public void masterStudentListWriteUp(String locationOfFile) {
		BufferedWriter writer = null;
        try {
        	String yes = locationOfFile + "Log of students and their records.txt";
            File logFile = new File(yes);
            writer = new BufferedWriter(new FileWriter(logFile));
            for(int x = 1; x <= masterStudentList.size();x++) {
                writer.write(String.valueOf(masterStudentList.get(String.valueOf(x)).getID()));
                writer.write(" " + masterStudentList.get(String.valueOf(x)).getName());
                writer.write(" " + masterStudentList.get(String.valueOf(x)).getGradeLevel());
                writer.newLine();
                writer.write("Desired:" + masterStudentList.get(String.valueOf(x)).listDesiredCourses());
                writer.newLine();
                writer.write("Enrolled:" + masterStudentList.get(String.valueOf(x)).listEnrolledCourses());
                writer.newLine();
                writer.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
            }
        }
	}
	
	public void noCourseStudentsListWriteUp(String locationOfFile) {
		BufferedWriter writer = null;
        try {
        	String yes = locationOfFile + "Log of students that did not get their classes.txt";
            File logFile = new File(yes);
            writer = new BufferedWriter(new FileWriter(logFile));
            for(int x = 1; x < noCourseStudentsList.size() ;x++) {
                writer.write(String.valueOf(noCourseStudentsList.get(x).getID()));
                writer.write(" - " + noCourseStudentsList.get(x).getName());
                writer.write(", " + noCourseStudentsList.get(x).getGradeLevel());
                writer.newLine();
                writer.write("Desired:" + noCourseStudentsList.get(x).listDesiredCourses());
                writer.newLine();
                writer.write("Enrolled:" + noCourseStudentsList.get(x).listEnrolledCourses());
                writer.newLine();
                writer.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
            }
        }
	}
	
	public void throwup() {
		this.campus.throwup();
	}
}

