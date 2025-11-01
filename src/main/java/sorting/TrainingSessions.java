package sorting;

import java.util.Arrays;

/**
 * The Olympic Games organizers need to allocate facilities for the athletes' training sessions.
 * Each team has a schedule of training sessions with a start and end time
 * 
 * To organize the athletes' training smoothly, the organizing committee must know
 * the minimum number of facilities they need so that the teams can train without overlap.
 * Each team has given the organizers their training slots,
 * represented by two integers timestamps: the start (included) and end time (not included!) of their session,
 * Given the training sessions' time, write the `minFacilitiesRequired` function,
 * which returns the minimum number of required facilities.
 *
 * Example Input with its visual representation:
 *
 * int[][] sessions = {
 *     {12, 20},//   --------
 *     {14, 25},//     -----------
 *     {19, 22},//          ---
 *     {25, 30},//                -----
 *     {26, 30},//                 ----
 * };
 *
 * In this example, the minimum number of facilities required is 3
 * as at time 19, there are 3 sessions (intervals) overlapping,
 * namely [12, 20), [14, 25), and [19, 22).
 *
 *
 * More formally, the goal is to minimize k such that for all time t,
 * the number of sessions that overlap at time t is at most kx^x
 *
 * The computation must be performed in O(n.log(n)) time complexity
 * where n is the number of training sessions.
 *
 *
 */
public class TrainingSessions {

    /**
     * Determines the minimum number of facilities required to accommodate
     * all the training sessions without overlap.
     *
     * @param sessions a non-null array of int arrays where each int array represents
     *                 a session with start time and end time.
     * @return the minimum number of facilities required.
     */
    /*public int minFacilitiesRequired(int[][] sessions) {
        // TODO

        sort(sessions);

        return calculateMinimumFacilities(sessions);
    }

    public static void main(String[] args) {
        int[][] sessions = {{10,13},{8,15},{13,17},{14,16},{9,14},{17,22},{15,18},{19,22},{19,21}};
        TrainingSessions sess = new TrainingSessions();
        sess.minFacilitiesRequired(sessions);
    }

    public static int calculateMinimumFacilities(int[][] sessions){
        int minimumNumberFacilities=1;
        for (int i = 0; i < sessions.length; i++) {
            int numberFacilitiesInUse=1;
            for (int j=i+1;j<sessions.length;j++){
                if(sessions[j][0]<sessions[i][1]){
                    numberFacilitiesInUse++;
                    if (numberFacilitiesInUse > minimumNumberFacilities) {
                        minimumNumberFacilities = numberFacilitiesInUse;
                    }
                }
                else{
                    break;//ca veut dire que la session i est terminée vu que le début de j est plus grand ou égal que la fin de i
                }
            }
        }
        return minimumNumberFacilities;
    }

    public static void sort(int [][] sessions){
        Arrays.sort(sessions, (a, b) -> {
            if (a[0] != b[0])
                return Integer.compare(a[0], b[0]);  // d'abord heure du début début
            return Integer.compare(a[1], b[1]);       // ensuite heure de fin
        });
    }*/

    public int minFacilitiesRequired(int[][] sessions) {
        if (sessions.length == 0) {
            return 0;
        }

        // Séparer les heures de début et de fin
        int[] starts = new int[sessions.length];
        int[] ends = new int[sessions.length];
        for (int i = 0; i < sessions.length; i++) {
            starts[i] = sessions[i][0];
            ends[i] = sessions[i][1];
        }
        // Tri des tableaux
        Arrays.sort(starts);
        Arrays.sort(ends);

        int usedFacilities = 0;
        int maxFacilities = 0;
        int i = 0;
        int j = 0;
        while (i < sessions.length && j < sessions.length) {
            // Une session commence avant qu'une autre se termine = on ajoute une salle
            if (starts[i] < ends[j]) {
                usedFacilities++;
                maxFacilities = Math.max(maxFacilities, usedFacilities);
                i++;
            }
            // Une session s'est terminée avant la suivante = on libère une salle
            else {
                usedFacilities--;
                j++;
            }
        }
        return maxFacilities;
    }
}
