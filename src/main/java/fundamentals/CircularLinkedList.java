package fundamentals;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Author Pierre Schaus
 *
 * We are interested in the implementation of a circular simply linked list,
 * i.e. a list for which the last position of the list refers, as the next position,
 * to the first position of the list.
 *
 * The addition of a new element (enqueue method) is done at the end of the list and
 * the removal (remove method) is done at a particular index of the list.
 *
 * A (single) reference to the end of the list (last) is necessary to perform all operations on this queue.
 *
 * You are therefore asked to implement this circular simply linked list by completing the class see (TODO's)
 * Most important methods are:
 *
 * - the enqueue to add an element;
 * - the remove method [The exception IndexOutOfBoundsException is thrown when the index value is not between 0 and size()-1];
 * - the iterator (ListIterator) used to browse the list in FIFO.
 *
 * @param <Item>
 */
public class CircularLinkedList<Item> implements Iterable<Item> {

    private long nOp = 0; // count the number of operations
    private int n;          // size of the stack
    private Node last;   // trailer of the list

    // helper linked list class
    private class Node {
        private Item item;
        private Node next;
    }

    public CircularLinkedList() {
        // TODO initialize instance variables
        //1er noeud est un noeud sentinelle, sa valeur sera tjrs vide !
        n=1; //en mettant dans l'init un élément vide, cela permet que plus tard dans le code faudra
        last=new Node();//pas vérifier si la liste est vide, elle est tjrs remplie !!!
        last.next=last;
        //on peut remarquer que le noeud last est le noeud sentinelle, donc le 1er vrai noeud sera last.next
    }

    public boolean isEmpty() {
        // TODO
        return n==1;
    }

    public int size() {
        // TODO
        return n-1;
    }

    private long nOp() {
        return nOp;
    }



    /**
     * Append an item at the end of the list
     * @param item the item to append
     */
    public void enqueue(Item item) {
        // TODO
        /*nOp++;     -->cette version pourrait être valide si on avait pas un noeud sentinelle
        Node newLastNode = new Node();
        newLastNode.item = item;
        Node firstNode = last.next;
        last.next = newLastNode;
        last = newLastNode;
        newLastNode.next = firstNode;
        n++;*/

        nOp++;
        Node oldLastNode = last;
        last = new Node();
        last.item = item;
        last.next = oldLastNode.next;
        oldLastNode.next = last;
        n++;
    }

    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their indices).
     * Returns the element that was removed from the list.
     */
    public Item remove(int index) {
        nOp++;
        if(index<0 || index>=size()){
            throw new IndexOutOfBoundsException();
        }
        Node previousNode =last.next; //donc 1er node
        for (int i = 0; i < index; i++) { //index = 4 ; 0-1-2-3-4 donc 5ieme elem
            previousNode = previousNode.next; //a la fin i=4, prev=prev.next donc, prev=au noeud actuel
        }
        if(previousNode.next==last)last= previousNode; //si le node qu on veut supp est le dernier, last=node precedent
        Item itemToRemove = previousNode.next.item; //ns on veut item de i=5, donc prev.next.item
        previousNode.next = previousNode.next.next; //ensuite on supp le lien, ducp prev.next=next.next
        n--;

        return itemToRemove;
    }


    /**
     * Returns an iterator that iterates through the items in FIFO order.
     * @return an iterator that iterates through the items in FIFO order.
     */
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    /**
     * Implementation of an iterator that iterates through the items in FIFO order.
     * The iterator should implement a fail-fast strategy, that is ConcurrentModificationException
     * is thrown whenever the list is modified while iterating on it.
     * This can be achieved by counting the number of operations (nOp) in the list and
     * updating it everytime a method modifying the list is called.
     * Whenever it gets the next value (i.e. using next() method), and if it finds that the
     * nOp has been modified after this iterator has been created, it throws ConcurrentModificationException.
     */
    private class ListIterator implements Iterator<Item> {

        // TODO You probably need a constructor here and some instance variables
        private long nOp;
        private Node currentNode;

        ListIterator() {
            nOp=nOp();
            currentNode=last.next.next; //pq last.next.next ?
            //car le 1er noeud est un noeud sentinelle, on ne l'utilise pas il est vide, sert juste
            //a nous faciliter le codage et eviter toutes les verifications...
        }

        @Override
        public boolean hasNext() {
            return currentNode!=last.next;
        }

        @Override
        public Item next() {
            if (nOp() != nOp) throw new ConcurrentModificationException();
            if (!hasNext()) throw new NoSuchElementException();

            Item currentItem = currentNode.item;
            currentNode = currentNode.next;
            return currentItem;
        }

    }

}


