package com.shpp.p2p.cs.bcimbal.assignment16;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class VArrayList <T> implements  Iterable<T>{

    private Object [] objArray;
    private int index = 0;
    private static final int DEFAULT_CAPACITY = 10;



    VArrayList() {
        objArray = new Object[0];
        //iterator = objArray.ite
    }

    public Object[] getObjArray() {
        return objArray;
    }

    public void add (T item) {
        rerize();
        this.objArray[this.index++] = item;

    }

    private void rerize() {
        /* resize objArray */
        Object [] tmp = this.objArray.clone();
        this.objArray = Arrays.copyOf(tmp, tmp.length + 1);
    }

    public T get(int index) {
        return (T)this.objArray[index];
    }

    public int size() {
        return objArray.length;
    }




    @Override
    public Iterator<T> iterator() {
        List a = Arrays.asList(objArray);
        Iterator it = a.iterator();
        return it;
    }


}
