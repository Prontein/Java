package geekbrains.homework.lesson6;

import java.io.*;

public class Homework6 {
    public static void main (String[] args) {
        try {
            FileOutputStream fileNumberOne = new FileOutputStream("fileOne.txt");
            PrintStream streamOne = new PrintStream(fileNumberOne);
            streamOne.println("Java is a class-based, object-oriented programming language that is designed to have as few implementation dependencies as possible. ");
            streamOne.close();
            fileNumberOne.close();

            FileOutputStream fileNumberTwo = new FileOutputStream("fileTwo.txt");
            PrintStream streamTwo = new PrintStream(fileNumberTwo);
            streamTwo.print("It is a general-purpose programming language intended to let application developers write once, run anywhere.");
            streamTwo.close();
            fileNumberTwo.close();
            // Задание № 2
            write ("fileOne.txt", "fileFusion.txt", false);
            write ("fileTwo.txt", "fileFusion.txt", true);
            // Задание №3
            System.out.println("Встречается ли заданное слово в файле? - " + checkWord ("language", "fileFusion.txt"));
            // Задание №4
            File folder = new File("C:\\Users\\Ilya\\Desktop\\GeekBr\\Homework1");
            System.out.println("Встречается ли заданное слово в файлах каталога? - " + traverseFolder ("intended", folder));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write  (String fileInput, String fileOutput, boolean append) {
        try {
            FileOutputStream fileFusion = new FileOutputStream(fileOutput, append);
            PrintStream streamFusion = new PrintStream(fileFusion);
            FileInputStream readOne = new FileInputStream(fileInput);
            int output;
            while ((output = readOne.read()) != -1) {
                streamFusion.print((char)output);
            }
            readOne.close();
            streamFusion.close();
            fileFusion.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkWord (String word, String fileInput) {
        try {
            FileInputStream readOne = new FileInputStream(fileInput);
            int output;
            StringBuilder b = new StringBuilder();
            while ((output = readOne.read()) != -1) {
                if ((output >= 65 && output <= 90) || (output >= 97 && output <= 122)) {
                    b.append((char) output);

                } else {
                    if (word.equals(b.toString())) return true;
                    b = new StringBuilder();
                }
//              System.out.print((char)output);
            }
            if (word.equals(b.toString())) return true; // Провекра если слово окажется последним в файле
            readOne.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  false;
    }

    public static boolean traverseFolder  (String word, File folder) {
        File[] folderEntries = folder.listFiles();
//        System.out.println("Какая папка" + folder.getAbsolutePath());
        for (File entry : folderEntries) { // Проверка файлов в папке
            if (entry.isFile()) {
                if (checkWord (word, entry.getAbsolutePath()))
                    return true;
            }
        }
        for (File entry : folderEntries) { // Проверка папки
            if (entry.isDirectory()) {
                if (traverseFolder(word, entry)) {
                    return true;
                }
            }
        }
        return false;
    }

}
