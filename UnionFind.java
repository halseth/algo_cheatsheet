import java.util.*;

class UnionFind {
  int[] elms;
  
  /**A data structure for working with grouping of elements
   * o(n)
   * @param n The number of elements*/
  UnionFind(int n) {
    elms = new int[n];
    Arrays.fill(elms, -1);
  }
  
  /**Which group does v belong to?
   * O(log* n)
   * @param v The element whose group to find
   * @return The group v belongs to*/
  int find(int v) {
    if(elms[v] < 0)
      return v;
    else
      return (elms[v] = find(elms[v]));
  }
  
  /**Join the group v belongs to and the group u belongs to
   * O(log* n)
   * @param v An element in the first group
   * @param w An element in the second group*/
  void join(int v, int w) {
    v = find(v);
    w = find(w);
    if(v == w)
      return;
    if(elms[v] > elms[w]) {
      int temp = v;
      v = w;
      w = temp;
    }
    elms[v] += elms[w];
    elms[w] = v;
  }
  
  /**How many elements are there in the group v belongs to?
   * O(log* n)
   * @param v An element in the group
   * @return The size of the group*/
  int size(int v) {
    return -elms[find(v)];
  }
}
