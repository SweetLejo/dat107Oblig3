package Department;

import Employee.UiHelper;
import entities.Department;
import entities.Employee;
import Department.DepartmentDao;
import java.util.Scanner;
import java.util.List;

public class DepartmentOperations {

    private final static DepartmentDao dao = new DepartmentDao();

    public static void mainDepartmentMenu(){
        Scanner in = new Scanner(System.in);
        int choice = 0;
        String menu = """
                ========================\s
                1. Get all departments:\s
                2. Search for department:\s
                3. Update a department:\s
                4. Add a department:\s
                To quit press any other key \s
                ========================\s
                choice: \s""";

        boolean done = false;
        while (!done) {

            choice = UiHelper.menuReturnChoice(menu);

            switch (choice) {
                case 1 -> printAllDepartments();
                case 2 -> searchForDepartmnt();
                case 3 -> updateDepartment();
                case 4 -> addDepartment();
                default -> {
                    System.out.println("Invalid int now exiting... ");
                    done = true;
                }
            }
        }
    }

    public static void printAllDepartments(){
        List<Department> departments = dao.getAllDepartments();
        for(int i = 0; i < departments.size(); i++){
            System.out.println(departments.get(i));
        }

    }

    public static void searchForDepartmnt(){
        Scanner in = new Scanner(System.in);
        System.out.println("Enter department id");

    }

    public static void updateDepartment(){

    }

    public static void addDepartment(){

    }



}
