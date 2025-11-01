package sorting;

import java.lang.reflect.Array;

/**
 * You're a photographer for a soccer meet.
 * You will be taking pictures of pairs of opposing teams.
 * All teams have the same number of players.
 * A team photo consists of a front row of players and
 * a back row of players.
 * In order to be visible, a player in the back row must be taller (not equal thus)
 * than the player in front of him.
 * All players in a row must be from the same team.
 * 
 * You must design an algorithm that takes as input two teams (their heights) and checks if it is
 * possible to arrange them to take a photo given the constraints.
 * If so, your method must return the
 * sum of the difference between the height of the aligned players.
 * For example if each team has three players, and their respective heights are [170, 160, 180] and
 * [192, 175, 178], then your method must return 35.
 * If no arrangement can be made, it returns -1.
 *
 * Feel free to use existing java classes.
 */
public class Photo {
    
    /**
     * This method checks if there is an arrangement of team A and B such that
     * a photo can be taken. If this is the case, it returns the sum of the
     * (absolute) difference between the players placed on the same spot (one
     * behind the other). Your method must run in O(n log(n)) with n the size
     * of the teams.
     * 
     * @param teamA height of the players in team A
     * @param teamB height of the players in team B
     * @return the sum of the difference between players on the same spot. If 
     *         no arrangement can be found, returns -1
     */

    //Mon code est pas 0(n log(n)) car insertion sort c'est o(n²) !!! arrays.sort c'est ok vu que j ai acces au librairies
    //et ca utilise dual-pivot quicksort... voir soluce prof mdrr
    public static int canTakePictures(int [] teamA, int [] teamB) {
        if(teamA.length != teamB.length)return -1;
        sort(teamA);
        System.out.println("Team a : ");
        showTeam(teamA);
        sort(teamB);
        System.out.println("Team b : ");
        showTeam(teamB);

        return alignTeamsInRow(teamA,teamB);
    }

    public static void sort(int [] team){
        for(int i=0; i<team.length;i++){
            for(int j=i;j>0 && less(team[j],team[j-1]);j--){
                exch(team,j,j-1);
            }
        }
    }

    public static boolean less(int v, int w){
        return v<w;
    }

    public static void exch(int [] team, int i, int j){
        int t=team[i];
        team[i]=team[j];
        team[j]=t;
    }

    public static void showTeam(int [] team){
        for (int i=0; i<team.length;i++){
            System.out.print(team[i]+" ");
        }
        System.out.println();
    }

    private static int alignTeamsInRow(int[] teamA, int[] teamB){
        int tallestPlayerTeamA = teamA[teamA.length-1];
        int tallestPlayerTeamB = teamB[teamB.length-1];

        if(tallestPlayerTeamA<tallestPlayerTeamB){
            return isRowPossible(teamB,teamA);
        }
        else{
            return isRowPossible(teamA,teamB);
        }
    }

    private static int isRowPossible(int[] tallTeam, int[] smallTeam){
        int totalSizeGapTeams=0;
        for (int i=tallTeam.length-1; i>=0;i--){
            if(tallTeam[i]<=smallTeam[i]) return -1;
            totalSizeGapTeams += tallTeam[i]-smallTeam[i];
        }
        return totalSizeGapTeams;
    }

    public static void main(String[] args) {
        int [] a = {170,182,168,190,165}; //--> 165,168,170,182,190
        int [] b = {180,172,165,142,165}; //--> 142,165,165,172,180

        System.out.println("Result = "+canTakePictures(a,b));
    }
}
