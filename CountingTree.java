import java.util.*;

class CountingTree_UpdateInterval_QueryCell {
  private final int offset;
  private final int[] tree;
  
  /**Data structure for adding values to intervals of cells, and retrieving values from single cells
   * O(n)
   * @param n The number of cells*/
  CountingTree_UpdateInterval_QueryCell(int n) {
    offset = n;
    tree = new int[2*offset];
  }
  
  /**Add value to all cells in interval [l,r]
   * O(log n)
   * @param l The first cell in the interval
   * @param r The last cell in the interval
   * @param v The value to add to all cells in the interval*/
  void update(int l, int r, int v) {
    for(l += offset-1, r += offset+1; l/2 != r/2; l /= 2, r /= 2) {
      if(l%2 == 0) {
          tree[l^1] += v; // sum
//        tree[l^1] = v;  // min/max
      }
      if(r%2 == 1) {
        tree[r^1] += v; // sum
//      tree[r^1] = v;  // min/max
      }
    }
  }
  
  /**Get the value in a cell
   * O(log n)
   * @param i The cell
   * @return The value of the cell*/
  int query(int i) {
    int result = 0;
    for(i += offset; i > 0; i /= 2){
    	result += tree[i];                  // sum
//		result = Math.min(result, tree[i]); // min
//		result = Math.max(result, tree[i]); // max
    }
    return result;
  }
}

class CountingTree_UpdateCell_QueryInterval {
  private final int offset;
  private final int[] tree;
  
  /**Data structure for updating values of single cells, and retrieving the sum/min/max/gcd over intervals of cells
   * O(n)
   * @param n The number of cells*/
  CountingTree_UpdateCell_QueryInterval(int n) {
    offset = n;
    tree = new int[2*offset];
  }
  
  /**Update the value of a cell
   * O(log n)
   * @param i The cell to update
   * @param v The value to put in the cell*/
  void update(int i, int v) {
    i += offset;
    tree[i] += v; // sum
//  tree[i] = v;  // min/max/gcd
    
    for(i /= 2; i > 0; i /= 2) {
      tree[i] = tree[2*i]+tree[2*i+1];                    // sum
//    tree[i] = Math.min(tree[2*i], tree[2*i+1]);         // min
//    tree[i] = Math.max(tree[2*i], tree[2*i+1]);         // max
//    tree[i] = NumberTheory.gcd(tree[2*i], tree[2*i+1]); // gcd
    }
  }
  
  /**Get the sum/min/max/gcd over all cells in interval [l,r]
   * O(log n)
   * @param l The first cell in the interval
   * @param r The last cell in the interval
   * @return The sum/min/max/gcd over the interval*/
  int query(int l, int r) {
    int result = 0;                 // sum/gcd
//  int result = Integer.MAX_VALUE; // min
//  int result = Integer.MIN_VALUE; // max
    
    for(l += offset-1, r += offset+1; l/2 != r/2; l /= 2, r /= 2) {
      if(l%2 == 0) {
        result += tree[l^1];                          // sum
//      result = Math.min(result, tree[l^1]);         // min
//      result = Math.max(result, tree[l^1]);         // max
//      result = NumberTheory.gcd(result, tree[l^1]); // gcd
      }
      if(r%2 == 1) {
        result += tree[r^1];                          // sum
//      result = Math.min(result, tree[r^1]);         // min
//      result = Math.max(result, tree[r^1]);         // max
//      result = NumberTheory.gcd(result, tree[r^1]); // gcd
      }
    }
    
    return result;
  }
}

class CountingTree_UpdateInterval_QueryInterval {
  private final int offset;
  private final long[] tree;
  private final long[] bonus;

  /**Data structure for adding values to intervals of cells, and retrieving the sum/min/max over intervals of cells
   * O(n)
   * @param n The number of cells*/
  CountingTree_UpdateInterval_QueryInterval(int n){
    offset = n+1;
    bonus = new long[2*offset];
    tree = new long[2*offset];
  }

  /**Add value to all cells in interval [l,r]
   * O(log n)
   * @param l The first cell in the interval
   * @param r The last cell in the interval
   * @param v The value to add to all cells in the interval*/
  void update(int l, int r, long v) {
    int chunk = 0;               // sum
    long lcount = 0, rcount = 0; // sum
    for(l += offset-1, r += offset+1; l>>1 != r>>1; l >>= 1, r >>= 1) {
      if(l%2 == 0) {
        bonus[l^1] += v;    // sum
        lcount += v<<chunk; // sum
//      tree[l^1] = v;      // min/max
//      bonus[l^1] = v;     // min/max
      }
      if(r%2 == 1) {
        bonus[r^1] += v;    // sum
        rcount += v<<chunk; // sum
//      tree[r^1] = v;      // min/max
//      bonus[r^1] = v;     // min/max
      }
      ++chunk; // sum
      
      tree[l>>1] += lcount;                                             // sum
      tree[r>>1] += rcount;                                             // sum
//    tree[l>>1] = Math.min(bonus[l>>1], Math.min(tree[l], tree[l^1])); // min
//    tree[r>>1] = Math.min(bonus[r>>1], Math.min(tree[r], tree[r^1])); // min
//    tree[l>>1] = Math.max(bonus[l>>1], Math.max(tree[l], tree[l^1])); // max
//    tree[r>>1] = Math.max(bonus[r>>1], Math.max(tree[r], tree[r^1])); // max
    }
    
    for(; l>>1 > 0; l >>= 1) {
      tree[l>>1] += lcount+rcount;                                      // sum
//    tree[l>>1] = Math.min(bonus[l>>1], Math.min(tree[l], tree[l^1])); // min
//    tree[l>>1] = Math.max(bonus[l>>1], Math.max(tree[l], tree[l^1])); // max
    }
  }

  /**Get the sum/min/max over all cells in interval [l,r]
   * O(log n)
   * @param l The first cell in the interval
   * @param r The last cell in the interval
   * @return The sum/min/max over the interval*/
  long query(int l, int r) {
    ArrayList<Integer> left = new ArrayList(); 
    ArrayList<Integer> right = new ArrayList();
    for(l += offset-1, r += offset+1; l>>1 != r>>1; l >>= 1, r >>= 1) {
      left.add(l);
      right.add(r);
    }
    long bs = 0;
    for(l >>= 1; l > 0; l >>= 1) {
      bs += bonus[l];              // sum
//    bs = Math.min(bs, bonus[l]); // min
//    bs = Math.max(bs, bonus[l]); // max
    }
    long lbonus = bs+bonus[r^1];            // sum
    long rbonus = bs+bonus[r|1];            // sum
//  long lbonus = Math.min(bs, bonus[r^1]); // min
//  long rbonus = Math.min(bs, bonus[r|1]); // min
//  long lbonus = Math.max(bs, bonus[r^1]); // max
//  long rbonus = Math.max(bs, bonus[r|1]); // max
    
    long result = 0;
    for(int i = left.size()-1; i >= 0; --i) {
      if(left.get(i)%2 == 0) {
        result += ((lbonus+bonus[left.get(i)^1])<<i)+tree[left.get(i)^1]; // sum
//      result = Math.min(result, Math.min(tree[left.get(i)^1], lbonus)); // min
//      result = Math.max(result, Math.max(tree[left.get(i)^1], lbonus)); // max
      }
      if(right.get(i)%2 == 1) {
        result += ((rbonus+bonus[right.get(i)^1])<<i)+tree[right.get(i)^1]; // sum
//      result = Math.min(result, Math.min(tree[right.get(i)^1], rbonus));  // min
//      result = Math.max(result, Math.max(tree[right.get(i)^1], rbonus));  // max
      }
      
      lbonus += bonus[left.get(i)];                   // sum
      rbonus += bonus[right.get(i)];                  // sum
//    lbonus = Math.min(lbonus, bonus[left.get(i)]);  // min
//    rbonus = Math.min(rbonus, bonus[right.get(i)]); // min
//    lbonus = Math.max(lbonus, bonus[left.get(i)]);  // max
//    rbonus = Math.max(rbonus, bonus[right.get(i)]); // max
    }
    return result;
  }
}
