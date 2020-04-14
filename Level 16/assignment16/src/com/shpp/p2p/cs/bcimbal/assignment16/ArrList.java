package com.shpp.p2p.cs.bcimbal.assignment16;

import java.util.*;
import java.util.List;

/* Class represent bad attempt of ArrayList implementation */

public class ArrList <T> extends AbstractList<T>{

    /* objects storage array */
    private Object [] objects;
    /* pointer to last inserted object */
    private int index = 0;
    /* initial capacity constant*/
    private  static final int INITIAL_CAPACITY = 10;
    /* collection size */
    private int size = 0;

    /* constructor, objects storage array initialise*/
    ArrList() {
        objects = new Object[INITIAL_CAPACITY];
    }

    /**
     * Add item to collection
     * @param e item data
     * @return true
     */
    @Override
    public boolean add(T e) {
        resize();
        objects[index++] = e;
        size++;
        return true;
    }

    /**
     * increase data storage array
     */
    private void resize() {
        /* resize objArray */
        if(index >= objects.length) {
            Object[] tmp = this.objects.clone();
            this.objects = Arrays.copyOf(tmp, tmp.length + INITIAL_CAPACITY);
        }
    }

    /**
     * collection size
     * @return int number of items in collection
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * get item from collection
     * @param i int item index
     * @return T item
     */
    @Override
    public T get(int i) {
        return (T)objects[i];
    }

    /**
     * set data to item at index
     * @param index item index
     * @param element item data
     * @return item data
     */
    @Override
    public T set(int index, T element) {
        if(isIndexInOfBounds(index)) {
            objects[index] = element;
            return element;
        }
        else return null;
    }

    /**
     * current collection iterator
     * @return collection iterator
     */
    @Override
    public Iterator<T> iterator() {
        List list = Arrays.asList(objects);
        return list.iterator();
    }

    /**
     * remove item at given index from collection
     * @param index item index
     * @return T item data
     */
    @Override
    public T remove(int index) {
        T returned = (T) objects[index];

        /* shift array items left in one position to removed element*/
        if(isIndexInOfBounds(index)) {
            Object [] tmp = new Object[objects.length - 1];
            for(int i = 0; i < objects.length - 1; i++) {
                if(i >= index){
                    tmp[i] = objects[i + 1];
                }
                else {
                    tmp[i] = objects[i];
                }
            }
            this.objects = tmp;
            size--;
            return returned;
        }
        else return null;
    }

    private boolean isIndexInOfBounds(int index) {
        return index >= 0 && index < size;
    }

}
