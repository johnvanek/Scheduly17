package main.database;

import java.sql.ResultSet;
import java.sql.Statement;

public abstract class Query {
    private static String query;
    private static Statement statement;
    private static ResultSet resultSet;

    //TODO make classes that represent the different queries for each model.
    public abstract void assembleQuery(String queryArg);
}
