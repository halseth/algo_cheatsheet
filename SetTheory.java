import java.util.*;

class SetTheory {
  /**Generates all combinations of k numbers from 0..n-1
   * @param n Number of elements to choose from
   * @param k Number of elements in each combination*/
  static ArrayList<ArrayList<Integer>> combinations(int n, int k) {
    ArrayList<ArrayList<Integer>> combs = new ArrayList();
    ArrayList<Integer> comb = new ArrayList();
    for(int i = 0; i < k; ++i)
      comb.add(i);
    while(true) {
      combs.add(new ArrayList(comb));
      int i = k-1;
      while(i >= 0 && comb.get(i) == n-k+i)
        i--;
      if(i < 0)
        break;
      comb.set(i, comb.get(i)+1);
      for(int j = i+1; j < k; j++)
        comb.set(j, comb.get(i)+j-i);
    }
    return combs;
  }
  
  /**Generates all permutations of numbers 0..n-1
   * @param visited [n] array indicating which numbers have been used (initialized to false)
   * @param perm For building a single permutation
   * @param perms Is filled with all the permutations*/
  static void permutations(boolean[] visited, Collection<Integer> perm, Collection<Collection<Integer>> perms) {
    if(perm.size() == visited.length)
      perms.add(perm);
    else {
      // Perform pruning if not all permutations should be generated
      
      for(int i = 0; i < visited.length; ++i)
        if(!visited[i]) {
          visited[i] = true;
          Collection<Integer> perm2 = new ArrayList(perm);
          perm2.add(i);
          permutations(visited, perm2, perms);
          visited[i] = false;
        }
    }
  }
}
