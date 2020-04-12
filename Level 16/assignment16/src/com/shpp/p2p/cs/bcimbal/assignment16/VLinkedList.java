package com.shpp.p2p.cs.bcimbal.assignment16;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class VLinkedList<T> implements Iterable<T>{
    LLNode last = null;
    LLNode<T> first = null;
    int size = 0;
    LLNode[] elements = new LLNode[0];
    public void add(T obj){

        LLNode <T> node = new LLNode<>(obj, last);
        if(first == null) first = node;
        last = node;
        size++;



    }

    @Override
    public Iterator<T> iterator() {
        elements = new LLNode[this.size];
        //T[] arr = (T[])new Object[this.size];
        VArrayList<T> dd = new VArrayList<>();
        LLNode tmp = this.last;
        for(int i = 0; i < size; i++) {
            dd.add( (T)tmp.getData());
            elements[i] = tmp;
            tmp = tmp.getParent();
        }
        List a = Arrays.asList(dd.getObjArray());
        Iterator it = a.iterator();
        return it;
    }
}
class  LLNode <T>{
    T data;
    LLNode parent;
    LLNode(T data, LLNode parent){
        this.data = data;
        this.parent = parent;
    }

    public T getData() {
        return data;
    }

    public LLNode getParent() {
        return parent;
    }
}
