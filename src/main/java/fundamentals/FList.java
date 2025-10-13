package fundamentals;


import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Author: Pierre Schaus
 *
 * Functional Programming is an increasingly important programming paradigm.
 * In this programming paradigm, data structures are immutable.
 * We are interested here in the implementation of an immutable list
 * called FList allowing to be used in a functional framework.
 * Look at the main method for a small example using this functional list
 *
 * Complete the implementation to pass the tests
 *
 * @param <A>
 */
public abstract class FList<A> implements Iterable<A> {

    public static void main(String[] args) {
        FList<Integer> list = FList.nil();

        for (int i = 0; i < 10; i++) {
            list = list.cons(i);
        }


        list = list.map(i -> i+1);
        // will print 10,9,...,1
        for (Integer i: list) {
            System.out.println(i);
        }
        list = list.filter(i -> i%2 == 0);
        // will print 10,...,6,4,2
        for (Integer i: list) {
            System.out.println(i);
        }
    }

    // return true if the list is not empty, false otherwise
    public final boolean isNotEmpty() {
        return this instanceof Cons;
    }

    // return true if the list is empty (Nill), false otherwise
    public final boolean isEmpty() {
        return this instanceof Nil;
    }

    // return the length of the list
    public final int length() {
        // TODO
        if(isEmpty()){
            return 0;
        }
        else{
            return 1+this.tail().length(); //1 est le head + tail qui est le reste de la liste
        }
    }

    // return the head element of the list ---> premier élément de la liste
    public abstract A head();

    // return the tail of the list ---> reste de la liste : tous les éléments, sauf le premier
    public abstract FList<A> tail();

    // creates an empty list
    public static <A> FList<A> nil() {
        return (Nil<A>) Nil.INSTANCE;
    }


    public final FList<A> cons(final A a) {
        return new Cons(a, this); //O(1)
    }

    // return a list on which each element has been applied function f
    //on veut que les élements A deviennent des éléments B grâce à la fonction f
    //la fonction f par ex : f = (i -> i + 1) donc si A =2, son B vaudra 3
    //important à préciser, une FList n'est pas une liste comme un tableau, C'est : Cons(3, Cons(2, Cons(1, Nil)))
    public final <B> FList<B> map(Function<A,B> f) {
        // TODO
        if(this.isEmpty())return nil(); //O(1)
        B newHead = f.apply(this.head()); //pour appliquer la fonction, on doit faire f.apply , O(1)
        FList<B> newTail = this.tail().map(f);//ici on va faire de la récursivité pour que ca s'applique aussi au reste de la liste (chacun devient head à son tour)
        //l appel a tail : T(n) = T(n - 1) + O(1), où
        //n = taille de la liste,
        //T(n) =temps total pour map sur une liste de taille n
        //o(1) pour le if...
        //T(n) = T(n-1) + O(1)
        //        = (T(n-2) + O(1)) + O(1)
        //        = (T(n-3) + O(1)) + O(1) + O(1)
        //       ...
        //        = T(0) + n * O(1) = O(n)

        return new Cons<>(newHead,newTail);//o(1) //donc la derniÈre récursivité s'arretera au if qd c vide, et puis on retourne la nvl liste avc la fonction appliquée

        //plus opti = (else) return tail().map(f).cons(f.apply(head()));
    }//-->complexité : n+n+recursif ? +

    // return a list on which only the elements that satisfies predicate are kept
    //Predicate : C’est une fonction de test (ou “condition”) qu’on peut appliquer à chaque élément. Va renvoyer true or false
    //EX : Predicate<Integer> estPair = x -> x % 2 == 0;
    public final FList<A> filter(Predicate<A> f) {
        // TODO
        if(this.isEmpty())return nil();

        // on filtre le reste de la liste d'abord
        FList<A> filteredTail = this.tail().filter(f);

        if(f.test(this.head())){ //on garde l'élément si c vrai
            return filteredTail.cons(this.head());//obligé de faire filtered = filter.cons, car pour rappel c'est immutable. On ne peut pas directement faire
        } //filtered.cons ---> cons ne modifie pas la liste, il renvoie une nouvelle liste !!!, dans mon cas je fais directement return (apres opti du code)

        return filteredTail;
    } //O(1)


    // return an iterator on the element of the list
    public Iterator<A> iterator() {
        return new Iterator<A>() {
            // complete this class

            FList<A> current = FList.this;

            public boolean hasNext() {
                // TODO
                return current.isNotEmpty();
            }

            public A next() { //le but c'est d'itérer dans la liste !
                // TODO
                //if (!hasNext()) throw new NoSuchElementException();
                A value = current.head();//Donc on stock la prochaine valeur mais après on itère
                current = current.tail(); //donc le current devient le reste de la liste sans le head, et puis apres sera pareil...
                return value; //on retourne le head qui est donc le next
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }


    private static final class Nil<A> extends FList<A> {
        public static final Nil<Object> INSTANCE = new Nil();

        @Override
        public A head() {
            // TODO
             //throw new NoSuchElementException("No head in an empty list");
            throw new IllegalArgumentException();
        }

        @Override
        public FList<A> tail() {
            // TODO
            //throw new NoSuchElementException("No tail in an empty list");
            throw new IllegalArgumentException();
        }
    }

    private static final class Cons<A> extends FList<A> {

        // TODO add instance variables
        private final A head;
        private final FList<A> tail;

        //Cons c'est juste le premier élément + le reste , donc notre liste quoi
        Cons(A a, FList<A> tail) {
            this.head = a;
            this.tail = tail;
        }

        @Override
        public A head() {
            // TODO
             return head;
        }

        @Override
        public FList<A> tail() {
            // TODO
             return tail;
        }
    }
}
