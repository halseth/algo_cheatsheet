import java.util.*;

class NumberTheory {
  static ArrayList<Integer> primes = new ArrayList();
  
  /**Generate all prime numbers less than or equal to n. Sieve of Eratosthenes
   * There are approximately n/log n prime numbers < n
   * O(n*log(log n))
   * @param n The largest number to consider
   * @return All prime numbers less than or equal to n*/
  static ArrayList<Integer> primes(int n) {
    boolean[] visited = new boolean[n+1];
    for(int p = 2; p <= Math.sqrt(n); ++p)
      if(!visited[p]) {
        primes.add(p);
        for(int m = p*p; m <= n && m > p; m += p)
          visited[m] = true;
      }
    return primes;
  }
  
  /**Generate a table specifying whether a number is prime or not, for all numbers up to n. Sieve of Eratosthenes
   * There are approximately n/log n prime numbers < n
   * O(n*log(log n))
   * @param n The largest number to consider
   * @return Which numbers less than or equal to n are prime*/
  static boolean[] primeTable(int n) {
    boolean[] primes = new boolean[n+1];
    Arrays.fill(primes, true);
    primes[0] = false;
    primes[1] = false;
    for(int p = 2; p <= Math.sqrt(n); ++p)
      if(primes[p]) {
        for(int m = p*p; m <= n && m > p; m += p)
          primes[m] = false;
      }
    return primes;
  }
  
  /**Factorize the integer n. primes() must have been run beforehand
   * O(sqrt n)
   * @param n The number to factorize
   * @return A list of primes and their power in the factorization*/
  static ArrayList<Pair> factorize(int n) {
    ArrayList<Pair> factors = new ArrayList();
    for(int p : primes) {
      if(p*p > n)
        break;
      int count = 0;
      while(n%p == 0) {
        count++;
        n /= p;
      }
      if(count > 0)
        factors.add(new Pair(p, count));
    }
    if(n > 1)
      factors.add(new Pair(n, 1));
    return factors;
  }
  
  /**Count how many times n! can be divided by the prime number p
   * <=> Count how many times p appears in the factorization of n!
   * O(log n)
   * @param n The base of the factorial
   * @param p The factor to count
   * @return How many times n! can be divided by the prime number p*/
  static int factorialDivisible(int n, int p) {
    int factor = p;
    int count = 0;
    while(true) {
      int times = n/factor;
      if(times <= 0)
        break;
      count += times;
      factor *= p;
    }
    return count;
  }
  
  /**Generate all binomial coefficients a choose b where a, b <= n
   * O(n^2)
   * @param n The largest binomial coefficient to generate
   * @return An array where index [i][j] is the binomial coefficient i choose j*/
  static int[][] binomialCoefficients(int n) {
    int[][] pascal = new int[n+1][n+1];
    for(int i = 1; i <= n; ++i) {
      pascal[i][0] = 1;
      for(int j = 1; j <= i; ++j)
        pascal[i][j] = pascal[i-1][j-1] + pascal[i-1][j];
    }
    return pascal;
  }
  
  /**Find the binomial coefficient n choose k = n!/(k!*(n-k)!)
   * O(k) time, O(1) space
   * @param n Number of elements to choose from
   * @param k Number of elements to choose
   * @return Number of ways to choose k from n elements*/
  static long binomialCoefficient(int n, int k) {
    long res = 1;
    if(n/2 < k)
      k = n-k;
    for(int i = 1; i <= k; ++i) {
      res *= (n-i+1);
      res /= i;
    }
    return res;
  }
  
  /**Generate the n first catalan numbers
   * O(n^2)
   * @param n How many numbers to generate
   * @return An array where index [i] is the i-th catalan number*/
  static int[] catalanNumbers(int n) {
    int[] catalan = new int[n+1];
    catalan[0] = 1;
    for(int i = 1; i <= n; ++i)
      for(int j = 0; j < i; ++j)
        catalan[i] += catalan[j]*catalan[i-j-1];
    return catalan;
  }
  
  /**Find the greates common divisor of a and b. Euclid's division algorithm
   * O(log min(|a|, |b|))
   * @param a The first number
   * @param b The second number
   * @return The greates common divisor*/
  static int gcd(int a, int b) {
    while(b != 0) {
      int t = b;
      b = a%b;
      a = t;
    }
    return Math.abs(a);
  }
  
  /**Calculate Bezout's identity: x and y, such that a*x+b*y = gcd(a, b). Euclid's extended algorithm
   * O((log min(|a|, |b|))^2)
   * @param a The first number
   * @param b The second number
   * @return <x, y, z> such that a*x+b*y = gcd(a, b) = z*/
  static Triple bezoutsIdentity(int a, int b) {
    int[] t = {1, 0};
    int[] s = {0, 1};
    int[] r = {b, a};
    int pos = 0;
    while(r[pos] != 0) {
      int npos = (pos+1)%2;
      int q = r[npos]/r[pos];
      r[npos] -= q*r[pos];
      s[npos] -= q*s[pos];
      t[npos] -= q*t[pos];
      pos = npos;
    }
    pos = (pos+1)%2;
    if(r[pos] < 0)
      return new Triple(-s[pos], -t[pos], -r[pos]);
    return new Triple(s[pos], t[pos], r[pos]);
  }
  
  /**Calculate the modular multiplicative inverse of a modulo m. Euclid's extended algorithm
   * O((log min(|a|, |b|))^2)
   * @param a A non-negative integer
   * @param m An integer > 1
   * @return The modular multiplicative inverse x such that ax === 1 (mod m), -1 if a and m are not coprime*/
  static int multiplicativeInverse(int a, int m) {
    Triple t = bezoutsIdentity(a, m);
    if(t.trd != 1)
      return -1;
    return (t.fst+m)%m;
  }
}
