package Daos;

import entities.Project;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ProjectDao {

    private EntityManagerFactory emf;
    public ProjectDao() {
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


    public Project idExsists(int ProjectId){
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Project.class, ProjectId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean addProject(Project project){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        boolean changed = false;
        try{
            tx.begin();
            em.persist(project);
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




}
