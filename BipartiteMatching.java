import java.util.*;

class BipartiteMatching_Unweighted {
  /**Find maximum matching in unweighted, undirected, bipartite graph
   * Requires: MaxFlow
   * O(a*b+maxFlow)
   * @param a Size of vertex set 1
   * @param b Size of vertex set 2
   * @param adj For each vertex in set 1, a list of adjacent vertices in vertex set 2 (named 0..b-1)
   * @return Size of the maximum matching*/
  static int bipartiteMatching(int a, int b, List<List<Integer>> adj) {
    int[][] capacity = new int[a+b+2][a+b+2];
    for(int i = 0; i < a; ++i) {
      for(int j : adj.get(i)) {
        capacity[i][i+j] = 1;
        capacity[i+j][i] = 1;
      }
      capacity[i][a+b] = 1;
      capacity[a+b][i] = 1;
    }
    for(int j = 0; j < b; ++j) {
      capacity[a+j][a+b+1] = 1;
      capacity[a+b+1][a+j] = 1;
    }
    return MaxFlow.augmentingPath(capacity, a+b, a+b+1);
  }
}
