

public class Bed {
	//Variables
	private int x;
	private int y;
	private Guy owner;
	
	//Constructors
	public Bed(int xx, int yy){
		x = xx;
		y = yy;
	}
	
	//Setters and Getters
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
	public Guy getOwner() {
		return owner;
	}
	public void setOwner(Guy owner) {
		this.owner = owner;
	}
	
	//Functions
}
