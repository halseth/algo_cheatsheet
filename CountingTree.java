class CountingTree_UpdateInterval_QueryCell {
  private final int offset;
  private final int[] tree;
  
  /**A data structure for adding values to intervals of cells, and retrieving values from single cells
   * O(n)
   * @param n The number of cells*/
  CountingTree_UpdateInterval_QueryCell(int n) {
    this.offset = n;
    tree = new int[2*n];
  }
  
  /**Add value to all cells in interval [lo, up]
   * O(log n)
   * @param lo The first cell in the interval
   * @param hi The last cell in the interval
   * @param value The value to add to all cells in the interval*/
  void update(int lo, int up, int value) {
    for(lo += offset-1, up += offset+1; lo/2 != up/2; lo /= 2, up /= 2) {
      if(lo%2 == 0){
          tree[lo^1] += value;  // sum
      //  tree[lo^1] = value; // max
      }
        
      if(up%2 == 1){
        tree[up^1] += value;
      //tree[up^1] = value;  // max
      }
        
    }
  }
  
  /**Get the value in a cell
   * O(log n)
   * @param cell The cell
   * @return The value of the cell*/
  int query(int cell) {
    int result = 0;
    for(cell += offset; cell > 0; cell /= 2){
    	result += tree[cell];	// sum
	//	result = Math.max(result, tree[cell]);	// max
    }      
    
    return result;
  }
}

class CountingTree_UpdateCell_QueryInterval {
  private final int offset;
  private final int[] tree;
  
  /**A data structure for updating values of single cells, and retrieving the sum/min/max/gcd over intervals of cells
   * O(n)
   * @param n The number of cells*/
  CountingTree_UpdateCell_QueryInterval(int n) {
    this.offset = n;
    tree = new int[2*n];
  }
  
  /**Update the value of a cell
   * O(log n)
   * @param cell The cell to update
   * @param value The value to update with*/
  void update(int cell, int value) {
    cell += offset;
    tree[cell] += value; // sum
//  tree[cell] = value;  // min
//  tree[cell] = value;  // max
//  tree[cell] = value;  // gcd
    
    for(cell /= 2; cell > 0; cell /= 2) {
      tree[cell] = tree[2*cell]+tree[2*cell+1];            // sum
//    tree[cell] = Math.min(tree[2*cell], tree[2*cell+1]); // min
//    tree[cell] = Math.max(tree[2*cell], tree[2*cell+1]); // max
//    tree[cell] = gcd(tree[2*cell], tree[2*cell+1]);      // gcd
    }
  }
  
  /**Get the sum/min/max/gcd over all cells in interval [lo, hi]
   * O(log n)
   * @param lo The first cell in the interval
   * @param hi The last cell in the interval
   * @return The sum/min/max/gcd over the interval*/
  int query(int lo, int up) {
    int result = 0;                 // sum
//  int result = Integer.MAX_VALUE; // min
//  int result = 0;                 // max
//  int result = 0;                 // gcd
    
    for(lo += offset-1, up += offset+1; lo/2 != up/2; lo /= 2, up /= 2) {
      if(lo%2 == 0) {
        result += tree[lo^1];                   // sum
//      result += Math.min(result, tree[lo^1]); // min
//      result += Math.max(result, tree[lo^1]); // max
//      result += gcd(result, tree[lo^1]);      // gcd
      }
      if(up%2 == 1) {
        result += tree[up^1];                   // sum
//      result += Math.min(result, tree[up^1]); // min
//      result += Math.max(result, tree[up^1]); // max
//      result += gcd(result, tree[up^1]);      // gcd
      }
    }
    
    return result;
  }
}
