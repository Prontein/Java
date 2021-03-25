package geekbrains.homework.lesson17;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Fruit {

    protected String type;
    protected float weight;


    Fruit () {
        this.weight = weight;

    }

    @Override
    public String toString(){
        return "тип фрукта "+type+", вес:" + weight;
    }

    public float getWeight() {
        return weight;
    }
}
