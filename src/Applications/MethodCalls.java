package Applications;

import org.h2.jdbc.JdbcSQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public abstract class MethodCalls {
    private Connection connection;
    static final String EMAIL_DOESNT_EXIST="Email Address Doesn't exist\nPlease Sign-up!";
    static final String INCORRECT_PW="Incorrect password, Please enter again";

    public void createConnection(String location,
                                 String user,
                                 String password){
        try {

            //This needs to be on the front of your location
            String url = "jdbc:h2:" + location;

            //This tells it to use the h2 driver
            Class.forName("org.h2.Driver");

            //creates the connection
            connection = DriverManager.getConnection(url,
                    user,
                    password);
        } catch (SQLException | ClassNotFoundException e) {
            //You should handle this better
            e.printStackTrace();
        }
    }

    /**
     * Checks if email is valid, if valid, returns welcome message
     * @param connection
     * @param email
     * @return
     */
    public String login(Connection connection, String email){
        String query="select first_name, last_name, password from customer where email = "+"\'"+email+"\'\n";
        String first_name;
        String last_name;
        String password;

        try {
            /**
             * Execute the query and return the result set
             */
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(query);
            result.next();

            first_name=result.getString("first_name");
            last_name=result.getString("last_name");
            password=result.getString("password");


            Scanner pw= new Scanner(System.in);
            System.out.print("Please Enter your Password: ");
            String password_input =pw.nextLine();

            // if incorrect password
            while(!password_input.equals(password)){
                System.out.println(INCORRECT_PW);
                System.out.print("Please Enter your Password: ");
                password_input = pw.nextLine();
            }
            result.close();
            stmt.close();
            return "Welcome! " +first_name+" "+last_name;


        } catch (SQLException e) {
            return EMAIL_DOESNT_EXIST;
        }
    }


    /**
     * Register the user
     * @param app this class object
     */
    public String register(MethodCalls app){
        String first_name;
        String last_name;
        String email;
        String num;
        String street;
        String city;
        String state;
        String zip;
        String password;
        String passwordCheck;
        String phone;
        String phonecheck="";
        String secondaryNum="";


        Scanner scanner= new Scanner(System.in);
        System.out.println("\n");
        System.out.println(">>>>>>>----- Signing you up!!!");
        System.out.println("|--------------------------------------------------|");
        System.out.print("| Please enter your first name: ");
        first_name = scanner.nextLine();
        System.out.print("| Please enter your last name:");
        last_name= scanner.nextLine();
        System.out.print("| Please enter your Email: ");
        email= scanner.nextLine();
        String check = app.login(app.getConnection(),email);
        if (check.startsWith("Welcome")){
            System.out.println(">>>>>>>----- Email already exists, please enter a new one");
        }else{
            System.out.print("| Please enter a password for your account: ");
            password = scanner.nextLine();
            System.out.print("| Please Re-enter Password: ");
            passwordCheck = scanner.nextLine();
            while(!passwordCheck.equals(password)){
                System.out.println(">>>>>>>----- Passwords Don't Match, Re-enter");
                passwordCheck = scanner.nextLine();
            }
            System.out.print("| Please enter your Street Number: ");
            num=scanner.nextLine();
            System.out.print("| Please enter your Street Name: ");
            street=scanner.nextLine();
            System.out.print("| Please enter your City: ");
            city=scanner.nextLine();
            System.out.print("| Please enter your State: ");
            state=scanner.nextLine();
            System.out.print("| Please enter your ZipCode: ");
            zip=scanner.nextLine();
            System.out.print("Please enter you phone number in this format XXX-XXX-XXXX: ");
            phone=scanner.nextLine();
            System.out.println("Do you a secondary number?(Y/N)");
            phonecheck=scanner.nextLine().toLowerCase();
            if(phonecheck.equals("y")){
                System.out.print("Please enter your secondary phone number in this format XXX-XXX-XXXX: ");
                secondaryNum=scanner.nextLine();
            }
            System.out.println("|--------------------------------------------------|");

            // add to database
            try{
                String count_query= "select count(customer_id) as count from customer";
                Statement stmt = connection.createStatement();

                ResultSet r= stmt.executeQuery(count_query);
                r.next();
                int count = r.getInt("count") + 1;

                // Building sql query to insert
                StringBuilder sb = new StringBuilder();

                sb.append("INSERT INTO customer (customer_id, first_name, last_name, num, street, city, state, zip, email, password, credit) VALUES");
                sb.append(String.format("(%d,\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\')",
                        count, first_name, last_name, num, street, city, state, zip, email, password, '0'));
                // executing the sql command to insert
                stmt.execute(sb.toString());
                System.out.println("Welcome! " +first_name+" "+last_name);
                String phone_query="";
                if(secondaryNum.equals("")) {
                    phone_query = "Insert into cust_phone values('"+count+"', '"+phone+"');";
                }else{
                    phone_query= "Insert into cust_phone values('"+count+"', '"+phone+"'), ('"+count+"', '"+secondaryNum+"');";
                }
                Statement phoneStm= connection.createStatement();
                phoneStm.execute(phone_query);
                r.close();
                stmt.close();

            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return email;
    }

    public ArrayList<String> viewItem(Connection connection, String item){
        ArrayList<String> arry= new ArrayList<String>();
        try{
            String query = "SELECT name \n" +
                    "  FROM "+item+" JOIN Product\n" +
                    "    ON Product.UPC = "+item+".UPC;";

            Statement stmt = connection.createStatement();

            ResultSet r= stmt.executeQuery(query);
            while(r.next()){
                arry.add(r.getString(1));
            }
            r.close();
            stmt.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return arry;
    }

    public String getUPC(String name, Connection connection){
        String retString="";
        try {
            String query = "SELECT UPC FROM  Product where name='" + name + "'";

            Statement stmt = connection.createStatement();

            ResultSet r = stmt.executeQuery(query);
            r.next();
            retString=r.getString(1);
            r.close();
            stmt.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return retString;
    }

    public String getPrice(String UPC){
        String price="";
        try {
            String query = "Select price from product \n" +
                    "where UPC = '"+UPC+"';";

            Statement stmt = connection.createStatement();

            ResultSet r = stmt.executeQuery(query);
            r.next();
            price =r.getString(1);
            r.close();
            stmt.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return price;
    }

    public void buyProduct(String UPC, int amountToBuy) {
        String amount = "";
        int updateAmt = 0;
        try {
            String query = "Select amount from contains \n" +
                    "where UPC = '" + UPC + "' and store_id = '5';";
            Statement stmt = connection.createStatement();

            ResultSet r = stmt.executeQuery(query);
            r.next();
            amount = r.getString(1);
            updateAmt = Integer.parseInt(amount) - amountToBuy;
            if(updateAmt<0){
                System.out.println("");
            }
            String updatequery = "Update contains\n" +
                    "set amount='" + updateAmt + "' where upc='" + UPC + "' and store_id='5';";

            Statement stm2 = connection.createStatement();
            stm2.execute(updatequery);
            r.close();
            stm2.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void buyProductStore(String UPC, int storeID) {
        String amount = "";
        int updateAmt = 0;

        try {
             String query = "Select amount from contains \n" +
                     "where UPC = '" + UPC + "' and store_id = '" + storeID + "';";
             Statement stmt = connection.createStatement();

             ResultSet r = stmt.executeQuery(query);
             r.next();
             amount = r.getString(1);
             updateAmt = Integer.parseInt(amount) - 1;
             String updatequery = "Update contains\n" +
                        "set amount='" + updateAmt + "' where upc='" + UPC + "' and store_id='" + storeID + "';";

                Statement stm2 = connection.createStatement();
                stm2.execute(updatequery);
                r.close();
                stm2.close();
                stmt.close();
            } catch (SQLException e) {
                System.out.println("Purchase unsuccessful, item does not exist in the store.");
            }
    }

    public boolean checkProductExist(String UPC, int storeID) {
        String count = "0";
        String query = "select count(*) from contains where UPC = '" + UPC + "' and store_id = '" + storeID + "';";
        try {
            Statement stmt = connection.createStatement();
            ResultSet r = stmt.executeQuery(query);
            r.next();
            count =r.getString(1);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if( Integer.parseInt(count) > 0 ) {
            return true;
        } else {
            return false;
        }
    }

    public String genrand() {
        Random rr = new Random();
        int low = 1000000;
        int high = 9999999;
        int result = rr.nextInt(high - low) + low;
        return String.valueOf(result);
    }

    /**
     * generate unique order_id
     * @param conn
     * @return
     */
    public String genOrder(Connection conn){
        String retString="";
        try {
            String query = "SELECT order_id FROM  orders";

            Statement stmt = connection.createStatement();

            ResultSet r = stmt.executeQuery(query);
            boolean found= false;
            String num="";

            while(true) {
                num = genrand();
                while (r.next()) {
                    String checkvalue = r.getString(1);
                    if (checkvalue.equals(num)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    break;
                }
            }
            retString=num;
            r.close();
            stmt.close();
        }catch(SQLException e){
            System.out.println("1");
            System.out.println(e.getMessage());
        }
        return retString;
    }


    public String getCustomer_id(String email){
        String id="";
        try {
            String query = "Select customer_id from customer \n" +
                    "where email = '"+email+"';";

            Statement stmt = connection.createStatement();

            ResultSet r = stmt.executeQuery(query);
            r.next();
            id =r.getString(1);
            r.close();
            stmt.close();
        }catch(SQLException e){
            System.out.println("2");
            System.out.println(e.getMessage());
        }
        return id;
    }

    public void putOrder(String order_id, String cust_id, Connection connection){
        try {
            String getdata = "Select * from customer where customer_id='"+cust_id+"'";
            Statement st = connection.createStatement();
            ResultSet set= st.executeQuery(getdata);
            set.next();
            String num = set.getString(4);
            String street= set.getString(5);
            String city= set.getString(6);
            String state = set.getString(7);
            String zipcode = set.getString(8);
            String email= set.getString(9);
            String query = "Insert into orders \n" +
                    "values('" + order_id + "', '" + num+"', '"+street+"', '"+city+"', '"+state+"', '"+zipcode+"', '"+email+"', '"+cust_id+"');";
            Statement stmt = connection.createStatement();
            stmt.execute(query);
            stmt.close();
        }catch (SQLException e){
            System.out.println("3");
            System.out.println(e.getMessage());
        }
    }

    public void signout(){
        System.out.println(">>>>>----You have successfully signedOut!!!!!-----<<<<<<");
        closeConnection();
        System.exit(0);
    }



    public Connection getConnection() {
        return connection;
    }

    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateIncludes(String UPC, String order_id, String amountToBuy, Connection connection){
        try {
            String query = "Insert into includes values('"+order_id+"', '"+UPC+"', '"+amountToBuy+"');";

            Statement stmt = connection.createStatement();

            stmt.execute(query);
            stmt.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

    }

    public void viewOrders(String id, Connection connection){
        try {
            String query = "SELECT o.order_id, i.amountToBuy, p.price, p.name\n" +
                    "FROM Orders o \n" +
                    "join includes i on o.order_id = i.order_id\n" +
                    "join product p on p.upc=i.upc \n" +
                    " where o.customer_id ='"+id+"';";

            Statement stmt = connection.createStatement();

            ResultSet r = stmt.executeQuery(query);

            while(r.next()){
                double totPrice= Double.parseDouble(r.getString(3))*Integer.parseInt(r.getString(2));

                System.out.println("|Order Number: "+r.getString(1)+ ",     Product name: "+r.getString(4)+",    price:  $"+ String.valueOf(totPrice)+"|");
                System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            r.close();
            stmt.close();
            System.out.println("\n\n");
        }catch(SQLException e){
            System.out.println("order error");
            System.out.println(e.getMessage());
        }

    }
    public void updateCredit(String price, String id){
        String credit = "";
        Double updateAmt = 0.0;
        try {
            String query = "Select credit from customer \n" +
                    "where customer_id = '" + id + "';";
            Statement stmt = connection.createStatement();
            ResultSet r = stmt.executeQuery(query);
            r.next();
            credit = r.getString(1);
            updateAmt = Double.parseDouble(credit) + Double.parseDouble(price);
            String updatequery = "Update customer\n" +
                    "set credit='" + updateAmt + "' where customer_id='" + id + "';";

            Statement stm2 = connection.createStatement();
            stm2.execute(updatequery);
            r.close();
            stmt.close();
            stm2.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void viewCredit(String id, Connection connection){
        try {
            String query = "SELECT credit from customer where customer_id='"+id+"';";

            Statement stmt = connection.createStatement();

            ResultSet r = stmt.executeQuery(query);

            r.next();
            System.out.println(">>>>>>>----- Credit = $"+r.getString(1));
            System.out.println("\n\n");
            r.close();
            stmt.close();
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }catch(SQLException e){
            System.out.println("order error");
            System.out.println(e.getMessage());
        }

    }

    public void deleteAccount(String id, Connection connection){
        try {
            String query = "Delete from customer where customer_id='" + id + "';";
            Statement st=connection.createStatement();
            st.execute(query);
            System.out.println(">>>>>----You have successfully Deleted Account!!!!!-----<<<<<<");
            closeConnection();
            System.exit(0);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

    }
}
