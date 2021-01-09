import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
public class GameFrame extends JFrame{ 
	public  JLabel scoreLabel = new JLabel("score: ");
	public  JLabel timerLabel = new JLabel("seconds: ");
	public  JLabel generationLabel = new JLabel("generation: ");
	public  JLabel childLabel = new JLabel("child: ");
	private JPanel scorePanel, gamePanel;
	public GameFrame( GamePanel gamePanel, int width, int height) {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		editLabel(scoreLabel);
		editLabel(timerLabel);
		editLabel(generationLabel);
		editLabel(childLabel);
		scorePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 4));
		cp.add(gamePanel, BorderLayout.CENTER);
		scorePanel.setBackground(new Color(163, 212, 247));
		scorePanel.add(generationLabel);
		scorePanel.add(childLabel);
		scorePanel.add(scoreLabel);
		scorePanel.add(timerLabel);
		cp.add(scorePanel, BorderLayout.NORTH);	
		setSize(width, height);
		setTitle("Super Mario");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gamePanel.repaint();
	}
	public void editLabel(JLabel label) {
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Comic Sans MS", Font.PLAIN,40));
	}
	public void editButton(JButton b) {
		b.setBackground(new Color(250, 22, 79));
		b.setForeground(Color.WHITE);
		b.setFont(new Font("Comic Sans MS", Font.PLAIN,20));
	}
	public void setLeftTime(long time) {
		timerLabel.setText("Time: " + (60 - time));
	}
	public void setScoreLabel(int s) {
		scoreLabel.setText("Score " + s );
	}
	public void setGenerationLabel(int g) {
		generationLabel.setText("Generation: " + g);
	}
	public void setChildLabel(int c) {
		childLabel.setText("Child " + c);
	}
}

