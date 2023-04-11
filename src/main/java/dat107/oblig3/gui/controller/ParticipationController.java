package dat107.oblig3.gui.controller;

import java.util.Collections;

import javax.swing.JOptionPane;

import dat107.oblig3.dao.EmployeeDAO;
import dat107.oblig3.entity.Employee;
import dat107.oblig3.entity.Project;
import dat107.oblig3.entity.ProjectParticipation;
import dat107.oblig3.gui.collection.ProjectParticipationList;
import dat107.oblig3.gui.screen.Screen;
import dat107.oblig3.gui.widget.ParticipationEditorWidget;
import dat107.oblig3.gui.widget.ParticipationsWidget;
import jakarta.persistence.EntityExistsException;

public class ParticipationController {
	
	private final Screen screen;
	private final ParticipationsWidget widget;
	private ProjectParticipation selected;
	
	public ParticipationController(Screen screen, ParticipationsWidget widget) {
		this.screen = screen;
		this.widget = widget;
	}
	
	public void setSelectedParticipation(ProjectParticipation participation) {
		this.selected = null;
	}
	
	ProjectParticipation getSelectedParticipation() {
		return selected;
	}
	
	public void deleteParticipation() {
		if(selected == null) {
			return;
		}
		
		Employee employee = selected.getEmployee();
		Project project = selected.getProject();
		
		EmployeeDAO dao = new EmployeeDAO();
		
		try {
			dao.removeEmployeeFromProject(employee.getId(), project.getId());
		} catch (IllegalArgumentException e) {
			handleException(e, "Employee has registered hours in the project.");
		} catch (Throwable e) {
			handleException(e, "Error occured deleting project participation");
		}
	}
	
	private void handleException(Throwable e, String message) {
		e.printStackTrace();
		screen.showErrorMessage(message);
	}
	
	public void editParticipation();
	
	void createNewParticipation();
	
	public void saveParticipation();
	
	default boolean fieldsAreValid() {
		
	}
	
	private void onCancel() {
		if(!editorWidget.isShowing()) {
			return;
		}
		
		removeField(editorWidget);
		
		screen.validate();
	}
	
	
	private void saveChanges() {
		Employee employee = (Employee) employee.getSelectedItem();
		Project project = (Project) project.getSelectedItem();
		String role = role.getText();
		int hours = hours.getInt();
		
		EmployeeDAO dao = new EmployeeDAO();
		
		boolean shouldSaveAsNew = false;
		
		try {
			dao.updateProjectParticipation(employee.getId(), project.getId(), role, hours);
		} catch (IllegalArgumentException e) {
			shouldSaveAsNew = askUserOnException(
					"Project partcicpation doesn't exist.",
					"Do you wish to save as new?");
		} catch (Throwable e) {
			e.printStackTrace();
			showErrorMessage("Unexpected error updating project participation.");
		}
		
		if(shouldSaveAsNew) {
			saveNewParticipation();
		}
	}
	
	/**
	 * Trys to save a new project participation with values from fields.
	 * Asks to update existing participation of participation exists.
	 */
	private void saveNewParticipation() {
		if(!employeeIsValid()) {
			showErrorMessage("No employee selected.");
			return;
		}
		if(!projectIsValid()) {
			showErrorMessage("No project selected.");
			return;
		}
		
		Employee employee = (Employee) employee.getSelectedItem();
		Project project = (Project) project.getSelectedItem();
		
		EmployeeDAO dao = new EmployeeDAO();
		
		String error = null;
		boolean shouldAskToUpdate = false;
		
		try {
			if(hoursIsValid() && roleIsValid()) {
				String role = role.getText();
				int hours = hours.getInt();
				
				dao.addEmployeeToProject(employee.getId(), project.getId(), role, hours);
			} else if(roleIsValid())  {
				String role = role.getText();
				
				dao.addEmployeeToProject(employee.getId(), project.getId(), role);
			} else if (hoursIsValid()) {
				int hours = hours.getInt();
				
				dao.addEmployeeToProject(employee.getId(), project.getId(), hours);	
			} else {
				dao.addEmployeeToProject(employee.getId(), project.getId());
			}
			
		} catch (EntityExistsException e) {
			e.printStackTrace();
			error = "Participation already exists.";
			shouldAskToUpdate = hoursIsValid();
		} catch (Throwable e) {
			e.printStackTrace();
			error = "Unexpected error updating project participation.";
		}
		
		if(error != null && shouldAskToUpdate) {
			askUserToUpdateExisting(error);
		}
		if(error != null & !shouldAskToUpdate) {
			showErrorMessage(error);
		}
	}
	
	private boolean employeeIsValid() {
		Employee employee = (Employee) employee.getSelectedItem();
		
		return employee != null;
	}
	
	private boolean projectIsValid() {
		Project project = (Project) project.getSelectedItem();
		
		return project != null;
	}
	
	private boolean roleIsValid() {
		return !role.getText().isBlank();
	}
	
	private boolean hoursIsValid() {
		try {
			hours.getInt();
		} catch(Exception e) {
			return false;
		}
		
		return true;
	}
	
	private void askUserToUpdateExisting(String error) {
		boolean doUpdate = askUserOnException(error, 
				"Do you wish to update the existing participation?");
		
		if(doUpdate) {
			saveChanges();
		}
	}
	
	private boolean askUserOnException(String error, String question) {
		int answer = JOptionPane.showConfirmDialog(screen, 
				error + "\n" + question, "Error saving project participation", 
				JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
		
		return answer == JOptionPane.YES_OPTION;
	}

	public Object cancelEditingParticipation() {
		// TODO Auto-generated method stub
		return null;
	}
}
