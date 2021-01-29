package geekbrains.homework.lesson4;

public class Homework4 {
    public static void main (String[] args) {
        Worker worker1 = new Worker("Максим", 28, "Инженер", 123, 50000);
        Worker worker2 = new Worker("Антон", 23, "Техник", 258, 60000);
        Worker worker3 = new Worker("Женя", 34, "Помощник", 597, 35000);
        Worker worker4 = new Worker("Петр", 41, "Уборщик", 348, 41000);
        Worker worker5 = new Worker("Егор", 55, "Водитель", 445, 74000);

        System.out.println("Задание № 4 Вывод ФИО и Должности");

        System.out.println("ФИО = " + worker1.getName() + "\t" + "Должность = " + worker1.getPosition());
        System.out.println("ФИО = " + worker2.getName() + "\t" + "Должность = " + worker2.getPosition());
        System.out.println("ФИО = " + worker3.getName() + "\t" + "Должность = " + worker3.getPosition());
        System.out.println("ФИО = " + worker4.getName() + "\t" + "Должность = " + worker4.getPosition());
        System.out.println("ФИО = " + worker5.getName() + "\t" + "Должность = " + worker5.getPosition());

        Worker[] workArray = new Worker[5];
        workArray[0] = worker1;
        workArray[1] = worker2;
        workArray[2] = worker3;
        workArray[3] = worker4;
        workArray[4] = worker5;
        System.out.println("Задание № 5 Сотрудники старше 40");
        oldArray(workArray);
        System.out.println("Задание № 6 Повышение зарплаты");
        riseSalary(workArray);
        System.out.println("Задание № 7 Порядковый номер");
        System.out.println("Порядковый номер сотрудника " + worker4.getName() + " = " + worker4.getId());
//        System.out.println(worker2.getId());

    }

    public static void oldArray (Worker[] workArray) {
        for (int i = 0; i < workArray.length; i ++) {
            if (workArray[i].age > 40) {
                System.out.println("Имя = " + workArray[i].name + "\tВозраст = " + workArray[i].age + "\tДолжность = " + workArray[i].position + "\tТелефон = " + workArray[i].number + "\tЗарплата = " + workArray[i].salary);
            }
        }
    }

    public static void riseSalary (Worker[] workArray) {
        for (int i = 0; i < workArray.length; i ++) {
            if (workArray[i].age > 35) {
                workArray[i].salary =  workArray[i].setSalary(workArray[i].salary) ;
            }
//            System.out.println("Имя = " + workArray[i].ФИО + "\tВозраст = " + workArray[i].возраст + "\tДолжность = " + workArray[i].должность + "\tТелефон = " + workArray[i].телефон + "\tЗарплата = " + workArray[i].зарплата);
        }

    }
}
