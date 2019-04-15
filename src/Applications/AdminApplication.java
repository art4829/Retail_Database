package Applications;


import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class AdminApplication {
    /**the connection with the database*/
    private Connection connection;

    /**
     * create connection with database
     * @param location -the path
     * @param user - username
     * @param password - password
     * */
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
            System.out.println(e.getMessage());
        }
    }

    /**
     * gets the connection
     * @return - return connection
     */
    public Connection getConnection(){
        return connection;
    }

    /**
     * clsoe the connection
     */
    public void closeConnection(){
        try{
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * execute the query with the given statement
     * If it returns the resultset, (ex) when you use select, it will print out
     * the table on the console
     * @param stmt - the statement
     * @param query - the query
     * @throws SQLException - exception on sql
     */
    public void executeQuery(Statement stmt, String query) throws SQLException {
        //check if it is select
        try {
            boolean ret = stmt.execute(query);
            if (ret) {
                ResultSet result = stmt.executeQuery(query);
                ResultSetMetaData rsmd = result.getMetaData();
                int columnCount = rsmd.getColumnCount();
                // The column count starts from 1quit

                ArrayList<String> column_names = new ArrayList<String>();
                for (int i = 1; i <= columnCount; i++) {
                    String name = rsmd.getColumnName(i);
                    column_names.add(name);
                    //System.out.print(name + " ");
                    System.out.format("|%-30s ",name);
                    // Do stuff with name
                }
                System.out.println();

                for (int i = 1; i <= columnCount; i++) {
                    for (int j=0; j<30; j++)
                        System.out.print("_");
                }
                System.out.println();

                while (result.next()) {
                    for (int i = 0; i < columnCount; i++) {
                        //System.out.print(result.getString(i + 1) + " ");
                        System.out.format("|%-30s ",result.getString(i+1));
                    }
                    System.out.println();
                }
                // STEP 5: Clean-up environment
                result.close();
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * the main application
     * checks the password and login
     * gets the sql queries and execute them
     * Keep do it until user prompts 'quit'
     * @param args - command line argument
     */
    public static void main(String[] args) {
        //login
        String pw;
        do {
            System.out.println("Please enter password:");
            Scanner scan2 = new Scanner(System.in);
            pw = scan2.nextLine();
        } while (!pw.equals("retaildomain"));

        AdminApplication app = new AdminApplication();
        // Hard drive location of database
        String location = "./retailDb/retailDb";
        String user = "cskid";
        String password = "retaildomain";

        //Create database connection
        app.createConnection(location, user, password);

        Connection conn = app.getConnection();

        System.out.println("Enter sql command ('quit' to stop) : ");
        Scanner scan = new Scanner(System.in);
        String query = scan.nextLine();
        while (!query.equals("quit")) {
            try {

                Statement stmt = conn.createStatement();

                //check if returns
                app.executeQuery(stmt,query);

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Enter sql command ('quit' to stop) : ");
            scan = new Scanner(System.in);
            query = scan.nextLine();
        }
        app.closeConnection();
    }

}
