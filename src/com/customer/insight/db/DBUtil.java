/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customer.insight.db;

import com.customer.insight.config.Config;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

/**
 *
 * @author TUNGLV su dung
 * https://laptrinh9x.wordpress.com/2017/09/29/cai-dat-va-su-dung-sqlite-manager/
 * de ket noi db sqlite
 */
public class DBUtil {

    static Logger logger = Logger.getLogger(DBUtil.class.getName());

    public static void main(String[] args) throws SQLException {
        connectDB("customer.db");
    }

    //tao ket noi den DB
    public static Connection connectDB(String fileName) throws SQLException {
        Connection con = null;

        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + fileName);
            System.out.println("tunglv4 ket noi thanh cong");
        } catch (Exception e) {
            logger.error("tunglv4 Loi ket noi den co so du lieu" + e.getMessage());
            con.close();
        }
        return con;
    }

    //kiem tra bang du lieu da tao chua
    //tableName : ten bang
    //true: bang da ton tai
    public static boolean tableExists(String tableName) throws SQLException {
        Connection conn = null;
        try {
            conn = connectDB(Config.DB_NAME);
            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getTables(null, null, tableName, null);
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            logger.error("tunglv4 loi kiem tra bang du lieu");
        } finally {
            conn.close();
        }
        return false;
    }

    public static void insertTest() {
        Connection c = null;
        Statement stmt = null;

        try {

            c = connectDB("test.db");
            stmt = c.createStatement();
            String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
                    + "VALUES (1, 'Paul', 32, 'California', 20000.00 );";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
                    + "VALUES (2, 'Allen', 25, 'Texas', 15000.00 );";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
                    + "VALUES (3, 'Teddy', 23, 'Norway', 20000.00 );";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
                    + "VALUES (4, 'Mark', 25, 'Rich-Mond ', 65000.00 );";
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }

    public static void createTableTest() {
        Connection c = null;
        Statement stmt = null;

        try {
            c = connectDB("test.db");

            stmt = c.createStatement();
            String sql = "CREATE TABLE COMPANY "
                    + "(ID INT PRIMARY KEY     NOT NULL,"
                    + " NAME           TEXT    NOT NULL, "
                    + " AGE            INT     NOT NULL, "
                    + " ADDRESS        CHAR(50), "
                    + " SALARY         REAL)";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }

    public static void selectTableTest() {
        Connection c = null;
        Statement stmt = null;
        try {
            c = connectDB("test.db");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM COMPANY;");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String address = rs.getString("address");
                float salary = rs.getFloat("salary");

                System.out.println("ID = " + id);
                System.out.println("NAME = " + name);
                System.out.println("AGE = " + age);
                System.out.println("ADDRESS = " + address);
                System.out.println("SALARY = " + salary);
                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }

    public static void dropTable(String tableName) throws SQLException {
        Connection c = null;
        Statement stmt = null;
        try {
            c = connectDB("test.db");

            stmt = c.createStatement();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        } finally {
            stmt.close();
            c.close();
        }
        System.out.println("Operation done successfully");
    }

}
