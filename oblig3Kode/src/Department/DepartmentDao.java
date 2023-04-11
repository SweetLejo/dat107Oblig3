package Department;

import entities.Department;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DepartmentDao {

    private EntityManagerFactory emf;
    public DepartmentDao() {
        //setupEntityManager();
        setupEntityManager1();
    }


        public void setupEntityManager() {
            Properties props = new Properties();
            try (InputStream in = getClass().getResourceAsStream("/home/leo/mystuff/database/db.properties")) {
                props.load(in);
                emf = Persistence.createEntityManagerFactory("my-persistence-unit", props);
            } catch (IOException e) {
                //idk
            }

            // Use the EntityManagerFactory to create an EntityManager and perform database operations
        }

        public void setupEntityManager1(){
            emf = Persistence.createEntityManagerFactory("my-persistence-unit");
        }


        public Department idExsists(int departmentId){
            try (EntityManager em = emf.createEntityManager()) {
                return em.find(Department.class, departmentId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


}
