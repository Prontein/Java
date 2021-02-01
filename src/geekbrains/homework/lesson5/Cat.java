package geekbrains.homework.lesson5;

public class Cat extends Animals {
    Cat (String name) {
        super(name);
        this.maxLength = RANDOM.nextInt(200) + 100;
        this.maxHeight = RANDOM.nextDouble()*4;
    }

    @Override
    public void swim(int length) {
        System.out.println("\n" + name + " не умеет плавать!");
    }
}
