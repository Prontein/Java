package geekbrains.homework.lesson4;

public class Worker {
    public String name;
    public String position;
    public int number;
    public int salary;
    public int age;

    private static int maxId = 0;
    private int id;

    public Worker (String name, int age, String position, int number, int salary) {
        this.name = name;
        this.age = age;
        this.position = position;
        this.number = number;
        this.salary = salary;
        setId(getNewId());
    }

    public String getName() {
        return name;
    }
    public String getPosition() {
        return position;
    }
    public int getNumber() {
        return number;
    }
    public int getSalary() {
        return salary;
    }
    public int setSalary(int salary) {
        salary = salary + 10000;
        return salary;
    }
    public int getAge() {
        return age;
    }

    public int getMaxId() {
        return maxId;
    }
    private void setMaxId(int maxId) {
        this.maxId = maxId;
    }
    public int getNewId() {
        int newId = this.getMaxId() + 1;
        setMaxId(newId);
        return newId;
    }
    public int getId() {
        return id;
    }
    private void setId(int id) {
        this.id = id;
    }


}


