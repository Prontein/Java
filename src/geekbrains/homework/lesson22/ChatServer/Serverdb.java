package ru.geekbrains;

import java.sql.*;

public class Serverdb {
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement ps;

    public static void startDB () {
        new Serverdb();
    }

    private Serverdb () {
        try {
            connect();
            statement.executeUpdate("create table if not exists users (id integer primary key autoincrement, nickname text, login text, password text);");
//            newUsers();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        } finally {
            disconnect();
        }

    }

    private static void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:usersdb.db"); // подключение к бд
        statement = connection.createStatement(); // запрос к бд
    }

    private static void disconnect () {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private static void newUsers () throws SQLException {
        statement.executeUpdate("insert into users (nickname, login, password) values ('user3', 'log3', 'pass3');");
    }

    public static void changeUserName(MessageDTO dto) throws SQLException, ClassNotFoundException {  // меняем в базе имя пользователя
        String from = dto.getFrom();
        String newUsername = dto.getUsername();
        connect();

        ps = connection.prepareStatement("update users set nickname = ? where nickname = ? ");
        ps.setString(1, newUsername);
        ps.setString(2, from);
        ps.execute();
        disconnect();
    }

    protected static String compareLogAndPass(String log, String pass) { // Проверем есть ли в базе пользователь с таким логином и паролем
        try {
            connect();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        try (ResultSet rs = statement.executeQuery("select login, password, nickname from users;")){
            while (rs.next()) {
                if (rs.getString("login").equals(log) && rs.getString("password").equals(pass)) return rs.getString("nickname");

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

}
