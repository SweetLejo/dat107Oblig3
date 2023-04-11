package dat107.oblig3.gui.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.swing.JOptionPane;

import dat107.oblig3.dao.EmployeeDAO;
import dat107.oblig3.entity.Department;
import dat107.oblig3.entity.Employee;
import dat107.oblig3.gui.collection.EntityCollection;
import dat107.oblig3.gui.screen.EmployeesScreen;
import dat107.oblig3.gui.widget.EmployeeEditorWidget;
import dat107.oblig3.gui.widget.ParticipationsWidget;
import dat107.oblig3.gui.widget.Widget;

public class EmployeeController {

    private final EmployeesScreen screen;
    private final EmployeeDAO dao;
    private Employee selected;

    public EmployeeController(EmployeesScreen screen) {
        this.screen = screen;
        this.dao = new EmployeeDAO();
    }

    public List<Employee> getAllEmployees() {
        return dao.getAll();
    }

    public List<Employee> searchAny(String search) {
        return dao.search(search);
    }

    public List<Employee> searchById(String search) {
    	Optional<Employee> result = Optional.empty();
    	
        try {
           result = dao.get(Integer.parseInt(search));
        } catch (NumberFormatException e) {
        	screen.showNoResultsDialog("ID must be a number");
        }  
      
        return result.map(Collections::singletonList).orElse(Collections.emptyList());
    }

    public List<Employee> searchByUsername(String search) {
        Optional<Employee> result = dao.getByUsername(search);
        
        return result.map(Collections::singletonList).orElse(Collections.emptyList());
    }	
    
	public void setSelectedEmployee(Employee employee) {
		selected = employee;
		screen.hideWidgets();
	}
	
	public Employee getSelectedEmployee() {
		return selected;
	}
	
	public void viewProjects() {
		Widget projectsWidget = screen.getProjectParticipationsWidget();
		
		if(!projectsWidget.isShowing()) {
			screen.showProjectParticipationsWidget();
		} else {
			screen.hideProjectParticipationsWidget();
		}
	}
	
	public void deleteEmployee() {
		try {
			dao.delete(selected.getId());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(screen, e.getMessage());
		} catch (Throwable e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(screen, "Error occured deleting employee.");
		}
		
		screen.refresh();
	}
	
	public void editEmployee() {
		if(selected == null) {
			return;
		}
		
		EmployeeEditorWidget editorWidget = screen.getEmployeeEditorWidget();
		
		editorWidget.populateAllFields(
				selected.getId(), 
				selected.getUsername(),
				selected.getFirstName(),
				selected.getLastName(), 
				selected.getEmploymentDate(), 
				selected.getPosition(), 
				selected.getMonthlySalary(), 
				selected.getDepartment());
		editorWidget.enableEditingForExistingEmployee();
		editorWidget.showSaveAndCancelButtons();
		
		screen.showEmployeeEditorWidget();
	}
	
	public void createNewEmployee() {
		screen.getCollectionComponent().clearSelection();
		
		EmployeeEditorWidget editorWidget = screen.getEmployeeEditorWidget();
		
		editorWidget.resetFieldsForNewEmployee();
		editorWidget.showSaveAndCancelButtons();
		
		screen.showEmployeeEditorWidget();	
	}
	
	public void saveEmployee() {
		if(selected != null) {
			saveExistingEmployee();
		} else {
			saveNewEmployee();
		}
		
		screen.hideEmployeeEditorWidget();
		screen.refresh();
	}
	
	private void saveExistingEmployee() {	
		if(salaryHasChanged()) {
			saveSalaryForExistingEmployee();
		}
		
		if(positionHasChanged()) {
			savePositionForExistingEmployee();
		}
		
		if(departmentHasChanged()) {
			saveDepartmentForExistingEmployee();
		}
	}
	
	private boolean salaryHasChanged() {
		return selected.getMonthlySalary() != screen.getEmployeeEditorWidget().getSalary();
	}
	
	private void saveSalaryForExistingEmployee() {
		try {
			dao.updateSalary(selected.getId(), screen.getEmployeeEditorWidget().getSalary());
		} catch(Throwable e) {
			handleException(e, "Error occured while updating salary.");
		}
	}
	
	private boolean positionHasChanged() {
		return !(selected.getPosition().equals(screen.getEmployeeEditorWidget().getPosition()));
	}
	
	private void savePositionForExistingEmployee() {
		try {
			dao.updatePosition(selected.getId(), screen.getEmployeeEditorWidget().getPosition());
		} catch(Throwable e) {
			handleException(e, "Error occured while updating position.");
		}
	}
	
	private boolean departmentHasChanged() {
		return !(selected.getDepartment().equals(screen.getEmployeeEditorWidget().getDepartment()));
	}
	
	private void saveDepartmentForExistingEmployee() {
		try {
			dao.updateDepartment(selected.getId(), screen.getEmployeeEditorWidget().getDepartment());
		} catch(Throwable e) {
			handleException(e, "Error occured while updating department.");
		}
	}
	
	private void saveNewEmployee() {
		EmployeeEditorWidget editorWidget = screen.getEmployeeEditorWidget();
		
		try {
			 dao.saveNewEmployee(
					 editorWidget.getUsername(),
					 editorWidget.getFirstName(),
					 editorWidget.getLastName(), 
					 editorWidget.getEmploymentDate(),
					 editorWidget.getPosition(),
					 editorWidget.getSalary(),
					 editorWidget.getDepartment());
		} catch(Throwable e) {
			handleException(e, "Error occured while saving new employee.");
		}
	}
	
	private void handleException(Throwable e, String message) {
		e.printStackTrace();
		JOptionPane.showMessageDialog(screen, message);
	}
	

	public void cancelEditingEmployee() {
		screen.hideEmployeeEditorWidget();
	}
	
}
