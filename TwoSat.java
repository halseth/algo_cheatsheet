import java.util.*;

class TwoSat {
  /**Checks if a 2-CNF (AND of ORs) formula is satisfiable
   * Requires: SCC
   * O(m+n)
   * To get a valid assignment:
   *   1: Pick an unassigned vertex V
   *   2: Assign TRUE to all vertices reachable from V
   *   3: Assign FALSE to their negations
   *   4: Repeat until all vertices are assigned
   * @param formula The 2-CNF formula
   * @param n The number of variables
   * @return Whether the formula is satisfiable*/
  static boolean twoSat(Collection<Clause> formula, int n) {
    List<List<Integer>> graph = new ArrayList();
    for(int i = 0; i < 2*n; ++i)
      graph.add(new ArrayList());
    
    // Construct graph from forumla
    for(Clause c : formula) {
      int a = c.y + (c.yn ? n : 0);
      int b = c.x + (c.xn ? n : 0);
      graph.get((a+n)%(2*n)).add(b);
      graph.get((b+n)%(2*n)).add(a);
    }
    
    // Check that a variable and it's negation are not in the same SCC
    int[] scc = SCC.scc(graph, new ArrayList());
    for(int i = 0; i < n; ++i)
      if(scc[i] == scc[i+n])
        return false;
    
    return true;
  }
  
  static class Clause {
    int x, y;
    boolean xn, yn;

    /**@param x First variable
     * @param xn Whether first variable is negated
     * @param y Second variable
     * @param yn Whether second variable is negated*/
    Clause(int x, boolean xn, int y, boolean yn) {
      this.x = x;
      this.xn = xn;
      this.y = y;
      this.yn = yn;
    }
  }
}
