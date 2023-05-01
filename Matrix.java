public class Matrix {

	/*
	 * One function to multiply / concatenate matrices (2d arrays)
	 */
    public static double[][] multiply(double[][] A, double[][] B) {
	int aRows = A.length;
	int aColumns = A[0].length;
	int bRows = B.length;
	int bColumns = B[0].length;
	
	if (aColumns != bRows) {
	    throw new IllegalArgumentException();
	}

	double[][] Result = new double[aRows][bColumns];
	for (int i = 0; i < aRows; i++) {
	    for (int j = 0; j < bColumns; j++) {
		Result[i][j] = 0.00000;
	    }
	}

	for (int i = 0; i < aRows; i++) { // aRow
	    for (int j = 0; j < bColumns; j++) { // bColumn
		for (int k = 0; k < aColumns; k++) { // aColumn
		    Result[i][j] += A[i][k] * B[k][j];
		}
	    }
	}
	
	return Result;
    }
}
