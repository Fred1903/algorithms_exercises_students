package graphs;


/**
 * In this exercise, we revisit the GlobalWarming
 * class from the sorting package.
 * You are still given a matrix of altitude in
 * parameter of the constructor, with a water level.
 * All the entries whose altitude is under, or equal to,
 * the water level are submerged while the other constitute small islands.
 *
 * For example let us assume that the water
 * level is 3 and the altitude matrix is the following
 *
 *      | 1 | 3 | 3 | 1 | 3 |
 *      | 4 | 2 | 2 | 4 | 5 |
 *      | 4 | 4 | 1 | 4 | 2 |
 *      | 1 | 4 | 2 | 3 | 6 |
 *      | 1 | 1 | 1 | 6 | 3 |
 * 
 * If we replace the submerged entries
 * by _, it gives the following matrix
 *
 *      | _ | _ | _ | _ | _ |
 *      | 4 | _ | _ | 4 | 5 |
 *      | 4 | 4 | _ | 4 | _ |
 *      | _ | 4 | _ | _ | 6 |
 *      | _ | _ | _ | 6 | _ |
 *
 * The goal is to implement two methods that
 * can answer the following questions:
 *      1) Are two entries on the same island?
 *      2) How many islands are there
 *
 * Two entries above the water level are
 * connected if they are next to each other on
 * the same row or the same column. They are
 * **not** connected **in diagonal**.
 * Beware that the methods must run in O(1)
 * time complexity, at the cost of a pre-processing in the constructor.
 * To help you, you'll find a `Point` class
 * in the utils package which identified an entry of the grid.
 * Carefully read the expected time complexity of the different methods.
 */
public class GlobalWarming {
    private int rows;
    private int cols;
    private int waterLevel;

    private int[] parent;
    private int[] rank;
    private boolean[] isLand;

    private int nbIslands;


    /**
     * Constructor. The run time of this method is expected to be in 
     * O(n x log(n)) with n the number of entry in the altitude matrix.
     *
     * @param altitude the matrix of altitude
     * @param waterLevel the water level under which the entries are submerged
     */
    public GlobalWarming(int [][] altitude, int waterLevel) {
        this.rows = altitude.length;
        this.cols = altitude[0].length;
        this.waterLevel = waterLevel;
        int n = rows * cols;
        this.parent = new int[n];
        this.rank = new int[n];
        this.isLand = new boolean[n];
        this.nbIslands = 0;

        // 1ère passe : marquer les terres et initialiser Union-Find
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                int id = index(x, y);
                if (altitude[x][y] > waterLevel) {
                    isLand[id] = true;
                    parent[id] = id;  // chaque terre est au départ son propre chef
                    rank[id] = 0;     // profondeur initiale
                    nbIslands++;      // chaque terre est une île au départ
                } else {
                    isLand[id] = false;
                    parent[id] = id;  // valeur quelconque, on ne l'utilisera pas pour l'eau
                    rank[id] = 0;
                }
            }
        }

        // 2ème passe : unir les terres voisines (droite et bas)
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                int id = index(x, y);
                if (!isLand[id]) {
                    continue; // on ignore l'eau
                }

                // voisin de droite
                if (y + 1 < cols) {
                    int rightId = index(x, y + 1);
                    if (isLand[rightId]) {
                        union(id, rightId);
                    }
                }

                // voisin du bas
                if (x + 1 < rows) {
                    int downId = index(x + 1, y);
                    if (isLand[downId]) {
                        union(id, downId);
                    }
                }
            }
        }
    }

    // Fusionne les ensembles contenant a et b
    private void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        if (rootA == rootB) {
            return; // déjà dans le même ensemble
        }

        // Union par rang : on attache l'arbre le plus petit sous le plus grand
        if (rank[rootA] < rank[rootB]) {
            parent[rootA] = rootB;
        } else if (rank[rootA] > rank[rootB]) {
            parent[rootB] = rootA;
        } else {
            parent[rootB] = rootA;
            rank[rootA]++; // la hauteur augmente d'un
        }

        // On vient de fusionner deux îles distinctes, donc une île en moins
        nbIslands--;
    }


    // Trouve le représentant (chef) de l'ensemble contenant id
    private int find(int id) {
        if (parent[id] != id) {
            parent[id] = find(parent[id]); // compression de chemin
        }
        return parent[id];
    }

    // Convertit une coordonnée (x, y) en index dans les tableaux 1D
    private int index(int x, int y) {
        return x * cols + y;
    }

    /**
     * Returns the number of island
     *
     * Expected time complexity O(1)
     */
    public int nbIslands() {
         return nbIslands;
    }

    /**
     * Return true if p1 is on the same island as p2, false otherwise
     *
     * Expected time complexity: O(1)
     *
     * @param p1 the first point to compare
     * @param p2 the second point to compare
     */
    public boolean onSameIsland(Point p1, Point p2) {
        int x1 = p1.getX();
        int y1 = p1.getY();
        int x2 = p2.getX();
        int y2 = p2.getY();

        int id1 = index(x1, y1);
        int id2 = index(x2, y2);

        // Si l'un des deux est dans l'eau, ils ne peuvent pas être sur la même île
        if (!isLand[id1] || !isLand[id2]) {
            return false;
        }

        // Sinon, on compare les représentants Union-Find
        return find(id1) == find(id2);
    }


    /**
     * This class represent a point in a 2-dimension discrete plane. This is used, for instance, to
     * identified cells of a grid
     */
    static class Point {

        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Point) {
                Point p = (Point) o;
                return p.x == this.x && p.y == this.y;
            }
            return false;
        }
    }
}
