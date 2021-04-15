package ru.geekbrains;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
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
    private File file;
    private List <String> listOfHistory = new ArrayList<>();

    public  OneServerManager(String host, int port, JTextArea messaging, AuthForm AuthForm, JList<String> contactList, ChatWindow chatWindow) throws IOException {
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

                        case PUBLIC_MESSAGE, PRIVATE_MESSAGE -> {
                            readDTOMsg(dto);
//                            writeInFile(dto);
                            MessageHistory.writeInFile(dto, file);
                            messaging.setCaretPosition(messaging.getDocument().getLength());
                        }
                        case AUTH_CONFIRM -> {
                            AuthForm.setVisible(false);
//                            fileHistory(dto.getLogin());
                            this.file = MessageHistory.fileHistory(dto.getLogin(), file);
//                            readFromHistory();
                            MessageHistory.readFromHistory(file, listOfHistory, messaging);
                            messaging.setCaretPosition(messaging.getDocument().getLength());
                            chatWindow.changeTitle(dto.getUsername());

                        }
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

//    private void fileHistory (String login) {
//        try {
//            File file = new File("history_" + login + ".txt");
//            if (file.createNewFile()) {
//                System.out.println("Файл создан");
//
//            } else
//                System.out.println("Файл уже существует");
//
//            this.file = file;
//
//        }
//
//        catch (Exception e) {
//            System.err.println(e);
//        }
//    }

//    private void writeInFile (MessageDTO dto) throws IOException {
//        System.out.println(file.getName());
//        PrintWriter writer = new PrintWriter((new FileWriter(""+file.getName()+"", true)));
//        if (dto.getMessageType() == MessageType.PRIVATE_MESSAGE) {
//            writer.println("Личное сообщение от пользователя: " + dto.getFrom() + "\n" + dto.getBody());
//
//        } else {
//            writer.println("Сообщение от пользователя: " + dto.getFrom() + "\n" + dto.getBody());
//        }
//        writer.close();
//    }

//    private void readFromHistory () throws IOException {
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader(file));
//            String line = "";
//            while ((line = reader.readLine()) != null) {
//                listOfHistory.add(line);
//            }
//            if (listOfHistory.size() > 200) { // в случае если строк в истории больше 200 ( В моем случае информационная строка от кого пришло сообщение тоже считается, поэтому для последних 100 сообщений идет 200 строк
//                for (int i = listOfHistory.size() - 200; i <= listOfHistory.size(); i++) {
//                    messaging.append(listOfHistory.get(i - 1) + System.lineSeparator());
//                }
//            } else {
//                for (int i = 1; i <= listOfHistory.size(); i++) {
//                    messaging.append(listOfHistory.get(i - 1) + System.lineSeparator());
//                }
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
}






