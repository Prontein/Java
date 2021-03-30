package ru.geekbrains;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

public class OneServerManager  {

    private String host;
    private int port;
    private Socket socket;
    private JTextArea messaging;
    private DataInputStream in;
    private DataOutputStream out;
    private AuthForm AuthForm;
    private String[] onlineListArray ;
    private JList<String> contactList;
    private List<String> onlineList ;
    private String user;

    public  OneServerManager(String host, int port, JTextArea messaging, AuthForm AuthForm, JList<String> contactList) throws IOException {
        this.contactList = contactList;
        this.host = host;
        this.port = port;
        this.socket = new Socket(host, port);
        this.messaging = messaging;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
        this.AuthForm = AuthForm;

        new Thread(() -> {
            try {
                while (true) {

                    String message = in.readUTF();
                    MessageDTO dto = MessageDTO.convertFromJson(message);
                    switch (dto.getMessageType()) {

                        case PUBLIC_MESSAGE, PRIVATE_MESSAGE ->
                                readDTOMsg(dto);

                        case AUTH_CONFIRM -> AuthForm.setVisible(false);
                        case ONLINE_STATUS -> {
                            onlineList = dto.getOnlineList();
                            convertListToArray(onlineList);  // Отправляем в метод преобразования из листа в массив
                            contactList.setListData(onlineListArray); // Заменяем список контактов массивом с пользователями полученным от сервера
                        }
                        case ERROR_MESSAGE -> AuthForm.setInfo(dto.getBody()); // Вызываем метод в окне авторизации для установки информации об ошибке

                    }

                    System.out.println("Клиент получил :" + message);

                    Thread.sleep(200);

                }
            } catch (SocketException e) {
                System.out.println("Соединение разорвано");
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

        }).start();
    }

    private void readDTOMsg (MessageDTO dto) {
        if (dto.getMessageType() == MessageType.PRIVATE_MESSAGE) {
            messaging.append("Личное сообщение от пользователя: " + dto.getFrom() + "\n" + dto.getBody() + System.lineSeparator());

        } else
        messaging.append("Сообщение от пользователя: " + dto.getFrom() + "\n" + dto.getBody() + System.lineSeparator());
    }

    public synchronized void writeMsg (String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized String[] convertListToArray(List<String> onlineList) { // Преобразует список в массив
        onlineListArray = (String[])onlineList.toArray(new String[onlineList.size()]);
        return onlineListArray;
    }

    public synchronized void socketClose () throws IOException {
        socket.close();

    }
}






