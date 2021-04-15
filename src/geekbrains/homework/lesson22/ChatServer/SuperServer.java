package ru.geekbrains;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SuperServer {

    private AuthService authService;
    private List<ClientHandler> clientHandlers;
    private List<String> onlineUsers = new ArrayList<>();
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement ps;
    private ExecutorService executorService;
    public static final Logger LOGGER = LogManager.getLogger(SuperServer.class);


    public SuperServer () {

        try (ServerSocket serverSocket = new ServerSocket(65500)) {
            LOGGER.info("Сервер запущен");
//            System.out.println("Сервер запущен");
            Serverdb.startDB();
            authService = new AuthServiceManager();
            authService.start();
            clientHandlers = new LinkedList<>();
            executorService = Executors.newCachedThreadPool();

            while (true) {
                LOGGER.info("Ожидание подключение клиента...");
//                System.out.println("Ожидание подключение клиента...");
                Socket socket = serverSocket.accept();
                LOGGER.info("Клиент подключился");
//                System.out.println("Клиент подключился");

                new ClientHandler(socket, this);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdownNow();
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
//        System.out.println(c.getUser());
        onlineUsers.add(c.getUser());
//        LOGGER.info(onlineUsers);
//        System.out.println(onlineUsers);
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

    public ExecutorService getExecutorService() {
        return executorService;
    }
}



