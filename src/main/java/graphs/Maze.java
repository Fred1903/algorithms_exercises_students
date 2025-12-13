package graphs;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * We are interested in solving a maze represented
 * by a matrix of integers 0-1 of size nxm.
 * This matrix is a two-dimensional array.
 * An entry equal to '1' means that there
 * is a wall and therefore this position is not accessible,
 * while '0' means that the position is free.
 * We ask you to write a Java code to discover
 * the shortest path between two coordinates
 * on this matrix from (x1, y1) to (x2, y2).
 * The moves can only be vertical (up/down) or horizontal (left/right)
 * (not diagonal), one step at a time.
 * The result of the path is an Iterable of
 * coordinates from the origin to the destination.
 * These coordinates are represented by integers
 * between 0 and n * m-1, where an integer 'a'
 * represents the position x =a/m and y=a%m.
 * If the start or end position is a wall
 * or if there is no path, an empty Iterable must be returned.
 * The same applies if there is no path
 * between the origin and the destination.
 */
public class Maze {
    private final static int WALL=1;
    private final static int FREE=0;

    public static Iterable<Integer> shortestPath(int[][] maze, int x1, int y1, int x2, int y2) {
        // TODO
        if(maze[x1][y1]==WALL || maze[x2][y2]==WALL)return new ArrayList<>();

        int n= maze.length;
        int m= maze[0].length;

        int start=ind(x1,y1,m);
        int end = ind(x2,y2,m);

        boolean[] marked=new boolean[n*m];
        int[] edgeTo=new int[n*m];

        Queue<Integer> queue = new LinkedList<>();

        marked[start]=true;
        queue.add(start);

        int [][] directions = {{0,-1},{0,+1},{+1,0},{-1,0}};

        while (!queue.isEmpty()){
            int v = queue.poll();
            int vx = row(v, m);
            int vy = col(v, m);
            for(int[] direction : directions){
                int nx = vx + direction[0];
                int ny = vy + direction[1];

                if (nx < 0 || nx >= n || ny < 0 || ny >= m)continue;
                if (maze[nx][ny] == WALL)continue;
                int w = ind(nx, ny, m);
                if (marked[w])continue;
                marked[w] = true;
                edgeTo[w] = v;
                queue.add(w);
                if (w == end) {
                    queue.clear();
                    break;
                }
            }
        }

        if(!marked[end])return new ArrayList<>();

        // Reconstruire le chemin de end -> start
        LinkedList<Integer> path = new LinkedList<>();
        for (int x = end; x != start; x = edgeTo[x]) {
            path.addFirst(x);
        }
        path.addFirst(start);

        return path;
    }

    public static int ind(int x, int y, int lg) {
        return x * lg + y;
    }

    public static int row(int pos, int mCols) {
        return pos / mCols;
    }

    public static int col(int pos, int mCols) {
        return pos % mCols;
    }

}
