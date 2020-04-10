package com.shpp.p2p.cs.bcimbal.assignment16;

import java.util.Arrays;
import java.util.Iterator;

public class VArrayList <T> implements Iterator<T>{

    private Object [] objArray;
    private int index = 0;
    private static final int DEFAULT_CAPACITY = 10;

    Iterator<T>  iterator;

    VArrayList() {
        objArray = new Object[0];
        //iterator = objArray.ite
    }

    public void add (T item) {
        rerize();
        this.objArray[this.index++] = item;
//        if(index > objArray.length) {
//            /* resize objArray */
//            Object [] tmp = objArray.clone();
//            objArray = new Object[tmp.length + 1];
//            Arrays.copyOfRange(tmp, 0, tmp.length);
//        }

    }

    private void rerize() {
        /* resize objArray */
        Object [] tmp = this.objArray.clone();
        //this.objArray = new Object[tmp.length + 1];
        this.objArray = Arrays.copyOf(tmp, tmp.length + 1);
    }

    public T get(int index) {
        return (T)this.objArray[index];
    }

    public int size() {
        return objArray.length;
    }


    @Override
    public boolean hasNext() {

        return false;
    }

    @Override
    public T next() {
        return null;
    }
}
