package sorting;


import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author Pierre Schaus
 *
 * Given an array of (closed) intervals, you are asked to implement the union operation.
 * This operation will return the minimal array of sorted intervals covering exactly the union
 * of the points covered by the input intervals.
 * For example, the union of intervals [7,9],[5,8],[2,4] is [2,4],[5,9].
 * The Interval class allowing to store the intervals is provided
 * to you.
 *
 */
public class Union {

    /**
     * A class representing an interval with two integers. Hence the interval is
     * [min, max].
     */
    public static class Interval implements Comparable<Union.Interval> {

        public final int min;
        public final int max;

        public Interval(int min, int max) {
            assert(min <= max);
            this.min = min;
            this.max = max;
        }

        @Override
        public boolean equals(Object obj) {
            return ((Union.Interval) obj).min == min && ((Union.Interval) obj).max == max;
        }

        @Override
        public String toString() {
            return "["+min+","+max+"]";
        }

        @Override
        public int compareTo(Union.Interval o) {
            if (min < o.min) return -1;
            else if (min == o.min) return max - o.max;
            else return 1;
        }
    }

    /**
     * Returns the union of the intervals given in parameters.
     * This is the minimal array of (sorted) intervals covering
     * exactly the same points than the intervals in parameter.
     * 
     * @param intervals the intervals to unite.
     */
    public static Interval[] union(Interval[] intervals) {
        // TODO
        if(intervals.length==0||intervals.length==1)return intervals;

        Arrays.sort(intervals);
        List<Interval> unionOfIntervals = new ArrayList<Interval>();
        int intervalMin=intervals[0].min;
        int intervalMax=intervals[0].max;

        for (int i = 1; i < intervals.length; i++) { //O(n)
            if(intervals[i].min<=intervalMax){//si min de current<max du précédent on fusionne intervalles
                intervalMax=Math.max(intervalMax, intervals[i].max);
            }
            else{
                unionOfIntervals.add(new Interval(intervalMin,intervalMax));
                //si on est plus dans l'intervalle précédente, min et max sont ceux de l'intervalle courante
                intervalMin=intervals[i].min;
                intervalMax=intervals[i].max;
            }
        }
        unionOfIntervals.add(new Interval(intervalMin,intervalMax)); //la dernière intervalle en dehors de la boucle

        /*for(Interval i : unionOfIntervals){
            System.out.println(i);
        }*/

        return unionOfIntervals.toArray(new Interval[unionOfIntervals.size()]);
    }

    public static void main(String[] args) {
        Interval i = new Interval(2,7);
        Interval u = new Interval(3,8);
        Interval v = new Interval(1,5);
        Interval o = new Interval(9,10);
        Interval l = new Interval(11,13);
        Interval p = new Interval(12,17);

        Interval[]intervals = {p,i,u,o,v,l};
        Union.union(intervals);
    }

}
