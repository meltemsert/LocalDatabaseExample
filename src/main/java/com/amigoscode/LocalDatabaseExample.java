package com.amigoscode;

import java.sql.*;

public class LocalDatabaseExample {
    public static void main(String[] args) {
        //Mysql cantainer bağlantı bilgileri

        String jdbcUrl = "jdbc:mysql://localhost:3307/testdb";
        String username = "root";
        String password = "123456";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            System.out.println("Successfully connected to the database");

            // Tablo oluşturma sorgusu
            String createTableQuery = "CREATE TABLE IF NOT EXISTS ad_soyad (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(255)," +
                    "surname VARCHAR(255))";

            try(PreparedStatement preparedStatement = connection.prepareStatement(createTableQuery)){
                preparedStatement.executeUpdate(createTableQuery);
                System.out.println("Table Created");
            }

            // PreparedStatement kullanarak sorgu örneği
            String insertQuery = "INSERT INTO ad_soyad (name, surname) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, "Burcu");
                preparedStatement.setString(2, "Sert");
                preparedStatement.executeUpdate();
            }

            // Verileri sorgula
            String selectQuery = "SELECT * FROM ad_soyad";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                {

                    while (resultSet.next())
                        System.out.println("Name: " +
                                resultSet.getString("name") + ", " +
                                "Surname: " + resultSet.getString("surname"));
                }
            }

            // Bir veriyi sil
            String deleteQuery = "DELETE FROM ad_soyad WHERE name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setString(1, "Çiğdem");
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}