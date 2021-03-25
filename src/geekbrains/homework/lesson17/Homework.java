package geekbrains.homework.lesson17;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Homework {

    public static void main(String[] args) {

        ArrayList MyList = new ArrayList<>();
        MyArray <?> arr = new MyArray<>(1, "Число", 3.0, 0.25, 5);

        int firstElement = 0;
        int secondElement = 0;

        firstElement = (int) (Math.random() * arr.obj.length);
        secondElement = generateElementTwo(arr, firstElement, secondElement);
//        System.out.println(firstElement);
//        System.out.println(secondElement);
        System.out.println("Исходный массив " + Arrays.toString(arr.getObj()));

        arr.replacementArray (firstElement, secondElement);
        System.out.println("Поменять местами элемент под номером " + firstElement + " с элементом " + secondElement);
        System.out.println("Конечный массив " + Arrays.toString(arr.getObj()));

        arr.listConvert(MyList);

        System.out.println(MyList);

        Box <Apple> appleBox = new Box<>(new Apple(), new Apple());
        Box <Orange> orangeBox = new Box<>(new Orange(), new Orange());

        System.out.println(appleBox.compare(orangeBox));

        appleBox.add(new Apple());
        appleBox.add(new Apple());

        System.out.println(appleBox.getWeight());
        System.out.println(orangeBox.getWeight());

        Box <Apple> appleBox1 = new Box<>();
        appleBox.transfer(appleBox1);

        System.out.println(appleBox1);

    }


    public static int generateElementTwo(MyArray <?> arr, int firstElement, int secondElement) {
        do {
            secondElement = (int) (Math.random() * arr.obj.length);
        } while (secondElement == firstElement);

        return secondElement;
    }


    public static class MyArray <T> {
        private T[] obj;

        public MyArray (T... arr) {
            this.obj = arr;
        }

        public T[] getObj() {
            return obj;
        }

        public void replacementArray (int firstElement, int secondElement) {

            for (int i = 0 ; i < obj.length; i ++ ){

                T changeElement = obj[firstElement];
                obj [firstElement] = obj [secondElement];
                obj [secondElement] = changeElement;

            }

        }

        public void listConvert (ArrayList MyList) {

            for (int i = 0 ; i < obj.length; i++ ) {
                MyList.add(obj[i]);
            }

        }


    }
}
