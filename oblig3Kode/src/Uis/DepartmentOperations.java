package Uis;

import Daos.DepartmentDao;
import Daos.EmployeeDao;
import entities.*;
import entities.Department;

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
                5. Get all Employees \s
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
                case 5 -> getAllEmployees();
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
        int id = UiHelper.menuReturnChoice("Enter department id");
        Department answer = dao.searchForId(id);
        if(answer != null){
            System.out.println(answer);
        }else{
            System.out.println("Department not found");
        }
    }

    public static void updateDepartment(){
        String menu = """
                ===============================\s
                1. Update name \s
                2. Update Boss \s
                3. Add Employees \s
                ==============================\s
                """;
        int choice = UiHelper.menuReturnChoice(menu);

        switch (choice){
            case 1 -> updateDepartmentName();
            case 2 -> updateDepartmentBoss();
            case 3 -> addEmployee();
            default -> {
                System.out.println("nothing chosen");
            }
        }
    }

    public static void addDepartment(){

        Scanner in = new Scanner(System.in);
        int BossId = UiHelper.menuReturnChoice("Who is the boss of the new department? ");
        if(dao.isBoss(BossId) != null){
            System.out.println("Employee is already a boss");
            return;
        }
        EmployeeDao EDao = new EmployeeDao();
        Employee newBoss = EDao.getEmployeeById(BossId);

        System.out.println("What is the name of the new department?: ");
        String name = in.nextLine();


        Department newDep = new Department(name, newBoss);
        boolean changed = dao.addDepartment(newDep);
        if(changed){
            System.out.println("Department added successfully\n" + newDep);
        }else{
            System.out.println("Failed to add department");
        }
    }

    private static void updateDepartmentBoss(){
        int promotionId = UiHelper.menuReturnChoice("Enter the Id of the employee who will get promoted");
        if(dao.isBoss(promotionId) != null){
            System.out.println("Employee is already the Boss of another Department");
            return;
        }
        int depToChange = UiHelper.menuReturnChoice("Enter the Id of the department you would like to change");

        boolean changed = dao.changeBoss(depToChange, promotionId);
        if(changed){
            System.out.println("Department changed successfully");
            System.out.println(dao.searchForId(depToChange));
        }else{
            System.out.println("Department not changed... ");
        }


    }

    private static void updateDepartmentName(){
        Scanner in = new Scanner(System.in);
        int depToChange = UiHelper.menuReturnChoice("Department id for department to change: ");

        System.out.println("what is the new name of the department");
        String newName = in.nextLine();
        boolean changed = dao.changeName(depToChange, newName);
        if(changed){
            System.out.println("Name changed successfully");
            System.out.println(dao.searchForId(depToChange));
        }else{
            System.out.println("name not chnaged... ");
        }
    }

    private static void addEmployee(){
        EmployeeDao Edao = new EmployeeDao();
        int newEmp = UiHelper.menuReturnChoice("The id of the employee you would like to add: ");
        Employee emp = Edao.getEmployeeById(newEmp);
        if(emp == null){
            System.out.println("Employee not found... ");
            return;
        }else{
            System.out.println("Employee found: \n" + emp);
        }

        int newDep = UiHelper.menuReturnChoice("The department id to which you would like to transfer the employee: ");
        boolean changed = dao.changeEmpDep(newDep, newEmp);
        if(changed){
            System.out.println("Department updated succesfully\n" + dao.searchForId(newDep));
        }else{
            System.out.println("Department not changed");
        }
    }

    public static void getAllEmployees(){
        int depChoice = UiHelper.menuReturnChoice("What department do you want to see the employees for? ");
        Department choosenDep = dao.searchForId(depChoice);
        if(choosenDep == null){
            System.out.println("Department not found");
            return;
        }
        List<Employee> employeeList = choosenDep.getEmployees();
        System.out.println("The following employees work at Department: " + choosenDep.getDepartmentName());
        for(Employee e : employeeList){
            System.out.println(e);
        }
    }


}
