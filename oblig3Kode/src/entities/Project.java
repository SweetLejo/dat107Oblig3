package entities;


import jakarta.persistence.Entity;
import jakarta.persistence.*;

@Entity
@Table(schema = "oblig", name = "project")
public class Project {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "project_id")
        private int projectId;

        @Column(name = "project_name")
        private String projectName;

        @Column(name = "description")
        private String description;


        @Column(name = "number_of_hours")
        private int numberOfHours;


        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
        private Employee projectManager;


        public int getProjectId() {
                return projectId;
        }

        public void setProjectId(int projectId) {
                this.projectId = projectId;
        }

        public String getProjectName() {
                return projectName;
        }

        public void setProjectName(String projectName) {
                this.projectName = projectName;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public int getNumberOfHours() {
                return numberOfHours;
        }

        public void setNumberOfHours(int numberOfHours) {
                this.numberOfHours = numberOfHours;
        }

        public Employee getProjectManager() {
                return projectManager;
        }

        public void setProjectManager(Employee projectManager) {
                this.projectManager = projectManager;
        }

        @Override
        public String toString() {
                return "Project{" +
                        "projectId=" + projectId +
                        ", projectName='" + projectName + '\'' +
                        ", description='" + description + '\'' +
                        ", numberOfHours=" + numberOfHours +
                        ", projectManager=" + projectManager +
                        '}';
        }
}
