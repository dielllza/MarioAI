import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Controller extends JFrame {
	public ArrayList<Mario> marios;
	public Population population;
	private Tiles t;
	private Mario m;
	private GeneticAlgorithm ga;
	private GamePanel gamePanel;
	public static int width = 1280;
	public static int height = 700;
	private TimeCalculator timer;
	private GameFrame game;
	private JLabel gameDescription = new JLabel("Score 200 points in 60 seconds to win the game.");
	private JButton startButton;
	private int i = 1;
	public Controller() {
		population = new Population();
		population.initalizePopulation();
		ga = new GeneticAlgorithm(population);
		marios = population.getPopulation();
		t = new Tiles(height - 50); // resend a new mario
		width = t.getImageWidth() * t.getNumberOfBlocks();
		gamePanel = new GamePanel(t); // resend a new mario
		timer = new TimeCalculator(3); // restart timer
		Container cp = getContentPane();
		cp.setLayout(new FlowLayout(FlowLayout.CENTER, 560, 160));
		cp.setBackground(new Color(163, 212, 247));
		game = new GameFrame(gamePanel, width, height);
		game.editLabel(gameDescription);
		cp.add(gameDescription);
		startButton = new JButton("Start");
		game.editButton(startButton);
//		timer.startTimer();
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				timer.startTimer();
				game.setVisible(true);
				setVisible(false);
			}
		});

		cp.add(startButton);
		setSize(width, height);
		setTitle("Super Mario");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
//		m = marios.get(0);
		m = marios.get(0);
		restart(m);
	}
	public void run() {
		game.setGenerationLabel(Population.generate);
		while (i <= Population.marioTotal) {
			game.setChildLabel(i);

			while (!timer.isOver() && !m.fallIntoPit() && m.calculateScore() < 250) {
				game.setLeftTime(timer.getCurrentTimer());
				game.setScoreLabel(m.calculateScore());
				gamePanel.repaint();
			}
			m.setTime(timer.getCurrentTimer());
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
			if (i == Population.marioTotal) {
				break;
			}
			m = marios.get(i);
			i++;
			restart(m);
		}
		i = 1;
		population.calculateFitness();
		for (Mario ma : marios) {
			System.out.println("Fitness " + ma.getFitness());
		}
		if (!population.populationConverged()) {
			marios = ga.generateNewPopulation();
			m = marios.get(0);
			restart(m);
		}
	}
	public void restart(Mario m) {
		t.setMario(m);
		t.resetPath();
		gamePanel.setMario(m);
		game.getContentPane().add(gamePanel);
		timer.startTimer();
		gamePanel.repaint();
		run();
	}
	public static void main(String[] args) {
		new Controller();
	}
}
