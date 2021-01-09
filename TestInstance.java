public class TestInstance{ 
	private Tiles t;
	private Mario m;
	private int width;
	private int height = 650;
	public TestInstance(Tiles t) {
		this.t = t;
		width = t.getImageWidth() * t.getNumberOfBlocks();
	}
	public void act() {
		t.collisionControll(m.getTranslateX());
		m.think(t);
		m.move();
	}
	public void setMario(Mario m) {
		this.m = m;
	}
}
