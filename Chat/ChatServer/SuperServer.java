package ru.geekbrains;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class SuperServer {

    private AuthService authService;
    private List<ClientHandler> clientHandlers;
    private List<String> onlineUsers = new ArrayList<>();

    public SuperServer () {
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


}



