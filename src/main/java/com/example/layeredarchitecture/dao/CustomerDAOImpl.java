package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.controller.PlaceOrderFormController;
import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.CustomerDTO;
import com.jfoenix.controls.JFXComboBox;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT * FROM Customer");

        ArrayList<CustomerDTO> customerDTOArrayList = new ArrayList<>();

        while (rst.next()) {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setId(rst.getString("id"));
            customerDTO.setName(rst.getString("name"));
            customerDTO.setAddress(rst.getString("address"));

            customerDTOArrayList.add(customerDTO);
        }
        return customerDTOArrayList;
    }

    @Override
    public void saveCustomer(String id, String name, String address) throws SQLException, ClassNotFoundException {
 //        Connection connection = DBConnection.getDbConnection().getConnection();
//        PreparedStatement pstm = connection.prepareStatement("INSERT INTO Customer (id,name, address) VALUES (?,?,?)");
//        pstm.setString(1, id);
//        pstm.setString(2, name);
//        pstm.setString(3, address);
//        pstm.executeUpdate();
        SQLUtil.execute("INSERT INTO Customer (id,name, address) VALUES (?,?,?)",
                CustomerDTO.);

    }

    @Override
    public void updateCustomer(String name, String address, String id) throws SQLException, ClassNotFoundException {
//        Connection connection = DBConnection.getDbConnection().getConnection();
//        PreparedStatement pstm = connection.prepareStatement("UPDATE Customer SET name=?, address=? WHERE id=?");
//        pstm.setString(1, name);
//        pstm.setString(2, address);
//        pstm.setString(3, id);
//        pstm.executeUpdate();
        SQLUtil.execute("UPDATE Customer SET name=?, address=? WHERE id=?"),
    }

    @Override
    public void deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("DELETE FROM Customer WHERE id=?");
        pstm.setString(1, id);
        pstm.executeUpdate();
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        ResultSet rst = connection.createStatement().executeQuery("SELECT id FROM Customer ORDER BY id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("id");
            int newCustomerId = Integer.parseInt(id.replace("C00-", "")) + 1;
            return String.format("C00-%03d", newCustomerId);
        } else {
            return "C00-001";
        }
    }


//    public void loadAllCustomerIds() throws SQLException, ClassNotFoundException {
//
//        PlaceOrderFormController placeOrderFormController = new PlaceOrderFormController();
//
//
//        Connection connection = DBConnection.getDbConnection().getConnection();
//            Statement stm = connection.createStatement();
//            ResultSet rst = stm.executeQuery("SELECT * FROM Customer");
//
//            while (rst.next()) {
//                placeOrderFormController.cmbCustomerId.getItems().add(rst.getString("id"));
//            }
//
//    }

    @Override
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT id FROM Customer WHERE id=?");
        pstm.setString(1, id);
        return pstm.executeQuery().next();

    }

    @Override
    public CustomerDTO searchCustomer(String newValue) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Customer WHERE id=?");
                       pstm.setString(1, newValue + "");
                       ResultSet rst = pstm.executeQuery();
                       rst.next();
                       return new CustomerDTO(newValue + "id", rst.getString("name"), rst.getString("address"));
    }
}
