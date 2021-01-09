import java.awt.*;
import javax.swing.*;
public class Mario { 
	private Image mario;
	public int mariosGround ;
	private int width = 1280;
	private int height = 700;
	public int xPosition, yPosition, imageWidth, imageHeight, blocksWidth;
	private int translateX, walking;
	private int score;
	private boolean pressedRight, pressedSpace, releasedSpace, isBlocked, isTube, gameOver, isTranslating, falledIntoPit, isFastest;
	private double runner;
	private String imagePath = "mario1.png";
	private NeuralNetwork brain;
	private double fitness;
	private double time;
	private int index;
	public Mario(int index) {
		this.index = index;
		initializeMario();
		this.brain = new NeuralNetwork(3, 6, 2);
	}
	public Mario(NeuralNetwork nn, int index) {
		this.index = index;
		initializeMario();
		this.brain = nn.copy();
		brain.mutate(0.01);
	}
	public void initializeMario() {
		imageWidth = new ImageIcon(imagePath).getImage().getWidth(null);
		imageHeight = new ImageIcon(imagePath).getImage().getHeight(null);
		mariosGround = -106;
		xPosition = 10;
		yPosition = mariosGround -imageHeight;
	}
	public void think(Tiles t) {
		double[] input= new double[3];
		input[0] = -1.0*yPosition/height; 
		input[1] = (1.0*(t.findClosestObstacleX(xPosition) + this.translateX) - xPosition)/width; // the distance to the closest tube/pit x position
		input[2] = -1.0*t.getClosestObstacleY()/height; // closest tube/pit height y position 
		double[] output = brain.feedForward(input);
		if(output[0] >= 0.5){
			pressedRight = true;
		}
		else if(pressedRight){
			pressedRight = false;
		}
		if(output[1] >= 0.8) {
			pressedSpace = true;
		}
		else if(pressedSpace){
			pressedSpace = false;
			releasedSpace = true;
		}
	}
	public void move() {
		if(this.isBlocked  && yPosition + this.imageHeight > -265 +4 && mariosGround > -265) {
			this.pressedRight = false;
			isTube = true;
			
		}
		else if(isTube && !pressedRight && mariosGround == -106 ) {
			this.mariosGround = -106;
		}
		else if(isBlocked) {
			this.mariosGround = -265;
			isTube = false;
		} 

		if(this.mariosGround == -265 && (pressedRight)) { 
			runner+=1;
		}
		if(pressedRight && pressedSpace  && !releasedSpace && !gameOver){ 
			if(xPosition < width/2 && yPosition >= mariosGround - 4*height/10) {
				   xPosition +=1;
				   yPosition-=1;
				   walking+=1;
			   }
			   else if(yPosition >= mariosGround - 4*height/10){
				   updateTranslateX(-1);
				   yPosition-=1;
				   walking+=1;
			   }
			if(yPosition < mariosGround - 4*height/10) {
				releasedSpace = true;
				pressedSpace = false;
			}
				
		}
		else if(pressedSpace && !releasedSpace && !gameOver) {
				   yPosition-=1;
				   if(yPosition < mariosGround - 4*height/10) {
					   releasedSpace = true;
					   pressedSpace = false;
				   }
		}
		else if(pressedRight && !gameOver) {
			if(xPosition < 0) {
				   xPosition = width;
				}
			else if(xPosition < width/2) { 
				   xPosition += 1;
					walking+=1;}
			else{
				   xPosition = width/2;
			   		updateTranslateX(-1);
			   		walking+=1;
			   }
		}
		if(releasedSpace && !gameOver) {
			pressedSpace = false;
			yPosition += 1;
			   if(yPosition < mariosGround -imageHeight){
				   pressedSpace = false;
				   releasedSpace = true;
			   }
			   else {
				   releasedSpace = false;
			   }
		}
		
		if(gameOver) {
			yPosition += 1;
			pressedRight = false;
			 if(yPosition < 0) {
				 gameOver = true;
			 }
			 else {
				 if(index == -1) {
					 System.out.println("Best Mario has fallen into pit. Score: " + getScore());
				 }
				 else {
					 System.out.println("Mario "+ index + " has fallen into pit. Score: " + getScore());
				 }
				 this.falledIntoPit = true;
				 
			 }
		}
		if(this.mariosGround == -265 && this.runner  - this.imageWidth -1>= this.blocksWidth ) {
		runner = 0;
		mariosGround = -106;
	 	this.releasedSpace = true;
		this.isBlocked = false;
		isTube = false;
	}
		
	}
	public int calculateScore() {
		walking = walking % this.blocksWidth;
		if(walking >= 65) {
			score+=1;
			walking = 0;
		}
		return score;
	}
	public int getTranslateX() {
		return translateX;
	}
	public void setTime(double time) {
		this.time = time;
	}
	public double getTime() {
		return this.time;
	}
	public NeuralNetwork getBrain() {
		return this.brain;
	}
	public void setFitness(double fitness) {
		this.fitness = fitness;
		}
	public double getFitness() {
		return this.fitness;
	}
	public void updateTranslateX(int translateX) {
		isTranslating = true;
		this.translateX += translateX;
	}
	public void setTranslateX(int translateX) {
			this.translateX = translateX;
	}
	public int getxPosition() {
		return xPosition;
	}
	public void setxPosition(int xPosition) {
	 this.xPosition = xPosition;
	}
	public int getyPosition() {
		return yPosition;
	}
	public int getImageWidth() {
		return imageWidth;
	}
	public int getImageHeight() {
		return imageHeight;
	}
	public void setBlocked(boolean b) {
		isBlocked = b;
	}
	public void isTube(boolean b) {
		isTube = b;
	}
	public void setGameOver(boolean b) {
		gameOver = b;
	}
	public boolean fallIntoPit() {
		return falledIntoPit;
	}
	public void setBlocksWidth(int blocksWidth) {
		this.blocksWidth = blocksWidth;
	}
	public boolean getMovement() {
		return pressedRight && isTranslating;
	}
	public Image getImage() {
		mario = new ImageIcon(imagePath).getImage();
		return mario;
	}
	public void setScore(int s) {
		this.score = s;
	}
	public int getScore() {
		return score;
	}
	public void setIsFastest(boolean b) {
		this.isFastest = b;
	}
	public boolean getisFastest() {
		return this.isFastest;
	}
}
