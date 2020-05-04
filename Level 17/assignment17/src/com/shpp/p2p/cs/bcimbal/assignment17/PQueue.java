package com.shpp.p2p.cs.bcimbal.assignment17;



import java.util.*;

public class PQueue<E> extends AbstractQueue<E> {
    private static  final int INITIAL_CAPACITY = 10;
    int size = 0;
    Object [] queue;
    Comparator comparator;


    public PQueue(Comparator<? super E> comparator) {
        queue = new Object[INITIAL_CAPACITY];
        this.comparator = comparator;
    }

    public PQueue() {
        this(null);
    }


    @Override
    public boolean add(E e) {
        queue[size++] = e;
        if(comparator == null) {
            sortNatural(e);
        }
        else {
            sortByComparator(e);
        }
        if(size >= queue.length) {
            queueResize();
        }

        return true;
//        return super.add(e);
    }

    private void sortByComparator(E e) {

//        Arrays.sort(queue, comparator);
//        //refactor links
//        clear();
//        for (int i = 0; i < tmp.length; i++) {
//            add((T)tmp[i]);
//        }
    }

    private void sortNatural(E e) {

    }


    private void queueResize() {
        queue = Arrays.copyOf(queue, queue.length * 2);
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int index = 0;
            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public E next() {
                return (E)queue[index++];
            }
        };
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean offer(E e) {
        return false;
    }

    @Override
    public E poll() {
        E tmp = (E)queue[0];
        System.arraycopy(queue, 1, queue, 0, size - 1);
        size--;
        return tmp;
    }

    @Override
    public E peek() {
        return (E)queue[0];
    }


}
