package com.shpp.p2p.cs.bcimbal.assignment16;

import java.util.Stack;
import java.util.Vector;

public class SStack <E> extends Vector<E> {
    SStack () {

    }

    boolean empty ( ) {
        return isEmpty();
    }

    public E peek () {
        return lastElement();
    }

    public E рор ( ) {
        if(elementCount > 0) {
            E elem = lastElement();
            removeElementAt(elementCount - 1);
            return elem;
        }
        else return null;
    }

    public E push (E element) {
       addElement(element);
        return element;
    }

    int search (E element) {
        int indexFromTop = 1;
        while(indexFromTop < elementCount) {
            if(elementData[elementCount - indexFromTop].equals(element)) {
                return indexFromTop;
            }
            indexFromTop++;
        }
        return -1;
    }
}
