package fundamentals;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Proposed at the exam of August 2025
 * You are asked to implement a specialized linked chain data structure
 * optimized for high-performance insertions.
 *
 * This chain consists of n elements numbered from 0 to n-1.
 * Elements 0 and n-1 are always present in the chain and cannot be removed or relocated.
 * No elements may be inserted before 0 or after n-1.
 * All other elements start unlinked and can be inserted exactly once.
 *
 * To ensure constant-time operations, the chain is represented internally using two arrays:
 * 	successor[i] and predecessor[i] represent the immediate next and
 * 	previous elements of element i in the chain.
 * 	If the element i is not yet part of the chain, we have successor[i] = predecessor[i] = i.
 *  By convention, predecessor[0] == 0 and successor[n - 1] == n - 1.
 *
 * When the data structure is initialized with n elements,
 * the chain contains exactly two linked elements: 0 <-> n-1.
 *
 * You must implement the following methods:
 * 	-	insertAfter(a, b): Insert element b immediately after element a in the chain.
 * 	-	insertBefore(a, b): Insert element b immediately before element a in the chain.
 * 	-   iterator() that enables to iterate over the elements of the chain in their current order,
 * 	    from 0 to n-1.
 */
public class LinkedChain implements Iterable<Integer> {

    protected int[] successor;
    protected int[] predecessor;

    /**
     * Constructs a linked chain with n elements.
     * The chain is initialized with two linked elements: 0 <-> n-1.
     * The other elements (1 to n-2) are not yet in the chain.
     * @param n > 1 the capacity of the chain
     */
    public LinkedChain(int n) {
        // TODO
        if(n<1)throw new IllegalArgumentException();

        predecessor = new int[n];
        successor = new int[n];

        for (int i = 0; i <= n-1; i++) {//qd un élément n'est pas dans la liste, pred[i]=suc[i]=i
            predecessor[i]=i;
            successor[i]=i;
        }
        successor[0]=n-1;
        predecessor[n-1]=0;
    }


    /**
     * Inserts an element just after another one in the chain
     * @param a the element after which b will be inserted,
     *        "a" must be in the chain and must be different from n-1 (last element)
     * @param b the element to insert, it must not be in the chain
     * @throws IllegalArgumentException if "a" is not in the chain,
     *         or if "b" is already in the chain or if "a" is the last
     *         or if "a" or "b" are not in the range [0, n-1]
     */

    //Quand a n'est pas dans la chaine, successor[a]=a=predecessor[a]. Néanmoins si on insère a après un autre élément,
    //predecessor[a]=qql chose, et successor[a]=successor[qqlchose], successor[qqlchose]=a
    public void insertAfter(int a, int b) {
        // TODO
        int n = predecessor.length;
        // VÉRIFIER LES BORNES D'ABORD ! -->pas opti ici
        if (a < 0 || a >= n || b < 0 || b >= n) {
            throw new IllegalArgumentException();
        }
        if(successor[a]!=a && successor[b]==b && a>=0 && a<n-1 && b>0 && b<n-1){
            int c= successor[a];
            successor[b]=c;
            predecessor[b]=a;
            successor[a]=b;
            predecessor[c]=b;
        }
        else{
            throw new IllegalArgumentException();
        }
    }


    /**
     * Inserts element "b" just before element "a".
     * @param a the element before which "b" should be inserted,
     *        "a" must be in the chain and must be different from 0 (first element)
     * @param b the element to insert, it must not be in the chain
     * @throws IllegalArgumentException if "a" is not in the chain,
     *        or if "b" is already in the chain or if "a" is the first
     *        or if "a" or "b" are not in the range [0, n-1]
     */
    public void insertBefore(int a, int b) {
        // TODO
        int n = predecessor.length;

        // VÉRIFIER LES BORNES D'ABORD !
        if (a < 0 || a >= n || b < 0 || b >= n) {
            throw new IllegalArgumentException();
        }

        if(predecessor[a]!=a && successor[b]==b && a>0 && a<=n-1 && b>0 && b<n-1){
            int c = predecessor[a];
            predecessor[b]=c;
            successor[b]=a;
            predecessor[a]=b;
            successor[c]=b;
        }
        else{
            throw new IllegalArgumentException();
        }
    }



    /**
     * Iterator over the chain from 0 to n-1 in the order they are connected.
     * @return an iterator over the chain, in the order they are connected,
     *         starting from the first i.e. 0 and ending with the last i.e. n-1
     */
    @Override
    public Iterator<Integer> iterator() {
        // TODO
        return new Iterator<Integer>() {
            int current=0;
            @Override
            public boolean hasNext() {
                return current!=-1;
            }

            @Override
            public Integer next() {
                if(!hasNext()) throw new NoSuchElementException();
                int value = current;
                if (current == successor.length - 1) {
                    current = -1; // plus d’élément à donner
                } else {
                    current = successor[current];
                }
                return value;
            }
        };
    }

}
