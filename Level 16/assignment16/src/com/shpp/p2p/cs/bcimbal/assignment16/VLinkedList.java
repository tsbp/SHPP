///*
//package com.shpp.p2p.cs.bcimbal.assignment16;
//
//import java.util.AbstractCollection;
//import java.util.Arrays;
//import java.util.Iterator;
//import java.util.List;
//
//public class VLinkedList<T> extends AbstractCollection<T>*/
///*implements Iterable<T>*//*
//{
//    LLNode <T>last = null;
//    LLNode <T>first = null;
//    int size = 0;
//    int index;
//
//    private Object [] objArray;
//
//    VLinkedList () {
//        objArray  = new Object[10];
//    }
//
//    public void set(int index, T data) {
//        LLNode<T> tmp = getNode(index);
//        if (tmp != null) {
//            tmp.setData(data);
//        }
//    }
//
//    public void clear(){
//        last = null;
//        first = null;
//    }
//
//    public boolean add(T obj){
//
//        if(first == null && last == null) {
//            LLNode<T> node = new LLNode<>(obj, null, null);
//            last = node;
//            first = node;
//        }
//        else {
//            LLNode<T> node = new LLNode<T>(obj, last, null);
//            last.setNext(node);
//            last = node;
//        }
//        this.objArray[size++] = obj;
//
//        return true;
//    }
//
//    public void addFirst(T data) {
//        first.setPrevious(first);
//        first = new LLNode<T>(data,null, first);
//        first.getNext().setPrevious(first);
//    }
//
//    public void addLast(T data) {
//        add(data);
//    }
//
//
//    public T poll() {
//        LLNode <T> tmp =  first;
//        first.getNext().setPrevious(null);
//        first = first.getNext();
//        size--;
//        return tmp.getData();
//
//    }
//
//    public boolean remove(int index) {
//        LLNode<T> tmp = getNode(index);
//
//        if(tmp == null) {
//            return false;
//        }
//
//        if(tmp.getPrevious() != null) {
//            tmp.getPrevious().setNext(tmp.getNext());
//        }
//        else {
//            first = tmp.getNext();
//        }
//
//        if(tmp.getNext() != null) {
//            tmp.getNext().setPrevious(tmp.getPrevious());
//        }
//        else {
//            last = tmp.getPrevious();
//        }
//
//        size--;
//        return true;
//    }
//
//    private LLNode <T> getNode(int index) {
//        LLNode<T> tmp = first;
//        while(index > 0) {
//            if(tmp.getNext() != null){
//                tmp = tmp.getNext();
//            }
//            else return  null;
//            index--;
//        }
//        return tmp;
//    }
//
//    @Override
//    public Iterator<T> iterator() {
//        Object[] arr = new Object[this.size];
//
//        LLNode <T> tmp = this.first;
//        for(int i = 0; i < size; i++) {
//            arr[i] = tmp.getData();
//            tmp = tmp.getNext();
//        }
//        List a = Arrays.asList(arr);
//        Iterator <T> it = a.iterator();
//        return it;
//    }
//
//    @Override
//    public int size() {
//        return 0;
//    }
//}
//class  LLNode <T>{
//    T data;
//    LLNode <T> next;
//    LLNode <T>previous;
//    LLNode(T data, LLNode <T> previous, LLNode <T>next){
//        this.data = data;
//        this.previous = previous;
//        this.next = next;
//    }
//
//    public void setData(T data){
//        this.data = data;
//    }
//
//    public T getData() {
//        return data;
//    }
//
//    public LLNode <T>getNext() {
//        return this.next;
//    }
//
//    public LLNode <T>getPrevious() {
//        return this.previous;
//    }
//
//    public void setNext(LLNode <T>next) {
//        this.next = next;
//    }
//
//    public void setPrevious(LLNode<T> previous) {
//        this.previous = previous;
//    }
//}
//*/
