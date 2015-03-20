import java.io.IOException;

/**
 * X460.10/2 - Java Programming 1 - Team A 
 * Main.java
 * Purpose: Main executable to run the team assignment
 * 
 * @author Chris Blackledge, Travis Cribbet, and Levi Hsiao
 * @version 1.0 3/6/14
 */

public class Main {

	static RegistrationSystem mRegSystem = new RegistrationSystem();
	
	// constants for the registration menu
	private final static int VIEW_ALL_COURSES = 1;
	private final static int VIEW_REGISTERED_COURSES = 2;
	private final static int ADD_COURSE = 3;
	private final static int DROP_COURSE = 4;
	private final static int LOG_OUT = 5;
	
	// constants for the introduction menu
	private final static int LOG_IN = 1;
	private final static int REGISTER_NEW_STUDENT = 2;
	
	/**
	 * Initial method of execution
	 * @param args Optional string arguments to pass in when executed
	 * @throws IOException 
	 */
	public static void main(String[] args)
	{	
		// display intro message
		mRegSystem.displayIntroMessage();
		
		// display the initial intro menu
		displayIntroMenu();
	}
	
	/**
	 * Display the registration menu
	 */
	public static void displayRegistrationMenu()
	{
		int intResult = 0;
		
		// display student registration menu and get the result
		intResult = mRegSystem.handleStudentRegistrationMenu();
		
		// determine what to do based upon the valid menu selection
		if(intResult == VIEW_ALL_COURSES)
		{
			// view all courses
			mRegSystem.showAllCourses();
			
			// call this method to re-display the menu
			displayRegistrationMenu();
		}
		else if(intResult == VIEW_REGISTERED_COURSES)
		{
			// view registered courses
			mRegSystem.showRegisteredCourses();
			
			// call this method to re-display the menu
			displayRegistrationMenu();
		}
		else if(intResult == ADD_COURSE)
		{
			// add a course
			mRegSystem.handleAddCourseMenu();
			
			// call this method to re-display the menu
			displayRegistrationMenu();
		}
		else if(intResult == DROP_COURSE)
		{
			// drop a course
			mRegSystem.handleDropCourseMenu();

			// call this method to re-display the menu
			displayRegistrationMenu();
		}
		else if(intResult == LOG_OUT)
		{
			// logout
			mRegSystem.logout();
			
			// call this method to re-display the menu
			displayIntroMenu();
		}
	}
	
	/**
	 * Display the introduction menu
	 */
	public static void displayIntroMenu()
	{
		int intResult = 0;
		boolean boolResult = false;
		
		// display student introduction menu and get the result
		intResult = mRegSystem.handleStudentIntroMenu();
		
		// determine what to do based upon the valid menu selection
		if(intResult == LOG_IN)
		{
			// existing student; login
			boolResult = mRegSystem.handleLoginMenu();

			// if true, a valid log in occurred
			if(boolResult)
			{
				// call this method to re-display the menu
				displayRegistrationMenu();
			}
		}
		else if(intResult == REGISTER_NEW_STUDENT)
		{
			// new student; register them
			mRegSystem.handleNewAccount();
			
			// call this method to re-display the menu
			displayRegistrationMenu();
		}
		else
		{
			// display the exit message and quit
			mRegSystem.displayExitMessage();
		}
	}
}
