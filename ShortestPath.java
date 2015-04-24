import java.util.*;

class SSSP_Unweighted {
  /**Non-weighted Single Source Shortest Paths: BFS
   * Finds shortest path from s to every other vertex
   * O(m)
   * @param graph Graph represented by adjacency list
   * @param parent [n] array filled with each vertex' parent
   * @param s The vertex to start the search from
   * @return [n] array with the distance from s to each vertex*/
  static int[] sssp(List<List<Integer>> graph, int[] parent, int s) {
    // Initialize distance array and parent array
    int[] dist = new int[graph.size()];
    Arrays.fill(dist, Integer.MAX_VALUE);
    Arrays.fill(parent, -1);
    dist[s] = 0;
    
    Deque<Integer> queue = new ArrayDeque();
    queue.add(s);
    
    while(!queue.isEmpty()) {
      int a = queue.poll();
      for(int b : graph.get(a))
        if(dist[a]+1 < dist[b]) {
          dist[b] = dist[a]+1;
          parent[b] = a;
          queue.add(b);
        }
    }
    return dist;
  }
}

class SSSP_Nonnegative {
  /**Non-negatively Weighted Single Source Shortest Path: Dijkstra
   * Finds shortest path from s to all vertices in a graph where the edges are weighted non-negatively
   * Requires: Pair
   * O((m+n)*log(n))
   * @param graph Graph represented by adjacency list
   * @param parent [n] array filled with each vertex' parent
   * @param s The vertex to start the search from
   * @return [n] array with the distance from s to each vertex*/
  static int[] sssp(List<List<Pair>> graph, int[] parent, int s) {
    // Initialize distance array and parent array
    int[] dist = new int[graph.size()];
    Arrays.fill(dist, Integer.MAX_VALUE);
    Arrays.fill(parent, -1);
    dist[s] = 0;
    
    Queue<Pair> queue = new PriorityQueue(10, new Pair.CompareSecond());
    queue.add(new Pair(s, dist[s]));
    
    while(!queue.isEmpty()) {
      Pair a = queue.poll();
      for(Pair b : graph.get(a.fst))
        if(dist[a.fst]+b.snd < dist[b.fst]) {
          dist[b.fst] = dist[a.fst]+b.snd;
          parent[b.fst] = a.fst;
          queue.add(new Pair(b.fst, dist[b.fst]));
        }
    }
    return dist;
  }
}

class SSSP_Arbitrary {
  /**Arbitrarily Weighted Single Source Shortest Paths: Bellman-Ford
   * Finds shortest path from s to all vertices in a graph where the edges are weighted arbitrarily
   * Only explores paths reachable from s
   * Requires: Pair
   * O(n*m)
   * @param graph Graph represented by adjacency list
   * @param parent [n] array filled with each vertex' parent
   * @param s The vertex to start the search from
   * @return [n] array with the distance from s to each vertex*/
  static int[] sssp(List<List<Pair>> graph, int[] parent, int s) {
    int n = graph.size();
    
    // Initialize distance array and parent array
    int[] dist = new int[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    Arrays.fill(parent, -1);
    dist[s] = 0;
    
    for(int i = 0; i < n-1; ++i)
      for(int u = 0; u < n; ++u)
        if(dist[u] != Integer.MAX_VALUE)
          for(Pair a : graph.get(u))
            if(dist[u]+a.snd < dist[a.fst]) {
              dist[a.fst] = dist[u]+a.snd;
              parent[a.fst] = u;
            }
    
    return dist;
  }

  /**Checks whether a graph contains a negative cycle, as calculated by sssp()
   * Only checks for negative cycles in parts of graph reachable from s, as calculated by sssp()
   * O(m)
   * @param graph Graph represented by adjacency list
   * @param dist The [n] dist matrix filled in by sssp()
   * @return Whether the graph contains a negative cycle or not*/
  static boolean hasNegativeCycle(List<List<Pair>> graph, int[] dist) {
    for(int u = 0; u < graph.size(); ++u)
      if(dist[u] != Integer.MAX_VALUE)
        for(Pair a : graph.get(u))
          if(dist[u]+a.snd < dist[a.fst])
            return true;
    
    return false;
  }
}

class APSP {
  /**All Pairs Shortest Path: Floyd-Warshall
   * Finds shortest paths between all pairs of vertices in the graph
   * Does not detect negative cycles
   * Requires Pair
   * O(n^3)
   * Properties:
   *   1: dist[u][v] gives the lenght of the shortest path from vertex u to vertex v
   *   2: parent[u][v] gives the last vertex on the shortest path from vertex u to vertex v
   *   3: If vertex v is not reachable from vertex u, dist[u][v] == Integer.MAX_VALUE/2 - 1 and parent[u][v] == -1
   * @param graph Graph represented by adjacency list
   * @param parent [n][n] matrix filled with the last vertex on the shortest path between every pair of vertices
   * @return [n][n] matrix with the distance between every pair of vertices*/
  static int[][] apsp(List<List<Pair>> graph, int[][] parent) {
    int n = graph.size();
    
    // Initialize distance array and parent array
    int[][] dist = new int[n][n];
    for(int i = 0; i < n; ++i) {
      Arrays.fill(dist[i], Integer.MAX_VALUE/2 - 1);
      Arrays.fill(parent[i], -1);
    }
    for(int i = 0; i < n; ++i)
      for(Pair a : graph.get(i)) {
        dist[i][a.fst] = a.snd;
        parent[i][a.fst] = i;
      }
    
    for(int k = 1; k < n; ++k)
      for(int i = 0; i < n; ++i)
        for(int j = 0; j < n; ++j)
          if(dist[i][k]+dist[k][j] < dist[i][j]) {
            dist[i][j] = dist[i][k]+dist[k][j];
            parent[i][j] = parent[k][j];
          }
    
    return dist;
  }
  
  /**Returns the shortest path between s and t, as calculated by apsp()
   * O(n)
   * @param parent The [n][n] parent matrix filled in by apsp()
   * @param s The start vertex
   * @param t The end vertex
   * @return The shortest path between s and t*/
  static ArrayList<Integer> getPath(int[][] parent, int s, int t) {
    ArrayList<Integer> path = new ArrayList();
    while(s != t && t != -1) {
      path.add(t);
      t = parent[s][t];
    }
    if(t == -1)
      return new ArrayList();
    path.add(t);
    Collections.reverse(path);
    return path;
  }
}
