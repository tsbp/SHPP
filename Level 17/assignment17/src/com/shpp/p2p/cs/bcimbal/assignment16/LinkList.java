package com.shpp.p2p.cs.bcimbal.assignment16;

import java.util.*;
/* The class provides a linked list data structure */

public class LinkList<T> extends AbstractSequentialList<T> {

    LLNode<T> last = null;
    LLNode<T> first = null;
    int size = 0;



    /*******************************************************************************************************************
     * Method to get first node of list
     * @return first node of list
     */
    //todo
//    public LLNode<T> getFirst() {
//        return first;
//    }

    public T getFirst() {
        return first.data;
    }


    /*******************************************************************************************************************
     * get object on input list node
     *
     * @param node input node
     * @return node object
     */
    public T getNodeData(LLNode<T> node){
        if(node != null) {
            return node.data;
        }
        return null;
    }

    /*******************************************************************************************************************
     * Method to remove node at index
     *
     * @param index removed node index
     * @return removed node object, if node present. Otherwise - null;
     */
    @Override
    public T remove(int index) {
        if(isIndexInOfBounds(index)) {
            LLNode<T> removed = getNode(index);
            assert removed != null;
            LLNode<T> prev = removed.previous;
            LLNode<T> next = removed.next;

            if(next == null) { // last node
                prev.next = null;
                last = prev;
            }
            else if(prev == null) { //first node
                next.previous = null;
                first = next;
            }
            else { // inner node
                prev.next = next;
                next.previous = prev;
            }
            size--;
            return removed.data;
        }
        return null;
    }

    /*******************************************************************************************************************
     * Clear list
     */
    @Override
    public void clear() {
        first = null;
        last = null;
        size = 0;
    }

    /*******************************************************************************************************************
     * check Index In Of Bounds of array
     * @param index input index
     * @return true if Index In Of Bounds
     */
    private boolean isIndexInOfBounds(int index) {
        return index >= 0 && index < size;
    }

    /*******************************************************************************************************************
     * Method too get object at index
     * @param index list index
     * @return  object at index, if node present. Otherwise - null;
     */
    @Override
    public T get(int index) {
        if(isIndexInOfBounds(index)) {
            LLNode<T> tmp = first;
            for (int i = 0; i < index; i++) {
                tmp = tmp.next;
            }
            return tmp.data;
        }
        return null;
    }

    /*******************************************************************************************************************
     * Method too get node at index
     * @param index list index
     * @return  node at index, if node present. Otherwise - null;
     */
    private LLNode <T> getNode (int index) {
        if(isIndexInOfBounds(index)) {
            LLNode<T> tmp = first;
            for (int i = 0; i < index; i++) {
                tmp = tmp.next;
            }
            return tmp;
        }
        return null;
    }

    /*******************************************************************************************************************
     * Method to set object data in node at given index
     *
     * @param index list index
     * @param element object to to be set in node
     * @return object to to be set, if node present. Otherwise - null;
     */
    @Override
    public T set(int index, T element) {
        if(isIndexInOfBounds(index)) {
            getNode(index).data = element;
            return element;
        }
        return null;
    }

    /*******************************************************************************************************************
     * Method to add element to the end of list
     * @param obj object to be added
     * @return true
     */
    @Override
    public boolean add(T obj) {
        if (first == null && last == null) {
            LLNode<T> node = new LLNode<>(obj, null, null);
            last = node;
            first = node;
        } else {
            LLNode<T> node = new LLNode<T>(obj, last, null);
            last.next = node;
            last = node;
        }
        size++;
        return true;
    }

    /*******************************************************************************************************************
     * Method to add element at given index of list
     * @param index list index
     * @param element object to be added
     */
    @Override
    public void add(int index, T element) {
        if(isIndexInOfBounds(index)) {
            LLNode <T> shifted = getNode(index);
            assert shifted != null;
            shifted.previous.next = new LLNode<>(element, shifted.previous, shifted);
            size++;
        }
    }

    /*******************************************************************************************************************
     * Method to add element to the end of list
     */

    public void addLast(T data) {
        add(data);
    }

    /*******************************************************************************************************************
     * Method to add element at first position of list
     */
    //todo changed
    public void addFirst(T data) {
        LLNode<T>tmpFirst = first;

 //       first.previous = first;
        first = new LLNode<T>(data, null, tmpFirst);
//        first.next.previous
        if (first.next != null)
            first.next.previous = first;
        size++;
    }

    /*******************************************************************************************************************
     * Method to remove first element of list
     */
    public void removeFirst(){
        if(size > 1) {
            first = first.next;
            first.previous = null;
            size--;
            if(size == 1) {
                last = first;
            }
        }
        else {
            size = 0;
            first = last = null;
        }
    }

    /*******************************************************************************************************************
     * Method to get first element of list with its removing
     * @return  first element of list
     */
    public T poll(){
        LLNode<T> toReturn = first;
        removeFirst();
        return  toReturn.data;
    }

    /*******************************************************************************************************************
     * Iterator for current list
     * @param index list index
     * @return iterator for current list
     */
    @Override
    public ListIterator<T> listIterator(int index) {
        Object[] tmp = new Object[size];
        for(int i = 0; i < size; i++) {
            tmp[i] = get(i);
        }
        List list = Arrays.asList(tmp);
        return list.listIterator();
    }

//todo added
//    @Override
//    public void sort(Comparator<? super T> c) {
//
//        Object[] a = this.toArray();
//        Arrays.sort(a, (Comparator) c);
//
//        {
//            System.out.println();
//        }
//    }

    @Override
    public ListIterator<T> listIterator() {
        //return super.listIterator();
        return new ListIterator<T>() {
            LLNode current = first;
            @Override
            public boolean hasNext() {
                return current.next == null;
            }

            @Override
            public T next() {
                return (T)current.next;
            }

            @Override
            public boolean hasPrevious() {
                return current.previous == null;
            }

            @Override
            public T previous() {
                return (T)current.previous;
            }

            @Override
            public int nextIndex() {
                return 0;
            }

            @Override
            public int previousIndex() {
                return 0;
            }

            @Override
            public void remove() {

            }

            @Override
            public void set(T t) {

            }

            @Override
            public void add(T t) {

            }
        };
    }

    //todo added
    @Override
    public Object[] toArray() {
//        LLNode<T> [] ret = (LLNode<T> [])new Object[size];
//        LLNode<T> current = first;
//        int index = 0;
//        while (current.next != null){
//            ret[index++] = current;
//            current = current.next;
//        }
        Object[] ret = new Object[size];
        for(int i = 0; i < size; i++) {
            ret[i] = get(i);
        }
        return  ret;
    }

    //todo added
    public Object clone() {
        return this;
        //return super.clone();
    }

    /*******************************************************************************************************************
     * Size of current list
     * @return size of current list
     */
    @Override
    public int size() {
        return this.size;
    }


    /* Class for list node */
   static class LLNode<T> {
        /* node object */
        T data;
        /* next node pointer */
        LLNode<T> next;
        /* previous node pointer */
        LLNode<T> previous;

        /*******************************************************************************************************************
         * Constructor
         * @param data node object
         * @param previous previous node pointer
         * @param next next node pointer
         */
        LLNode(T data, LLNode<T> previous, LLNode<T> next) {
            this.data = data;
            this.previous = previous;
            this.next = next;
        }
    }
}





