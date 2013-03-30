public class MapTile {
	public int x;
	public int y;
	private final int SIZE = 20;
	private String type;
	private boolean passible;
	private int f;
	private int g;
	private int h;
	private MapTile parentTile;
	
	//Constructors
	public MapTile(int xx, int yy, String type, boolean passable){
		this.x = xx;
		this.y = yy;
		this.type = type;
		this.passible = passable;
		this.f = 0;
		this.g = 0;
		this.h = 0;
		this.parentTile = null;
	}
	
	//Setters, Getters
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public boolean isPassible() {
		return passible;
	}

	public void setPassible(boolean passible) {
		this.passible = passible;
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

	public int getF() {
		return f;
	}

	public void setF(int f) {
		this.f = f;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public MapTile getParentTile() {
		return parentTile;
	}

	public void setParentTile(MapTile parentTile) {
		this.parentTile = parentTile;
	}

	public int getSIZE() {
		return SIZE;
	}
	
}
