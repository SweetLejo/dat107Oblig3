package Department;

import entities.Department;
import entities.Employee;
import jakarta.persistence.*;

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

        public List<Employee> getAllBosses(){
        EntityManager em = emf.createEntityManager();
        try{
            String q = "Select d.boss from Department d";
            TypedQuery<Employee> bosses = em.createQuery(q, Employee.class);
            return bosses.getResultList();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            em.close();
        }
        return null;
        }

        public Department isBoss(int employeeId){
            Department bossDep = null;
            try (EntityManager em = emf.createEntityManager()) {
                Employee maybeBoss = em.find(Employee.class, employeeId);

                TypedQuery<Department> query = em.createQuery(
                        "SELECT d FROM Department d WHERE d.boss = :maybeBoss", Department.class);
                query.setParameter("maybeBoss", maybeBoss);
                bossDep = query.getSingleResult();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bossDep;
        }

        public boolean changeBoss(int departmentId, int newBossId){
        boolean changed = false;
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try{
            transaction.begin();
            Department depToChange = em.find(Department.class, departmentId);
            Employee newBoss = em.find(Employee.class, newBossId);
            depToChange.setBoss(newBoss);
            em.persist(depToChange);
            transaction.commit();
            changed = true;
        }catch(Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            em.close();
        }
        return changed;
        }


        public boolean changeName(int depId, String newName){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        boolean changed = false;
        try{
            tx.begin();
            Department depToChange = em.find(Department.class, depId);
            depToChange.setDepartmentName(newName);
            em.persist(depToChange);
            tx.commit();
            changed = true;
        }catch(Exception e){
            if(tx != null){
                tx.rollback();
            }
            e.printStackTrace();
        }finally {
            em.close();
        }
        return changed;
        }

    public boolean changeEmpDep(int departmentToChangeTo, int employeeToChange){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        boolean changed = false;
        try{
            tx.begin();
            Department newDep = em.find(Department.class, departmentToChangeTo);
            Employee employee = em.find(Employee.class, employeeToChange);
            employee.setDepartment(newDep);
            em.persist(employee);
            tx.commit();
            changed = true;
        }catch(Exception e){
            if(tx != null){
                tx.rollback();
            }
            e.printStackTrace();
        }finally{
            em.close();
        }
        return changed;
    }


    public boolean addDepartment(Department newDepartment){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        boolean changed = false;
        try{
            tx.begin();
            em.persist(newDepartment);
            tx.commit();
            changed = true;
        }catch(Exception e){
            if(tx != null){
                tx.rollback();
            }
            e.printStackTrace();
        }finally {
            em.close();
        }
        return changed;
    }
}
