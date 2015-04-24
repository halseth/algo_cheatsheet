import java.util.*;

class SCC {
  /**Searches a graph for its strongly connected components
   * Requires: DFS
   * O(m+n)
   * @param graph Graph represented by adjacency list
   * @param components Is filled with each SCC's vertices
   * @return An array with each vertex' SCC-number*/
  static int[] scc(List<List<Integer>> graph, List<List<Integer>> components) {
    int n = graph.size();
    
    // Reverse graph
    List<List<Integer>> reverse = new ArrayList();
    for(int i = 0; i < n; ++i)
      reverse.add(new ArrayList());
    for(int a = 0; a < n; ++a)
      for(int b : graph.get(a))
        reverse.get(b).add(a);
    
    // Initialize arrays for DFS
    int[] pre = new int[n];
    int[] post = new int[n];
    int[] scc = new int[n];
    Arrays.fill(pre, -1);
    Arrays.fill(post, -1);
    Arrays.fill(scc, -1);
    
    DFS.dfs(reverse, pre, post, scc, -1);
    
    // Sort vertices by post-value
    int[] order = new int[2*n];
    Arrays.fill(order, -1);
    for(int i = 0; i < n; ++i)
      order[post[i]] = i;
    
    // Initialize arrays for DFS
    Arrays.fill(pre, -1);
    Arrays.fill(post, -1);
    Arrays.fill(scc, -1);
    
    // Search for SCCs in the graph
    for(int i = 2*n-1; i >= 0; --i)
      if(order[i] != -1) {
        if(scc[order[i]] == -1) {
          components.add(new ArrayList());
          DFS.dfs(graph, pre, post, scc, order[i]);
        }
        components.get(scc[order[i]]).add(order[i]);
      }
    
    return scc;
  }
}
