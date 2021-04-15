package ru.geekbrains;

import javax.swing.*;
import java.io.*;
import java.util.List;

public class MessageHistory {

    public static File fileHistory (String login, File file) {
        try {
            File fileClient = new File("history_" + login + ".txt");
            if (fileClient.createNewFile()) {
                System.out.println("Файл создан");

            } else
                System.out.println("Файл уже существует");

            return fileClient;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static void writeInFile (MessageDTO dto, File file) throws IOException {
        System.out.println(file.getName());
        PrintWriter writer = new PrintWriter((new FileWriter(""+file.getName()+"", true)));
        if (dto.getMessageType() == MessageType.PRIVATE_MESSAGE) {
            writer.println("Личное сообщение от пользователя: " + dto.getFrom() + "\n" + dto.getBody());

        } else {
            writer.println("Сообщение от пользователя: " + dto.getFrom() + "\n" + dto.getBody());
        }
        writer.close();
    }

    public static void readFromHistory (File file, List<String> listOfHistory, JTextArea messaging) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = reader.readLine()) != null) {
                listOfHistory.add(line);
            }
            if (listOfHistory.size() > 200) { // в случае если строк в истории больше 200 ( В моем случае информационная строка от кого пришло сообщение тоже считается, поэтому для последних 100 сообщений идет 200 строк
                for (int i = listOfHistory.size() - 200; i <= listOfHistory.size(); i++) {
                    messaging.append(listOfHistory.get(i - 1) + System.lineSeparator());
                }
            } else {
                for (int i = 1; i <= listOfHistory.size(); i++) {
                    messaging.append(listOfHistory.get(i - 1) + System.lineSeparator());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
