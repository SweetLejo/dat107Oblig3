package Employee;

import Department.DepartmentDao;
import Project.ProjectDao;
import entities.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.util.Date;


public class EmployeeOperations {

    private static EmployeeDao dao = new EmployeeDao();
    public static int menuReturnChoice(String menu){

        Scanner in = new Scanner(System.in);
        System.out.println(menu);
        if(in.hasNextInt()){
            return in.nextInt();
        }else{
            System.out.println("Didn't enter int, now exiting...");
            return -1;
        }
    }


    public static int byNameOrByID(){
        return menuReturnChoice("""
                ========================\s
                Search by name or by id?
                1. Enter name:\s
                2. Enter id: \s
                ========================\s
                choice:""");
    }

    public static void printAllEmployees(){
        //Get all employees
        List<Employee> employees = dao.getEmployees();
        for (Employee employee : employees) {
            System.out.println(employee);
        }

    }

    public static void searchForEmployee(){
        int choice = byNameOrByID();
        switch (choice){
            case 1 -> printOneEmployeeByName();
            case 2 -> printOneEmployeeById();
            default -> {
                System.out.println("invalid input");
            }
        }
    }

    public static void printOneEmployeeById(){
        Scanner in = new Scanner(System.in);
        int choice;
        System.out.println("Please enter the id: ");
        if(in.hasNextInt()){
            choice = in.nextInt();
        }else{
            System.out.println("Didn't enter int, now exiting...");
            return;
        }
        Employee chosenEmployee = dao.getEmployeeById(choice);
        if(chosenEmployee == null){
            System.out.println("Employee not found... ");
        }else{
            System.out.println(chosenEmployee);
        }

    }

    public static void printOneEmployeeByName(){
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter a name: ");
        String name = in.nextLine();
        Employee match = dao.getEmployeeByName(name);
        if(match == null){
            System.out.println("no employee found by given name.. ");
        }else {
            System.out.println(match);
        }
    }

    public static void updateEmployee(){
        int choice = menuReturnChoice("""
                ========================\s
                1. Change name: \s
                2. Change Wage: \s
                3. Change Position \s
                ========================\s
                choice:""");
        switch(choice){
            case 1 -> changeName();
            case 2 -> changeWage();
            case 3 -> changePosition();
            default -> {
                System.out.println("invalid input now exiting... ");
            }
        }
    }


    public static void changeName(){
        boolean success = false;
        Scanner in = new Scanner(System.in);
        int choice = byNameOrByID();
        switch(choice){
            case 1 -> {
                System.out.println("Enter old name: ");
                String oldName = in.nextLine();
                System.out.println("Enter new name: ");
                String newName = in.nextLine();
                success = dao.changeEmployeeName(newName, oldName);

            }
            case 2 -> {
                int id = menuReturnChoice("Enter id: ");
                System.out.println("Enter new Name: ");
                String newName = in.nextLine();
                success = dao.changeEmployeeName(newName ,id);
            }
            default -> {
                System.out.println("Wrong choice entered now exiting... ");
            }
        }
        if(success){
            System.out.println("Name changed successfully");
        }else{
            System.out.println("Name not changed");
        }
    }

    public static void changeWage(){
        boolean success = false;
        Scanner in = new Scanner(System.in);
        int choice = byNameOrByID();
        switch(choice){
            case 1 -> {
                System.out.println("Enter name: ");
                String name = in.nextLine();
                int newWage = menuReturnChoice("New wage: ");
                success = dao.changeWage(newWage, name);

            }
            case 2 -> {
                int id = menuReturnChoice("Enter id: ");
                int newWage = menuReturnChoice("New wage: ");
                success = dao.changeWage(newWage ,id);
            }
            default -> {
                System.out.println("Wrong choice entered now exiting... ");
            }
        }
        if(success){
            System.out.println("Wage changed successfully");
        }else{
            System.out.println("Wage not changed");
        }
    }


    public static void changePosition(){
        boolean success = false;
        Scanner in = new Scanner(System.in);
        int choice = byNameOrByID();
        System.out.println("New job title: ");
        String newJob = in.nextLine();

        switch(choice){
            case 1 -> {
                System.out.println("Enter name: ");
                String name = in.nextLine();
                success = dao.changePosition(newJob, name);
            }
            case 2 -> {
                int id = menuReturnChoice("Enter id: ");
                success = dao.changePosition(newJob ,id);
            }
            default -> {
                System.out.println("Wrong choice entered now exiting... ");
            }
        }
        if(success){
            System.out.println("Job title changed successfully");
        }else{
            System.out.println("Job title not changed");
        }
    }


    public static void addEmployee(){
        Scanner in = new Scanner(System.in);
        System.out.print("\nEnter unique name key: ");
        String uniqueName = in.nextLine();
        System.out.print("\nEnter first name: ");
        String firstName = in.nextLine();
        System.out.print("\nEnter Last name: " );
        String lastName = in.nextLine();

        System.out.print("\nEnter a employment date in the format dd/MM/yyyy: ");
        String inputDate = in.nextLine();
        Date date;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = format.parse(inputDate);
        } catch(ParseException e) {
            System.out.println("Invalid date format");
            return;
        }

        System.out.print("\nEnter job title: ");
        String jobTitle = in.nextLine();
        int wage = menuReturnChoice("\nEnter wage: ");


        DepartmentDao DDao = new DepartmentDao();
        int departmentId = menuReturnChoice("\nEnter department_id: ");
        Department department = DDao.idExsists(departmentId);

        ProjectDao PDao = new ProjectDao();
        int projectId = menuReturnChoice("\nEnter project_id: ");
        Project project = PDao.idExsists(projectId);


        Employee newEmp = new Employee(uniqueName, firstName, lastName, date, jobTitle, wage, department, project);

        if(dao.addEmployee(newEmp)){
            System.out.println("Employee " + newEmp.toString() + " Added successfully");
        }else{
            System.out.println("Employee not added...");
        }
    }
}



