package com.shpp.p2p.cs.bcimbal.assignment16;

import java.util.Queue;

/***********************************************************************************************************************
 * The class implements the behavior of a queue, which acts as a first-in-first-out list.
 *
 */
public class QQueue<E> extends LinkList<E> implements Queue<E> {

    /* is queue limited flag*/
    private boolean limited ;
    /* limited queue size */
    private int qSize ;

    /*******************************************************************************************************************
     * Constructor for unlimited queue
     */
    QQueue() {
        limited = false;
    }

    /*******************************************************************************************************************
     * Constructor for limited queue
     * @param size int queue size
     */
    QQueue (int size) {
        this.qSize = size;
        this.limited = true;
    }

    /*******************************************************************************************************************
     * Attempts to enter the specified object into the queue. Returns
     * boolean true if the object is entered, otherwise boolean f'alse
     *
     * @param e object probably to be added to queue
     * @return true if  object added to queue
     */
    @Override
    public boolean offer(E e) {
        if(limited  && size() >= qSize) {
            return false;
        }
        add(e);
        return true;
    }

    /*******************************************************************************************************************
     * Removes an item from the head of the queue, returning it.
     * Throws an exception of type NoSuchElementException,
     * if the queue is empty
     *
     * @return removed object
     */
    @Override
    public E remove()  {
        if(!isEmpty()) {
            return poll();
        }
        else throw new NoSuchElementException("queue is empty");
    }

    /*******************************************************************************************************************
     * Returns an item from the head of the queue. Returnable
     * item is not deleted. If the queue is empty, an exception
     * of type NoSuchElementException is thrown
     *
     * @return object from the head of the queue
     */
    @Override
    public E element() {
        if(!isEmpty()) {
            return peek();
        }
        else throw new NoSuchElementException("queue is empty");
    }

    /*******************************************************************************************************************
     * Returns an item from the head of the queue. If the queue is empty,
     * returns null value. Return item not removed from the queue
     *
     * @return queue head object
     */
    @Override
    public E peek() {
        if (!isEmpty()) {
            return getNodeData(getFirst());
        }
        return null;
    }
}

/***********************************************************************************************************************
 * No Such Element Exception class
 * for exception to be thrown by QQueue class methods
 */
 class NoSuchElementException extends RuntimeException {
    public NoSuchElementException(String errorMsg) {
        super(errorMsg);
    }
}
