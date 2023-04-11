package dat107.oblig3.gui.widget;

import java.util.Date;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import dat107.oblig3.dao.EmployeeDAO;
import dat107.oblig3.entity.Department;
import dat107.oblig3.entity.Employee;
import dat107.oblig3.gui.UITheme;
import dat107.oblig3.gui.controller.EmployeeController;
import dat107.oblig3.gui.inputcontrols.DateField;
import dat107.oblig3.gui.inputcontrols.EntityComboBox;
import dat107.oblig3.gui.inputcontrols.NumericField;
import dat107.oblig3.gui.inputcontrols.StyledTextField;
import dat107.oblig3.gui.screen.Screen;

@SuppressWarnings("serial")
public class EmployeeEditorWidget extends Widget {
	
	private final EmployeeController controller;
	private final JTextField id;
	private final JTextField username;
	private final JTextField firstName;
	private final JTextField lastName;
	private final DateField employmentDate;
	private final JTextField position;
	private final NumericField salary;
	private final EntityComboBox<Department> department;
	private final JButton saveButton;
	private final JButton cancelButton;
	
	public EmployeeEditorWidget(EmployeeController controller) {
		super("About Employee");
		this.controller = controller;
		this.id = new StyledTextField(10);
		this.username = new StyledTextField(10);
		this.firstName = new StyledTextField(10);
		this.lastName = new StyledTextField(10);
		this.employmentDate = new DateField();
		this.position = new StyledTextField(10);
		this.salary = new NumericField(10, true);
		this.department = EntityComboBox.createDepartmentComboBox();
		this.saveButton = createWidgetButton("Save", e -> controller.saveEmployee());
		this.cancelButton = createWidgetButton("Cancel", e -> controller.cancelEditingEmployee());

		configureComponents();
		addComponents();
	}
	
	private void configureComponents() {
		employmentDate.setBackground(UITheme.ALTERNATIVE_BACKGROUND_COLOR);	
		department.setPreferredSize(position.getPreferredSize());
		
		setIdEditable(false);
		setUsernameEditable(false);
		setUsernameEditable(false);
		setFirstNameEditable(false);
		setLastNameEditable(false);
		setEmploymentDateEditable(false);
		setPositionEditable(false);
		setSalaryEditable(false);
		setDepartmentEditable(false);
	}
	
	private void addComponents() {
		addLabeledField("ID:", id, "Auto-generated");
		addLabeledField("Username:", username, "3 characters");
		addLabeledField("First Name:", firstName);
		addLabeledField("Last Name", lastName);
		addLabeledField("Date of Employment:", employmentDate, "Day/Month/Year");
		addLabeledField("Position:", position);
		addLabeledField("Monthly Salary:", salary);
		addLabeledField("Department:", department);
	}
	
	public void populateAllFields(int id, String username, String firstName, 
			String lastName, Date employmentDate, String position, 
			double salary, Department department) {
		setId(id + "");
		setUsername(username);
		setFirstName(firstName);
		setLastName(lastName);
		setEmploymentDate(employmentDate);
		setPosition(position);
		setSalary(salary + "");
		setDepartment(department);
	}
	
	public void enableEditingForExistingEmployee() {
		setTitle("Edit Employee");
		
		setIdEditable(false);
		setUsernameEditable(false);
		setUsernameEditable(false);
		setFirstNameEditable(false);
		setLastNameEditable(false);
		setEmploymentDateEditable(false);
		setPositionEditable(true);
		setSalaryEditable(true);
		setDepartmentEditable(true);
	}
	
	public void resetFieldsForNewEmployee() {
		setTitle("New Employee");
		
		emptyFieldsForNewEmployee();
		setFieldsEditableForNewEmployee();
	}
	
	private void emptyFieldsForNewEmployee() {
		setId("Generated");
		setUsername("");
		setFirstName("");
		setLastName("");
		setEmploymentDate(null);
		setPosition("");
		setSalary("");
		setDepartment(null);
	}
	
	private void setFieldsEditableForNewEmployee() {
		setIdEditable(false);
		setUsernameEditable(true);
		setFirstNameEditable(true);
		setLastNameEditable(true);
		setEmploymentDateEditable(true);
		setPositionEditable(true);
		setSalaryEditable(true);
		setDepartmentEditable(true);
	}
	
	public void disableAllFields() {
		setIdEditable(false);
		setUsernameEditable(false);
		setUsernameEditable(false);
		setFirstNameEditable(false);
		setLastNameEditable(false);
		setEmploymentDateEditable(false);
		setPositionEditable(false);
		setSalaryEditable(false);
		setDepartmentEditable(false);
	}
	
	public void showSaveAndCancelButtons() {
		if(!cancelButton.isShowing() || !saveButton.isShowing()) {
			setButtons(cancelButton, saveButton);
		}
	}
	
	public void hideSaveAndCancelButtons() {
		if(cancelButton.isShowing() || saveButton.isShowing()) {
			setButtons();
		}
	}
	
	public String getId() {
		return id.getText();
	}
	
	public void setId(String id) {
		this.id.setText(id);
	}
	
	public void setIdEditable(boolean editable) {
		id.setEditable(editable);
	}
	
	public String getUsername() {
		return username.getText();
	}
	
	public void setUsername(String username) {
		this.username.setText(username);
	}
	
	public void setUsernameEditable(boolean editable) {
		username.setEditable(editable);
	}
	
	public String getFirstName() {
		return firstName.getText();
	}
	
	public void setFirstName(String firstName) {
		this.firstName.setText(firstName);
	}
	
	public void setFirstNameEditable(boolean editable) {
		firstName.setEditable(editable);
	}
	
	public String getLastName() {
	    return lastName.getText();
	}

	public void setLastName(String lastName) {
	    this.lastName.setText(lastName);
	}

	public void setLastNameEditable(boolean editable) {
	    lastName.setEditable(editable);
	}

	public Date getEmploymentDate() {
	    return employmentDate.getDate();
	}
	
	public void setEmploymentDate(Date employmentDate) {
	    this.employmentDate.setDate(employmentDate);
	}

	public void setEmploymentDateEditable(boolean editable) {
	    employmentDate.setEditable(editable);
	}

	public String getPosition() {
	    return position.getText();
	}

	public void setPosition(String position) {
	    this.position.setText(position);
	}

	public double getSalary() throws IllegalArgumentException {
	    return salary.getDouble();
	}
	
	public void setPositionEditable(boolean editable) {
	    position.setEditable(editable);
	}
	
	public void setSalary(String salary) {
	    this.salary.setText(salary);
	}

	public void setSalaryEditable(boolean editable) {
	    salary.setEditable(editable);
	}

	public Department getDepartment() {
	    return (Department) department.getSelectedItem();
	}

	public void setDepartment(Department department) {
	    this.department.setSelectedItem(department);
	}

	public void setDepartmentEditable(boolean editable) {
	    department.setEditable(editable);
	}
	
}
