import java.util.ArrayList;

/**
 * X460.10/2 - Java Programming 1 - Team A 
 * Student.java
 * Purpose: Contains the student data
 * 
 * @author Chris Blackledge, Travis Cribbet, and Levi Hsiao
 * @version 1.0 3/3/14
*/

public class Student
{
	private int studentId;
	private String firstName;
	private String lastName;
	private String password;
	private ArrayList<Integer> courseList;
	
	/**
	 * Constructor
	 */
	public Student()
	{
		courseList = new ArrayList<Integer>();
	}
	
	/**
	 * Constructor
	 * @param id the student id
	 * @param fName the student first name
	 * @param lName the student last description
	 * @param pWord the student password
	 * @param courseList the list of courses the student is currently enrolled in
	 */
	public Student(int id, String fName, String lName, String pWord, ArrayList<Integer> courseList)
	{
		this.studentId = id;
		this.firstName = fName;
		this.lastName = lName;
		this.password = pWord;
		this.courseList = courseList;		
	}
	
	/**
	 * Get the Student Id
	 */
	public int getStudentId()
	{
		return studentId;
	}

	/**
	 * Set the Student Id
	 * @param studentId the student id
	 */
	public void setStudentId(int studentId)
	{
		this.studentId = studentId;
	}

	/**
	 * Get the First Name
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * Set the First Name
	 * @param firstName the first name
	 */
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	/**
	 * Get the Last Name
	 */
	public String getLastName()
	{
		return lastName;
	}

	/**
	 * Set the Last Name
	 * @param lastName the last name
	 */
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	/**
	 * Get the Password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * Set the Password
	 * @param lastName the last name
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * Get the Course List
	 */
	public ArrayList<Integer> getCourseList()
	{
		return courseList;
	}

	/**
	 * Set the Course List - This is beneficial when all courses are known and
	 * not due to an individual course adding and removal
	 * 
	 * @param courseList the course list to set
	 */
	public void setCourseList(ArrayList<Integer> courseList)
	{
		this.courseList = courseList;
	}

	/**
	 * Add a course
	 * @param courseToAdd the course to add
	 */
	public void addCourse(Integer courseToAdd)
	{
		courseList.add(courseToAdd);
	}
	
	/**
	 * Remove a course
	 * @param courseToRemove the course to remove
	 */
	public void removeCourse(Integer courseToRemove)
	{
		courseList.remove(courseToRemove);
	}
	
	/**
	 * Return course numbers in proper format for appending to student info
	 */
	public String formatCourse()
	{
		StringBuilder sb = new StringBuilder();
		int count = 0;
		
		// iterate through the course list
		for(Integer courseId : courseList)
		{
			// increment the count
			count++;
			
			// determine if a colon delimiter is needed between the courses;
			// if the last course to add, then the colon is not needed
			if(courseList.size() - count == 0)
			{
				sb.append (Integer.toString(courseId));
			}
			else
			{
				sb.append (Integer.toString(courseId) + ":");
			}
		}
		
		// return the formatted course string
		return sb.toString();
	}
	
	/**
	 * Return student info in proper format for writing to file
	 */
	public String formatStudent()
	{
		return "STUDENT," + studentId + "," + firstName + "," + lastName + "," + password +
				"," + formatCourse();
	}
}
