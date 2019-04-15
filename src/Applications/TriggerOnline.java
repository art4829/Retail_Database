/*
 * Copyright 2004-2019 H2 Group. Multiple-Licensed under the MPL 2.0,
 * and the EPL 1.0 (https://h2database.com/html/license.html).
 * Initial Developer: H2 Group
 */
package Applications;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.api.Trigger;

/**
 * This sample application shows how to use database triggers.
 */
public class TriggerOnline extends MethodCalls {

    /**
     * generate unique order_id
     * @param conn
     * @return
     */
    public static String genReorder(Connection conn){
        String retString="";
        try {
            String query = "SELECT reorder_id FROM  reorder";

            Statement stmt = conn.createStatement();

            ResultSet r = stmt.executeQuery(query);
            boolean found= false;
            String num="";
            MethodCalls methodCalls = new TriggerOnline();

            while(true) {
                num = methodCalls.genrand();
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
        }catch(SQLException e){
            System.out.println("1");
            System.out.println(e.getMessage());
        }
        return retString;
    }

    /**
     * This method is called when executing this sample application from the
     * command line.
     *
     * @param args the command line parameters
     */
    public static void main(String... args) throws Exception {
        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:./retailDb/retailDb", "cskid", "retaildomain");
        Statement stat = conn.createStatement();

//        stat.execute("Drop trigger updateOnline");
//        stat.execute("CREATE TRIGGER updateOnline " +
//                "AFTER UPDATE ON CONTAINS FOR EACH ROW " +
//               "CALL \"Applications.TriggerOnline$MyTrigger\" ");
//

//        stat.execute("INSERT INTO INVOICE VALUES(1, 10.0)");
//        stat.execute("INSERT INTO INVOICE VALUES(2, 19.95)");
//        stat.execute("UPDATE INVOICE SET AMOUNT=20.0 WHERE ID=2");
//        stat.execute("DELETE FROM INVOICE WHERE ID=1");
//        stat.execute("Insert into contains values('2','915000809965','11')");
//        stat.execute("Update contains set amount = '9' where UPC='413874160000' and store_id = '1'");

        ResultSet rs;
        rs = stat.executeQuery("SELECT * FROM reorder");

        while( rs.next()){
            System.out.println(rs.getString(1)+"     "+rs.getString(2)+"       "+rs.getString(3));
        }
        rs.close();
        stat.close();
        conn.close();
    }

    /**
     * This class is a simple trigger implementation.
     */
    public static class MyTrigger implements Trigger {

        /**
         * Initializes the trigger.
         *
         * @param conn a connection to the database
         * @param schemaName the name of the schema
         * @param triggerName the name of the trigger used in the CREATE TRIGGER
         *            statement
         * @param tableName the name of the table
         * @param before whether the fire method is called before or after the
         *            operation is performed
         * @param type the operation type: INSERT, UPDATE, or DELETE
         */
        @Override
        public void init(Connection conn, String schemaName,
                         String triggerName, String tableName, boolean before, int type) {
            // initialize the trigger object is necessary
        }

        /**
         * This method is called for each triggered action.
         *
         * @param conn a connection to the database
         * @param oldRow the old row, or null if no old row is available (for
         *            INSERT)
         * @param newRow the new row, or null if no new row is available (for
         *            DELETE)
         * @throws SQLException if the operation must be undone
         */
        @Override
        public void fire(Connection conn,
                         Object[] oldRow, Object[] newRow)
                throws SQLException {
            if (newRow != null) {
                String amount= (String) newRow[2];
                int amountInt= Integer.parseInt(amount);
                Statement stat = conn.createStatement();
                ResultSet rs = stat.executeQuery("select vendor_id from product " +
                        "where UPC = '" + oldRow[1] + "';");
                rs.next();
                String vendor_id = rs.getString(1);
                if (amountInt<10){
                    System.out.println("true");
                    PreparedStatement prep=conn.prepareStatement("insert into reorder values" +
                            "('"+genReorder(conn)+"','"+vendor_id+"','"+oldRow[1] + "','50',null,null,'" + oldRow[0] +"');");
                    prep.execute();
                }
            }

        }

        @Override
        public void close() {
            // ignore
        }

        @Override
        public void remove() {
            // ignore
        }

    }

}