class ValueSearch {
  static final double precision = 0.0000001;
  static double func(double m) {return 0;}
  
  /**Find the value x in [lo, hi] such that func(x) == value
   * Such x must exist, and func must be non-decreasing on [lo, hi]
   * O(log(hi-lo)*O(func)
   * @param value The value to search for
   * @param lo The lowest key to try
   * @param hi The highest key to try
   * @return A value x such that the key giving func(key) == value is in the interval [x-precision, x+precision]*/
  static double binarySearch(double value, double lo, double hi) {
    while(hi-lo > precision) {
      double m = (lo+hi)/2;
      if(value < func(m))
        hi = m;
      else
        lo = m;
    }
    return (lo+hi)/2;
  }
  
  /**Find the index containing value in the sorted array values
   * O(log n)
   * @param values The sorted array to search in
   * @param value The value to search for
   * @return The index containing value, or -1 if the value does not exist in the array*/
  static int binarySearch(int[] values, int value) {
    int lo = 0;
    int hi = values.length-1;
    while(lo <= hi) {
      int m = (lo+hi)/2;
      if(value < values[m])
        hi = m-1;
      else if(value > values[m])
        lo = m+1;
      else
        return m;
    }
    return -1;
  }
  
  /**Find the value x in [lo, hi] such that func(x) is minimal/maximal
   * func must be either non-increasing and then non-decreasing on [lo, hi], or opposite
   * O(log(hi-lo))*O(func)
   * @param lo The lowest key to try
   * @param hi The highest key to try
   * @return A value x such that the key giving minimal/maximal func(key) is in the interval [x-precision, x+precision]*/
  static double ternarySearch(double lo, double hi) {
    while(hi-lo > precision) {
      double m1 = lo+(hi-lo)/3;
      double m2 = hi-(hi-lo)/3;
//    if(func(m1) > func(m2)) // find min in interval
      if(func(m1) < func(m2)) // find max in interval
        lo = m1;
      else
        hi = m2;
    }
    return (lo+hi)/2;
  }
}
