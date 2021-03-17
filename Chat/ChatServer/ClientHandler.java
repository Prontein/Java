package ru.geekbrains;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class ClientHandler {
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private String user;
    private SuperServer superServer;

    public ClientHandler(Socket socket, SuperServer superServer) {
        try {
            this.superServer = superServer;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            System.out.println("Установлено соединение с пользователем ");

            new Thread(() -> {
                authenticate();
                readMessages();
            }).start();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendMessage(MessageDTO dto) {
        try {
            out.writeUTF(dto.convertToJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void readMessages() {
        try {
            while (true) {
                String msg = in.readUTF();
                MessageDTO dto = MessageDTO.convertFromJson(msg);
                dto.setFrom(user);

                switch (dto.getMessageType()) {
                    case SEND_AUTH_MESSAGE -> authenticate();
                    case PUBLIC_MESSAGE -> superServer.broadcastMessage(dto);
                    case PRIVATE_MESSAGE -> superServer.sendPrivate(dto);
                    case ONLINE_STATUS -> closeHandler();
                }
            }
        } catch (SocketException e) {
            System.out.println("Соединение с пользователем " + getUser() + " разорвано");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private synchronized void authenticate() {
        System.out.println("Пожалуйста авторизуйтесь!");
        try {
            while (true) {
                socket.setSoTimeout(120000); // Устанавливаем время ожидания 2 минуты
                String authMessage = in.readUTF();
                MessageDTO dto = MessageDTO.convertFromJson(authMessage);
                String username = superServer.getAuthService().getUsernameAndPass(dto.getLogin(), dto.getPassword());
                MessageDTO send = new MessageDTO();
                if (!(username == null)) {
                    if (superServer.isUserAuth(username)) {
                        send.setMessageType(MessageType.ERROR_MESSAGE);
                        send.setBody("Такой пользователь уже в сети!");
                        sendMessage(send);
                        System.out.println("Такой пользователь уже в сети!");

                    } else {
                        send.setMessageType(MessageType.AUTH_CONFIRM);
                        user = username;
                        superServer.subscribe(this);
                        System.out.println("Успешная авторизация");
                        sendMessage(send);
                        socket.setSoTimeout(0);
                        break;
                    }
                } else {
                    send.setMessageType(MessageType.ERROR_MESSAGE);
                    send.setBody("Неверный логин или пароль!");
                    sendMessage(send);
                }
            }
        } catch (SocketTimeoutException e) { // В случае если в течении 2 минут от пользователя не приходило никаких данных, происходит отключение клиента и его информирование
            try {MessageDTO send = new MessageDTO();
                send.setMessageType(MessageType.ERROR_MESSAGE);
                send.setBody("Превышено время ожидания авторизации. Вы были отключены от сервера.");
                sendMessage(send);
                socket.close();
            } catch (IOException ioException) {
                System.out.println("Превышено время ожидания авторизации");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public synchronized void closeHandler() {
        try {
            superServer.unsubscribe(this);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUser() {
        return user;
    }



}
