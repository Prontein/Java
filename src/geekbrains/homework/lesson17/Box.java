package geekbrains.homework.lesson17;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Box<T extends Fruit>  {

    List<T> fruitList;
    public Box() {
        this.fruitList = new ArrayList<>();

    }

    public Box (T... fruits) {

        this.fruitList = new ArrayList<>(Arrays.asList(fruits));
        System.out.println(fruitList);
    }

    public double getWeight() {

        double weight = 0.0;
        for (T fruit : fruitList) {
            weight += fruit.getWeight();
        }
        return weight;
    }

    public boolean compare(Box <?> anotherBox) {
        return Math.abs(getWeight() - anotherBox.getWeight()) <0.0001;
    }

    public void add(T fruit) {

        this.fruitList.add(fruit);
    }

    public void transfer(Box<T> another) {
        if (this == another) return;
        another.getFruits().addAll(fruitList);
        fruitList.clear();
    }

    public List<T> getFruits() {
        return fruitList;
    }

}
