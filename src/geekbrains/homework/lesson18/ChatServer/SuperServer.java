package ru.geekbrains;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class SuperServer {

    private AuthService authService;
    private List<ClientHandler> clientHandlers;
    private List<String> onlineUsers = new ArrayList<>();
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement ps;


    public SuperServer () {
        try {
            connect();
            statement.executeUpdate("create table if not exists users (id integer primary key autoincrement, nickname text, login text, password text);");
//            newUsers();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }

        try (ServerSocket serverSocket = new ServerSocket(65500)) {
            System.out.println("Сервер запущен");

            authService = new AuthServiceManager();
            authService.start();
            clientHandlers = new LinkedList<>();

            while (true) {
                System.out.println("Ожидание подключение клиента...");
                Socket socket = serverSocket.accept();
                System.out.println("Клиент подключился");
                new ClientHandler(socket, this);
            }

        } catch (IOException e) {
            e.printStackTrace();
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

    public synchronized void broadcastMessage(MessageDTO dto) { // Сообщения для общего чата
        for (ClientHandler clientHandler : clientHandlers) {
            clientHandler.sendMessage(dto);
        }
    }

    public synchronized void subscribe(ClientHandler c) { // После успешной авторизации добавляем пользователя в список onlineUsers и высылаем его всем пользователям
        clientHandlers.add(c);
        MessageDTO dto = new MessageDTO();
        dto.setMessageType(MessageType.ONLINE_STATUS);
        System.out.println(c.getUser());
        onlineUsers.add(c.getUser());

        System.out.println(onlineUsers);
        dto.setOnlineList(onlineUsers);

        for (ClientHandler clientHandler : clientHandlers) {
            clientHandler.sendMessage(dto);
        }
    }

    public synchronized void unsubscribe(ClientHandler c) { // После отсоединения пользователь удаляется из списка onlineUsers. Список высылаем всем подключенным пользователям.
        clientHandlers.remove(c);
        MessageDTO dto = new MessageDTO();
        dto.setMessageType(MessageType.ONLINE_STATUS);
        onlineUsers.remove(c.getUser());
        dto.setOnlineList(onlineUsers);

        for (ClientHandler clientHandler : clientHandlers) {
             clientHandler.sendMessage(dto);
        }
    }

    public AuthService getAuthService() {
        return authService;
    }

    public synchronized void sendPrivate(MessageDTO dto) { //Отправка личных сообщений

        String privatUsername = dto.getTo();

        for (ClientHandler clientHandler : clientHandlers) {
            if (clientHandler.getUser().equals(privatUsername))
                clientHandler.sendMessage(dto);
        }
    }

    public List<String> getOnlineUsers() {
        return onlineUsers;
    }

    public boolean isUserAuth(String username) { // Проверяем есль ли такой пользователь уже в сети
        return getOnlineUsers().contains(username);

    }
    private static void newUsers () throws SQLException {
        statement.executeUpdate("insert into users (nickname, login, password) values ('user3', 'log3', 'pass3');");
    }
    public void changeUserName(MessageDTO dto) throws SQLException, ClassNotFoundException {  // меняем в базе имя пользователя
        String from = dto.getFrom();
        String newUsername = dto.getUsername();
        connect();

        ps = connection.prepareStatement("update users set nickname = ? where nickname = ? ");
        ps.setString(1, newUsername);
        ps.setString(2, from);
        ps.execute();

    }

    public String compareLogAndPass(String log, String pass) { // Проверем есть ли в базе пользователь с таким логином и паролем
        try {
            connect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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

    public synchronized void subscribeNewUsername(ClientHandler c, MessageDTO oldName) { // заменяет имя пользователя в листе контактов у всех пользователей чата
        MessageDTO dto = new MessageDTO();
        dto.setMessageType(MessageType.ONLINE_STATUS);
        onlineUsers.remove(oldName.getFrom());
        onlineUsers.add(c.getUser());
        dto.setOnlineList(onlineUsers);

        for (ClientHandler clientHandler : clientHandlers) {
            clientHandler.sendMessage(dto);
        }
    }

}



