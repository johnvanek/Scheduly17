package main.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Query {

    private static ResultSet resultSet;

    public static void executeQuery(String query) throws SQLException {
        Connection.makePreparedStatement(query, Connection.getConnection());
        PreparedStatement preparedStatement = Connection.getPreparedStatement();
        assert preparedStatement != null;
        resultSet = preparedStatement.executeQuery();
    }

    public static void executePreparedStatement(PreparedStatement ps) throws SQLException {
        resultSet = ps.executeQuery();
    }

    public static void assembleQueryIntoPreparedStatement() {
        //might not make this
    }

    public static ResultSet getResultSet() {
        return resultSet;
    }
}
