import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LearnMain {
	private TestInstance testInstance;
	private ArrayList<Mario> marios;
	private Population population;
	private GeneticAlgorithm ga;
	private Tiles tilesBest = new Tiles(Controller.height - 50);
	public LearnMain() {
		population = new Population();
		population.initalizePopulation();
		ga = new GeneticAlgorithm(population);
		marios = population.getPopulation();
	}
	public static void main(String[] args) {
		LearnMain m = new LearnMain();
		m.threadsExecution();

	}
	public void controllGame() {
		population.calculateFitness();
		for (int i = 0; i < marios.size(); i++) {
			System.out.println("Mario " + (i + 1) + " fitness: " + marios.get(i).getFitness());
		}
		if (!population.populationConverged()) {
			marios = ga.generateNewPopulation();
			threadsExecution();
		} else {
			System.out.println("Popularion Coverged. Running best Mario");
//			marios.get(2).getBrain().biasH.printMatrix();
			NeuralNetwork brain = population.bestBrain();
			Mario bestMario = new Mario(brain, -1);
			tilesBest.setMario(bestMario);
			GamePanel gamePanel = new GamePanel(tilesBest);
			gamePanel.setMario(bestMario);
			GameFrame gameFrame = new GameFrame(gamePanel, Controller.width, Controller.height);
			gameFrame.setVisible(true);
			TimeCalculator timer = new TimeCalculator(60);
			timer.startTimer();
			while (!timer.isOver() && !bestMario.fallIntoPit() && bestMario.calculateScore() < 120) {
				gameFrame.setGenerationLabel(Population.generate);
				gameFrame.setChildLabel(population.bestMarioIndex() + 1);
				gameFrame.setLeftTime(timer.getCurrentTimer());
				gameFrame.setScoreLabel(bestMario.calculateScore());
				gamePanel.repaint();
			}
			bestMario.setTime(timer.getCurrentTimer());
			if (timer.isOver()) {
				System.out.println("Best Marios time is over");
			}

			else if (bestMario.fallIntoPit()) {
				System.out.println("Best Mario Falled into pit");
			} else if (bestMario.calculateScore() >= 120) {
				System.out.println("Congratulations! You Won!");
			}
			System.exit(0);
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
	}

	public void threadsExecution() {
		System.out.println("-------------------Generation " + Population.generate + "-------------------");
		ExecutorService executorService = Executors.newFixedThreadPool(50);
		for (int i = 0; i < marios.size(); i++) {
			int j = i + 1;
			Mario m = marios.get(i);
			TimeCalculator timer = new TimeCalculator(30);
			timer.startTimer();
			Thread thread = new Thread() {
				@Override
				public void run() {
					System.out.println("Running Mario " + j + " from generation " + Population.generate);
					Tiles tiles1 = new Tiles(Controller.height - 50);
					tiles1.setMario(m);
					TestInstance testInstance = new TestInstance(tiles1);
					testInstance.setMario(m);
					while (!timer.isOver() && !m.fallIntoPit() && m.calculateScore() < 250) {
						testInstance.act();
					}
					m.setTime(timer.getCurrentTimer());

				}
			};
			executorService.submit(thread);
		}
		try {
			executorService.shutdown();
			if (!executorService.awaitTermination(1, TimeUnit.MINUTES)) {
				System.out.println("Not done in one minute!");
			}
		} catch (InterruptedException e) {
			System.out.println("INTERRUPTED");
		}
		controllGame();
	}
}