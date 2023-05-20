package projects.service;

import java.util.List;
import java.util.NoSuchElementException;
import projects.dao.ProjectDao;
import projects.entity.Project;
import projects.exception.DbException;

public class ProjectService {
	
	private ProjectDao projectDao = new ProjectDao();

	public Project addProject(Project project) {
		//Calls the method "insertProject" on the "projectDao" method
		return projectDao.insertProject(project);
	}
	//Calls the fetchAllProjects() method on the projectDao object. Returns a List of Project
	public List<Project> fetchAllProjects() {
		return projectDao.fetchAllProjects();
	}
	/*
	 *Fetches a project that corresponds to a projectID entered by the user. 
	 *If the project Id does not exist, it will throw an error message.
	 */
	public Project fetchProjectById(Integer projectId) {
		return projectDao.fetchProjectById(projectId).orElseThrow(() -> new NoSuchElementException(
				"Project with project ID=" + projectId + " does not exist"));			
	}
	/*
	 *modifies project details that corresponds to a projectID entered by the user. 
	 *If the project Id does not exist, it will throw an error message.
	 */
	public void modifyProjectDetails(Project project) {
		if(!projectDao.modifyProjectDetails(project)) {
			throw new DbException("Project with ID=" + project.getProjectId() + " does not exist");
		}
	}
	
	/*
	 *deletes a project that corresponds to a projectID entered by the user. 
	 *If the project Id does not exist, it will throw an error message.
	 */
	public void deleteProject(Integer projectId) {
		if(!projectDao.deleteProject(projectId)) {
			throw new DbException("Project with ID=" + projectId + " does not exist.");
		}
	}	

}
