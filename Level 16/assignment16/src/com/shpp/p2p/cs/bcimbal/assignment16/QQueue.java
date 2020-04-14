package com.shpp.p2p.cs.bcimbal.assignment16;

import sun.misc.Queue;

public class QQueue <E> extends LinkList<E> {

    QQueue() {

    }
    public E peek() {
        if(!isEmpty()) {
           return getNodeData(getFirst());
        }
        return null;
    }

    @Override
    public E poll() {
        return super.poll();
    }
    //    add()
//    peek()
//    element()

}
