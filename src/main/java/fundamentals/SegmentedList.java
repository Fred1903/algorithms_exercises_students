package fundamentals;

import java.util.*;

/**
 * Author Pierre Schaus
 *
 * The SegmentedList is a linear structure that consists of several segments (or groups) of elements,
 * where each segment can have its own size.
 * It is like a list of lists, but the user interacts with it as a single linear sequence.
 * The iterator will need to traverse each segment in order, element by element,
 * seamlessly presenting the entire structure as a single flat sequence.
 *
 * Be careful with the iterator, it should implement the fail-fast behavior.
 * A fail-fast iterator detects illegal concurrent modification during iteration (see the tests).
 *
 * Example:
 *
 *         SegmentedList<Integer> segmentedList = create();
 *
 *         // Add segments
 *         List<Integer> segment1 = new ArrayList<>();
 *         segment1.add(1); segment1.add(2); segment1.add(3);
 *         segmentedList.addSegment(segment1); // add [1,2,3]
 *
 *         List<Integer> segment2 = new ArrayList<>();
 *         segment2.add(4); segment2.add(5);
 *         segmentedList.addSegment(segment2); // add [4,5]
 *
 *         List<Integer> segment3 = new ArrayList<>();
 *         segment3.add(6); segment3.add(7); segment3.add(8); segment3.add(9);
 *         segmentedList.addSegment(segment3); // add [6,7,8,9]
 *
 *         segmentedList.removeSegment(1); // remove [4,5], the segment in second position
 *
 *         // Iterate through the SegmentedList that is elements 1,2,3,6,7,8,9
 *         for (Integer value : segmentedList) {
 *             System.out.println(value);
 *         }
 *
 *         // Example of using get()
 *         System.out.println("Element at global index 4: " + segmentedList.get(4)); // Should print 5
 *
 * @param <T>
 */
public interface SegmentedList<T> extends Iterable<T> {

    // Add a new segment (list) to the SegmentedList.
    void addSegment(List<T> segment);

    // Remove a segment by its index.
    void removeSegment(int index);

    // Get the total size of the segmented list (across all segments).
    int size();

    // Retrieve an element at a global index (spanning all segments).
    T get(int globalIndex);

    // Static method inside the interface
    static <T> SegmentedList<T> create() {
        return new SegmentedListImpl<>();
    }

}

class SegmentedListImpl<T> implements SegmentedList<T> {
    // TODO: Implement the SegmentedList interface here
    private List<List<T>> segments = new LinkedList<>();
    private int nOp = 0;

    // Add a new segment (list) to the SegmentedList.
    public void addSegment(List<T> segment) {
        nOp++; //on va augmenter nOp car on veut pas qu'il y ait un add pendant qu'on parcourt avec iterator
        segments.add(segment);
    }

    // Remove a segment by its index.
    public void removeSegment(int index) {
        if(index >= 0 && index < segments.size()) {
            segments.remove(index);
        }
        else{
            throw new IndexOutOfBoundsException();
        }
    }

    // Get the total size of the segmented list (across all segments).
    public int size() {
        int totalSizeSegmentedList = 0;
        for(List<T> segment : segments){
            totalSizeSegmentedList+=segment.size();
        };
        return totalSizeSegmentedList;
    }

    // Retrieve an element at a global index (spanning all segments).
    public T get(int globalIndex) {
        int count=0;
        for (List<T> segment : segments) {
            if(globalIndex<count + segment.size()){
                return segment.get(globalIndex - count);
            }
            count += segment.size();
        }
        throw new IndexOutOfBoundsException("Index out of bounds.");
    }



    // Return an iterator for the segmented list.
    @Override
    public Iterator<T> iterator() {
         return new SegmentedListIterator();
    }


    private class SegmentedListIterator implements Iterator<T> {
        private int segmentIndex = 0; // Current segment we are traversing
        private int elementIndex = 0; // Current element within the segment

        private int t = nOp; //on save valeur de nOp avant d'itérer pr pouvoir verif que c la meme

        @Override
        public boolean hasNext() {
            if(t!=nOp){
                throw new ConcurrentModificationException("Concurrent Modification");
            }
            while(segmentIndex<segments.size()){
                if(elementIndex<segments.get(segmentIndex).size()){
                    return true;
                }
                segmentIndex++;
                elementIndex=0;
            }
            return false;
        }

        @Override
        public T next() {
            if(t!=nOp){
                throw new ConcurrentModificationException("Concurrent Modification");
            }
            if(!hasNext()){
                throw new NoSuchElementException("No element left");
            }
            return segments.get(segmentIndex).get(elementIndex++);
        }
    }
}
