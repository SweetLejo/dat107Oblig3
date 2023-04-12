package Department;

import entities.Department;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.List;

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



        public List<Department> getAllDepartments(){
        EntityManager em = emf.createEntityManager();
        try{
            String q = "Select d from Department d";
            TypedQuery<Department> query = em.createQuery(q, Department.class);
            return query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            em.close();
        }
        return null;
        }


        public Department searchForId(int departmentId){
            try (EntityManager em = emf.createEntityManager()) {
                return em.find(Department.class, departmentId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        public boolean departmentIdExsists(int departmentId){
            try (EntityManager em = emf.createEntityManager()) {
                return em.find(Department.class, departmentId) != null;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }




}
