package geekbrains.homework.lesson3;
// К сожалению метод не удалось пока до конца приспособить к изменению размера поля и победной серии. Так же в случае возникновения ситуации
// _ X X X _ программа не будет блокировать ход игрока, так как это не приведет к нужному результату.
// В программе не проработаны случаи с приоритетом на победную серию компьютера. Буду переделывать с ипользованием векторов показанными нам вчера.
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Homework3 {
    public static int width = 5;
    public static int high = 5;
    public static int winStreak = 4;            // Победная серия
    public static char [][] gameField;

    public static final Scanner SCANNER = new Scanner(System.in);
    public static final Random RANDOM = new Random();

    public static final char Empty_Dot = '_' ;
    public static final char Human_Dot = 'X' ;
    public static final char AI_Dot = '0' ;

    public static void main (String[] args) {
        fieldCreator();
        fieldRead();

        while (true) {
            humanTurn();
            fieldRead();

            if (testWin(Human_Dot)) {
                System.out.println("Человек победил");
                break;
            }
            if (testDraw()) {
                System.out.println("Ничья");
                break;
            }
            aiTurn();
            fieldRead();

            if (testWin(AI_Dot)) {
                System.out.println("Компьютер победил");
                break;
            }
            if (testDraw()) {
                System.out.println("Ничья");
                break;
            }
        }
        System.out.println("Игра окончена");
    }

    public static char[][] fieldCreator () {            // Создание поля
        gameField = new char [high][width];
        for (int y = 0; y < high; y++ ) {
            for (int x = 0; x < width; x++) {
                gameField[y][x] = Empty_Dot;
            }
        }
        return gameField;
    }

    public static void fieldRead () {         // Распечатывание поля
        for (int y = 0; y < high; y++ ) {
            for (int x = 0; x < width; x++) {
                System.out.print(gameField[y][x] + " | ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void humanTurn() {
        int x;
        int y;
        do {
            System.out.println("Ваш ход! Введите координаты вашего хода: ");
            x = SCANNER.nextInt() - 1;
            y = SCANNER.nextInt() - 1;
        } while (!borderLimit(y,x) || !repeatValue(y,x));
            gameField[y][x] = Human_Dot;
    }

    public static boolean borderLimit (int y, int x) {      // Границы поля
        return x >= 0 && x < width && y >= 0 && y < high;
    }
    public static boolean repeatValue (int y, int x) {      // Повторяющиеся значения поля
        return gameField [y][x] == Empty_Dot;
    }

   public static void aiTurn() {
        int x = -1;
        int y = -1;
        int p = -1;
        int[] support = getSupportArray(Human_Dot, 0);  // Вызов вспомогательного метода для проверки победной серии человека
        for (int k = (winStreak -1); k > 0; k--) {             // Проверка от наибольшего количества последовательных повторений "X"
           for (int i = 0; i < (gameField.length * 2) + 2; i++) { // Поиск во вспомогательном массиве нахождение по линиям выйгрышных позиций
               if (support[i] > k) {
                   if (i < gameField.length) {                  // i = 0..4 ячейки под горизонтальные линии
                       p = returnHorizontalLine(i);
                       if (p >= 0) {
                           x = p;
                           y = i;
                           break;
                       }
                   }
                   if (i >= gameField.length && i < (gameField.length * 2)) { // i = 5..9 ячейки под вертикальные линии
                       p = returnVerticalLine(i);
                       if (p >= 0) {
                           y = p;
                           x = i - width;
                           break;
                       }
                   }
                   if (i == (gameField.length * 2)) {       // i = 10 главная  диагональ
                       p = returnDiagonalLine(i);
                       if (p >= 0) {
                           x = p;
                           y = p;
                           break;
                       }
                   }
                   if (i == (gameField.length * 2 + 1)) {  // i = 11 обратная  диагональ
                       p = returnDiagonalLine(i);
                       if (p >= 0) {
                           x = width - 1 - p;
                           y = p;
                           break;
                       }
                   }
               }
           }
       }
        if (x == -1) {  // Если не будет найдено близких к победе комбинаций игрока то выбираем случайную координату
           do {
               x = RANDOM.nextInt(width);
               y = RANDOM.nextInt(high);
           } while (!repeatValue(y, x));
       }
       gameField[y][x] = AI_Dot;
   }

   public static int returnHorizontalLine(int i) {
       int endValue = 0; // Переменная отвечает за количество пустых ячеек в крайнем положении линии
       int middleValue = 0; // Переменная отвечает за количество пустых ячеек между крайними ячейками линии
       int p = -1; // Вспомогательная переменная, вернет отрицательное значение если угрозы победной линиии не обнаружено
       int g = -1; // Вспомогательная переменная, вернет отрицательное значение если угрозы победной линиии не обнаружено
       int zero = 0;
       for (int j = 0; j < width; j++) {
           if (gameField[i][j] == Empty_Dot) {
               if (j == 0 || j == (width - 1)) {
                   endValue++;
                   g = j;
               } else {
                   middleValue++;
                   p = j;
               }
           }
           if (j > 0 && j < (width -1) && gameField[i][j] == AI_Dot) {
               zero++;
           }
      }
       if (endValue == 2 && middleValue == 1)           // Случай если по краям пустые ячейки и есть 1 пустая между ними, приоритет на центральную ячейку
           return p;
       if (endValue == 1 && (gameField[i][0] == AI_Dot || gameField[i][width-1] == AI_Dot)) // Случай если по краю пустая ячейка, а другая занята "O"
            return g;
       if (endValue == 1 && middleValue == 1 && zero == 0) // Случай если по краю и между есть пустая ячейка, а в центре нету значений "O"
            return p;
       if (middleValue == 1 && (gameField[i][0] == AI_Dot || gameField[i][width-1] == AI_Dot)) // Случай если в центре есть пустая ячейка и одно из крайних значений "O"
            return p;
       return p;
   }

    public static int returnVerticalLine(int i) {
        int endValue = 0;
        int middleValue = 0;
        int p = -1;
        int g = -1;
        int zero = 0;
        for (int j = 0; j < high; j++) {
            if (gameField[j][i - high] == Empty_Dot) {
                if (j == 0 || j == (high-1)) {
                    endValue++;
                    g = j;
                } else {
                    middleValue++;
                    p = j;
                }
            }
            if (j > 0 && j < (high -1) && gameField[j][i - high] == AI_Dot) {
                zero++;
            }
        }
        if (endValue == 2 && middleValue == 1)
            return p;
        if (endValue == 1 && (gameField[0][i - high] == AI_Dot || gameField[high-1][i - high] == AI_Dot))
            return g;
        if (endValue == 1 && middleValue == 1 && zero == 0)
            return p;
        if (middleValue == 1 && (gameField[0][i - high] == AI_Dot || gameField[high-1][i - high] == AI_Dot))
            return p;
        return p;
    }

    public static int returnDiagonalLine(int i) {
        int endValue = 0;
        int middleValue = 0;
        int p = -1;
        int g = -1;
        int zero = 0;
        if (i == 10) {
            for (int j = 0; j < width; j++) {
                if (gameField[j][j] == Empty_Dot) {
                    if (j == 0 || j == (width - 1)) {
                        endValue++;
                        g = j;
                    } else {
                        middleValue++;
                        p = j;
                    }
                }
                if (j > 0 && j < (width -1) && gameField[j][j] == AI_Dot) {
                    zero++;
                }
            }
            if (endValue == 2 && middleValue == 1)
                return p;
            if (endValue == 1 && (gameField[0][0] == AI_Dot || gameField[high - 1][width - 1] == AI_Dot))
                return g;
            if (endValue == 1 && middleValue == 1 && zero == 0)
                return p;
            if (middleValue == 1 && (gameField[0][0] == AI_Dot || gameField[high - 1][width - 1] == AI_Dot))
                return p;
        } else if (i == 11) {
            for (int j = 0; j < width; j++) {
                if (gameField[j][width - 1 - j] == Empty_Dot) {
                    if (j == 0 || j == (width - 1)) {
                        endValue++;
                        g = j;
                    } else {
                        middleValue++;
                        p = j;
                    }
                }
                if (j > 0 && j < (width -1) && gameField[j][width - 1 - j] == AI_Dot) {
                    zero++;
                }
            }
            if (endValue == 2 && middleValue == 1)
                return p;
            if (endValue == 1 && (gameField[0][width - 1] == AI_Dot || gameField[high - 1][0] == AI_Dot))
                return g;
            if (endValue == 1 && middleValue == 1 && zero == 0)
                return p;
            if (middleValue == 1 && (gameField[0][width - 1] == AI_Dot || gameField[high - 1][0] == AI_Dot))
                return p;
            }
    return p;
    }

   public static int[] getSupportArray (char dot, int setZero) {  // Метод создает вспомогательных массив который считает кол-во повторений передаваемого символа по линиям
        int[] support = new int[(gameField.length * 2) + 2 ];
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField.length; j++) {
                if (gameField[i][j] == dot) {
                    support[i]++;
                    support[gameField.length + j]++;
                    if (i == j) {
                        support[gameField.length * 2]++;
                    }
                    if (j == gameField.length - 1 - i) {
                        support[(gameField.length * 2) + 1]++;
                    }
                } else if (setZero == 1 && (i > 0) && (i < gameField.length - 1) && (j > 0) && (j < gameField.length - 1)) {  // При обращении из метода testWin обнуляет значения, если между нужными символами появятся другие
                        support[gameField.length + j] = 0;
                        support[i] = 0;
                    if (i == j)
                        support[gameField.length * 2] = 0;
                    if (j == gameField.length - 1 - i)
                        support[(gameField.length * 2) + 1] = 0;
                }
            }
        }
//       System.out.println(dot + " метод проверки " + Arrays.toString(support));
       return support;
   }
   public static boolean testWin (char dot) {
        int[] support = getSupportArray(dot, 1); /*new int[(gameField.length * 2) + 2 ];*/
//        System.out.println(dot + " тест " + Arrays.toString(support));
//        support = getSupportArray(dot);
        for (int i = 0; i < (gameField.length * 2) + 2; i++) {
           if (support[i] >= winStreak)
               return true;
       }
       return false;
   }
//        System.out.println(Arrays.toString(support));

   public static boolean testDraw () {
        for (int y = 0; y < high; y++) {
            for (int x = 0; x < width; x++) {
                if (gameField[y][x] == Empty_Dot)
                    return false;
            }
        }
        return true;
   }
}
