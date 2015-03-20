import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * X460.10/2 - Java Programming 1 - Team A 
 * DatabaseHelper.java
 * Purpose: Provides access to the registration system data.
 *
 * @author Chris Blackledge, Travis Cribbet, and Levi Hsiao
 * @version 1.0 3/3/14
 */

public class DatabaseHelper
{
	private String DatabasePath;
	private final String COURSE_TEXT = "COURSE";
	private final String STUDENT_TEXT = "STUDENT";
	private final String DATE_FORMAT = "MM/dd/yyyy";
	enum InformationType {Student, Course}

	/**
	 * Constructor
	 * @param databasePath the string to the database file
	 */
	public DatabaseHelper(String databasePath)
	{
		DatabasePath = databasePath;
	}

	/**
	 * Get a Course
	 * @param id the course id
	 */
	public Course getCourse(Integer id)
	{
		// read the database
		List<String> allLines = readDatabase();

		// iterate through all lines in the file
		for(int i = 0 ; i < allLines.size(); i++)
		{
			// get the current line of text for the course
			String currentLine = allLines.get(i);

			// if the line contains the given course id
			if(currentLine.contains(Integer.toString(id)))
			{
				// return an instance of a course object based on string data
				return stringToCourse(currentLine);
			}
		}
		
		// no courses contained the id; return null
		return null;
	}

	/**
	 * Modify a student
	 * @param student the course id
	 */
	public void modifyStudent(Student student)
	{
		// convert the student object to string data
		String databaseStudent = studentToDatabaseString(student);

		// read the database
		List<String> allLines = readDatabase();
		
		// determine which line pertains to the given student id 
		int lineIndex = findLineIndex(allLines, Integer.toString(student.getStudentId()));
		
		// if the line is found, update it
		if(lineIndex != -1)
		{
			allLines.set(lineIndex, databaseStudent);
		}
		
		// write all the lines to the file
		writeDatabase(allLines);		
	}
	
	/**
	 * Modify a course
	 * @param course the course to modify
	 */
	public void modifyCourse(Course course)
	{
		// convert the course object to string data
		String databaseCourse = courseToDatabaseString(course);
		
		// read the database
		List<String> allLines = readDatabase();
		
		// determine which line pertains to the given course id
		int lineIndex = findLineIndex(allLines, Integer.toString(course.getCourseId()));
		
		// if the line is found, update it
		if(lineIndex != -1)
		{
			allLines.set(lineIndex, databaseCourse);
		}
		
		// write all the lines to the file
		writeDatabase(allLines);
	}
    
	/**
	 * Get a map of courses
	 */
	public HashMap<Integer, Course> getCourses()
	{
		// get all the course lines of text from the file
		List<String> linesOfInterest = getLinesOfInterest(InformationType.Course);

		// create a map instance to return
		HashMap<Integer, Course> courseMap = new HashMap<Integer, Course>();

		// for each line entry in the list...
		for(String str : linesOfInterest)
		{
			// get a course object instance
			Course currentCourse = stringToCourse(str);

			// get the course id
			Integer id = new Integer(currentCourse.getCourseId());

			// add the course to the hashmap
			courseMap.put(id, currentCourse);
		}

		// return the hashmap
		return courseMap;
	}
    
	/**
	 * Get a map of students
	 */
	public HashMap<Integer, Student> getStudents()
	{
		// get all the student lines of text from the file
		List<String> linesOfInterest = getLinesOfInterest(InformationType.Student);

		// create a map instance to return
		HashMap<Integer, Student> studentMap = new HashMap<Integer, Student>();

		// for each line entry in the list...
		for(String str : linesOfInterest)
		{
			// get a student object instance
			Student currentStudent = stringToStudent(str);

			// get the course id
			Integer id = new Integer( currentStudent.getStudentId());

			// add the course to the hashmap
			studentMap.put(id, currentStudent);
		}

		// return the hashmap
		return studentMap;
	}

	/**
	 * Add a student to the file
	 * @param student the student instance to add
	 */
	public void addStudent(Student student)
	{
		// read the database
		List<String> allLines = readDatabase();
		
		// get a list of students
		List<String> studentLines = getStudentLines(allLines);
		
		// get a list of courses
		List<String> courseLines = getCourseLines(allLines);
		
		// convert the student instance to a string, and add it to the list of students
		studentLines.add(studentToDatabaseString(student));
		
		// re-initialize the List
		allLines = new ArrayList<String>();
		
		// for each course string, add it to the ArrayList to write
		for(String courseString : courseLines)
		{
			allLines.add(courseString);
		}
		
		// for each student string, add it to the ArrayList to write
		for(String studentString : studentLines)
		{
			allLines.add(studentString);
		}
		
		// write all the lines to the file
		writeDatabase(allLines);
	}
	
	/**
	 * Add a course to the file
	 * @param course the course instance to add
	 */
	public void addCourse(Course course)
	{
		// read the database
		List<String> allLines = readDatabase();
		
		// get a list of students
		List<String> studentLines = getStudentLines(allLines);
		
		// get a list of courses
		List<String> courseLines = getCourseLines(allLines);
		
		// convert the student instance to a string, and add it to the list of courses
		courseLines.add(courseToDatabaseString(course));
		
		// re-initialize the List
		allLines = new ArrayList<String>();
		
		// for each course string, add it to the ArrayList to write
		for(String courseString : courseLines)
		{
			allLines.add(courseString);
		}
		
		// for each student string, add it to the ArrayList to write
		for(String studentString : studentLines)
		{
			allLines.add(studentString);
		}
		
		// write all the lines to the file
		writeDatabase(allLines);
	}
	
	/**
	 * Remove a course
	 * @param course the course instance to remove
	 */
	public void removeCourse(Course course)
	{
		// read the database
		List<String> allLines = readDatabase();		

		// get a list of students
		List<String> studentLines = getStudentLines(allLines);
		
		// get a list of courses
		List<String> courseLines = getCourseLines(allLines);
		
		// re-initialize the List
		allLines = new ArrayList<String>();
		
		// iterate through each course string in the list
		for(String courseString : courseLines)
		{
			// if the course does not contain the given course id, add it to the list
			if(!courseString.contains(Integer.toString(course.getCourseId())))
			{
				allLines.add(courseString);
			}
		}
		
		// for each student string, add it to the ArrayList to write
		for(String studentString : studentLines)
		{
			allLines.add(studentString);
		}
		
		// write all the lines to the file
		writeDatabase(allLines);
	}
	
	/**
	 * Remove a student
	 * @param student the student instance to remove
	 */
	public void removeStudent(Student student)
	{
		// read the database
		List<String> allLines = readDatabase();

		// get a list of students
		List<String> studentLines = getStudentLines(allLines);
		
		// get a list of courses
		List<String> courseLines = getCourseLines(allLines);
		
		// re-initialize the List
		allLines = new ArrayList<String>();
		
		// for each student string, add it to the ArrayList to write
		for(String courseString : courseLines)
		{
			allLines.add(courseString);
		}
		
		// iterate through each student string in the list
		for(String studentString : studentLines)
		{
			// if the student does not contain the given student id, add it to the list
			if(!studentString.contains(Integer.toString(student.getStudentId())))
			{
				allLines.add(studentString);
			}
		}
		
		// write all the lines to the file
		writeDatabase(allLines);
	}
	
	/**
	 * Get the student lines from all the lines
	 * @param allLines all lines in the file database
	 */
	private List<String> getStudentLines(List<String> allLines)
	{
		// initialize a list
		List<String> studentLines = new ArrayList<String>();
		
		// for each line in the list of all the lines
		for(String currentLine : allLines)
		{
			// if the line indicates it is a student
			if(currentLine.contains(STUDENT_TEXT))
			{
				// add to the list to return
				studentLines.add(currentLine);
			}
		}
		
		// return the list
		return studentLines;
	}
	
	/**
	 * Get the course lines from all the lines
	 * @param allLines all lines in the file database
	 */
	private List<String> getCourseLines(List<String> allLines)
	{
		// initialize a list
		List<String> courseLines = new ArrayList<String>();
		
		// for each line in the list of all the lines
		for(String currentLine : allLines)
		{
			// if the line indicates it is a course
			if(currentLine.contains(COURSE_TEXT))
			{
				// add to the list to return
				courseLines.add(currentLine);
			}
		}
		
		// return the list
		return courseLines;
	}
	
	/**
	 * Write all course and student data to the database file
	 * @param databaseLines all lines in the database file
	 */
	private void writeDatabase(List<String> databaseLines)
	{
		try
		{
			// get a file reference and file writer references
			File file = new File(DatabasePath);
			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fileWriter);
			
			// for each line to write
			for(String currentLine : databaseLines)
			{
				// write the line and add a new line
				out.write(currentLine);
				out.newLine();
			}
			
			// close the writer
			out.close();
		}
		catch(IOException ex)
		{
			System.out.println(ex.toString());
		}
	}
	
	/**
	 * Read the lines from the database file
	 */
	private List<String> readDatabase()
	{
		List<String> lines = new ArrayList<String>();
		String currentLine;
		
		try
		{
			// get a file reference and file reader references
			File file = new File(DatabasePath);
			FileReader fileReader = new FileReader(file);
			BufferedReader in = new BufferedReader(fileReader);
			
			// while there are lines to read
			while((currentLine = in.readLine()) != null)
			{
				// add the line to the list
				lines.add(currentLine);
			}
			
			// close the reader
			in.close();
		}
		catch(IOException ex)
		{
			System.out.println(ex.toString());
		}
		
		// return the read lines
		return lines;
	}

	/**
	 * Convert a student instance to a string in the database file format
	 * @param student the student instance
	 */
	private String studentToDatabaseString(Student student)
	{
		// build the string with initially known data
		String databaseStudent = "STUDENT," + student.getStudentId() + "," + student.getFirstName() + 
				"," + student.getLastName() + "," + student.getPassword() + ",";
		
		// iterate through the student course list
		for(Integer courseNumber : student.getCourseList())
		{
			// add the course list id strings with a colon delimiter to the string
			databaseStudent += courseNumber.toString() + ":";
		}		
		
		// return the string without the trailing colon
		return databaseStudent.substring(0,databaseStudent.length() - 1);
	}
	
	/**
	 * Convert a course instance to a string in the database file format
	 * @param course the course instance
	 */
	private String courseToDatabaseString(Course course)
	{
		// create a date formatter
		DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		
		// format the date the course starts
		String fromDate = formatter.format(course.getDateFrom());
		
		// format the date the course ends
		String toDate = formatter.format(course.getDateTo());
		
		// build the string in the database file format
		String databaseCourse = "COURSE," + course.getCourseId() + "," 
				+ course.getCourseName() + "," + course.getCourseDesc() + "," +
				Integer.toString(course.getEnrolledCount()) + "," + Integer.toString(course.getEnrolledMax())
				+ "," + fromDate+ "," + toDate; 

		// return the string
		return databaseCourse;
	}
	
	/**
	 * Find the index of a given line with criteria to match
	 * @param lines the lines in the database file
	 * @param matchString the criteria to match
	 */
	private int findLineIndex(List<String> lines, String matchString)
	{
		// iterate through all lines
		for(int i = 0 ; i < lines.size(); i++)
		{
			// get the current line
			String currentLine = lines.get(i);
			
			// if the current line matches the criteria, return it
			if(currentLine.contains(matchString))
			{
				return i;
			}
		}

		// return -1 if not found
		return -1;
	}

	/**
	 * Convert a string to a course instance
	 * @param str the course data string
	 */
	private Course stringToCourse(String str)
	{
		try
		{
			// split the line in the file based upon commas
			String[] courseParams = str.trim().split(",");
			
			// get the course id
			int id = Integer.parseInt(courseParams[1]);
			
			// get the course name
			String name = courseParams[2];
			
			// get the course description
			String desc = courseParams[3];
			
			// get the number enrolled in the course
			int enrolled = Integer.parseInt(courseParams[4]);
			
			// get the maximum number that can enroll in the course
			int maxEnrolled = Integer.parseInt(courseParams[5]);
			
			// get the starting date
			String from = courseParams[6];
			
			// get the ending date
			String to = courseParams[7];
			
			// create a date formatter 	
			DateFormat df = new SimpleDateFormat(DATE_FORMAT);
			
			// convert the starting and ending dates for the course
			Date dFrom = (Date) df.parse(from);
			Date dTo = (Date) df.parse(to);
			
			// create a course instance based upon the acquired data
			Course theCourse = new Course(id, name, desc, enrolled, maxEnrolled, dFrom, dTo);
			
			// return the course
			return theCourse;  
		}
		catch(ParseException e)
		{
			return null;	
		}
    	}
    
	/**
	 * Convert a string to a student instance
	 * @param str the student data string
	 */
	private Student stringToStudent(String str)
	{
		// split the line in the file based upon commas
		String[] studentParams = str.trim().split(",");

		// get the course id
		int id = Integer.parseInt(studentParams[1]);
 
		// get the first name
		String firstName = studentParams[2];
 
		// get the last name 
		String lastName = studentParams[3];
 
		// get the password 
		String password = studentParams[4];
 
		// initialize a course list
		ArrayList<Integer> courseList = new ArrayList<Integer>();

		// check if they have any courses yet; they may not if they just registered
		// or dropped all their courses
		if(studentParams.length > 5)
		{
			// get the list of course ids the student is enrolled in
			String[] courseIdStrings = studentParams[5].trim().split(":");

			// for each course the student is enrolled in
			for(String courseId : courseIdStrings)
			{
				// add the course id to the list
				courseList.add(Integer.parseInt(courseId));
			}
		}

		// create a student instance based upon the acquired data
		Student theStudent = new Student(id, firstName, lastName, password, courseList);

		// return the student
		return theStudent;
  	}

	/**
	 * Get either course or student lines from the database file
	 * @param infoType a course or student indicator 
	 */
	private List<String> getLinesOfInterest(InformationType infoType)
	{
		// read the database
		List<String> allLines = readDatabase();

		// initialize a list for each line if applicable
		List<String> linesOfInterest = new ArrayList<String>();

		// for each line in the list
		for(String str : allLines)
		{
			// if interested in courses
			if(infoType == InformationType.Course)
			{
				// check to see if it is a course
				if(str.contains(COURSE_TEXT))
				{
					// add to the list to return
					linesOfInterest.add(str);
				}
			}
			else
			{
				// check to see if it is a student
				if(str.contains(STUDENT_TEXT))
				{
					// add to the list to return
					linesOfInterest.add(str);
				}
			}
		}

		// return the list
		return linesOfInterest;
	}
}