package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.ItemDTO;
import com.example.layeredarchitecture.view.tdm.ItemTM;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class ItemDAOImpl {

    public ArrayList<ItemDTO> loadAllItems() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT * FROM Item");

        ArrayList<ItemDTO> itemDTOArrayList = new ArrayList<>();

        while (rst.next()) {
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setCode(rst.getString("code"));
            itemDTO.setDescription(rst.getString("description"));
            itemDTO.setUnitPrice(rst.getBigDecimal("unitPrice"));
            itemDTO.setQtyOnHand(rst.getInt("qtyOnHand"));
            itemDTOArrayList.add(itemDTO);

        }
        return itemDTOArrayList;
}

public void deleteItem(String code) throws SQLException, ClassNotFoundException {
    Connection connection = DBConnection.getDbConnection().getConnection();
    PreparedStatement pstm = connection.prepareStatement("DELETE FROM Item WHERE code=?");
    pstm.setString(1, code);
    pstm.executeUpdate();
}

public void saveItem(String code, String description, BigDecimal unitPrice,Integer qtyOnHand) throws SQLException, ClassNotFoundException {
    Connection connection = DBConnection.getDbConnection().getConnection();
    PreparedStatement pstm = connection.prepareStatement("INSERT INTO Item (code, description, unitPrice, qtyOnHand) VALUES (?,?,?,?)");
    pstm.setString(1, code);
    pstm.setString(2, description);
    pstm.setBigDecimal(3, unitPrice);
    pstm.setInt(4, qtyOnHand);
    pstm.executeUpdate();
}


    public void updateItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
               PreparedStatement pstm = connection.prepareStatement("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?");
                pstm.setString(1, itemDTO.getDescription());
                pstm.setBigDecimal(2, itemDTO.getUnitPrice());
                pstm.setInt(3, itemDTO.getQtyOnHand());
                pstm.setString(4, itemDTO.getCode());
                pstm.executeUpdate();
    }

    public boolean existItem(String code) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT code FROM Item WHERE code=?");
        pstm.setString(1, code);
        return pstm.executeQuery().next();

    }

    public String generateNewId() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        ResultSet rst = connection.createStatement().executeQuery("SELECT code FROM Item ORDER BY code DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("code");
            int newItemId = Integer.parseInt(id.replace("I00-", "")) + 1;
            return String.format("I00-%03d", newItemId);
        } else {
            return "I00-001";
        }
    }
}
