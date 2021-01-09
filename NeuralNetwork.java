public class NeuralNetwork {
	private int input_nodes, hidden_nodes, output_nodes;
	public Matrix weightsIH, weightsHO, biasH, biasO, hiddenVector, outputVector, inputVector;
	private double learning_rate = 0.1, mutation_rate;
	public NeuralNetwork(NeuralNetwork nn) {
		input_nodes = nn.input_nodes;
		hidden_nodes = nn.hidden_nodes;
		output_nodes = nn.output_nodes;
		
		weightsIH = nn.weightsIH.copyMatrix();
		weightsHO = nn.weightsHO.copyMatrix();
		biasH = nn.biasH.copyMatrix();
		biasO = nn.biasO.copyMatrix();
		
		inputVector = new Matrix(input_nodes, 1);
		hiddenVector = new Matrix(hidden_nodes, 1);
		outputVector = new Matrix(output_nodes, 1);
	}
	public NeuralNetwork(int input_nodes, int hidden_nodes, int output_nodes) {
		this.input_nodes = input_nodes;
		this.hidden_nodes = hidden_nodes;
		this.output_nodes = output_nodes;
		weightsIH = new Matrix(hidden_nodes,input_nodes);
		biasH = new Matrix(hidden_nodes, 1);
		weightsHO = new Matrix(output_nodes,hidden_nodes);
		biasO = new Matrix(output_nodes, 1);
		inputVector = new Matrix(input_nodes, 1);
		hiddenVector = new Matrix(hidden_nodes, 1);
		outputVector = new Matrix(output_nodes, 1);
		weightsIH.randomize();
		weightsHO.randomize();
		biasH.randomize();
		biasO.randomize();
	}
	public double[] feedForward(double[] input){
		double[][] iV = Matrix.createVerticalVector(input);
		inputVector.setMatrixData(iV);
		hiddenVector.setMatrixData(Matrix.matrixMultiplication(weightsIH, inputVector));
		hiddenVector.matrixSum(biasH.getMatrixData());
		double[][] hV = hiddenVector.getMatrixData();
		for(int i = 0; i < hV.length; i++) {
			for(int j = 0; j < hV[i].length; j++) {
				double val = hV[i][j];
				hV[i][j] = sigmoid(val);
			}
		}
		outputVector.setMatrixData(Matrix.matrixMultiplication(weightsHO, hiddenVector));
		outputVector.matrixSum(biasO.getMatrixData());
		double[][] oV = outputVector.getMatrixData();
		for(int i = 0; i < oV.length; i++) {
			for(int j = 0; j < oV[i].length; j++) {
				double val = oV[i][j];
				oV[i][j] = sigmoid(val);
			}
		}
		return Matrix.createHorizontalVector(oV);
	}
	public double sigmoid(double x) {
		return 1.0/(1.0 + Math.exp(-x));
	}
	public void mutate(double rate) {
		this.mutation_rate = rate; 
		mutateMatrix(weightsIH);
		mutateMatrix(weightsHO);
		mutateMatrix(biasH);
		mutateMatrix(biasO);
	
	}
	public void mutateMatrix(Matrix m) {
		double[][] m1 = m.getMatrixData();
		for(int i = 0; i < m1.length; i++) {
			for(int j = 0; j < m1[i].length; j++) {
				m1[i][j] = mutateValue(m1[i][j]);
			}
		}
		m.setMatrixData(m1);
	}
	public double mutateValue(double value) {
		if(Math.random() < mutation_rate) {
//			return value + Math.random()/10;
			return Math.random();
		}
		else 
			return value;
	}
	public NeuralNetwork copy() {
		return new NeuralNetwork(this);
	}
	
}
