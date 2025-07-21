
package path;    import java.util.*;
public class CountShortesPath {
    static class Edge {
        int target, weight;
        Edge(int target, int weight) {
            this.target = target;
            this.weight = weight;
        }
    }

    static class Node implements Comparable<Node> {
        int vertex, dist;
        Node(int vertex, int dist) {
            this.vertex = vertex;
            this.dist = dist;
        }
        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.dist, other.dist);
        }
    }

    public static void dijkstra(List<List<Edge>> graph, int source, int destination) {
        int n = graph.size();
        int[] dist = new int[n];
        int[] count = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;
        count[source] = 1;

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(source, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int u = current.vertex;
            if (current.dist > dist[u]) continue; // skip outdated entries

            for (Edge edge : graph.get(u)) {
                int v = edge.target;
                int weight = edge.weight;
                if (dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    count[v] = count[u]; // new shortest path found
                    pq.add(new Node(v, dist[v]));
                } else if (dist[u] + weight == dist[v]) {
                    count[v] += count[u]; // additional path found
                }
            }
        }

        System.out.println("Shortest distance from " + source + " to " + destination + ": " + dist[destination]);
        System.out.println("Number of shortest paths: " + count[destination]);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of vertices: ");
        int v = sc.nextInt();
        System.out.print("Enter number of edges: ");
        int e = sc.nextInt();

        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < v; i++) {
            graph.add(new ArrayList<>());
        }

        System.out.println("Enter edges (source destination weight):");
        for (int i = 0; i < e; i++) {
            int u = sc.nextInt();
            int dest = sc.nextInt();
            int weight = sc.nextInt();
            graph.get(u).add(new Edge(dest, weight));
            // If undirected graph, also add: graph.get(dest).add(new Edge(u, weight));
        }

        System.out.print("Enter source vertex: ");
        int source = sc.nextInt();
        System.out.print("Enter destination vertex: ");
        int destination = sc.nextInt();

        dijkstra(graph, source, destination);
    }
}
}
