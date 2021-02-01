package geekbrains.homework.lesson5;

public class Horse extends Animals {
    Horse (String name) {
        super(name);
        this.maxLength = RANDOM.nextInt(1500) + 500;
        this.maxSwim = RANDOM.nextInt(100) + 50;
        this.maxHeight = RANDOM.nextDouble()*15;
    }
}
