import java.io.*;
import java.util.*;

/**For performing fast input operations*/
public class Input {
  public static void main(String[] args) throws Exception {
    
  }

  static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
  static StringTokenizer st = new StringTokenizer("");
  static String readString() throws Exception {
    while(!st.hasMoreTokens())
      // On EOF, stdin.readLine() returns null, and StringTokenizer throws NullPointerException
      st = new StringTokenizer(stdin.readLine());
    return st.nextToken();
  }
  static int readInt() throws Exception {
    return Integer.parseInt(readString());
  }
  static long readLong() throws Exception {
    return Long.parseLong(readString());
  }
  static double readDouble() throws Exception {
    return Double.parseDouble(readString());
  }
}
