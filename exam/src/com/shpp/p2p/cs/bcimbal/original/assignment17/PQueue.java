package com.shpp.p2p.cs.bcimbal.original.assignment17;

import java.util.*;

/**
 * The class represent bad attempt of PriorityQueue implementation
 * Never use this class
 */
public class PQueue<E> extends AbstractQueue<E> {
    /* Priority queue size */
    private int size = 0;
    /* Priority Queue length limit */
    private int length = 0;
    /* is queue limited flag*/
    private boolean limited ;
    /* Priority Queue storage array */
    private Object [] queue;
    /* Priority Queue ordering type */
    private Comparator comparator;


    /*******************************************************************************************************************
     * Creates a length limited priority queue with the  initial
     * capacity (1) that orders its elements according to their
     * ordering defined by comprator
     * @param length length of priority queue
     * @param comparator ordering type
     */
    public PQueue(int length, Comparator<? super E> comparator) {
        queue = new Object[1];
        this.comparator = comparator;
        if(length > 0) {
            this.length = length;
            limited = true;
        }
    }

    /*******************************************************************************************************************
     * Creates a priority queue with the  initial
     * capacity (1) that orders its elements according to their
     * natural ordering
     */
    public PQueue() {
        this(0,null);
    }

    /*******************************************************************************************************************
     *  Creates a length limited priority queue with the  initial
     *  capacity (1) that orders its elements according to their
     *  natural ordering
     * @param length length of priority queue
     */
    public PQueue(int length) {
        this(length,null);
    }

    /*******************************************************************************************************************
     * Inserts the specified element into this priority queue
     * @param e inserted element
     * @return return true if element inserted
     */
    @Override
    public boolean add(E e) {
        return offer(e);
    }

    /*******************************************************************************************************************
     * Grows up queue array
     */
    private void queueResize() {
        queue = Arrays.copyOf(queue, queue.length + 1);
    }

    /*******************************************************************************************************************
     * Priority queue iterator
     * @return queue iterator
     */
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

    /*******************************************************************************************************************
     * Tryes to insert the specified element into this priority queue
     * @param e inserted element
     * @return return true if element inserted
     */
    @Override
    public boolean offer(E e) {
        if(e== null || (limited && size >= length)) {
            return  false;
        }
        queue[size++] = e;
        Arrays.sort(queue, comparator);
        if(size >= queue.length) {
            queueResize();
        }
        return true;
    }

    /*******************************************************************************************************************
     * Return first element of queue and remove it
     * @return polled element
     */
    @Override
    public E poll() {
        E tmp = (E)queue[0];
        System.arraycopy(queue, 1, queue, 0, size - 1);
        size--;
        return tmp;
    }

    /**
     * Return first element of queue
     * @return first element of queue
     */
    @Override
    public E peek() {
        return (E)queue[0];
    }

    /*******************************************************************************************************************
     * Creates array representation of queue
     * @return queue as array of objects
     */
    @Override
    public Object[] toArray() {
        return queue;
    }
}
