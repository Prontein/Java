package geekbrains.homework.lesson2;

/* Created by Ilya Suleymanov */

public class Homework2 {
    public static void main(String[] args) {

        int[] arrFirst = new int[10];                                   // Задание № 1
        arrFirst[0] = 1;
        arrFirst[1] = 1;
        arrFirst[2] = 0;
        arrFirst[3] = 0;
        arrFirst[4] = 1;
        arrFirst[5] = 0;
        arrFirst[6] = 1;
        arrFirst[7] = 1;
        arrFirst[8] = 0;
        arrFirst[9] = 0;
        System.out.print("Результат вычисления первого задания: ");
        readArrayFirst(arrChange(arrFirst));

        int[] arrSecond = new int[8];                                   // Задание № 2
        System.out.print("Результат вычисления второго задания: ");
        readArraySecond(arrEnter(arrSecond));

        int[] arrThird = new int[12];                                   // Задание № 3
        arrThird[0] = 1;
        arrThird[1] = 5;
        arrThird[2] = 3;
        arrThird[3] = 2;
        arrThird[4] = 11;
        arrThird[5] = 4;
        arrThird[6] = 5;
        arrThird[7] = 2;
        arrThird[8] = 4;
        arrThird[9] = 8;
        arrThird[10] = 9;
        arrThird[11] = 1;
        System.out.print("Результат вычисления третьего задания: ");
        readArrayThird(arrMultiply(arrThird));

        int[] arrFourth = new int[6];                                   // Задание № 4
        arrFourth[0] = 2;
        arrFourth[1] = 5;
        arrFourth[2] = 20;
        arrFourth[3] = 9;
        arrFourth[4] = 1;
        arrFourth[5] = 15;
        int minNumber = 0, maxNumber = 0;
        System.out.println("Результат вычисления четвертого задания: ");
        System.out.println("Минимальное значение " + arrFourth[arrSearchMin(arrFourth)] + " у элемента с номером " + arrSearchMin(arrFourth));
        System.out.println("Максимальное значение " + arrFourth[arrSearchMax(arrFourth)] + " у элемента с номером " + arrSearchMax(arrFourth));

        int[][] arrFifth = new int [5][5];                              // Задание № 5
        System.out.println("Результат вычисления пятого задания: ");
        readArrayFifth(arrPack(arrFifth));

        int[] arrSixth = new int[7];                                    // Задание № 6
        arrSixth[0] = 1;
        arrSixth[1] = 2;
        arrSixth[2] = 3;
        arrSixth[3] = 4;
        arrSixth[4] = 5;
        arrSixth[5] = 6;
        arrSixth[6] = 7;
        int n = 2;
        System.out.println("Результат вычисления шестого задания: ");
        readArraySixth(arrSwap(arrSixth,n));
    }

    public static int[] arrChange(int[] arrFirst) {                     // Задание № 1 Метод замены
        for (int i = 0; i < arrFirst.length; i++) {
            arrFirst[i] = (arrFirst[i] == 0) ? 1 : 0;
        }
        return arrFirst;
    }
    public static void readArrayFirst(int[] arrFirst) {                 // Задание № 1 Метод для вывода
        for (int i = 0; i < arrFirst.length; i++) {
            System.out.print(arrFirst[i] + " ");
        }
        System.out.println();
    }


    public static int[] arrEnter(int[] arrSecond) {                     // Задание № 2 Метод заполнения
        for (int i = 0; i < arrSecond.length; i++) {
            arrSecond[i] = (i * 3) + 1;
        }
        return arrSecond;
    }
    public static void readArraySecond(int[] arrSecond) {               // Задание № 2 Метод для вывода
        for (int i = 0; i < arrSecond.length; i++) {
            System.out.print(arrSecond[i] + " ");
        }
        System.out.println();
    }


    public static int[] arrMultiply(int[] arrThird) {                           // Задание № 3 Метод для умножения
        for (int i = 0; i < arrThird.length; i++) {
            arrThird[i] = (arrThird[i] < 6) ? arrThird[i] * 2 : arrThird[i];
        }
        return arrThird;
    }
    public static void readArrayThird(int[] arrThird) {                         // Задание № 3 Метод для вывода
        for (int i = 0; i < arrThird.length; i++) {
            System.out.print(arrThird[i] + " ");
        }
        System.out.println();
    }


    public static int arrSearchMin(int[] arrFourth) {                   // Задание № 4 Метод поиска минимального значения
        int minValue = arrFourth[0], minNumber = 0;
        for (int i = 0; i < arrFourth.length; i++) {
            if (arrFourth[i] < minValue) {
                minValue = arrFourth[i];
                minNumber = i;
            }
        }
        return minNumber;
    }
    public static int arrSearchMax(int[] arrFourth) {                   // Задание № 4 Метод поиска максимального значения
        int  maxValue = arrFourth[0], maxNumber = 0;
        for (int i = 0; i < arrFourth.length; i++) {
            if (arrFourth[i] > maxValue) {
                maxValue = arrFourth[i];
                maxNumber = i;
            }
        }
        return maxNumber;
    }


//    public static int[][] arrPack(int[][] arrFifth) {                             // Задание № 5 Метод заполнения диагоналей
//        for (int i = 0; i < Math.round((float)arrFifth.length/2); i++) {              Тут я использовал округлений Math.round((float)arrFifth.length/2)
//            arrFifth[i][i] = 1;                                                       для того чтобы не испозовать if else (заполнение происходит по четвертям)
//            arrFifth[i][arrFifth.length - 1 - i] = 1;                                 но так как мы не проходили этого с вами не знаю можно ли использовать такое решение.
//            arrFifth[arrFifth.length - 1 - i][i] = 1;
//            arrFifth[arrFifth.length - 1 - i][arrFifth.length - 1 - i] = 1;
//        }
//        return arrFifth;
//    }
    public static int[][] arrPack (int[][] arrFifth) {
        for (int i = 0 ; i < arrFifth.length; i++) {                                // Задание № 5 Метод заполнения диагоналей
            arrFifth[i][i] = 1;
            arrFifth[i][arrFifth.length - 1 - i] = 1;
        }
        return arrFifth;
    }
    public static void readArrayFifth(int[][] arrFifth) {                           // Задание № 5 Метод для вывода
        for (int i = 0 ; i < arrFifth.length; i++) {
            for (int j = 0; j < arrFifth[i].length; j++) {
                System.out.print(arrFifth[i][j] + " ");
            }
            System.out.println();
        }
    }


    public static int[] arrSwap(int[] arrSixth, int n) {                            // Задание № 6 Метод смещения
        if (n > 0) {
            int j = 0;
            while (j < n) {
                int memory = arrSixth[0];
                int memory2 = 0;
                arrSixth[0] = arrSixth[arrSixth.length - 1];
                for (int i = 0; i < arrSixth.length - 1; i++) {
                    memory2 = arrSixth[i + 1];
                    arrSixth[i + 1] = memory;
                    memory = memory2;
                }
                j++;
            }
        } else {
            while (n < 0) {
                int memory = arrSixth[arrSixth.length-1];
                int memory2 = 0;
                arrSixth[arrSixth.length-1] = arrSixth [0];
                for (int i = arrSixth.length-2; i >= 0; i--) {
                    memory2 = arrSixth[i];
                    arrSixth[i] = memory;
                    memory = memory2;
                }
                n++;
            }
        }
        return arrSixth;
    }
    public static void readArraySixth(int[] arrSixth) {                           // Задание № 6 Метод для вывода
        for (int i = 0; i < arrSixth.length; i++) {
            System.out.print(arrSixth[i] + " ");
        }
        System.out.println();
    }

}



