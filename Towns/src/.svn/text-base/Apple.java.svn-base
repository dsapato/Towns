
public class Apple {
	private int x;
	private int y;
	private final int SIZE = 20;
	public Guy targetedBy;
	private Guy carrier;
	
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

	public boolean isTargetedBy(Guy g) {
		return targetedBy == g;
	}

	public Guy getTargetedBy() {
		return targetedBy;
	}

	public void setTargetedBy(Guy g) {
		this.targetedBy = g;
	}

	public Apple(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void draw(){
		Zen.drawImage("apple.png", x, y, SIZE, SIZE);
		if(targetedBy != null){
			Zen.drawText("Targeted.", x, y);
		}
	}
	
	public void update(){
		if(carrier != null){
			this.x = carrier.getX();
			this.y = carrier.getY();
		}
	}
	
	public boolean checkBarrelCollision(Barrel b){
		if(collided(b.getX(), b.getY(), b.getSIZE(), b.getSIZE()) && carrier != null){
			carrier.setHasTargetApple(false);
			b.incrementAppleCount(1);
			this.x = 50;
			return true;
		}
		return false;
	}
	
	public void attachToPlayer(Guy g){
		if(targetedBy == g){
			carrier = g;
		}
	}
	
	private boolean collided(int x, int y, int width, int height){
		if(this.x + this.SIZE > x && this.x< x + width){
			if(this.y + this.SIZE > y && this.y < y + height){
				return true;
			}
		}
		return false;
	}	
}
