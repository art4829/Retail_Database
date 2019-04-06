package Applications;

import java.sql.*;

public class VendorApplication {
    private Connection connection;
    private final String EMAIL_DOESNT_EXIST="Email Address Doesn't exist\nPlease Sign-up!";
    private final String INCORRECT_PW="Incorrect password, Please enter again";

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

    public void executeQuery(Statement stmt, String query) throws SQLException {
        //check if it is select
        Boolean ret = stmt.execute(query);
        if (ret) {
            ResultSet result = stmt.executeQuery(query);
            ResultSetMetaData rsmd = result.getMetaData();
            int columnCount = rsmd.getColumnCount();
            // The column count starts from 1

            //ArrayList<String> column_names = new ArrayList<String>();
            for (int i = 1; i <= columnCount; i++) {
                String name = rsmd.getColumnName(i);
                //column_names.add(name);
                System.out.print(name + " ");
                // Do stuff with name
            }
            System.out.println();

            while (result.next()) {
                for (int i = 0; i < columnCount; i++) {
                    System.out.print(result.getString(i + 1) + " ");
                }
                System.out.println();
            }
            // STEP 5: Clean-up environment
            result.close();
        }
    }

    public static void main(String[] args) {
        VendorApplication app = new VendorApplication();
        // Hard drive location of database
        String location = "./retailDb/retailDb";
        String user = "cskid";
        String password = "retaildomain";
        //Create database connection
        app.createConnection(location, user, password);

        Connection conn = app.getConnection();

        //create view
        try {
            Statement stmt = conn.createStatement();

            String query = "create view v as select * from Reorder;";
            stmt.execute(query);

            //test: print the view
            query = "select * from view";
            app.executeQuery(stmt,query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
}
