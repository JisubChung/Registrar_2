package COMP182;

public class Main {
	static public void main(String[] args){
		System.out.println("Begin Application\n");
		//csun.throwup();
		
		/*
		 * Please note that you may have to change your directories as is appropriate
		 * Just as an aside, the full list of files needed to run this are:
		 * CSV Files: BLDG_INFO, COURSE_INFO, MOCK_DATA
		 * Java Files: Student, Session, Room, Registrar, Person, Main, Instructor, Course, Campus, Building 
		 */
		
		Registrar csun = new Registrar("CSUN");
		csun.createCampus("D:/JAVA projects/Registrar 2/src/COMP182/BLDG_INFO_ALL.csv");
		csun.createCourses("D:/JAVA projects/Registrar 2/src/COMP182/COURSE_INFO.csv");
		csun.enrollStudents("D:/JAVA projects/Registrar 2/src/COMP182/MOCK_DATA.csv");
		csun.errorWriteUp("D:/JAVA projects/Registrar 2/src/COMP182/");
		csun.sessionAndStudentsEnrolledWriteUp("D:/JAVA projects/Registrar 2/src/COMP182/");
		csun.courseAndStudentsEnrolledWriteUp("D:/JAVA projects/Registrar 2/src/COMP182/");
		csun.masterStudentListWriteUp("D:/JAVA projects/Registrar 2/src/COMP182/");
		csun.noCourseStudentsListWriteUp("D:/JAVA projects/Registrar 2/src/COMP182/");

		System.out.println("\nEnd Application");
	}
}








