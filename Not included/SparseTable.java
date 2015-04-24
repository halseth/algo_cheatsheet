
public class SparseTable {

	int[] logTable;
	int[][] tab;
	int[] a;

	// Initializes the SparseTable (RMQ). GCD in interval in comments. O(N*logN).
	public SparseTable(int[] a) {
		this.a = a;
		int n = a.length;

		logTable = new int[n + 1];
		for (int i = 2; i <= n; i++)
			logTable[i] = logTable[i >> 1] + 1;

		int k = logTable[n] + 1;
		tab = new int[n][k];

		for (int i = 0; i < n; ++i){
			tab[i][0] = i;
		}

		for(int j = 1; j < k; j++) {
			for (int i = 0; i + (1 << j) <= n; i++) {
				int tv = i + (1 << (j - 1));
				int x = tab[i][j - 1];
				int y = tab[tv][j - 1];
				tab[i][j] = a[x] <= a[y] ? x : y; // gcd: tab[i][j] = gcd(x, y);
			}
		}
	}

	// Position of minimum element in interval [l,r]. O(1) // GCD in interval [l, r]
	public int minPos(int l, int r) {
		int k = logTable[r - l];
		int x = tab[l][k];
		int y = tab[r - (1 << k) + 1][k];
		return a[x] <= a[y] ? x : y;	// gcd: return gcd(y, x);
	}
}