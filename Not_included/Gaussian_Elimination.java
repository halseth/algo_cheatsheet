
public class Gaussian_Elimination {
	// Transforms A so that the leftmost square matrix has at most one 1 per row,
	// and no other nonzero elements.
	// O(n^3)
	public static void gauss(double[][] A, int num_pivoting_columns) {
	    
		double eps = 0.001; //Some epsilon
	    int n = A.length;
	    int m = A[0].length;
	    
	    for (int i = 0; i < num_pivoting_columns; i++) {
	        // Process column i.
	        {
	            // Consider swapping the i-th row with another one.
	            int best = i;
	            for (int j = i + 1; j < n; j++)
	                if (Math.abs(A[j][i]) > Math.abs(A[best][i]))
	                    best = j;
	            if (Math.abs(A[best][i]) < eps)
	                continue;
	            
	            if (best != i){
	            	//swaps
	            	double[] temp = A[best];
	            	A[best] = A[i];
	            	A[i] = temp;	
	            }	            
	        }

	        // Normalize the i-th row.
	        for (int k = i + 1; k < m; k++)
	            A[i][k] /= A[i][i];
	        A[i][i] = 1;

	        // Combine the i-th row with the following rows.
	        for (int j = i + 1; j < n; j++) {
	            
	            double c = A[j][i];
	            
	            if (Math.abs(c) < eps)
	                continue;
	            
	            A[j][i] = 0;
	            for (int k = i + 1; k < m; k++)
	                A[j][k] -= c * A[i][k];
	        }
	    }

	    for (int i = num_pivoting_columns; i-- > 0; ) {
	        
	        // Find the pivot, if any.
	        int pivot = 0;
	        while (pivot < num_pivoting_columns && Math.abs(A[i][pivot]) < eps)
	            ++pivot;
	        if (pivot == num_pivoting_columns)
	            continue;
	        
	        if (Math.abs(A[i][pivot] - 1) > eps)
	            break;
	        
	        // Combine A[i] with the previous rows.
	        for (int j = 0; j < i; ++j) {
	            
	            if (Math.abs(A[j][pivot]) < eps)
	                continue;
	            
	            double c = A[j][pivot];
	            A[j][pivot] = 0;
	            
	            for (int k = pivot + 1; k < m; ++k)
	                A[j][k] -= c * A[i][k];
	        }
	    }
	}

	public static void gauss(double[][] A) {
	    gauss(A, Math.min(A.length, A[0].length));
	}
}
