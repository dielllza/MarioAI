import java.util.ArrayList;
public class Population {
	public static int marioTotal = 20;
	public static int generate;
	public ArrayList<Mario> marios = new ArrayList<Mario>();
	int fittest = 0;
	
	public void initalizePopulation() {
		generate = 1;
		for(int i= 0; i < marioTotal; i++) {
			marios.add(new Mario(i+1));
		}
	}
	public void calculateFitness() {
		double sumFitness = 0;
		for(Mario m: marios) {
			sumFitness = sumFitness + (m.getScore()/ m.getTime());
		}
		for(Mario m : marios) {
			double fitness = m.getScore();
			m.setFitness(fitness);
		}
	}
	public double calculateTotalFitness() {
		double fitnessTot = 0;
		for(Mario m : marios) {
			fitnessTot += m.getFitness();
		}
		return fitnessTot;
	}
	public boolean populationConverged() {
		boolean b = false;
		for(Mario m : marios) {
			if(m.getFitness() >= 240) {
				b = true;
			}
		}
		return b;
	}
	public int bestMarioIndex() {
		double bestFitness = marios.get(0).getFitness();
		int indx = 0;
		for(int i = 1; i < marioTotal; i++ ) {
			if(marios.get(i).getFitness() > bestFitness) {
				bestFitness = marios.get(i).getFitness();
				indx = i;
			}
		}
		return indx;
	}
	public NeuralNetwork bestBrain() {
		return marios.get(bestMarioIndex()).getBrain();
	}
	public ArrayList<Mario> getPopulation(){
		return this.marios;
	}
	public void setPopulation(ArrayList<Mario> m) {
		this.marios = m;
	}
}
