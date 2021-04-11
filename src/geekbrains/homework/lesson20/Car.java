package geekbrains.homework.lesson20;


public class Car implements Runnable {
    private static int CARS_COUNT;
    private Race race;
    private int speed;
    private String name;
    private int numberOfRiders;


    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed/*, int numberOfRiders*/) {
        this.race = race;
        this.speed = speed;;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            MainClass.cb.await();

        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);

        }
        MainClass.lock.lock();
        try {

            if (MainClass.FINISH_COUNT == 1) {
                System.out.println("Победитель гонки - " + this.name);
                MainClass.FINISH_COUNT++;
            }
        } finally {
            MainClass.lock.unlock();
        }

        MainClass.cb.await();

        } catch (Exception e) {
        e.printStackTrace();
    }
    }
}
