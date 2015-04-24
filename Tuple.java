import java.util.*;

/**Couples together an id and a weight, for use in priority queues*/
class Pair {
  int fst, snd;

  /**@param fst First value
   * @param snd Second value*/
  Pair(int fst, int snd) {
    this.fst = fst;
    this.snd = snd;
  }

  /**A Pair Comparator comparing only on the second value*/
  static class CompareSecond implements Comparator<Pair> {
    @Override
    public int compare(Pair o1, Pair o2) {
      return Integer.compare(o1.snd, o2.snd);
    }
  }
}

class Triple {
  int fst, snd, trd;

  /**@param fst First value
   * @param snd Second value
   * @param trd Third value*/
  Triple(int fst, int snd, int trd) {
    this.fst = fst;
    this.snd = snd;
    this.trd = trd;
  }
}