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

static class CountingTree_UpdateInterval_QueryInterval {

  private int OFFSET = 300000; // This may have to be increased for big input
  private long bonus[];
  private long tree[];

  /**
   * Returns: sum/max in interval [l,r]. O(logn)
   */
  public long getSum(int l, int r){
    l+=OFFSET-1; r+=OFFSET+1; 
    ArrayList<Integer> left = new ArrayList<Integer>(); 
    ArrayList<Integer> right = new ArrayList<Integer>();
    while ((l>>1)!=(r>>1)) {
      left.add(l); right.add(r);
      l>>=1; r>>=1;
    }
    int root=(l>>1);
    long bs=0;
    while (l>>1 != 0) {
      bs+=bonus[l>>1];                //sum
//    bs = Math.max(bs,bonus[l>>1]);  //max
      l>>=1;
    }
    long lbonus=bs+bonus[root<<1];              //sum
//  long lbonus = Math.max(bs,bonus[root<<1]);  //max
    long rbonus=bs+bonus[(root<<1)+1];              //sum
//  long rbonus = Math.max(bs,bonus[(root<<1)+1]);  //max
    long result=0;
    for(int i = left.size()-1; i >= 0; i--){                    
      if (!(left.get(i)%2 == 1)){
        result+=((lbonus+bonus[left.get(i)^1])<<i)+tree[left.get(i)^1]; //sum
//      result = Math.max(result,Math.max(tree[left.get(i)^1],lbonus)); //max
      }                 
      if ( (right.get(i)%2 ==1)){
        result+=((rbonus+bonus[right.get(i)^1])<<i)+tree[right.get(i)^1]; //sum
//      result = Math.max(result,Math.max(tree[right.get(i)^1],rbonus));  //max
      }
      lbonus+=bonus[left.get(i)];                   //sum
//    lbonus = Math.max(lbonus,bonus[left.get(i)]); //max
      rbonus+=bonus[right.get(i)];                    //sum
//    rbonus = Math.max(rbonus,bonus[right.get(i)]);  //max
    }
    return result;
  }

  /**
   * Adds v to all cells in interval [l,r]. O(logn)
   */
  public void add(int l, int r, long v){
    l+=OFFSET-1; r+=OFFSET+1;
    long chunk=0,lcount=0,rcount=0;
    while ((l>>1)!=(r>>1)){
      if (!(l%2 == 1)) 
        { bonus[l^1]+=v; lcount+=v<<chunk; }  //sum
//      {tree[l^1]=v; bonus[l^1]=v;}          //max
      
      if ( (r%2 == 1)) 
        { bonus[r^1]+=v; rcount+=v<<chunk; }  //sum
//      { tree[r^1]=v; bonus[r^1]=v;}         //max
      
      tree[l>>1]+=lcount;                                               //sum
//    tree[l>>1] = Math.max(bonus[l>>1], Math.max(tree[l],tree[l^1]));  //max
      
      tree[r>>1]+=rcount;                                               //sum
//    tree[r>>1] = Math.max(bonus[r>>1], Math.max(tree[r],tree[r^1]));  //max
      
      l>>=1; r>>=1; chunk++;
    }
    lcount+=rcount;
    while ((l >> 1 != 0)) {
      tree[l>>1]+=lcount;                                               //sum
//    tree[l>>1] = Math.max(bonus[l>>1], Math.max(tree[l],tree[l^1]));  //max
      l>>=1;
    }
  }

  /**
   * Constructor using specified OFFSET
   */
  public CountingTree_UpdateInterval_QueryInterval(){
    bonus = new long[OFFSET*2];
    tree = new long[OFFSET*2];
  }

  /**
   * Constructor using n - the size of the array.
   */
  public CountingTree_UpdateInterval_QueryInterval(int n){
    OFFSET = n+1;
    bonus = new long[OFFSET*2];
    tree = new long[OFFSET*2];
  }
}
