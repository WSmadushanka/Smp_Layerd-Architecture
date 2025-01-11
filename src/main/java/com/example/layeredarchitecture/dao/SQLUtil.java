package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLUtil {

    public static <T> T execute(String sql) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement stm = connection.prepareStatement(sql);
//        stm.setString(1,sql);

        if (sql.startsWith("SELECT")) {
          return (T)  stm.executeQuery();
        }else {
          return (T) (Boolean)  (stm.executeUpdate()>0);
        }
    }
}
