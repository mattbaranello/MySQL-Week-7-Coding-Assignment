package projects;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import projects.entity.Project;
import projects.exception.DbException;
import projects.service.ProjectService;

public class Projects {
		
		
		private Scanner scanner = new Scanner(System.in);
		private ProjectService projectService = new ProjectService();
		private Project curProject;
		/*
		 * Creates a List called "operations". Included in the list is "1) Add a project". This requires user to input "1" to 
		 * start the program
		 */
		// @formatter:off
		private List<String> operations = List.of(
				"1) Add a project",
				"2) List projects",
				"3) Select a project"
		);
		// @formatter:on
		
			

	public static void main(String[] args) {
		new Projects().processUserSelections();
	}
		
	//This method displays menu selections, gets a selection from the user, then acts on the selection
	private void processUserSelections() {
		boolean done = false;
		/*
		 * While loop continues program until it is done, which will then call the "exitMenu" method.
		 * By default, if the user does not enter a valid selection, the program will print out 
		 * "\n" + selection + " is not a valid selection. Try again."
		 */
		
		while(!done) {
			try {
				int selection = getUserSelection();
				
				switch(selection) {
					case -1:
						done = exitMenu();
						break;
					case 1:
						createProject();
						break;
					case 2:
						listProjects();
						break;
					case 3:
						selectProject();
						break;
						
				default:
					System.out.println("\n" + selection + " is not a valid selection. Try again.");
					break;
				}
					
			}
			catch(Exception e) {
				System.out.println("\nError: " + e + " Try again."); 
			}
		}
	}
	//This method calls the list of projects and allows us to select projects via project ID
	private void selectProject() {
		listProjects();
		Integer projectId = getIntInput("Enter a project ID to select a project");
		//unselects current project
		curProject = null;
		//Will throw an exception if current project ID is invalid
		curProject = projectService.fetchProjectById(projectId);
		
	}

	//Creates a List of Project named "projects" which will fetch all projects and print their project ID and name.
	private void listProjects() {
		List<Project> projects = projectService.fetchAllProjects();
		
		System.out.println("\nProjects: ");
		
		projects.forEach(project -> System.out.println("   " + project.getProjectId()
				+ ": " + project.getProjectName()));
	}

	//Creates the project and its contents. It will set the parameters and prompts for the user to input.
	private void createProject() {
		String projectName = getStringInput("Enter the project name");
		BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours");
		BigDecimal actualHours = getDecimalInput("Enter the actual hours");
		Integer difficulty = getIntInput("Enter the project difficulty (1-5)");
		String notes = getStringInput("Enter the project notes");
		
		Project project = new Project();
		
		project.setProjectName(projectName);
		project.setEstimatedHours(estimatedHours);
		project.setActualHours(actualHours);
		project.setDifficulty(difficulty);
		project.setNotes(notes);
		
		Project dbProject = projectService.addProject(project);
		System.out.println("You have successfully created project: " + dbProject);
		
		curProject = projectService.fetchProjectById(dbProject.getProjectId());

	}
	
	/*
	 * Defines a method called "getDecimalInput" that returns a BigDecimal object from a String prompt.
	 * Input will return null if no valid decimal input was given.
	 * If a non-null string value is entered in, the method will convert that value into a BigDecimal object of two decimal places "(setScale)".
	 * An exception will be thrown if the value format cannot be converted to a BigDecimal object.
	 */
	private BigDecimal getDecimalInput(String prompt) {
		String input = getStringInput(prompt);
		
		if(Objects.isNull(input)) {
			return null;
		}
		try {
			return new BigDecimal(input).setScale(2);
		}
		catch(NumberFormatException e) {
			throw new DbException(input + " is not a valid decimal number.");
		}
	}

	//Returns "Exiting the menu" message if the user presses the "Enter" key.
	private boolean exitMenu() {
		System.out.println("Exiting the menu.");
		return true;
	}


	private int getUserSelection() {
		printOperations();
		
		Integer input = getIntInput("\nEnter a menu selection");
		return Objects.isNull(input) ? -1 : input;
	}

	//Prompts the user input as an integer. Throws an exception if the user enters in an invalid number
	private Integer getIntInput(String prompt) {
		String input = getStringInput(prompt);
		
		if(Objects.isNull(input)) {
			return null;
		}
		try {
			return Integer.valueOf(input);
		}
		catch(NumberFormatException e) {
			throw new DbException(input + " is not a valid number.");
		}
	}

	/*
	 * Prints the prompt as a String and gets input from the user. This is the lowest level input method.
	 * Other method input methods such as "getIntInput" call this method and convert it to their respected data type.
	 */
	private String getStringInput(String prompt) {
		System.out.print(prompt + ": ");
		
		String input = scanner.nextLine();
		
		return input.isBlank() ? null : input;
	}


	private void printOperations() {
		System.out.println("\nThese are the available selections. Press the Enter key to quit:");
		
		operations.forEach(line -> System.out.println("   " + line));
		
		if(Objects.isNull(curProject)) {
			System.out.println("\nYou are not working with a project.");
		} else {
			System.out.println("\nYou are working in project: " + curProject);
		}
	}
}
