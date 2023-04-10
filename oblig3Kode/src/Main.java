import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    private String url = System.getenv("DB_URL");
    private String user = System.getenv("DB_USER");
    private String password = System.getenv("DB_PASSWORD");

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-persistence-unit");

    public static void main(String[] args){

    }

}