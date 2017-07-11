package main.java.org.ycwu.generic;

/**
 * Created by Administrator on 2017/7/11 0011.
 */
public class Plate<T> {

    private T item;

    public Plate(T item) {
        this.item = item;
    }

    public void setItem(T t) {
        this.item = t;
    }

    public T getItem() {
        return item;
    }
}
