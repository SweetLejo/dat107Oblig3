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
		selected = participation;
		widget.hideParticipationEditorWidget();
		widget.showButtons();
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
	
	public void editParticipation() {
		widget.hideButtons();
		widget.getParticipationEditorWidget().enableEditingForExistingParticipation();
		widget.showParticipationEditorWidget();
	}
	
	public void createNewParticipation() {
		widget.hideButtons();
		widget.getParticipationEditorWidget().resetFieldsForNewParticipation();
	}
	
	public void saveParticipation() {
		if(selected != null) {
			saveExistingParticipation();
		} else {
			saveNewParticipation();
		}
	}
	
	private void saveExistingParticipation() {
		if(!validateFields()) {
			return;
		}
		
		ParticipationEditorWidget editorWidget = widget.getParticipationEditorWidget();
		
		Employee employee = editorWidget.getEmployee();
		Project project = editorWidget.getProject();
		String role = editorWidget.getRole();
		int hours = editorWidget.getHours();
		
		EmployeeDAO dao = new EmployeeDAO();
		
		boolean shouldSaveAsNew = false;
		
		try {
			dao.updateProjectParticipation(employee.getId(), project.getId(), role, hours);
		} catch (IllegalArgumentException e) {
			shouldSaveAsNew = askUserOnException(
					"Project partcicpation doesn't exist.",
					"Do you wish to save as new?");
			
			if(shouldSaveAsNew) {
				saveNewParticipation();
			}
			
		} catch (Throwable e) {
			e.printStackTrace();
			screen.showErrorMessage("Unexpected error updating project participation.");
		}
		
		
	}
	
	private boolean validateFields() {
		if(!employeeIsValid()) {
			screen.showErrorMessage("No employee selected.");
			return false;
		}
		
		if(!projectIsValid()) {
			screen.showErrorMessage("No project selected.");
			return false;
		}
		
		return true;
	}
	
	private boolean employeeIsValid() {
		return widget.getParticipationEditorWidget().getEmployee() != null;
	}
	
	private boolean projectIsValid() {
		return widget.getParticipationEditorWidget().getProject() != null;
	}
	
	private boolean roleIsValid() {
		return ! widget.getParticipationEditorWidget().getRole().isBlank();
	}
	
	private boolean hoursIsValid() {
		try {
			widget.getParticipationEditorWidget().getHours();
		} catch(Exception e) {
			return false;
		}
		
		return true;
	}
	
	private boolean askUserOnException(String error, String question) {
		int answer = JOptionPane.showConfirmDialog(screen, 
				error + "\n" + question, "Error saving project participation", 
				JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
		
		return answer == JOptionPane.YES_OPTION;
	}
	
	private void saveNewParticipation() {
		ParticipationEditorWidget editorWidget = widget.getParticipationEditorWidget();
		
 		EmployeeDAO dao = new EmployeeDAO();
		
		try {
			if(hoursIsValid() && roleIsValid()) {
				dao.addEmployeeToProject(
						editorWidget.getEmployee().getId(),
						editorWidget.getProject().getId(),
						editorWidget.getRole(), 
						editorWidget.getHours());
			} else if(roleIsValid())  {
				dao.addEmployeeToProject(
						editorWidget.getEmployee().getId(),
						editorWidget.getProject().getId(),
						editorWidget.getRole());
			} else if (hoursIsValid()) {
				dao.addEmployeeToProject(
						editorWidget.getEmployee().getId(),
						editorWidget.getProject().getId(),
						editorWidget.getHours());	
			} else {
				dao.addEmployeeToProject(
						editorWidget.getEmployee().getId(),
						editorWidget.getProject().getId());
			}
			
		} catch (EntityExistsException e) {
			e.printStackTrace();
			String error = "Participation already exists.";

			if(hoursIsValid()) {
				askUserToUpdateExisting(error);
			} else {
				screen.showErrorMessage(error);
			}
		
		} catch (Throwable e) {
			e.printStackTrace();
			screen.showErrorMessage("Unexpected error updating project participation.");
		}
	}
	
	private void askUserToUpdateExisting(String error) {
		boolean doUpdate = askUserOnException(error, 
				"Do you wish to update the existing participation?");
		
		if(doUpdate) {
			saveExistingParticipation();
		}
	}
	
	public void cancelEditingParticipation() {
		widget.hideParticipationEditorWidget();
		widget.showButtons();
	}
	
}
