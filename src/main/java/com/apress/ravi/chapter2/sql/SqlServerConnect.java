package com.apress.ravi.chapter2.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;

import com.apress.ravi.chapter2.dto.AssetsDTO;

public class SqlServerConnect {
    private static String connectionUrl =
    "jdbc:sqlserver://localhost:1433;"
    + "database=assets;"
    + "user=sa;"
    + "password=971026;"
    + "encrypt=true;"
    + "trustServerCertificate=true;"
    + "loginTimeout=30;";

    public static Connection getConnection(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(connectionUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static List<AssetsDTO> findAll() {
		List<AssetsDTO> assets = new ArrayList<AssetsDTO>();
        Connection conn = getConnection();
        try {
            Statement statement = conn.createStatement();
            String selectSql = "SELECT * from dbo.asset";
            ResultSet resultSet = statement.executeQuery(selectSql);

            while (resultSet.next()) {
                AssetsDTO currAsset = new AssetsDTO();
                currAsset.setId(resultSet.getLong(1));
                currAsset.setName(resultSet.getString(2));
                currAsset.setManufacturer(resultSet.getString(3));
                currAsset.setPrice(resultSet.getFloat(4));
                assets.add(currAsset);
            }
            conn.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
		return assets;
	}

    
    public static AssetsDTO findById(Long id) {
		AssetsDTO asset = null;
        Connection conn = getConnection();
        try {
            Statement statement = conn.createStatement();
            String selectSql = "SELECT * from dbo.asset where asset_id="+String.valueOf(id)+"; ";
            System.out.println(selectSql);
            ResultSet resultSet = statement.executeQuery(selectSql);

            asset = new AssetsDTO();
            Integer cnt = 0;
            while (resultSet.next()) {
                cnt += 1;
                asset.setId(resultSet.getLong(1));
                asset.setName(resultSet.getString(2));
                asset.setManufacturer(resultSet.getString(3));
                asset.setPrice(resultSet.getFloat(4));
                System.out.println(resultSet.getLong(1));
            }
            if(cnt==0){
                asset = null;
            }
            conn.close();
        }
        catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
        }
		return asset;
	}

    
    public static AssetsDTO findByName(String name) {
		AssetsDTO asset = null;
        Connection conn = getConnection();
        try {
            Statement statement = conn.createStatement();
            String selectSql = "SELECT * from dbo.asset where asset_name='"+String.valueOf(name)+"';";
            System.out.println(selectSql);
;
            ResultSet resultSet = statement.executeQuery(selectSql);
            
            asset = new AssetsDTO();
            Integer cnt = 0;
            while (resultSet.next()) {
                cnt += 1;
                asset.setId(resultSet.getLong(1));
                asset.setName(resultSet.getString(2));
                asset.setManufacturer(resultSet.getString(3));
                asset.setPrice(resultSet.getFloat(4));
                System.out.println(resultSet.getLong(1));
            }
            if(cnt==0){
                asset = null;
            }
            conn.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
		return asset;
	}

    public static Integer findMaxId() {
        Integer maxId = 1;
        Connection conn = getConnection();
        try {
            Statement statement = conn.createStatement();
            String selectSql = "SELECT max(asset_id) from dbo.asset;";
            
            ResultSet resultSet = statement.executeQuery(selectSql);

            while (resultSet.next()) {
                maxId = Integer.parseInt(resultSet.getString(1));
                System.out.println("maxId:"+resultSet.getString(1));
            }
            conn.close();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
		return maxId;
	}
    
    public static void save(AssetsDTO asset) {
        Integer maxId = findMaxId() +1;
        ResultSet resultSet = null;
        
        Connection conn = getConnection();
        try {
            String insertSql = "INSERT INTO dbo.asset (asset_id,asset_name,manufacturer,price) VALUES ("
                + maxId +",'"+ asset.getName() +"','"+ asset.getManufacturer() +"',"+ asset.getPriceStr()+");" ;
            PreparedStatement prepsInsertProduct = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            prepsInsertProduct.execute();
            resultSet = prepsInsertProduct.getGeneratedKeys();

            while (resultSet.next()) {
                System.out.println("Generated: " + resultSet.getString(1));
            }
            conn.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
	}

    
    public static void delete(Long id) {
        Connection conn = getConnection();

        try {
            String deleteSql = "DELETE from dbo.asset WHERE asset_id = "+id.toString()+";";
            PreparedStatement prepsDeleteProduct = conn.prepareStatement(deleteSql);
            prepsDeleteProduct.execute();
            // resultSet = prepsInsertProduct.getGeneratedKeys();

            // while (resultSet.next()) {
            //     System.out.println("Generated: " + resultSet.getString(1));
            // }
        }
    
        catch (SQLException e) {
            e.printStackTrace();
        }
	}

    
    public static void saveAndFlush(AssetsDTO asset) {
        String insertSql = "update dbo.asset set asset_name = '"+ asset.getName() +"',manufacturer='"+ 
            asset.getManufacturer() +"',price="+ asset.getPriceStr() +"where asset_id ="+ asset.getIdStr() +";";
        System.out.println(insertSql);
        ResultSet resultSet = null;

        try (Connection connection = DriverManager.getConnection(connectionUrl);
                PreparedStatement prepsInsertProduct = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);) {

            prepsInsertProduct.execute();
            resultSet = prepsInsertProduct.getGeneratedKeys();

            while (resultSet.next()) {
                System.out.println("Updated: " + resultSet.getString(1));
            }
        }
    
        catch (SQLException e) {
            e.printStackTrace();
        }
	}
}
