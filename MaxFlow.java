import java.util.*;

class MaxFlow {
  /**Finds the maximum flow through a graph. Edmonds-Karp's algorithm
   * O(n*m^2)
   * @param capacity Capacity matrix (must be n by n)
   * @param s Source vertex
   * @param t Sink vertex
   * @return The maximum flow from s to t*/
  static int augmentingPath(int[][] capacity, int s, int t) {
    int n = capacity.length;
    List<List<Integer>> graph = graph(capacity);
    
    // Residual capacity from u to v is capacity[u][v] - flow[u][v]
    int[][] flow = new int[n][n];
    while(true) {
      // Initialize parent table
      int[] parent = new int[n];
      Arrays.fill(parent, -1);
      parent[s] = s;
      
      // Initialize capacity of found path to node
      int[] found = new int[n];
      found[s] = Integer.MAX_VALUE;
      Deque<Integer> queue = new ArrayDeque();
      queue.add(s);
      LOOP:
      while(!queue.isEmpty()) {
        int u = queue.poll();
        for(int v : graph.get(u)) {
          // There is available capacity, and v is not seen before in search
          if(capacity[u][v]-flow[u][v] > 0 && parent[v] == -1) {
            parent[v] = u;
            found[v] = Math.min(found[u], capacity[u][v]-flow[u][v]);
            if(v != t)
              queue.add(v);
            else {
              // Backtrack search, updating flow
              while(parent[v] != v) {
                u = parent[v];
                flow[u][v] += found[t];
                flow[v][u] -= found[t];
                v = u;
              }
              break LOOP;
            }
          }
        }
      }
      if(parent[t] == -1) { // No path to t found
        int sum = 0;
        for(int x : flow[s])
          sum += x;
        return sum;
      }
    }
  }
  
  /**Finds the maximum flow through a graph. Push-Relabel approach
   * O(n^3)
   * @param capacity Capacity matrix (must be n by n)
   * @param s Source vertex
   * @param t Sink vertex
   * @return The maximum flow from s to t*/
  static int pushRelabel(int[][] capacity, int s, int t) {
    int n = capacity.length;
    List<List<Integer>> graph = graph(capacity);
    
    int[] height = new int[n];
    height[s] = n;
    int[] excess = new int[n];
    int[][] flow = new int[n][n];
    for(int v : graph.get(s)) {
      flow[s][v] = capacity[s][v];
      flow[v][s] = -capacity[s][v];
      excess[v] = capacity[s][v];
      excess[s] = -capacity[s][v];
    }
    
    Deque<Integer> queue = new ArrayDeque();
    boolean[] active = new boolean[n];
    for(int v : graph.get(s))
      if(v != t) {
        queue.add(v);
        active[v] = true;
      }
    while(!queue.isEmpty()) {
      int u = queue.peek();
      int h = Integer.MAX_VALUE;
      if(excess[u] > 0)
        for(int v : graph.get(u)) {
          if(capacity[u][v]-flow[u][v] > 0) {
            if(height[u] > height[v]) {
              int increase = Math.min(excess[u], capacity[u][v]-flow[u][v]);
              flow[u][v] += increase;
              flow[v][u] -= increase;
              excess[u] -= increase;
              excess[v] += increase;
              if(!active[v] && v != s && v != t) {
                active[v] = true;
                queue.add(v);
              }
            } else
              h = Math.min(h, height[v]);
          }
        }
      if(excess[u] == 0) {
        active[u] = false;
        queue.poll();
      } else
        height[u] = h+1;
    }
    return excess[t];
  }
  
  /**Create graph represented as adjacency list based on capacity matrix
   * @param capacity The capacity matrix
   * @return The graph represented as adjacency list*/
  private static List<List<Integer>> graph(int[][] capacity) {
    int n = capacity.length;
    List<List<Integer>> graph = new ArrayList();
    for(int i = 0; i < n; ++i)
      graph.add(new ArrayList());
    for(int i = 0; i < n; ++i)
      for(int j = i+1; j < n; ++j)
        if(capacity[i][j] > 0 || capacity[j][i] > 0) {
          graph.get(i).add(j);
          graph.get(j).add(i);
        }
    return graph;
  }
}
