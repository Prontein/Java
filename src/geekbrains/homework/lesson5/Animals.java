package geekbrains.homework.lesson5;

import java.util.Random;

public class Animals {
    protected int maxLength;
    protected int maxSwim;
    protected double maxHeight;
    public Random RANDOM = new Random();
    protected String name;

    Animals(String name) {
        this.name = name;
    }

    public void run (int length) {
        System.out.println("\nМаксимальная дистанция бега = " + maxLength);
        if (length <= maxLength) {
            System.out.println(name + " пробежал дистанцию!");
        } else {
         System.out.println(name + " не справился с дистанцией!");
        }
    }

    public void jump(double height) {
        System.out.printf("\nМаксимальная высота прыжка = " + "%.1f",maxHeight);
        if (height<= maxHeight) {
            System.out.println("\n" + name + " справился с прыжком!");
        } else {
            System.out.println("\n" + name + " не допрыгнул!");
        }
    }

    public void swim(int length) {
        System.out.println("\nМаксимальная дистанция заплыва = " + maxSwim);
        if (length <= maxSwim) {
            System.out.println(name + " проплыл дистанцию!");
        } else {
            System.out.println(name + " не справился с дистанцией!");
        }
    }
}
