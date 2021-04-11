package geekbrains.homework.lesson20;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainClass {
    public static final int CARS_COUNT = 4;
    public static int FINISH_COUNT = 1;
    public static final CyclicBarrier cb = new CyclicBarrier(CARS_COUNT + 1);
    public static final Lock lock = new ReentrantLock();
    public static void main(String[] args) {

        CountDownLatch cdl = new CountDownLatch(CARS_COUNT);
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }

        try {
            while (cb.getNumberWaiting() != CARS_COUNT) {
                Thread.sleep(300);
            }

            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Участники готовы. Гонка началась!!!");
            cb.await();
            while (cb.getNumberWaiting() != CARS_COUNT) {
                Thread.sleep(300);
            }
            cb.await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");

        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

    }
}



