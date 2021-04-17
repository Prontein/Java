package ru.geekbrains.lesson23;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Homework {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        start(Testing.class);
    }

    public static void start (Class c) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = c.getDeclaredMethods();

        List<Method> list = new ArrayList<>();

        for (Method o: methods) {
            if (o.isAnnotationPresent(Test.class)) {
                int priority = o.getAnnotation(Test.class).priority();
                if (priority < 1 || priority > 10) throw new RuntimeException("Неверно задан приоритет");
                list.add(o);
            }
        }

        list.sort(new Comparator<Method>() {
            @Override
            public int compare(Method o1, Method o2) {
                return o2.getAnnotation(Test.class).priority() - o1.getAnnotation(Test.class).priority();
            }
        });

        for (Method o: methods) {
            if (o.isAnnotationPresent(BeforeSuite.class)) {
                if (list.get(0).isAnnotationPresent(BeforeSuite.class))
                    throw new RuntimeException("BeforeSuite exception");
                list.add(0,o);
            }

            if (o.isAnnotationPresent(AfterSuite.class)) {
                if (list.get(list.size() - 1).isAnnotationPresent(AfterSuite.class))
                    throw new RuntimeException("AfterSuite exception");
                list.add(o);
            }
        }

        for (Method o: list) {
            o.invoke (null);
        }
    }

}

class Testing {
    public static void method1() {
        System.out.println("met1");
    }

    @BeforeSuite
    public static void begin() {
        System.out.println("begin");
    }

    @AfterSuite
    public static void end() {
        System.out.println("end");
    }

    @Test(priority = 3)
    public static void method4() {
        System.out.println("met4");
    }

    @Test(priority = 1)
    public static void method5() {
        System.out.println("met5");
    }

    @Test(priority = 6)
    public static void method6() {
        System.out.println("met6");
    }

    @Test(priority = 3)
    public static void method7() {
        System.out.println("met7");
    }

 }



