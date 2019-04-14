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

public class OnlineApplication extends MethodCalls {
    /**
     * Run the application
     *
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

        //Check if reorder is delivered TODO IMPORTANT

        try {
            Statement stmt = app.getConnection().createStatement();
//            String sql = "create table temp select reorder_id, UPC, store_id, reorder.amount from reorder " +
//                    " inner join contains on reorder.UPC=contains.UPC and reorder.store_id=contains.store_id " +
//                    "where Delivery_date < curdate();";
//            stmt.execute(sql);
//            sql = "update contains set amount = contains.amount + temp.amount;";
//            stmt.execute(sql);
            //TODO by james

            //String sql = "create table temp select * from reorder where Delivery_date < curdate();";
            //stmt.execute(sql);
            while (true){
                ResultSet reorders = stmt.executeQuery("select count(reorder_id) from reorder where " +
                        "delivery_date<curdate();");
                reorders.next();
                int count = reorders.getInt(1);
                if (count == 0)
                    break;
                ResultSet temp = stmt.executeQuery("select * from reorder where delivery_date<curdate();");
                temp.next();
                int add = temp.getInt(4);
                String store_id = temp.getString(7);
                String UPC = temp.getString(3);
                String reorder_id = temp.getString(1);
                String checkDate = "update contains set amount = amount + " + add + " where store_id = " + store_id
                        + " and UPC = " + UPC;
                stmt.execute(checkDate);
                //stmt.executeQuery("delete top (1) from temp;");
                stmt.execute("delete from reorder where reorder_id = " + reorder_id+";");
            }
            //TODO from here, Jack's code
//            String sql = "create table temp as select reorder_id,UPC, store_id as t from reorder where " +
//                    "Delivery_date<curdate()";
//            stmt.execute(sql);

//            String sql = "update contains set amount = contains.amount + temp.amount " +
//                    "from temp where select reorder_id,UPC, store_id as t from reorder where " +
//                    "Delivery_date<curdate()" +
//                    "(temp.UPC = contains.UPC and temp.store_id = contains.store_id);";
//            stmt.execute(sql); //TODO from temp might be making error
            /*
                int count = stmt.executeQuery("select count(*) from temp;").getInt(1);
                if (count == 0)
                    break;
                ResultSet temp = stmt.executeQuery("select * from temp");
                int add = temp.getInt(3);
                String store_id = temp.getString(2);
                String UPC = temp.getString(1);
                String checkDate = "update contains set amount = amount + " + add + " where store_id = " + store_id
                        + " and UPC" + UPC;
                stmt.executeQuery("delete top (1) from temp;");
                stmt.executeQuery("delete from reorder where UPC = " + UPC + " and store_id = " + store_id +
                        " and amount = " + add + ";");
            */
//            stmt.executeQuery("drop table temp;");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Scanner scan = new Scanner(System.in);
        System.out.println("|------------------------------------------|");
        System.out.println("|-------------WELCOME TO RETAIL------------|");
        System.out.println("|----------Please enter 1 to LOGIN---------|");
        System.out.println("|----------Please enter 2 to SIGNUP--------|");
        System.out.println("|------------------------------------------|");
        int check = scan.nextInt();
        String email = "";
        if (check == 1) {
            // Get email to check
            System.out.println("Please Enter your Email: ");
            Scanner scan2 = new Scanner(System.in);
            email = scan2.nextLine();
            // Check login and display
            String display = app.login(app.getConnection(), email);

            System.out.println(display);
            if (display.equals(EMAIL_DOESNT_EXIST)) {
                System.out.println("Do you want to register?(Press Y for yesd)");
                String reg = scan2.nextLine();
                if(reg.toLowerCase().equals("y")) {
                    email = app.register(app);
                }else{
                    System.out.println("Thank you for you time!");
                    System.exit(0);
                }
            }
        } else if (check == 2) {
            // If sign up, Register user
            System.out.println("Registering you");
            email = app.register(app);
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
        while (true) {
            Scanner startup = new Scanner(System.in);
            System.out.println("|------------------------------------------|");
            System.out.println("|     What would you like to do today?     |");
            System.out.println("|------------------------------------------|");
            System.out.println("|     1. Buy Food!                         |");
            System.out.println("|     2. View Orders!                      |");
            System.out.println("|     3. View Credit!                      |");
            System.out.println("|     9. Delete Account!                   |");
            System.out.println("|     0. Sign Out!                         |");
            System.out.println("|--Please enter the number of your choice--|");
            System.out.println("|------------------------------------------|");
            int start = startup.nextInt();
            if (start == 0) {
                app.signout();
            } else if (start == 2) {
                app.viewOrders(app.getCustomer_id(email),app.getConnection());
            } else if (start == 3) {
                app.viewCredit(app.getCustomer_id(email),app.getConnection());
            } else if (start == 9) {
                System.out.println(">>>>>---- Are you sure you want to delete your account??(Y/N)");
                Scanner s2= new Scanner(System.in);
                String delete = s2.nextLine();
                if(delete.toLowerCase().equals("y")){
                    app.deleteAccount(app.getCustomer_id(email),app.getConnection());
                }
            } else if (start == 1) {
                System.out.println("|------------------------------------------|");
                System.out.println("| What Product will you like to Buy today? |");
                System.out.println("|------------------------------------------|");
                System.out.println("| Press 1 for Bakery                       |");
                System.out.println("| Press 2 for Beverage                     |");
                System.out.println("| Press 3 for Grocery                      |");
                System.out.println("| Press 4 for Pantry                       |");
                System.out.println("|------------------------------------------|\n\n");

                int product_choice = scan.nextInt();

                if (product_choice == 0) {
                    app.signout();
                }
                int count;
                ArrayList<String> itms;
                int answer = 0;
                Map<Integer, String> menu = new HashMap<Integer, String>();

                switch (product_choice) {
                    case 1:
                        System.out.println("|--------------------|");
                        System.out.println("| Press 1 for Bakery |");
                        System.out.println("| Press 2 for Bread  |");
                        System.out.println("| Press 3 for Pastry |");
                        System.out.println("|--------------------|\n\n");
                        int bakery_choice = scan.nextInt();
                        if (bakery_choice == 0) {
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
                        if (grocery_choice == 0) {
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
                if (answer == 0) {
                    app.signout();
                }
                System.out.println("\n>>>>>>>----- You have chosen: " + menu.get(answer));
                String UPC = app.getUPC(menu.get(answer), app.getConnection());
                System.out.print(">>>>>>>----- Please enter the amount you want to buy: ");
                int amountToBuy= startup.nextInt();
                double priceToBuy= amountToBuy * Double.parseDouble(app.getPrice(UPC));
                System.out.println(String.format(">>>>>>>----- The total price is: %.2f", priceToBuy));
                System.out.println(">>>>>>>----- Do you still want to buy it?(Y/N)");
                Scanner buyoption = new Scanner(System.in);
                String option = buyoption.nextLine().toLowerCase();
                String customer_id = app.getCustomer_id(email);

                if (option.equals("y")) {
                    app.buyProduct(UPC, amountToBuy);
                    String order_id=app.genOrder(app.getConnection());
                    app.putOrder(order_id, customer_id, app.getConnection());
                    app.updateIncludes(UPC,order_id,String.valueOf(amountToBuy),app.getConnection());
                    app.updateCredit(String.valueOf(priceToBuy),customer_id);
                    System.out.println(">>>>>>>----- Congratulations, you have successfully bought " + menu.get(answer));
                    System.out.println(">>>>>>>----- Your order number is: "+ order_id);
                    System.out.println(">>>>>>>----- Your item will be shipped to you soon!");
                }
            }
        }

    }
}

