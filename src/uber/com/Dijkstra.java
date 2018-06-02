package uber.com;


import java.util.HashMap;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;

public class Dijkstra {

    private static final int START = 0;
    private static final int END = 4;

    public static void buildGraph(Map<String, Edge> EdgeGraph)
    {
        final Graph.Edge[] GRAPH = new Graph.Edge[EdgeGraph.size()];
        int i =0;

        for (Edge edge : EdgeGraph.values())
        {
             // inserting Edge into graph of Edges(Type) ie: new Graph.Edge("2", "6", 0.04532),
            GRAPH[i] = new Graph.Edge(edge.startNodeId,edge.endNodeID , edge.l2Distance);
            i++;
        }

        Graph g = new Graph(GRAPH); //replace GRAPH with Edges; arr of Edges
        g.dijkstra(START);
        g.printPath(END);
        //g.printAllPaths();
    }
}

class Graph {

    private final Map<Integer, Vertex> graph; // map of Node ids to Node objects, derived from Edge Map

    //Edge struct
    public static class Edge {
        public final int v1, v2;
        public final float dist;
        public Edge(int v1, int v2, float dist) {
            this.v1 = v1;
            this.v2 = v2;
            this.dist = dist;
        }
    }

    //Vertex Struct
    public  class Vertex implements Comparable<Vertex>{
        public final int name;
        public float dist = Integer.MAX_VALUE; // Assumed to hold infinity
        public Vertex previous = null;
        public final Map<Vertex, Float> neighbours = new HashMap<>();

        public Vertex(int name)
        {
            this.name = name;
        }

        private void printPath()
        {
            if (this == this.previous)
            {
                System.out.printf("%s", this.name);
            }
            else if (this.previous == null)
            {
                System.out.printf("%s(unreached)", this.name);
            }
            else
            {
                this.previous.printPath();
                System.out.printf(" -> %s(%f)", this.name, this.dist);
            }
        }

        public  int compareTo(Vertex other)
        {
            if (dist == other.dist)
                return Integer.compare(this.name, other.name);


            return Float.compare(dist, other.dist);
        }

        @Override public String toString()
        {
            return "(" + name + ", " + dist + ")";
        }
    }

    /** Builds a graph from a set of edges */
    public Graph(Edge[] edges) {
        graph = new HashMap<>(edges.length);

        //one pass to find all vertices
        for (Edge e : edges) {

            if(e == null)
                System.out.println("NUll found");

            if (!graph.containsKey(e.v1))
                graph.put(e.v1, new Vertex(e.v1));

            if (!graph.containsKey(e.v2))
                graph.put(e.v2, new Vertex(e.v2));
        }

        //putting into neighbouring vertices
        for (Edge e : edges) {
            graph.get(e.v1).neighbours.put(graph.get(e.v2), e.dist);
            //graph.get(e.v2).neighbours.put(graph.get(e.v1), e.dist); // also do this for an undirected graph
        }
    }

    /** Runs dijkstra depending on given start vertex */
    public void dijkstra(int startId) {
        if (!graph.containsKey(startId)) {
            System.err.printf("Graph doesn't contain start vertex \"%s\"\n", startId);
            return;
        }
        final Vertex source = graph.get(startId);
        NavigableSet<Vertex> q = new TreeSet<>();

        // set-up vertices
        for (Vertex v : graph.values()) {
            v.previous = v == source ? source : null;
            v.dist = v == source ? 0 : Integer.MAX_VALUE;
            q.add(v);
        }
        dijkstra(q);
    }

    /** Implementation of dijkstra's algorithm using a binary heap. */
    private void dijkstra(final NavigableSet<Vertex> q) {
        Vertex u, v;
        while (!q.isEmpty()) {

            u = q.pollFirst(); // Node with shortest distance
            if (u.dist == Integer.MAX_VALUE) break; //ignoring unreachable nodes

            //distances to each neighbouring Node
            for (Map.Entry<Vertex, Float> a : u.neighbours.entrySet()) {
                v = a.getKey(); //neighbour in this iteration

                final float alternateDist = u.dist + a.getValue();
                if (alternateDist < v.dist) { // shortest path to neighbour found
                    q.remove(v);
                    v.dist = alternateDist;
                    v.previous = u;
                    q.add(v);
                }
            }
        }
    }

    /**  Path from the startNode to the  endnodeId */
    public void printPath(int endId) {
        if (!graph.containsKey(endId)) {
            System.err.printf("Graph doesn't contain end Node \"%s\"\n", endId);
            return;
        }
        graph.get(endId).printPath();
        System.out.println();
    }
    /** Path from the startNode to every other Node */
    public void printAllPaths() {
        for (Vertex v : graph.values()) {
            v.printPath();
            System.out.println();
        }
    }
}