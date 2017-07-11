package main.java.org.ycwu.generic;

import org.junit.Test;

/**
 * Created by Administrator on 2017/7/11 0011.
 */
public class TestGeneric {

    @Test
    public void testExtend() {
        Plate<? extends Fruit> plate = new Plate<Apple>(new Apple());
        // error: cant set anything
        // plate.setItem(new Pear());
        // plate.setItem(new Fruit());
        // plate.setItem(new Apple());

        // error:
        // Apple apple = plate.getItem();
        Apple apple = (Apple) plate.getItem();
        Fruit fruit = plate.getItem();
    }

    @Test
    public void testSuper(){
        Plate<? super Fruit> plate=new Plate<Fruit>(new Fruit());
        plate.setItem(new Apple());
        plate.setItem(new Pear());

        Pear pear= (Pear) plate.getItem();
        // error:
        // Fruit fruit=plate.getItem();


    }
}
