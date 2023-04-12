package Daos;

import entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class EmployeeDao {

    private EntityManagerFactory emf;
    public EmployeeDao(){
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



    public List<Employee> getEmployees(){
        EntityManager em = emf.createEntityManager();

        try {
            String q = "SELECT e FROM Employee e";
            TypedQuery<Employee> query = em.createQuery(q, Employee.class);

            return query.getResultList();

        } catch(Exception e) {
            e.printStackTrace();
            return null;

        } finally {
            em.close();
        }
    }

    public Employee getEmployeeById(int id){
        EntityManager em = emf.createEntityManager();

        try {
           //String q = "select e from Employee e where e.employeeId = :id";
           //TypedQuery<Employee> query = em.createQuery(q, Employee.class);
           //query.setParameter("id", id);
           //return query.getSingleResult();

            return em.find(Employee.class, id);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }finally {
            em.close();
        }
    }

    public Employee getEmployeeByName(String name){

        name = name.toUpperCase();

        try (EntityManager em = emf.createEntityManager()) {
            String q = "Select e from Employee e where e.uniqueName = :name";
            TypedQuery<Employee> match = em.createQuery(q, Employee.class);
            match.setParameter("name", name);
            return match.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public boolean changeEmployeeName(String newName, int id){
        if(!(newName.length() >= 3 && newName.length() <=4)){
            return false;
        }

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        boolean changed = false;
        newName = newName.toUpperCase();

        try{
            tx.begin();
            Employee employeeToChange = em.find(Employee.class, id);
            employeeToChange.setUniqueName(newName);
            em.persist(employeeToChange);
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
    public boolean changeEmployeeName(String newName, String oldName){
        Employee employeeToChange = this.getEmployeeByName(oldName);
        if(employeeToChange != null){
            int id = employeeToChange.getEmployeeId();
            return changeEmployeeName(newName, id);
        }else{
            return false;
        }
    }

    public boolean changeWage(int newWage, int id){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        boolean changed = false;

        try{
            tx.begin();
            Employee employeeToChange = em.find(Employee.class, id);
            employeeToChange.setWage(newWage);
            em.persist(employeeToChange);
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

    public boolean changeWage(int newWage, String name){
        Employee employeeToChange = this.getEmployeeByName(name);
        if(employeeToChange != null){
            return changeWage(newWage, employeeToChange.getEmployeeId());
        }else{
            return false;
        }
    }

    public boolean changePosition(String newPosition, int id){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        boolean changed = false;

        try{
            tx.begin();
            Employee employeeToChange = em.find(Employee.class, id);
            employeeToChange.setJobTitle(newPosition);
            em.persist(employeeToChange);
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
    public boolean changePosition(String newPosition, String name){
        Employee employeeToChange = this.getEmployeeByName(name);
        if(employeeToChange != null){
            return changePosition(newPosition, employeeToChange.getEmployeeId());
        }else{
            return false;
        }
    }

    public boolean addEmployee(Employee newEmp){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        boolean success = false;
        try {
            transaction.begin();
            em.persist(newEmp);
            transaction.commit();
            success = true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        return success;
    }


}