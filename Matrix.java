public class Matrix {
	private double[][] matrixData;
	public Matrix(int rows,int cols) {
		matrixData = new double[rows][cols];
	}
	public double[][] scalarMultiplication(double val){
		for(int i = 0; i < matrixData.length; i++) {
			for(int j = 0; j < matrixData[i].length; j++) {
				matrixData[i][j] *= val;
			}
		}
		return matrixData;
	}
	public double[][] addValue(double val){
		for(int i = 0; i < matrixData.length; i++) {
			for(int j = 0; j < matrixData[i].length; j++) {
				matrixData[i][j] += val;
			}
		}
		return matrixData;
	}
	public double[][] randomize(){
		for(int i = 0; i < matrixData.length; i++) {
			for(int j = 0; j < matrixData[i].length; j++) {
				matrixData[i][j] = Math.random() * 2 -1;
			}
		}
		return matrixData;
	}
	public double[][] matrixSum(double[][] matrix2){
//		double[][] sumMatrix = new double[matrixData.length][matrixData[0].length];
		for(int i = 0; i < matrixData.length; i++) {
			for(int j = 0; j < matrixData[i].length; j++) {
				 matrixData[i][j] += matrix2[i][j];
			}
		}
		return matrixData;
	}
	public static double[][] substractMatrices(double[][] m1, double[][] m2){
		double[][] sustraction = new double[m1.length][m1[0].length];
		for(int i = 0; i < sustraction.length; i++) {
			for(int j = 0; j < sustraction[i].length; j++) {
				sustraction[i][j] = m1[i][j] - m2[i][j];
			}
		}
		return sustraction;
	}
	public static double[][] createVerticalVector(double[] arr){
		double[][] matrix = new double[arr.length][1];
		for(int i = 0; i < arr.length; i++) {
			matrix[i][0] = arr[i];
		}
		return matrix;
	}
	public static double[] createHorizontalVector(double[][] matrix){
		double[] vector = new double[matrix.length];
		for(int i = 0; i < vector.length; i++) {
			vector[i] = matrix[i][0];
		}
		return vector;
	}
	public static double vectorRowSum(double[][] matrix, int rowIndex) {
		double sum = 0;
		for(int i = 0; i < matrix[rowIndex].length; i++) {
			sum += matrix[rowIndex][i];
		}
		return sum;
	}
	public static Matrix transposeMatrix(double[][] matrix){
		double[][] matrixT = new double[matrix[0].length][matrix.length];
		for(int i = 0; i < matrixT.length; i++) {
			for(int j = 0; j < matrixT[i].length; j++) {
				matrixT[i][j] = matrix[j][i];
			}
		}
		Matrix m = new Matrix(matrixT.length, matrixT[0].length);
		m.setMatrixData(matrixT);
		return m;
	}
	public static double[][] matrixMultiplication(Matrix m1, Matrix m2){
		double[][] matrix1 = m1.getMatrixData();
		double[][] matrix2 = m2.getMatrixData();
		double[][] productMatrix = new double[matrix1.length][matrix2[0].length];
			for(int i = 0; i < matrix1.length; i++) {
				for(int j = 0; j < matrix2[0].length; j++) {
					double sum = 0;
					for(int k = 0; k < matrix2.length; k++) {
					//	productMatrix[][] = matrix1[][] * matrix2[][];
						sum += matrix1[i][k] * matrix2[k][j];
					}
					productMatrix[i][j] = sum; 
				}
			}
		return productMatrix; 
	}
	public static double[][] elementWiseMultiplication(Matrix m1, Matrix m2){
		double[][] matrix1 = m1.getMatrixData();
		double[][] matrix2 = m2.getMatrixData();
		double[][] productMatrix = new double[matrix1.length][matrix2[0].length];
			for(int i = 0; i < matrix1.length; i++) {
				for(int j = 0; j < matrix2[0].length; j++) {
					productMatrix[i][j] = matrix1[i][j] * matrix2[i][j];				
				}
			}
		return productMatrix; 
	}
	public double[][] multiplyByMatrix(Matrix m) {
//		double[][] matrix2 = m.copyMatrix();
		return matrixMultiplication(this, m); 
		}
	public void printMatrix() {
		for(int i = 0; i < matrixData.length; i++) {
			for(int j = 0; j < matrixData[i].length; j++) {
				System.out.print(matrixData[i][j] + "\t");
			}
			System.out.println();
		}
	}
	public double[][] getMatrixData() {
		return matrixData;
	}
	public void setMatrixData(double[][] matrixData) {
		this.matrixData = matrixData;
	}
	
	public double[][] copy() {
		double[][] newMatrix = new double[matrixData.length][matrixData[0].length];
		for(int i = 0; i < matrixData.length; i++) {
			for(int j = 0; j < matrixData[i].length; j++) {
				newMatrix[i][j] = matrixData[i][j];
			}
		}
		return newMatrix;
	}
	public Matrix copyMatrix() {
			double[][] cp = copy();
			Matrix newMatrix = new Matrix(cp.length, cp[0].length);
			newMatrix.setMatrixData(cp);
			return newMatrix;
		}
}	
	

