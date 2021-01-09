import java.util.ArrayList;
public class GeneticAlgorithm {
	private Population population = new Population();
	//Roulette Wheel Selection
	public GeneticAlgorithm(Population p) {
		this.population = p;
	}
	public ArrayList<Mario> generateNewPopulation() {
		ArrayList<Mario> oldPopulation = population.getPopulation();
		 ArrayList<Mario> newMarios = new  ArrayList<Mario>();
		  for (int i = 0; i < oldPopulation.size(); i++) {
		    // Select a Mario based on fitness
			NeuralNetwork brain = oldPopulation.get(selection()).getBrain();		
		    Mario m = new Mario(brain, i+1);
		    newMarios.add(m);
		  }
		  population.setPopulation(newMarios);
		  Population.generate++;
		  return newMarios;
		}
	public int selection(){
		double totalFitness = population.calculateTotalFitness();
		double random = Math.random()*totalFitness;
		double partialSum = 0;
		ArrayList<Mario> marios = population.getPopulation();
		for(int i = marios.size()-1; i >=0;  i--) {
			partialSum += marios.get(i).getFitness();
			if(partialSum >= random) {
				return i;
			}
		}
		return -1;
	}
	
}
