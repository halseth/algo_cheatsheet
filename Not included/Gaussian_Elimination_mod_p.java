
public class Gaussian_Elimination_mod_p {
	
	static int inverse(long a, int p) {
		return 0;
	}
	
	// Transforms A so that the leftmost square matrix has at most one 1 per row,
	// and no other nonzero elements.
	// O(n^3)
	static int prime;
	public static void gauss(int[][] A, int num_columns) {
		int n = A.length;
		int m = A[0].length;

		for (int i = 0; i < num_columns; i++) {
			// Finding row with nonzero element at column i, swap this to row i
			for(int k = i; k < num_columns; k++){
				if(A[k][i] != 0){
					int t[] = A[i];
					A[i] = A[k];
					A[k] = t;
				}
			}
			// Normalize the i-th row.
			int inverse = (int)inverse((long)A[i][i], prime);
			for (int k = i ; k < m; k++) A[i][k] = (A[i][k]*inverse) % prime;

			// Combine the i-th row with the following rows.
			for (int j = 0; j < n; j++) {
				if(j == i) continue;
				int c = A[j][i];
				A[j][i] = 0;
				for (int k = i + 1; k < m; k++){
					A[j][k] = (A[j][k] - c * A[i][k] + c * prime) % prime;
				}
			}
		}
	}

	public static void gauss(int[][] A) {
		gauss(A, Math.min(A.length, A[0].length));
	}
}
