package entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(schema = "oblig", name = "employee")
public class Employee {

    public Employee(){

    }

    public Employee(String uniqueName, String firstName, String lastName, Date employmentDate, String jobTitle,
                    int wage, Department department, Project project) {
        this.uniqueName = uniqueName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.employmentDate = employmentDate;
        this.jobTitle = jobTitle;
        this.wage = wage;


        this.department = department;
        this.project = project;

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private int employeeId;

    @Column(name= "unique_name")
    private String uniqueName;

    @Column(name= "first_name")
    private String firstName;

    @Column(name= "last_name")
    private String lastName;

    @Temporal(TemporalType.DATE)
    @Column(name= "employment_date")
    private Date employmentDate;

    @Column(name= "job_title")
    private String jobTitle;

    @Column(name= "wage")
    private int wage;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", referencedColumnName = "department_id")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    private Project project;


    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(Date employmentDate) {
        this.employmentDate = employmentDate;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public int getWage() {
        return wage;
    }

    public void setWage(int wage) {
        this.wage = wage;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", uniqueName='" + uniqueName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", employmentDate=" + employmentDate +
                ", jobTitle='" + jobTitle + '\'' +
                ", wage=" + wage +
                ", department=" + department.getDepartmentId() +
                ", project=" + project.getProjectId() +
                '}';
    }
}
