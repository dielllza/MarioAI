import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
public class Tiles {
	private Image tile;
	private ArrayList<Integer> tilesList;
	private int imageWidth, imageHeight, numberOfBlocks;
	private int frameHeight;
	private int translatorMario;
	private int closestObstacleY;
	private Mario m;
	public Tiles(int frameHeight) {
		tile =  new ImageIcon("tile4.png").getImage();
		imageWidth = tile.getWidth(null);
		imageHeight = tile.getHeight(null);
		tilesList = new ArrayList<Integer>();
		numberOfBlocks = 16;
		this.frameHeight = frameHeight;
		generateInitialArray();
	}
	private Integer generateTileHeight() {
		int n = (int)(Math.random()*10);
		if(n <=1 ) { // ==0
			return new Integer(0); //probabiliteti 0.2
		}
		else if(n < 8) {
			return new Integer(1); //probabiliteti 0.7
		}
		else {
			return new Integer(2); //probabiliteti 0.2
		}
	}
	public void generateInitialArray(){
		tilesList.add(1);
		for(int i = 1; i <= numberOfBlocks; i++) {
			Integer num = generateTileHeight();
			if(num == 2 && tilesList.get(i-1).equals(num)) {
				num = 1;
			}
			else if(num == 0 && tilesList.get(i-1).equals(num)){
				num = 1;
			}
			tilesList.add(num);
		}
	}
	public void resetPath() {
		tilesList.set(0, 1);
		for(int i = 1; i <= numberOfBlocks; i++) {
			Integer num = generateTileHeight();
			if(num == 2 && tilesList.get(i-1).equals(num)) {
				num = 1;
			}
			tilesList.set(i, num);
		}
	}
	public void updateTilesList(){
		tilesList.remove(0);
		Integer num = generateTileHeight();
		if(num == 2 && tilesList.get(tilesList.size()-1).equals(num)) {
			num = 1;
		}
		else if(num == 0 && tilesList.get(tilesList.size()-1).equals(num)){
			num = 1;
		}
		tilesList.add(num);
	}
	public ArrayList<Integer> getTilesList() {
		return tilesList;
	}
	public void drawTiles(Graphics g, ImageObserver i){
		g.translate(translatorMario, frameHeight);
		for(int x = 0; x < tilesList.size(); x++) {
			for (int y = 0; y < 3*tilesList.get(x); y++) {
				g.drawImage(tile, x*imageWidth , -y*imageHeight, i);
			}
		}
		g.translate(-translatorMario, 0);
	}
	public void collisionControll(int translator){
		translatorMario = translator;
		for(int x = 0; x < tilesList.size(); x++) {
				if((tilesList.get(x) == 0 && m.getxPosition() >= x*imageWidth && m.getxPosition()+ m.getImageWidth() - translator <= (x+1)*imageWidth) && m.getyPosition() + m.getImageHeight() == -2*imageHeight) {
					m.setGameOver(true);
				}
			for (int y = 0; y < 3*tilesList.get(x); y++) {
				if(tilesList.get(x) == 2) {			
					if(m.getxPosition() + m.getImageWidth() - translator >= x*imageWidth   && m.getxPosition() - translator <= (x+1)*imageWidth) {
						m.setBlocked(true);
					
					}
				}
			}
		}
		if(translator <= -imageWidth) {
			this.updateTilesList();
			m.setTranslateX(0);
			translatorMario = 0;
		}
	}
	public int findClosestObstacleX(int marioX){
		int minD = this.imageWidth*16;
		int minTubeXPos = 1280;
		for(int x = 0; x < tilesList.size(); x++) {
			if((tilesList.get(x)==2 || tilesList.get(x)==0)&& x*imageWidth - marioX < minD && x*imageWidth - marioX > 0) {
				minD = x*imageWidth - marioX;
				minTubeXPos = x*imageWidth;
				this.closestObstacleY = -3*tilesList.get(x)*imageHeight;
			}
		}
		return minTubeXPos;
	}
	public void setMario(Mario m) {
		this.m = m;
		m.setBlocksWidth(imageWidth);
	}
	public int getClosestObstacleY() {
		return closestObstacleY;
	}
	public int getImageWidth() {
		return imageWidth;
	}
	public int getImageHeight() {
		return imageHeight;
	}
	public int getNumberOfBlocks() {
		return numberOfBlocks;
	}
}

