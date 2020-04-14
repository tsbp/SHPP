package com.shpp.p2p.cs.bcimbal.assignment16;

import java.util.*;

public class LinkList<T> extends AbstractSequentialList<T> {

    LLNode<T> last = null;
    LLNode<T> first = null;
    int size = 0;

    LinkList() {

    }

    @Override
    public T remove(int index) {
        if(isIndexInOfBounds(index)) {
            LLNode<T> removed = getNode(index);
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

    @Override
    public void clear() {
        first = null;
        last = null;
        size = 0;
    }

    private boolean isIndexInOfBounds(int index) {
        return index >= 0 && index < size;
    }

    @Override
    public T get(int index) {
        LLNode <T>tmp = first;
        for (int i = 0; i < index; i++) {
            tmp = tmp.next;
        }
        return tmp.data;
    }

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

    @Override
    public T set(int index, T element) {
        if(isIndexInOfBounds(index)) {
            getNode(index).data = element;
            return element;
        }
        return null;
    }

    @Override
    public boolean add(T obj) {
        if (first == null && last == null) {
            LLNode<T> node = new LLNode<>(obj, null, null);
            last = node;
            first = node;
        } else {
            LLNode<T> node = new LLNode<T>(obj, last, null);
            last.setNext(node);
            last = node;
        }
        size++;
        return true;
    }

    @Override
    public void add(int index, T element) {
        if(isIndexInOfBounds(index)) {
            LLNode <T> shifted = getNode(index);
            shifted.previous.next = new LLNode<>(element, shifted.previous, shifted);
            size++;
        }
    }

    public void addLast(T data) {
        add(data);
    }

    public void addFirst(T data) {
        first.setPrevious(first);
        first = new LLNode<T>(data, null, first);
        first.getNext().setPrevious(first);
        size++;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        Object[] tmp = new Object[size];
        for(int i = 0; i < size; i++) {
            tmp[i] = get(i);
        }
        List list = Arrays.asList(tmp);
        return list.listIterator();
    }

    @Override
    public int size() {
        return this.size;
    }

    private static class LLNode<T> {
        T data;
        LLNode<T> next;
        LLNode<T> previous;

        LLNode(T data, LLNode<T> previous, LLNode<T> next) {
            this.data = data;
            this.previous = previous;
            this.next = next;
        }

        public void setData(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }

        public LLNode<T> getNext() {
            return this.next;
        }

        public LLNode<T> getPrevious() {
            return this.previous;
        }

        public void setNext(LLNode<T> next) {
            this.next = next;
        }

        public void setPrevious(LLNode<T> previous) {
            this.previous = previous;
        }
    }
}



