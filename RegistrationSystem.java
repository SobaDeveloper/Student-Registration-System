import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 * X460.10/2 - Java Programming 1 - Team A 
 * RegistrationSystem.java
 * Purpose: Contains the registration system functionality
 * 
 * @author Chris Blackledge, Travis Cribbet, and Levi Hsiao
 * @version 1.0 3/3/14
*/

public class RegistrationSystem
{
	// constants
	private final String MENU_SELECTION_ERROR = "Error reading your menu selection; Please select an option and press ENTER: ";
	
	// class variables
	private int loggedInStudentId = 0;
	private DatabaseHelper fileHelper = new DatabaseHelper("RegistrationSystemFile.txt");
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private HashMap<Integer, Student> studentHashMap = fileHelper.getStudents();
	
	/**
	 * Constructor
	 */
	public RegistrationSystem()
	{
	}
		
	/**
	 * Display the introductory welcome message
	 */
	public void displayIntroMessage()
	{
		System.out.println("***********************************************************************");
		System.out.println("********** Welcome to the Team A Student Registration System **********");
		System.out.println("***********************************************************************");
		System.out.println("");
	}
	
	/**
	 * Display the exit message
	 */
	public void displayExitMessage()
	{
		System.out.println("");
		System.out.println("********************************************************************************");
		System.out.println("********** Thank you for using the Team A Student Registration System **********");
		System.out.println("********************************************************************************");
		
		// exit the system
		System.exit(0);
	}
	
	/**
	 * Display the student introductory menu
	 * @return returns the result
	 */
	public int handleStudentIntroMenu()
	{
		System.out.println("Please select from the following menu...");
		System.out.println("1. Login to Account");
		System.out.println("2. Register New Account");
		System.out.println("3. Exit");
		System.out.print("Please select an option and press ENTER: ");

		return getMenuSelection(1, 3);
	}
	
	/**
	 * Display the student registration menu
	 * @return returns the result
	 */
	public int handleStudentRegistrationMenu()
	{
		System.out.println("Please select from the following menu...");
		System.out.println("1. View All Courses");
		System.out.println("2. View Registered Courses");
		System.out.println("3. Add a Course");
		System.out.println("4. Drop a Course");
		System.out.println("5. Logout");
		System.out.print("Please select an option and press ENTER: ");

		return getMenuSelection(1, 5);
	}
	
	/**
	 * Log in to use the registration system
	 * @param studentId the student id
	 * @param passwordStr the student password
	 */
	private boolean login(String studentId, String passwordStr)
	{
		boolean returnVal = false;
		
		try
		{
			// convert the string to an int
			int studentIdInt = Integer.parseInt(studentId);
			
			// determine if the student id is contained in the map
			if(studentHashMap.containsKey(studentIdInt))
			{
				// create a temp student object without having to iterate
				Student tempStudent = studentHashMap.get(studentIdInt);
				
				// compare student data with user-entered login data
				if((tempStudent.getStudentId() == studentIdInt) && (tempStudent.getPassword().equals(passwordStr)))
				{
					// set the logged in student id
					loggedInStudentId = studentIdInt;
				
					// set the return value
					returnVal = true;
				}
			}
		}
		catch (NumberFormatException nfe)
		{
			// don't display a message here...  error messages will be displayed via the 
			// handleLoginMenu method
		}
		
		return returnVal;
	}
	
	/**
	 * Display the student login menu
	 * @return returns the result
	 */
	public boolean handleLoginMenu()
	{
		String studentIdStr = "";
		String passwordStr = "";
		boolean validLogin = false;
		int failureCounter = 0;
		
		while(!validLogin)
		{
			System.out.println("Please enter your Student Id... ");
		
			try
			{
				studentIdStr = br.readLine();
			}
			catch (IOException e)
			{
				System.out.println("Failed to read the student id.");
			}
			
			System.out.println("Please enter your Password... ");
			
			try
			{
				passwordStr = br.readLine();
			}
			catch (IOException e)
			{
				System.out.println("Failed to read the password.");
			}
			
			// determine if valid login and password data was provided
			validLogin = login(studentIdStr, passwordStr);
			
			// increment the failure counter if an invalid login
			if(!validLogin)
			{
				failureCounter++;
				
				// check the counter to see
				if(failureCounter == 3)
				{
					System.out.println("Too many invalid logins...  Exiting...");
					displayExitMessage();
				}
				else
				{
					System.out.println("Invalid Student ID or Password; Please try again...");
				}
			}
		}
		
		// return if a valid login or not
		return validLogin;
	}
   
	/**
	 * Handles new student registration
	 */
	public void handleNewAccount()
	{
		String firstName = "";
		String lastName = "";
		String password = "";
		Student newStudent = new Student();
		
		System.out.print("Please enter your first name: ");
		try
		{
			// prompt the user to enter their first name until it is not blank
			while((firstName = br.readLine()).isEmpty())
			{
				System.out.println("This field cannot be blank.");
				System.out.print("Please enter your first name: ");
			}
			
			// set their first name
			newStudent.setFirstName(firstName);
		}
		catch (IOException e)
		{
			System.out.println("Failed to read the first name.");
		}
			
		System.out.print("Please enter your last name: ");
		try
		{
			// prompt the user to enter their last name until it is not blank
			while((lastName = br.readLine()).isEmpty())
			{
				System.out.println("This field cannot be blank.");
				System.out.print("Please enter your last name: ");
			}
			
			// set their last name
			newStudent.setLastName(lastName);
		}
		catch (IOException e)
		{
			System.out.println("Failed to read the last name.");
		}
		
		System.out.print("Please enter a password: ");
		try
		{
			// prompt the user to enter their password until it is not blank
			while((password = br.readLine()).isEmpty())
			{
				System.out.println("This field cannot be blank.");
				System.out.print("Please enter a password: ");
			}
			
			// set their password
			newStudent.setPassword(password);
		}
		catch (IOException e)
		{
			System.out.println("Failed to read the password.");
		}
		
		// generate new student id
		int max = 0;
		for(Integer key : studentHashMap.keySet())
		{
			// get the student id to determine the max value
			int temp = studentHashMap.get(key).getStudentId();
			if (temp > max)
			{
				max = temp;
			}
		}
		
		// create the new student id
		int newId = max + 1;
		
		// set the student id
		newStudent.setStudentId(newId);
		
		// update the logged in variable
		loggedInStudentId = newId;
		
		// insert the student id into the map
		studentHashMap.put(newId, newStudent);
		
		// output confirmation message
		System.out.println("\nThank you for registering, " + firstName + " " + lastName + "!"
				+ "\nYour Student Id is: " + newId +"\n");
	
		// add the student to the database file
		fileHelper.addStudent(newStudent);
	}
   
	/**
	 * Handle the drop course menu
	 */
	public void handleDropCourseMenu()
	{
		// get a map of students
		HashMap<Integer, Student> studentMap = fileHelper.getStudents();

		// create a temporary course option choice map
		HashMap<Integer, Integer> tempDropCourseHashMap = new HashMap<Integer, Integer>();
		
		// if the logged in student is a valid student
		if(studentMap.containsKey(loggedInStudentId))
		{
			// get the student from the map
			Student student = studentMap.get(loggedInStudentId);
			
			// get the student's courses
			ArrayList<Integer> studentCourseList = student.getCourseList();
			
			// get all courses initially
			Map<Integer, Course> unsortedCourseMap = fileHelper.getCourses();
			
			// convert the map to a list of courses
			Set<Entry<Integer,Course>> set = unsortedCourseMap.entrySet();
			List<Entry<Integer,Course>> list = new ArrayList<Entry<Integer,Course>>(set);
			
			// sort the courses
			Collections.sort(list, new Comparator<Map.Entry<Integer,Course>>()
			{
				public int compare (Map.Entry<Integer,Course>m1, Map.Entry<Integer,Course>m2)
				{
					return (m1.getValue().getCourseName()).compareTo(m2.getValue().getCourseName());
				}
			});
			
			// if they are taking courses
			if(studentCourseList.size() != 0)
			{
				System.out.println("\nPlease select from the following menu which course you would like to drop...");
		        
				// iterate through the list of sorted courses
				for(Map.Entry<Integer,Course>entry : list)
				{
					// get the course instance
					Course tempCourse = entry.getValue();
					
					// determine if the student has a given course id; if so, output it as an option;
					// this allows a student to only drop a course they are already taking
					if(studentCourseList.contains(tempCourse.getCourseId()))
					{
						// output the menu option
						System.out.println((tempDropCourseHashMap.size() + 1) + ". " + tempCourse.getCourseName());
						
						// keep track of the options
						tempDropCourseHashMap.put((tempDropCourseHashMap.size() + 1), tempCourse.getCourseId());
					}
				}
				
				// finish outputting the menu
				System.out.print("Please select an option and press ENTER: ");
                         
				// get the menu selection
				int menuSelection = getMenuSelection(1, tempDropCourseHashMap.size());

				// get the selected course
				Course selectedCourse = fileHelper.getCourse((Integer)tempDropCourseHashMap.values().toArray()[menuSelection - 1]);

				// update the enrollment count
				int enrollmentCount = selectedCourse.getEnrolledCount();
		        
				// if the course has students enrolled, reduce the count
				if(enrollmentCount > 0)
				{
					enrollmentCount--;
				}
		        
				// update the enrolled count
				selectedCourse.setEnrolledCount(enrollmentCount);

				// initialize an empty array list
				ArrayList<Integer> newStudentCourseList = new ArrayList<Integer>();
		        
                // update the student's course list
				for(Integer courseId : studentCourseList)
				{
					// only add the course ids to the up
					if(courseId != selectedCourse.getCourseId())
					{
						newStudentCourseList.add(courseId);
					}
				}
				
				// update the student course list
				student.setCourseList(newStudentCourseList);

				// save off the new student and course to text file
				fileHelper.modifyStudent(student);
				fileHelper.modifyCourse(selectedCourse);	
				System.out.println("\n******** The course " + selectedCourse.getCourseName() + " has been dropped! ********\n");
			}
			else
			{
				System.out.println("\nYou don't have any courses to drop!\n");
			}
		}
	}
	
	/**
	 * Display the add course menu
	 * @return returns the result
	 */
	public void handleAddCourseMenu()
	{
		// create a temporary student hashmap
		HashMap<Integer, Student> tempStudentHashMap = fileHelper.getStudents();
		
		// create a temporary course option choice map
		HashMap<Integer, Integer> tempAddCourseHashMap = new HashMap<Integer, Integer>();
		
		// determine if the student id is contained in the map
		if(tempStudentHashMap.containsKey(loggedInStudentId))
		{
			// create a temp student object
			Student tempStudent = tempStudentHashMap.get(loggedInStudentId);
				
			// get their course list
			ArrayList<Integer> tempCourseList = tempStudent.getCourseList();
			
			// get all courses initially
			Map<Integer, Course> unsortedCourseMap = fileHelper.getCourses();
			
			// convert the map to a list of courses
			Set<Entry<Integer,Course>> set = unsortedCourseMap.entrySet();
			List<Entry<Integer,Course>> list = new ArrayList<Entry<Integer,Course>>(set);
			
			// sort the courses
			Collections.sort(list, new Comparator<Map.Entry<Integer,Course>>()
			{
				public int compare (Map.Entry<Integer,Course>m1, Map.Entry<Integer,Course>m2)
				{
					return (m1.getValue().getCourseName()).compareTo(m2.getValue().getCourseName());
				}
			});

			// if the student has courses that can be added
			if(tempCourseList.size() != list.size())
			{
				// start outputting the menu
				System.out.println("\nPlease select from the following menu which course you would like to add...");

				// iterate through the list of sorted courses
				for(Map.Entry<Integer,Course>entry : list)
				{
					// get the course instance
					Course tempCourse = entry.getValue();
					
					// determine if the student has a given course id; if not, output it as an option;
					// this prevents a student for adding a course they are already taking
					if(!tempCourseList.contains(tempCourse.getCourseId()))
					{
						// output the menu option
						System.out.println((tempAddCourseHashMap.size() + 1) + ". " + tempCourse.getCourseName());
						
						// keep track of the options
						tempAddCourseHashMap.put((tempAddCourseHashMap.size() + 1), tempCourse.getCourseId());
					}
				}
				
				// finish outputting the menu
				System.out.print("Please select an option and press ENTER: ");

				// get the menu selection
				int menuSelection = getMenuSelection(1, tempAddCourseHashMap.size());

				// get the selected course
				Course selectedCourse = fileHelper.getCourse((Integer)tempAddCourseHashMap.values().toArray()[menuSelection - 1]);

				// get the number enrolled in the course
				int enrolledCount = selectedCourse.getEnrolledCount();

				// update the count now that we have a new enrollment
				enrolledCount++;
		        
				// check if the enrollment has exceed the max that can be enrolled
				if(enrolledCount > selectedCourse.getEnrolledMax())
				{
					// too many enrolled...  let the student know
					System.out.println("\nSorry, max enrollment has been reached!\n");
					return;
				}
				else
				{
					// update the enrollment count
					selectedCourse.setEnrolledCount(enrolledCount);
		        	
					// add a course to the students course list
					tempStudent.addCourse(new Integer(selectedCourse.getCourseId()));
		        	
					// save off the new student and course to text file
					fileHelper.modifyStudent(tempStudent);
					fileHelper.modifyCourse(selectedCourse);	
					System.out.println("\n******** The course " + selectedCourse.getCourseName() + " has been added! ********\n");
				}
			}
			else
			{
				System.out.println("\nYou can't add anymore courses!\n");
			}					
		}
	}
	
	/**
	 * Get a valid menu selection for any menu displayed to the user
	 * @param minMenuOption the minimum valid menu option
	 * @param maxMenuOption the maximum valid menu option
	 */
	public int getMenuSelection(int minMenuOption, int maxMenuOption)
	{
		Integer menuChoice = 0;
		
		while (menuChoice == 0)
		{
			try
			{
				// read the menu choice entered by the student
				menuChoice = Integer.parseInt(br.readLine());
			
				// it is a number provided, but ensure it is in the valid range
				if((menuChoice < minMenuOption) || (menuChoice > maxMenuOption))
				{
					System.out.print(MENU_SELECTION_ERROR);
					menuChoice = 0;
				}
			}
			catch (IOException ioe)
			{
				System.out.print(MENU_SELECTION_ERROR);
				menuChoice = 0;
			}
			catch (NumberFormatException nfe)
			{
				System.out.print(MENU_SELECTION_ERROR);
				menuChoice = 0;
			}
		}
		
		// return the menu option selected
		return menuChoice;
	}
	
	/**
	 * Display all the registered courses by the logged in student
	 */
	public void showRegisteredCourses()
	{
		// create a temporary student hashmap to not get it every time
		HashMap<Integer, Student> tempStudentHashMap = fileHelper.getStudents();
		
		// create a temporary hashmap to not get it every time
		HashMap<Integer, Course> tempCourseHashMap = fileHelper.getCourses();
		
		// determine if the student id is contained in the map
		if(tempStudentHashMap.containsKey(loggedInStudentId))
		{
			// create a temp student object without having to iterate
			Student tempStudent = tempStudentHashMap.get(loggedInStudentId);
			
			// get their course list
			ArrayList<Integer> tempCourseList = tempStudent.getCourseList();
			
			// sort courses by alphabetical order
			Collections.sort(tempCourseList, new Comparator<Integer>()
			{
				@Override
				public int compare (Integer i1, Integer i2)
				{
					HashMap<Integer,Course> temp = fileHelper.getCourses();
					return temp.get(i1).getCourseName().compareTo(temp.get(i2).getCourseName());
				}
			});
			
			System.out.println("Here are your registered courses...");
			
			// iterate through their course list
			for (Integer courseId : tempCourseList)
			{
				// determine if the course id is contained in the map
				if(tempCourseHashMap.containsKey(courseId))
				{
					// output the course data
					System.out.println(tempCourseHashMap.get(courseId).courseToString());
				}
			}
		}
		
		System.out.println("");
	}
	
	/**
	 * Display all the courses available
	 */
	public void showAllCourses()
	{
		// create temporary hashmap of courses
		HashMap<Integer,Course> tempCourseHashMap = fileHelper.getCourses();

		//Sort courses according to alphabetical order
		sortCourses(tempCourseHashMap);
		       
		System.out.println("");		
	}
	
	/**
	 * Sort courses in alphabetical order
	 * @param map map of courses to sort
	 */
	public void sortCourses(Map<Integer,Course> map)
	{
		// convert the map to a list of courses
		Set<Entry<Integer,Course>> set = map.entrySet();
		List<Entry<Integer,Course>> list = new ArrayList<Entry<Integer,Course>>(set);
		
		// sort the courses
		Collections.sort(list, new Comparator<Map.Entry<Integer,Course>>()
		{
			public int compare (Map.Entry<Integer,Course>m1, Map.Entry<Integer,Course>m2)
			{
				return (m1.getValue().getCourseName()).compareTo(m2.getValue().getCourseName());
			}
		});
		
		// now that the list is sorted, output each course
		for(Map.Entry<Integer,Course>entry : list)
		{
			System.out.println(entry.getValue().courseToString());
		}
	}
	
	/**
	 * Logs out the currently logged in user
	 */
	public void logout()
	{
		// reset the logged in variable
		loggedInStudentId = 0;
		
		System.out.println("You have now been logged out.");
	}
}