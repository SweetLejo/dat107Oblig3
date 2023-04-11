package dat107.oblig3.gui.widget;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dat107.oblig3.dao.EmployeeDAO;
import dat107.oblig3.entity.Employee;
import dat107.oblig3.entity.Project;
import dat107.oblig3.entity.ProjectParticipation;
import dat107.oblig3.gui.controller.ParticipationController;
import dat107.oblig3.gui.inputcontrols.EntityComboBox;
import dat107.oblig3.gui.inputcontrols.NumericField;
import dat107.oblig3.gui.inputcontrols.StyledTextField;
import dat107.oblig3.gui.screen.Screen;
import jakarta.persistence.EntityExistsException;

@SuppressWarnings("serial")
public class ParticipationEditorWidget extends Widget {

	private final ParticipationController controller;
	private final EntityComboBox<Employee> employee;
	private final EntityComboBox<Project> project;
	private final JTextField role;
	private final NumericField hours;
	private final JButton saveButton;
	private final JButton cancelButton;
	
	public ParticipationEditorWidget(ParticipationController controller) {
		super("About Participation");
		this.controller = controller;
		this.employee = EntityComboBox.createEmployeeComboBox();
		this.project = EntityComboBox.createProjectComboBox();
		this.role = new StyledTextField(12);
		this.hours = new NumericField(12);
		this.saveButton = createWidgetButton("Save", e -> controller.saveParticipation());
		this.cancelButton = createWidgetButton("Cancel", e -> controller.cancelEditingParticipation());
		
		configureComponents();
		addComponents();
	}
	
	private void configureComponents() {
		employee.setPreferredSize(hours.getPreferredSize());
		project.setPreferredSize(hours.getPreferredSize());
	}
	
	private void addComponents() {
		addLabeledField("Employee:", employee);
		addLabeledField("Project:", project);
		addLabeledField("Role:", role);
		addLabeledField("Hours:", hours);
		
		setButtons(cancelButton, saveButton);
	}
	
	public void enableEditingForExistingParticipation() {
		setTitle("Edit Participation");
		
		setEmployeeEditable(false);
		setProjectEditable(false);
		setRoleEditable(true);
		setHoursEditable(true);
	}
	
	public void resetFieldsForNewParticipation() {
		setTitle("New Participation");
		
		emptyFieldsForNewParticipation();
		setFieldsEditableForNewParticipation();
	}
	
	private void emptyFieldsForNewParticipation() {
		setEmployee(null);
		setProject(null);
		setRole("");
		setHours("");
	}
	
	private void setFieldsEditableForNewParticipation() {
		setEmployeeEditable(true);
		setProjectEditable(true);
		setRoleEditable(true);
		setHoursEditable(true);
	}
	
	public void resetFieldsForNewParticipation(Employee preSelected) {
		resetFieldsForNewParticipation();
		setEmployee(preSelected);
		setEmployeeEditable(false);
	}
	
	public void resetFieldsForNewParticipation(Project preSelected) {
		resetFieldsForNewParticipation();
		setProject(preSelected);
		setProjectEditable(false);
	}
	
	public Employee getEmployee() {
		return (Employee) employee.getSelectedItem();
	}
	
	public void setEmployee(Employee employee) {
		this.employee.setSelectedItem(employee);
	}

	public void setEmployeeEditable(boolean editable) {
		employee.setEditable(editable);
	}
	
	public Project getProject() {
		return (Project) project.getSelectedItem();
	}
	
	public void setProject(Project project) {
		this.project.setSelectedItem(project);
	}

	public void setProjectEditable(boolean editable) {
		project.setEditable(editable);
	}
	
	public String getRole() {
		return role.getText();
	}
	
	public void setRole(String role) {
		this.role.setText(role);
	}

	public void setRoleEditable(boolean editable) {
		role.setEditable(editable);
	}
	
	public int getHours() throws IllegalArgumentException {
		return hours.getInt();
	}
	
	public void setHours(String hours) {
		this.hours.setText(hours);
	}
	
	public void setHoursEditable(boolean editable) {
		hours.setEditable(editable);
	}
	
}
