import javax.swing.*;
import java.awt.*;
public class GamePanel extends JPanel{ 
	private Tiles t;
	private Mario m;
	private int width;
	private int height = 650;
	public GamePanel(Tiles t) {
		this.t = t;
		width = t.getImageWidth() * t.getNumberOfBlocks();
		setSize(width, height);
		setVisible(true);
	}
	public void paint(Graphics g)
	   {
	   Image Imazhi=createImage(getWidth(),getHeight());
	   Graphics gi=Imazhi.getGraphics();
	   paintComponent(gi);
	   g.drawImage(Imazhi,0,0,this);
	   }
	public void paintComponent(Graphics g) {
		g.setColor(new Color(163, 212, 247));
		g.fillRect(0, 0, width, height);
		t.collisionControll(m.getTranslateX());
		t.drawTiles(g, this);
		m.think(t);
		m.move();
		g.drawImage(m.getImage(), m.getxPosition(), m.getyPosition(), this);
	}
	public void setMario(Mario m) {
		this.m = m;
	}
}
