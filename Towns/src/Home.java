public class Home {
	private int x;
	private int y;
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getSIZE() {
		return SIZE;
	}

	private final int SIZE = 20;
	
	public Home(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void draw(){
		Zen.drawImage("home.png", x - Game.screenXPos, y - Game.screenYPos, SIZE, SIZE);
	}
}
