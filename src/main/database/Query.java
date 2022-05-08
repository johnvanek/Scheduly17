package main.database;

import java.sql.ResultSet;
import java.sql.Statement;

public class Query {
    private static String query;
    private static Statement statement;
    private static ResultSet resultSet;

    public static void assembleQuery(String queryArg){
        query = queryArg;
        statement = Connection.makePreparedStatement(query,);
    }
}
