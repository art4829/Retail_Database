package SQL;

//Class for SQL commands

public class SQLCommands {
    public String insert(String table, String [] values){
        String result = "insert into " + table + "(";
        for (int i = 0; i < values.length; i++){
            result += values[i];
            if (i == values.length)
                break;
            else
                result += ", ";
        }
        result += ")";
        return result;
    }

    public String select(String [] att, String table,  String where ){
        String result = "select ";
        for (int i = 0; i < att.length; i++) {
            result += att[i];
            if (i == att.length)
                result += " ";
            else
                result += ", ";
        }
        result += "from " + table + " ";
        if (where != null){
            result += "where " + where;
        }
        return result;
    }

    public String createTable(String intConst){
        String result = "create table " + intConst;
        return result;
    }
}
