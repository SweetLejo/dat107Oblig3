import java.util.Scanner;
import Employee.*;

public class Main {


    public static void main(String[] args) {

        menu();
    }


    public static void menu() {

        Scanner in = new Scanner(System.in);
        int choice = 0;
        String menu = """
                ========================\s
                1. Get all employess:\s
                2. Search for employee:\s
                3. Update an employee:\s
                4. Add an employee:\s
                To quit press any other key \s
                ========================\s
                choice: \s""";

        boolean done = false;
        while (!done) {

            choice = EmployeeOperations.menuReturnChoice(menu);

            switch (choice) {
                case 1 -> EmployeeOperations.printAllEmployees();
                case 2 -> EmployeeOperations.searchForEmployee();
                case 3 -> EmployeeOperations.updateEmployee();
                case 4 -> EmployeeOperations.addEmployee();
                default -> {
                    System.out.println("Invalid int now exiting... ");
                    done = true;
                }
            }
        }

    }
}
