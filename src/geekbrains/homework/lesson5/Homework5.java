package geekbrains.homework.lesson5;

public class Homework5 {
    public static void main (String[] args) {

        Dog dog1 = new Dog("Барбос");
        Dog dog2 = new Dog("Тузик");

        Cat cat1 = new Cat("Барс");
        Cat cat2 = new Cat("Тимофей");

        Horse horse1 = new Horse("Конь");
        Horse horse2 = new Horse("Жеребец");

        Bird bird1 = new Bird("Жорик");
        Bird bird2 = new Bird("Кеша");

        dog1.run(150);
        dog1.swim(6);
        dog1.jump (2);

        cat1.run(200);
        cat1.swim(100);
        cat1.jump(3.5);

        horse1.run(1200);
        horse1.swim(81);
        horse1.jump(5);

        bird1.run(3);
        bird1.swim(2);
        bird1.jump(0.4);
    }
}
