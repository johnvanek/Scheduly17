package main.database;

import java.sql.ResultSet;
import java.sql.Statement;

public abstract class Query {
    private static String query;
    private static Statement statement;
    private static ResultSet resultSet;
    public abstract void assembleQuery(String queryArg);
}
