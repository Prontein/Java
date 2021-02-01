package geekbrains.homework.lesson5;

import java.util.Random;

public class Dog extends Animals {
    Dog(String name) {
        super(name);
        this.maxLength = RANDOM.nextInt(500) + 200;
        this.maxSwim = RANDOM.nextInt(10) + 5;
        this.maxHeight = RANDOM.nextDouble() + 0.2;
    }
}