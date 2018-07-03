package com.topica.daonc.jdbc.connectionpool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, InterruptedException {
        ConnectionPool connectionPool = new ConnectionPool("com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/sinhvien",
                "root", "",
                5, 7,true);

        Connection conn = connectionPool.getConnection();
        PreparedStatement prepStmt = conn.prepareStatement("SELECT * FROM student");

        ResultSet resultSet = prepStmt.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString("hoten"));
        }
        resultSet.close();
        prepStmt.close();
        conn.close();

        try {
            System.out.println("Waiting for conn release...");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //connectionPool.free(conn);

        Connection conn2 = connectionPool.getConnection();
        Connection conn3 = connectionPool.getConnection();
        Connection conn4 = connectionPool.getConnection();
        Connection conn5 = connectionPool.getConnection();
        Connection conn7 = connectionPool.getConnection();
        Connection conn8 = connectionPool.getConnection();

        System.out.println(connectionPool.display());

        Connection conn6 = connectionPool.getConnection();

        PreparedStatement ps = conn6.prepareStatement("SELECT * FROM student");

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getString("diachi"));
        }
    }
}
