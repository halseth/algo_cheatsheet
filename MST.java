import java.util.*;

class MST {
  /**Searches a graph for its minimum spanning tree. Prim's algorithm.
   * Allows for negative weights and disconnected graph
   * Requires: Pair
   * O((m+n)*log(n))
   * @param graph Graph represented by adjacency list
   * @return An [n] array with each vertex' parent*/
  static int[] mst(List<List<Pair>> graph) {
    int n = graph.size();
    
    // Initialize visited, parent and cost arrays
    boolean[] visited = new boolean[n];
    int[] parent = new int[n];
    int[] cost = new int[n];
    Arrays.fill(parent, -1);
    Arrays.fill(cost, Integer.MAX_VALUE);
    
    Queue<Pair> queue = new PriorityQueue(10, new Pair.CompareSecond());
    int count = 0;
    int s = 0;
    while(s != -1) {
      queue.add(new Pair(s, 0));
      while(!queue.isEmpty() && count < n) {
        Pair a = queue.poll();
        if(!visited[a.fst]) {
          ++count;
          visited[a.fst] = true;
          for(Pair b : graph.get(a.fst))
            if(!visited[b.fst] && cost[b.fst] > b.snd) {
              cost[b.fst] = b.snd;
              parent[b.fst] = a.fst;
              queue.add(new Pair(b.fst, b.snd));
            }
        }
      }
      s = -1;
      if(count < n)
        // Find next vertex to start search from
        for(int i = 0; i < n && s == -1; ++i)
          if(!visited[i])
            s = i;
    }
    
    return parent;
  }
}
