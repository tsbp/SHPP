package com.shpp.p2p.cs.bcimbal.assignment16;


import java.util.Vector;

/* The class implements a standard stack, operating on the principle of "last come - first served" */

public class SStack <E> extends Vector<E> {


    /*******************************************************************************************************************
     * Check if stack is empty
     * @return Returns the boolean true if it is empty, and if it is
     * contains elements then the boolean false
     */
    boolean empty ( ) {
        return isEmpty();
    }

    /*******************************************************************************************************************
     * Returns an item from the top of the stack but does not delete it
     * @return item from the top of the stack
     */
    public E peek () {
        return lastElement();
    }

    /*******************************************************************************************************************
     * Returns an item from the top of the stack, deleting it
     * @return item from the top of the stack
     */
    public E pop ( ) {
        if(elementCount > 0) {
            E elem = lastElement();
            removeElementAt(elementCount - 1);
            return elem;
        }
        else return null;
    }

    /*******************************************************************************************************************
     * Pushes the given item on the stack and returns that item
     * @param element item
     * @return item
     */
    public E push (E element) {
       addElement(element);
        return element;
    }

    /*******************************************************************************************************************
     * Performs a search for a given element in the stack.
     * @param element given element
     * @return If the specified element is found,
     * its offset relative to the top of the stack, otherwise -1
     */
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
