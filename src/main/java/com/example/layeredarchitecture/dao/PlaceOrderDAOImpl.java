package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.db.DBConnection;
import com.jfoenix.controls.JFXComboBox;

import java.sql.*;

public class PlaceOrderDAOImpl {

    public boolean existItem(String code) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT code FROM Item WHERE code=?");
        pstm.setString(1, code);
        return pstm.executeQuery().next();

    }

    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT id FROM Customer WHERE id=?");
        pstm.setString(1, id);
        return pstm.executeQuery().next();

    }

    public String generateNewId() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT oid FROM `Orders` ORDER BY oid DESC LIMIT 1;");

        return rst.next() ? String.format("OID-%03d", (Integer.parseInt(rst.getString("oid").replace("OID-", "")) + 1)) : "OID-001";
    }


    public void loadAllCustomerIds(JFXComboBox<String> cmbCustomerId) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
           Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Customer");

            while (rst.next()) {
               cmbCustomerId.getItems().add(rst.getString("id"));
           }
    }

    public void loadAllItemCodes(JFXComboBox<String> cmbItemCode) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT * FROM Item");
        while (rst.next()) {
            cmbItemCode.getItems().add(rst.getString("code"));
        }
    }
}
