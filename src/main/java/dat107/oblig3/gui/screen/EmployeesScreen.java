package dat107.oblig3.gui.screen;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Scrollable;

import dat107.oblig3.dao.EmployeeDAO;
import dat107.oblig3.entity.Employee;
import dat107.oblig3.gui.UITheme;
import dat107.oblig3.gui.collection.EmployeeTable;
import dat107.oblig3.gui.collection.EntityCollection;
import dat107.oblig3.gui.controller.EmployeeController;
import dat107.oblig3.gui.widget.EmployeeEditorWidget;
import dat107.oblig3.gui.widget.ParticipationsWidget;

@SuppressWarnings("serial")
public class EmployeesScreen extends SearchScreen<Employee> {
	
	private final EmployeeController controller;
	private final EmployeeEditorWidget editEmployeeWidget;
	private final ParticipationsWidget projectsWidget;
	private final JButton viewProjectsButton;
	
	public EmployeesScreen() {
		this.controller  = new EmployeeController(this);
		this.editEmployeeWidget = new EmployeeEditorWidget(controller);
		this.projectsWidget = new ParticipationsWidget(this);
		
		addSearchOption("Any", s -> controller.searchAny(s));
		addSearchOption("ID", s -> controller.searchById(s));
		addSearchOption("Username", s -> controller.searchByUsername(s));
		
		addSelectionListener(selected -> controller.setSelectedEmployee(selected));
		
		this.viewProjectsButton = 
				addButton("View Projects", e -> controller.viewProjects(), true);
		addButton("Delete Employee", e -> controller.deleteEmployee(), true);
		addButton("Edit Employee", e -> controller.editEmployee(), true);
		addButton("Add New Employee", e -> controller.createNewEmployee(), false);
	}

	@Override
	protected EntityCollection<Employee> initializeCollectionComponent() {
		return new EmployeeTable();
	}
	
	public EmployeeEditorWidget getEmployeeEditorWidget() {
		return editEmployeeWidget;
	}
	
	public ParticipationsWidget getProjectParticipationsWidget() {
		return projectsWidget;
	}
	
	public EmployeeTable getCollectionComponent() {
		return (EmployeeTable) dataset;
	}
	
	public void showWidgets() {
		showEmployeeEditorWidget();
		showProjectParticipationsWidget();
	}
	
	public void showEmployeeEditorWidget() {
		if(!editEmployeeWidget.isShowing()) {
			showWidget(editEmployeeWidget, 0);	
		}
	}
	
	public void showProjectParticipationsWidget() {
		if(!projectsWidget.isShowing()) {
			showWidget(projectsWidget, 1);	
		}
	}
	
	public void hideWidgets() {
		hideEmployeeEditorWidget();
		hideProjectParticipationsWidget();
	}
	
	public void hideEmployeeEditorWidget() {
		if(editEmployeeWidget.isShowing()) {
			hideWidget(editEmployeeWidget);	
			viewProjectsButton.setText("Hide Projects");
		}
	}
	
	public void hideProjectParticipationsWidget() {
		if(projectsWidget.isShowing()) {
			hideWidget(projectsWidget);	
			viewProjectsButton.setText("View Projects");
		}
	}
	
	@Override
	public void display() {
		dataset.updateContent(controller.getAllEmployees());
		
		searchBar.removeText();
	}
	
}
