package Applications;

import Database.Populate.Products;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.zip.CheckedOutputStream;

public class OnlineApplication extends MethodCalls{


    /**
     * Run the application
     * @param args
     */
    public static void main(String[] args) {
        MethodCalls app = new OnlineApplication();
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
        String email="";
        if (check == 1) {
            // Get email to check
            System.out.println("Please Enter your Email: ");
            Scanner scan2 = new Scanner(System.in);
            email = scan2.nextLine();
            // Check login and display
            String display = app.login(app.getConnection(), email);
            System.out.println(display);
            if(display.equals(EMAIL_DOESNT_EXIST)){
                app.register(app);
            }
        } else if (check == 2) {
            // If sign up, Register user
            System.out.println("Registering you");
            email=app.register(app);
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("|--------------------------------------|");
        System.out.println("|     Welcome To Our Online Store!     |");
        System.out.println("| To SignOut please press 9 at anytime |");
        System.out.println("|--------------------------------------|\n\n");
        while(true) {
            System.out.println("|------------------------------------------|");
            System.out.println("| What Product will you like to Buy today? |");
            System.out.println("|------------------------------------------|");
            System.out.println("| Press 1 for Bakery                       |");
            System.out.println("| Press 2 for Beverage                     |" );
            System.out.println("| Press 3 for Grocery                      |");
            System.out.println("| Press 4 for Pantry                       |");
            System.out.println("|------------------------------------------|\n\n");

            int product_choice = scan.nextInt();

            if(product_choice==9){
                app.signout();
            }
            int count;
            ArrayList<String> itms;
            int answer = 0;
            Map<Integer, String> menu= new HashMap<Integer, String>();

            switch (product_choice) {
                case 1:
                    System.out.println("|--------------------|");
                    System.out.println("| Press 1 for Bakery |");
                    System.out.println("| Press 2 for Bread  |");
                    System.out.println("| Press 3 for Pastry |");
                    System.out.println("|--------------------|\n\n");
                    int bakery_choice = scan.nextInt();
                    if(bakery_choice==9){
                        app.signout();
                    }

                    switch (bakery_choice) {
                        case 1:
                            count = 1;
                            itms = app.viewItem(app.getConnection(), "bakery");
                            System.out.println("Please enter the number of the Item:- ");
                            System.out.println("---------------------------------------|");
                            for (String it : itms) {
                                System.out.println(count + ": " + it);
                                menu.put(count, it);
                                count += 1;
                            }
                            answer = scan.nextInt();
                            break;
                        case 2:
                            count = 1;
                            itms = app.viewItem(app.getConnection(), "bread");
                            System.out.println("Please enter the number of the Item:- ");
                            for (String it : itms) {
                                System.out.println(count + ": " + it);
                                menu.put(count, it);
                                count += 1;
                            }
                            answer = scan.nextInt();
                            break;
                        case 3:
                            count = 1;
                            itms = app.viewItem(app.getConnection(), "pastry");
                            System.out.println("Please enter the number of the Item:- ");
                            System.out.println("---------------------------------------|");
                            for (String it : itms) {
                                System.out.println(count + ": " + it);
                                menu.put(count, it);
                                count += 1;
                            }
                            answer = scan.nextInt();
                            break;
                    }
                    break;
                case 2:
                    count = 1;
                    itms = app.viewItem(app.getConnection(), "beverage");
                    System.out.println("Please enter the number of the Item:- ");
                    System.out.println("---------------------------------------|");
                    for (String it : itms) {
                        System.out.println(count + ": " + it);
                        menu.put(count, it);
                        count += 1;
                    }
                    answer = scan.nextInt();
                    break;
                case 3:
                    System.out.println("|---------------------|");
                    System.out.println("| Press 1 for Dairy   |");
                    System.out.println("| Press 2 for Grocery |");
                    System.out.println("| Press 3 for Meat    |");
                    System.out.println("| Press 4 for Produce |");
                    System.out.println("|---------------------|\n\n");
                    int grocery_choice = scan.nextInt();
                    if(grocery_choice==9){
                        app.signout();
                    }

                    switch (grocery_choice) {
                        case 1:
                            count = 1;
                            itms = app.viewItem(app.getConnection(), "dairy");
                            System.out.println("Please enter the number of the Item:- ");
                            System.out.println("---------------------------------------");
                            for (String it : itms) {
                                System.out.println(count + ": " + it);
                                menu.put(count, it);
                                count += 1;
                            }
                            answer = scan.nextInt();
                            break;
                        case 2:
                            count = 1;
                            itms = app.viewItem(app.getConnection(), "grocery");
                            System.out.println("Please enter the number of the Item:- ");
                            System.out.println("---------------------------------------");
                            for (String it : itms) {
                                System.out.println(count + ": " + it);
                                menu.put(count, it);
                                count += 1;
                            }
                            answer = scan.nextInt();
                            break;
                        case 3:
                            count = 1;
                            itms = app.viewItem(app.getConnection(), "meat");
                            System.out.println("Please enter the number of the Item:- ");
                            System.out.println("---------------------------------------");
                            for (String it : itms) {
                                System.out.println(count + ": " + it);
                                menu.put(count, it);
                                count += 1;
                            }
                            answer = scan.nextInt();
                            break;
                        case 4:
                            count = 1;
                            itms = app.viewItem(app.getConnection(), "produce");
                            System.out.println("Please enter the number of the Item:- ");
                            System.out.println("---------------------------------------");
                            for (String it : itms) {
                                System.out.println(count + ": " + it);
                                menu.put(count, it);
                                count += 1;
                            }
                            answer = scan.nextInt();
                            break;
                    }
                    break;
                case 4:
                    count = 1;
                    itms = app.viewItem(app.getConnection(), "pastry");
                    System.out.println("Please enter the number of the Item:- ");
                    System.out.println("---------------------------------------");
                    for (String it : itms) {
                        System.out.println(count + ": " + it);
                        menu.put(count, it);
                        count += 1;
                    }
                    answer = scan.nextInt();

                    break;

            }
            if(answer==9){
                app.signout();
            }
            System.out.println("\n>>>>>>>----- You have chosen: " + menu.get(answer));
            String UPC = app.getUPC(menu.get(answer), app.getConnection());
            System.out.println(">>>>>>>----- The price for the chosen item is: " + app.getPrice(UPC));
            System.out.println(">>>>>>>----- Do you still want to buy it?(Y/N)");
            Scanner buyoption = new Scanner(System.in);
            String option = buyoption.nextLine().toLowerCase();
            String customer_id = app.getCustomer_id(email);

            if(option.equals("y")){
                    app.buyProduct(UPC);
                    app.putOrder(app.genOrder(app.getConnection()),customer_id,app.getConnection());
                    System.out.println(">>>>>>>----- Congratulations, you have succesfully bought "+menu.get(answer));
                    System.out.println(">>>>>>>----- Your item will be shipped to you soon!");
            }
            System.out.println(">>>>>>>----- Would you like to look at another item?(Y/N)");
            String option2 = buyoption.nextLine().toLowerCase();
            if(option2.equals("n")){
                break;
            }
        }

    }
}
