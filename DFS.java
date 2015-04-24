import java.util.*;

class DFS {
  /**Does a depth first search from s and calculates the pre-, post- and connected component-values
   * O(m+n)
   * Properties:
   *   1: Vertex v is reachable from start iff post[v] != -1 && post[v] < post[start]
   *   2: Cycle in directed graph iff pre[v] < pre[u] < post[v] for some edge (u, v)
   *   3: Vertices ordered by decreasing post-value gives a topological sort
   * @param graph Graph represented by adjacency list
   * @param pre [n] matrix filled with each vertex' pre-value (initialized to -1)
   * @param post [n] matrix filled with each vertex' post-value (initialized to -1)
   * @param cc [n] matrix filled with each vertex' connected component-number (initialized to -1)
   * @param s The vertex to start the search from (-1 will search for all connected components)*/
  static void dfs(List<List<Integer>> graph, int[] pre, int[] post, int[] cc, int s) {
    int n = graph.size();
    boolean allCC = s == -1;
    if(s == -1)
      s = 0;
    
    // Initialize the next values that can be assigned
    int num = 0;
    int ccnum = 0;
    for(int i = 0; i < n; ++i) {
      num = Math.max(num, post[i]+1);
      ccnum = Math.max(ccnum, cc[i]+1);
    }
    
    Deque<Integer> queue = new ArrayDeque();
    while(s < n) {
      queue.push(s);
      while(!queue.isEmpty()) {
        int a = queue.peek();
        cc[a] = ccnum;
        if(pre[a] == -1) {
          pre[a] = num++;
          for(int b : graph.get(a))
            if(pre[b] == -1)
              queue.push(b);
        } else {
          if(post[a] == -1)
            post[a] = num++;
          queue.pop();
        }
      }
      
      if(allCC) {
        // Find next vertex to start search from
        while(s < n && post[s] != -1)
          ++s;
        ++ccnum;
      }
    }
  }
}
