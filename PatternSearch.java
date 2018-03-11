import java.util.*;

public class PatternSearch {
   /**Find the occurences of a pattern in a string. Knuth-Morris-Pratt algorithm
    * O(n+k). n = length of string, k = length of pattern
    * @param string The string to search in
    * @param pattern The pattern to search for
    * @return A list of all indexes where the pattern occurs in the string*/
   static List<Integer> kmp(String string, String pattern) {
      char[] s = string.toCharArray();
      char[] p = pattern.toCharArray();
      
      // Preprocess the pattern
      int[] b = new int[p.length+1];
      for(int i = -1, j = -2; i < p.length; b[++i] = ++j) {
         // There is a mismatch, consider next widest border
         while(j >= 0 && p[i] != p[j])
            j = b[j];
      }
      
      // Find occurrences of pattern in string
      List<Integer> pos = new ArrayList();
      for(int i = 0, j = 0; i < s.length; ++i) {
         while(j >= 0 && s[i] != p[j])
            j = b[j];
         ++j;
         // Match is found
         if(j == p.length) {
            pos.add(i+1-p.length);
            j = b[j];
         }
      }
      
      return pos;
   }
}
