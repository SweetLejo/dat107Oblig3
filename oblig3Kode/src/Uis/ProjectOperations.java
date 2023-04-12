package Uis;

import Daos.DepartmentDao;
import Daos.EmployeeDao;
import Daos.ProjectDao;
import entities.Employee;
import entities.Project;

import java.util.Scanner;

public class ProjectOperations {

    private static final ProjectDao dao = new ProjectDao();
    public static void mainProjectMenu(){
        Scanner in = new Scanner(System.in);
        int choice = 0;
        String menu = """
                ========================\s
                1. Add a project:\s
                2. Register project participation:\s
                3. Change the time on project:\s
                4. Print info about project:\s
                To quit press any other key \s
                ========================\s
                choice: \s""";

        boolean done = false;
        while (!done) {

            choice = UiHelper.menuReturnChoice(menu);

            switch (choice) {
                case 1 -> addProject();
                case 2 -> addEmployeeToProject();
                case 3 -> changeTime();
                case 4 -> printProject();
                default -> {
                    System.out.println("Invalid int now exiting... ");
                    done = true;
                }
            }
        }
    }

    public static void addProject(){
        Scanner in = new Scanner(System.in);
        System.out.println("What is the name of your new project? ");
        String projectName = in.nextLine();
        System.out.println("Waht is the description of the project?");
        String description = in.nextLine();
        int bossEmp = UiHelper.menuReturnChoice("Who is the boss of the project?: ");
        EmployeeDao EDao = new EmployeeDao();
        DepartmentDao DDao = new DepartmentDao();
        Employee boss = EDao.getEmployeeById(bossEmp);
        if((DDao.isBoss(bossEmp) != null) || boss == null){
            System.out.println("Project not added");
        }

        Project newProd = new Project(projectName, description, boss, 0);
        boolean success = dao.addProject(newProd);
        if(success){
            System.out.println("Project added successfully!\n" + newProd);
        }

    }

    public static void addEmployeeToProject(){
        Scanner in = new Scanner(System.in);
        int employeeId = UiHelper.menuReturnChoice("Employee Id of participant");
        int projectId = UiHelper.menuReturnChoice("Project ID of project");
        int timeSpent = UiHelper.menuReturnChoice("How many hours have they worked on said project");
        System.out.println("What was their role on the project? ");
        in.nextLine();
    }

    public static void changeTime(){

    }

    public static void printProject(){

    }




}
