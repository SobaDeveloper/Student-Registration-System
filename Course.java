import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * X460.10/2 - Java Programming 1 - Team A 
 * Course.java
 * Purpose: Contains the course data
 * 
 * @author Chris Blackledge, Travis Cribbet, and Levi Hsiao
 * @version 1.0 3/3/14
*/

public class Course
{
	private final String DATE_FORMAT = "MM/dd/yyyy";
	
	private int courseId;
	private String courseName;
	private String courseDesc;
	private int enrolledCount;
	private int enrolledMax;
	private Date dateFrom;
	private Date dateTo;
	
	/**
	 * Constructor
	 * @param id the course id
	 * @param name the course name
	 * @param desc the course description
	 * @param currentlyEnrolled the number of currently enrolled students for the course
	 * @param maxEnrolled the max number of students that can enroll for the course
	 * @param from the date the course starts
	 * @param to the date the course ends
	 */
	public Course(int id, String name, String desc, int currentlyEnrolled, int maxEnrolled, Date from, Date to)
	{
		this.courseId = id;
		this.courseName= name;
		this.courseDesc = desc;
		this.enrolledCount = currentlyEnrolled;
		this.enrolledMax = maxEnrolled;
		this.dateFrom = from;
		this.dateTo = to;	
	}
	
	/**
	 * Get the Course Id
	 */
	public int getCourseId()
	{
		return courseId;
	}
	
	/**
	 * Set the Course Id
	 * @param courseId the course id
	 */
	public void setCourseId(int courseId)
	{
		this.courseId = courseId;
	}
	
	/**
	 * Get the Course Name
	 */
	public String getCourseName()
	{
		return courseName;
	}
	
	/**
	 * Set the Course Name
	 * @param courseName the course name
	 */
	public void setCourseName(String courseName)
	{
		this.courseName = courseName;
	}
	
	/**
	 * Get the Course Description
	 */
	public String getCourseDesc()
	{
		return courseDesc;
	}
	
	/**
	 * Set the Course Description
	 * @param courseDesc the course description
	 */
	public void setCourseDesc(String courseDesc)
	{
		this.courseDesc = courseDesc;
	}
	
	/**
	 * Get the number of students currently enrolled in the course
	 */
	public int getEnrolledCount()
	{
		return enrolledCount;
	}
	
	/**
	 * Set the Enrolled Count
	 * @param enrolledCount the number of students currently enrolled in this course
	 */
	public void setEnrolledCount(int enrolledCount)
	{
		this.enrolledCount = enrolledCount;
	}
	
	/**
	 * Get the maximum that can be enrolled
	 */
	public int getEnrolledMax()
	{
		return enrolledMax;
	}
	
	/**
	 * Set the maximum that can be enrolled
	 * @param enrolledMax the max that can be enrolled
	 */
	public void setEnrolledMax(int enrolledMax)
	{
		this.enrolledMax = enrolledMax;
	}
	
	/**
	 * Get the date the course begins
	 */
	public Date getDateFrom()
	{
		return dateFrom;
	}
	
	/**
	 * Set the date the course begins
	 * @param dateFrom the date the course begins
	 */
	public void setDateFrom(Date dateFrom)
	{
		this.dateFrom = dateFrom;
	}
	
	/**
	 * Set dateFrom to proper format
	 */
	public String dateFromToString()
	{
		String newString = new SimpleDateFormat("MM/dd/yyyy").format(dateFrom);
		return newString;
	}
	
	/**
	 * Get the date the course ends
	 */
	public Date getDateTo()
	{
		return dateTo;
	}
	
	/**
	 * Set the date the course ends
	 * @param dateTo the date the course ends
	 */
	public void setDateTo(Date dateTo)
	{
		this.dateTo = dateTo;
	}
	
	/**
	 * Set dateTo to proper format
	 */
	public String dateToToString()
	{
		String newString = new SimpleDateFormat(DATE_FORMAT).format(dateTo);
		return newString;
	}
		
	/**
	 * Returns course info
	 */
	public String courseToString()
	{
		 return("\nCourse ID: " + getCourseId() + "\n" +
				"Course Name: " + getCourseName() + "\n" +
				"Course Description: " + getCourseDesc() + "\n" +
				"Course Dates: " + dateFromToString() + " to " + dateToToString() + "\n" +
				"Students Enrolled: " + getEnrolledCount() + "/" + getEnrolledMax());
	}
}
