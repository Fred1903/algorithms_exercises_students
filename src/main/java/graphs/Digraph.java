package graphs;


import java.util.ArrayList;
import java.util.List;

/**
 * Implement the Digraph.java interface in
 * the Digraph.java class using an adjacency list
 * data structure to represent directed graphs.
 */
public class Digraph {
    private int V;
    private int E;
    private List<Integer>[] adj;

    public Digraph(int V) {
        // TODO
        this.V=V;
        this.E=0;
        adj=(List<Integer>[]) new ArrayList[V];
        for (int i = 0; i < V; i++) {
            adj[i]=new ArrayList<>();
        }
    }

    /**
     * The number of vertices
     */
    public int V() {
        // TODO
         return V;
    }

    /**
     * The number of edges
     */
    public int E() {
        // TODO
         return E;
    }

    /**
     * Add the edge v->w
     */
    public void addEdge(int v, int w) {
        // TODO
        adj[v].add(w);
        E++;
    }

    /**
     * The nodes adjacent to node v
     * that is the nodes w such that there is an edge v->w
     */
    public Iterable<Integer> adj(int v) {
        // TODO
         return adj[v];
    }

    /**
     * A copy of the digraph with all edges reversed
     */
    public Digraph reverse() {
        // TODO

        Digraph reverse = new Digraph(V);

        for (int i = 0; i < V; i++) {
            for(int w: adj[i]){
                reverse.addEdge(w,i);
            }
        }
        return reverse;
    }
}
