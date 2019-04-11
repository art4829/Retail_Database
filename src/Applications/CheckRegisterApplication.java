package Applications;

import javax.naming.OperationNotSupportedException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class CheckRegisterApplication extends MethodCalls {

    public static void main( String[] args ) {
        MethodCalls app = new CheckRegisterApplication();

        // Hard drive location of database
        String location = "./retailDb/retailDb";
        String user = "cskid";
        String password = "retaildomain";
        //Create database connection
        app.createConnection(location, user, password);
        Scanner scan = new Scanner(System.in);

        System.out.println("|------------------------------------------|");
        System.out.println("|-------------WELCOME TO RETAIL------------|");
        System.out.println("|----------Please enter 1 to LOGIN---------|");
        System.out.println("|----------Please enter 2 to SIGNUP--------|");
        System.out.println("|------------------------------------------|");
        int check = scan.nextInt();

        if (check == 1) {
            // Get email to check
            System.out.println("Please Enter your Email: ");
            Scanner scan2 = new Scanner(System.in);
            String email = scan2.nextLine();
            // Check login and display
            String display = app.login(app.getConnection(), email);
            System.out.println(display);
            app.register(app);
        } else if (check == 2) {
            // If sign up, Register user
            System.out.println("Registering you");
            app.register(app);
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("|--------------------------------------|");
        System.out.println("|     Welcome To Our Online Store!     |");
        System.out.println("| To SignOut please press 0 at anytime |");
        System.out.println("|--------------------------------------|\n\n");

        while(true) {
            int answer = 0;

            System.out.println("|---------------------|");
            System.out.println("| Press 1 to Checkout |");
            System.out.println("|---------------------|\n\n");
            answer = scan.nextInt();

            if( answer == 1 ) {
                System.out.println("Please enter the product UPC: ");
                String UPC = "";
                UPC = scan.next();

                //reduce the number of item in the database
                app.buyProduct(UPC);
            } else if( answer == 0 ) {
                break;
            }

        }
    }
}
