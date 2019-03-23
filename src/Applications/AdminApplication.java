package Applications;


import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class AdminApplication {
    private Connection connection;

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

    public Connection getConnection(){
        return connection;
    }

    public void closeConnection(){
        try{
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        AdminApplication app = new AdminApplication();
        // Hard drive location of database
        String location = "./retailDb/retailDb";
        String user = "cskid";
        String password = "retaildomain";
        //Create database connection
        app.createConnection(location, user, password);

        Connection conn = app.getConnection();
        System.out.println("Enter sql command: ");
        Scanner scan = new Scanner(System.in);
        String query = scan.nextLine();
        String[] query_array = query.split(" ");

        /*for (int i=0; i<query_array.length; i++){
            System.out.println(query_array[i]);
        }*/
        try {

            //check if it is select
            Statement stmt = conn.createStatement();
            if (query_array[0].equals("select")){
                ResultSet result = stmt.executeQuery(query);
                ResultSetMetaData rsmd = result.getMetaData();
                int columnCount = rsmd.getColumnCount();
                // The column count starts from 1

                ArrayList<String> column_names=new ArrayList<String>();
                for (int i = 1; i <= columnCount; i++ ) {
                    String name = rsmd.getColumnName(i);
                    column_names.add(name);
                    System.out.print(name + " ");
                    // Do stuff with name
                }
                System.out.println();

                while(result.next()) {
                    for (int i=0; i<columnCount; i++){
                        System.out.print(result.getString(i+1) + " ");
                    }
                    System.out.println();
                }
                // STEP 5: Clean-up environment
                result.close();
            }
            else{
                stmt.executeQuery(query);
            }





        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
