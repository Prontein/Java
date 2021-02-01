package geekbrains.homework.lesson5;

public class Bird extends Animals {
    Bird (String name) {
        super(name);
        this.maxLength = RANDOM.nextInt(5) + 5;
        this.maxHeight = RANDOM.nextDouble();
    }
    @Override
    public void swim(int length) {
        System.out.println("\n" + name + " не умеет плавать!");
    }
}
