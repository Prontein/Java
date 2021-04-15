package ru.geekbrains;


import java.util.Arrays;

public class lesson22 {
    public static final int ARRAY_SIZE = 9;
    public static final int NUMBER = 4;
    public static void main(String[] args) {

        int [] myArray = new int[ARRAY_SIZE];

        for (int i = 0; i < myArray.length; i++) {
            myArray[i] = (int) (Math.random() * 10);
        }

        System.out.println(Arrays.toString(myArray));
        System.out.println("Новый полученный массив - " + Arrays.toString(operation(myArray)));

        System.out.println(arrayContents(new int[]{1, 2, 3, 4, 5, 6, 7, 8}));
        System.out.println(arrayContents(new int[]{0, 2, 3, 4, 5, 6, 7, 8}));

    }
    public static int [] operation (int [] array) throws RuntimeException {

        int lastIndexNumber = -1;

        for (int i = 0; i < array.length; i++) {
            if (array[i] == NUMBER) {
                lastIndexNumber = i;
            }
        }
//        System.out.println("Индекс последней четверки = " + lastIndexNumber);
        if (lastIndexNumber == -1) {
            throw new RuntimeException("Входной массив должен содержать хотя бы одну четверку");
        }

        int newArraySize = array.length - 1 - lastIndexNumber;
        int [] newArray = new int[newArraySize];
//        System.out.println("Размер нового массива - " + newArraySize);
        for (int j = 0; j < newArraySize; j++ ) {
            newArray[j] = array[lastIndexNumber + 1];
            lastIndexNumber++;
        }
        return newArray;
    }

    public static boolean arrayContents (int [] array) {
        int numberOne = 0;
        int numberFour = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 1) numberOne++;
            if (array[i] == 4) numberFour++;
        }
        return (numberOne > 0 && numberFour > 0);
    }
}
