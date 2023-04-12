package entities;


import jakarta.persistence.*;

@Entity
@Table(schema = "oblig", name = "project_participation")
//@IdClass(ProjectParticipation.class)
public class ProjectParticipation {

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    @Column(name = "employee_id")
    private Employee bossEmployee;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    @Column(name = "project_id")
    private Project project;

    @Column(name = "time_spent")
    private int timeSpent;

    @Column(name = "role")
    private String role;

    public Employee getBossEmployee() {
        return bossEmployee;
    }

    public void setBossEmployee(Employee bossEmployee) {
        this.bossEmployee = bossEmployee;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public int getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(int timeSpent) {
        this.timeSpent = timeSpent;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "ProjectParticipation{" +
                "bossEmployee=" + bossEmployee +
                ", project=" + project +
                ", timeSpent=" + timeSpent +
                ", role='" + role + '\'' +
                '}';
    }
}
