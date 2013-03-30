import java.awt.Color;


public class Barrel {
	private int x;
	private int y;
	private final int SIZE = 20;
	private int appleCount = 0;
	
	public Barrel(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void draw(){
		//Barrel
		Zen.drawImage("barrel.png", x - Game.screenXPos, y - Game.screenYPos, SIZE, SIZE + 5);
		
		//Count
		Zen.setColor(Color.WHITE);
		Zen.setFont("Helvetica-14");
		Zen.drawText("Apples: " + appleCount, x - 10 - Game.screenXPos, y - 10 - Game.screenYPos);
	}
	
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

	public void incrementAppleCount(int count) {
		this.appleCount += count;
	}

	public int getAppleCount() {
		return appleCount;
	}
}
