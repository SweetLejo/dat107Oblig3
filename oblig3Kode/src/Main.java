import java.util.Scanner;

import Uis.*;
import Uis.ProjectOperations;

public class Main {


    public static void main(String[] args) {
        menu();
    }


    public static void menu() {

        Scanner in = new Scanner(System.in);
        int choice = 0;
        String menu = """
                ========================\s
                1. Employee settings:\s
                2. Department settings:\s
                3. Project settings:\s
                To quit press any other key \s
                ========================\s
                choice: \s""";

        boolean done = false;
        while (!done) {

            choice = UiHelper.menuReturnChoice(menu);

            switch (choice) {
                case 1 -> EmployeeOperations.mainEmployeeMenu();
                case 2 -> DepartmentOperations.mainDepartmentMenu();
                case 3 -> ProjectOperations.mainProjectMenu();
                default -> {
                    System.out.println("Invalid int now exiting... ");
                    done = true;
                }
            }
        }

    }



}
