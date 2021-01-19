package geekbrains.homework.lesson1;
/*
    Created by ILya Suleymanov
 */
public class Homework {
    public static void main (String[] args){
        int a = 5, b = 6, c = 7, d = 8; // Задача №1
        calculation(a,b,c,d);
        System.out.println ("Результат вычислений первой задачи - " + calculation(a,b,c,d));

        int number1 = 0, number2 = 7; // Задача №2
        sum(number1,number2);
        System.out.println ("Результат проверки второй задачи - " + sum(number1,number2));

        int verNumber = 11; // Задача №3
        check(verNumber);

        String name = "Николай";  // Задача №4
        greeting(name);
        System.out.println (greeting(name));

        int year = 2024;    // Задача №5
        yearCheck(year);

    }
    public static float calculation (int a,int b, int c, int d){        // Задача №1
        return a * (b + ((float)c / d));

    }

    public static boolean sum (int number1, int number2){               // Задача №2
        int sumResult = number1 + number2;
        return sumResult >= 10 && sumResult <= 20;
    }

    public static void check (int verNumber){                           // Задача №3
        if (verNumber >= 0) {
            System.out.println(verNumber + " - Число положительное");
        } else {
            System.out.println(verNumber + " - Число отрицательное");
        }

    }

    public static String greeting (String name){                        // Задача №4
        return "Привет, "+ name + "!";
    }

    public static void yearCheck (int year){                            // Задача №5
        if (year % 4 == 0 && year % 100 == 0 && year % 400 !=0){
            System.out.println (year + " - невисокосный год");
        }else if (year % 4 == 0 && year % 400 == 0) {
            System.out.println (year + " - високосный год");
        }else if (year % 4 == 0){
            System.out.println (year + " - високосный год");
        }else {
            System.out.println (year + " - невисокосный год");
        }
    }

}

