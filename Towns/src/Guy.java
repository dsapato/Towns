import java.awt.Color;


public class Guy {
	private String name;
	private String[] names = new String[]{"DannyTheGiraffe","JoeTheTall","HandlePledge","AlpTheIraqi","DavidTheTeaMaster","JimThePussy","SchnitzTheLargeMouth","CesarioTheItalian","ZacTheStallion","JakeTheFIFALoser"};
	private String state;
	private int x;
	private int y;
	private int goalX;
	private int goalY;
	private int moveSpeed;
	private long timestamp;
	private final int SIZE = 20;
	private int sleep = 100;
	private int hunger = 100;
	private Apple targetApple;
	private boolean goToBarrel = false;
	
	//Constructors
	public Guy(int xx, int yy, int moveSpeed, int name){
		this.x = xx;
		this.y = yy;
		this.moveSpeed = moveSpeed;
		this.name = names[name];
		this.timestamp = System.currentTimeMillis();
	}
	
	//Setters/Getters
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}
	public int getSIZE() {
		return SIZE;
	}
	public void setTargetApple(Apple a){
		targetApple = a;
	}
	
	//Functions
	public void setGoal(int xx, int yy){
		goalX = xx;
		goalY = yy;
	}
	
	public void doYourThing(AppleList a, Barrel b, Home h , Map m){
		chooseGoal(a,b,h,m);
		move(moveSpeed);
	}
	
	public void checkBarrelCollision(Barrel b){
		if(collided(b.getX()-10, b.getY()-10, b.getSIZE()+20, b.getSIZE()+20)){ //Extended Range
			goToBarrel = false;
			if(hunger <= 75){
				if(b.getAppleCount() > 0){
					hunger += 25;
					b.incrementAppleCount(-1);
				}
			}
		}
	}
	
	public void checkHomeCollision(Home h){
		if(collided(h.getX() - SIZE, h.getY() - SIZE, 2 * SIZE, 2 * SIZE) && sleep < 100){
			sleep++;
		}
	}
	
	public void checkAppleCollision(Apple a){
		if(collided(a.getX(), a.getY(), a.getSIZE(), a.getSIZE())){
			a.attachToPlayer(this);
			if(a.isTargetedBy(this)){
				goToBarrel = true;
			}
		}
	}
	
	public void checkGuyCollision(Guy g){
		if(this.x + this.SIZE > g.getX() && this.x < g.getX() + g.getSIZE()){
			if(this.x > g.getX()){x+=moveSpeed*2;}
			else{x-=moveSpeed*2;}
		}
		
		if(this.y + this.SIZE > g.getY() && this.y < g.getY() + g.getSIZE()){
			if(this.y > g.getY()){y+=moveSpeed*2;}
			else{y-=moveSpeed*2;}
		}
	}
	


	public void draw(){
		Zen.drawImage("guy.png", x, y - SIZE, SIZE * 2, SIZE * 2);
		
		Zen.setColor(Color.WHITE);
		Zen.setFont("Helvetica-10");
		Zen.drawText("Sleep: " + sleep, x - 5, y - 15);
		Zen.drawText("Hunger: " + hunger, x - 6, y - 30);
		Zen.drawText("State: " + state, x - 5, y - 45);
		Zen.setFont("Helvetica-10");
		Zen.drawText(name, x - 5, y + SIZE + 15);
	}
	
	//Class Functions
	private void move(int speed){
		if(Math.random() > .98)sleep--;
		if(Math.random() > .98)hunger--;
		if(goalX > x)x+= speed;
		if(goalX < x)x-= speed;
		if(goalY > y)y+= speed;
		if(goalY < y)y-= speed;
	}
	
	private boolean collided(int x, int y, int width, int height){
		if(this.x + this.SIZE > x && this.x< x + width){
			if(this.y + this.SIZE > y && this.y < y + height){
				return true;
			}
		}
		return false;
	}

	private void chooseGoal(AppleList a, Barrel b, Home h, Map m){
		if(hunger < 50){	//Go to barrel
			state = "eating";
			goalX = b.getX();
			goalY = b.getY() + 20;
		}
		else if(sleep < 50 || (sleep != 100 && collided(h.getX(), h.getY(), h.getSIZE(), h.getSIZE()))){	//Go home
			state = "sleeping";
			goalX = h.getX();
			goalY = h.getY();
		}
		
		else if(b.getAppleCount() < 10 || targetApple != null){	//Gather apples
			state = "gathering apples";
			if(goToBarrel){	//To barrel
				goalX = b.getX();
				goalY = b.getY();
			}
			else if(targetApple != null){
				goalX = targetApple.getX();
				goalY = targetApple.getY();				
			}
			else{
				for(int i = 0; i < a.length(); i++){ //Find new target apple
					if(a.appleAt(i).isTargetedBy(null)){
						a.appleAt(i).setTargetedBy(this);
						targetApple = a.appleAt(i);
						return;
					}
				}
			}
		}
		else {
			state = "idle";
			wander(m);
		}
	}
	private void wander(Map m){
		if(System.currentTimeMillis() - timestamp > 1000){
			int tryX = this.x + (int)(Math.random() * 100 - 50);
			int tryY = this.y + (int)(Math.random() * 100 - 50);
			if(tryX > 0 && tryY > 0 && tryX < Zen.getZenWidth() && tryY < Zen.getZenHeight() && m.isLocationPassible(tryX, tryY)){
				goalX = tryX;
				goalY = tryY;
			}
			this.timestamp = System.currentTimeMillis();
		}
	}
}
