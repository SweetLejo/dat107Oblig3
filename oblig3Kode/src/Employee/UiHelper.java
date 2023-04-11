package Employee;

import java.util.Scanner;

public class UiHelper {

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




}
