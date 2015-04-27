class ValueSearch {
  static final double precision = 0.0000001;
  static double func(double m) {return 0;}
  
  /**Find the value x in [l,r] such that func(x) == value
   * Such x must exist, and func must be non-decreasing on [l,r]
   * O(log(r-l)*O(func))
   * @param value The value to search for
   * @param l The lowest key to consider
   * @param r The highest key to consider
   * @return A value x such that the key giving func(key) == value is in the interval [x-precision, x+precision]*/
  static double binarySearchContinuous(double value, double l, double r) {
    while(r-l > precision) {
      double m = (l+r)/2;
      if(value < func(m))
        r = m;
      else
        l = m;
    }
    return (l+r)/2;
  }
  
  /**Find the index containing value in the sorted array values
   * O(log n), where n is the number of elements of values
   * @param values The sorted array to search in
   * @param value The value to search for
   * @return The index containing value, or -1 if the value does not exist in the array*/
  static int binarySearchDiscrete(double[] values, double value) {
    int l = 0;
    int r = values.length-1;
    while(l <= r) {
      int m = (l+r)/2;
      if(value < values[m])
        r = m-1;
      else if(value > values[m])
        l = m+1;
      else
        return m;
    }
    return -1;
  }
  
  /**Find the value x in [l,r] such that func(x) is minimal/maximal
   * func must be either non-increasing and then non-decreasing on [l,r], or opposite
   * O(log(r-l))*O(func)
   * @param l The lowest key to consider
   * @param r The highest key to consider
   * @return A value x such that the key giving minimal/maximal func(key) is in the interval [x-precision, x+precision]*/
  static double ternarySearchContinuous(double l, double r) {
    while(r-l > precision) {
      double m1 = l+(r-l)/3;
      double m2 = r-(r-l)/3;
//    if(func(m1) > func(m2)) // find min in interval
      if(func(m1) < func(m2)) // find max in interval
        l = m1;
      else
        r = m2;
    }
    return (l+r)/2;
  }
  
  /**Find the index i in [l,r] such that values[i] is minimal/maximal
   * values must be either non-increasing and then non-decreasing on [l,r], or opposite
   * O(log(r-l))
   * @param values The array to search in
   * @param l The lowest index to consider
   * @param r The highest index to consider
   * @return An index i such that values[i] is minimal/maximal among values[l..r]*/
  static int ternarySearchDiscrete(double[] values, int l, int r) {
    while(l < r) {
      int m1 = l+(r-l)/3;
      int m2 = r-(r-l)/3;
//    if(values[m1] > values[m2]) // find min in interval
      if(values[m1] < values[m2]) // find max in interval
        l = Math.max(m1, l+1);
      else 
        r = Math.min(m2, r-1);
    }
    return l;
  }
}
