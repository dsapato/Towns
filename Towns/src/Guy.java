import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;


public class Guy {
	private String name;
	private String[] names = new String[]{"DannyTheGiraffe","JoeTheTall","HandlePledge","AlpTheIraqi","DavidTheTeaMaster","JimTheEndLineMisser","SchnitzTheLargeMouth","CesarioTheItalian","ZacTheStallion","JakeTheFIFALoser","BrotherMark"};
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
	private boolean going = false;
	private boolean dead = false;
	private List<MapTile> path = new ArrayList<MapTile>();
	private boolean selected = false;
	private boolean mouseReleased = false;
	
	//Constructors
	public Guy(int xx, int yy, int moveSpeed, int name){
		this.x = xx;
		this.y = yy;
		this.moveSpeed = moveSpeed;
		this.name = names[name];
		this.timestamp = System.currentTimeMillis();
		goalX = this.x;
		goalY = this.y;
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
	
	public boolean isDead() {
		return dead;
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
		Zen.drawImage("guy.png", x - Game.screenXPos, y - SIZE - Game.screenYPos, SIZE * 2, SIZE * 2);
		if(isHovered() || selected){
			Zen.setColor(Color.WHITE);
			Zen.setFont("Helvetica-10");
			Zen.drawText("Sleep: " + sleep, x - 5 - Game.screenXPos, y - 15 - Game.screenYPos);
			Zen.drawText("Hunger: " + hunger, x - 6 - Game.screenXPos, y - 30 - Game.screenYPos);
			Zen.drawText("State: " + state, x - 5 - Game.screenXPos, y - 45 - Game.screenYPos);
			Zen.setFont("Helvetica-10");
			Zen.drawText(name, x - 5 - Game.screenXPos, y + SIZE + 15 - Game.screenYPos);
		}
	}
	
	//Class Functions
	private void move(int speed){
		if(Math.random() > .98)sleep--;
		if(Math.random() > .98)hunger--;
		if(path == null){
			return;
		}
		if(path.size() > 0){
			goalX = path.get(path.size() - 1).getX() * SIZE;
			goalY = path.get(path.size() - 1).getY() * SIZE;
			if(Math.abs(goalX - x) < 10 && Math.abs(goalY - y) < 10 ){
				path.remove(path.size() - 1);
			}
		}
		else{
			going = false;
		}
		if(goalX > x)x += speed;
		if(goalX < x)x -= speed;
		if(goalY > y)y += speed;
		if(goalY < y)y -= speed;	
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
		
		if(hunger < 1 || sleep < 1){
			die(m);
		}
		else if(hunger < 50 && b.getAppleCount() > 0){	//Go to barrel
			state = "eating";
			if(!going){
				going = true;
				path = AStar.Search(x,y, b.getX(), b.getY() + 20, m);
			}
		}
		else if(sleep < 50 || (sleep != 100 && collided(h.getX(), h.getY(), h.getSIZE(), h.getSIZE()))){	//Go home
			state = "sleeping";
			if(!going){
				going = true;
				path = AStar.Search(x,y, h.getX(), h.getY(), m);
			}
		}
		
		else if(b.getAppleCount() < 10 || targetApple != null){	//Gather apples
			state = "gathering apples";
			if(goToBarrel){	//To barrel
				if(!going){
					going = true;
					path = AStar.Search(x,y, b.getX(), b.getY(), m);
				}
			}
			else if(targetApple != null){	//Go to apple
				if(!going){
					going = true;
					path = AStar.Search(x,y, targetApple.getX(), targetApple.getY(), m);
				}
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
		else if(going){	//Have goal, get to it
			state = "going";
			if(Zen.isKeyPressed('g')){
				path = AStar.Search(x,y, Zen.getMouseX() - Game.screenXPos ,Zen.getMouseY() - Game.screenYPos, m);
			}
			
		}
		else{
			state = "idle";
			if(Zen.isKeyPressed('g')){	//Go to mouse
				path = AStar.Search(x,y, Zen.getMouseX() - Game.screenXPos ,Zen.getMouseY() - Game.screenYPos, m);
				going = true;
				return;
			}			
		
			wander(m);
		}
	}
	private void wander(Map m){
		if(System.currentTimeMillis() - timestamp > 1000){
			int tryX = this.x + (int)(Math.random() * 100 - 50);
			int tryY = this.y + (int)(Math.random() * 100 - 50);
			if(tryX > 0 && tryY > 0 && m.isLocationPassible(tryX, tryY)){
				path = AStar.Search(x,y, tryX, tryY, m);
			}
			this.timestamp = System.currentTimeMillis();
		}
	}
	private void die(Map m){
		Zen.drawImage("explosion.png", x - Game.screenXPos - 50, y - Game.screenYPos - 50);
		m.setMapTile(new MapTile(x/SIZE, y/SIZE, "burntgrass", true));
		dead = true;
		
	}
	private boolean isHovered(){
		if(this.x + 80  - Game.screenXPos > Zen.getMouseX() && this.x - 80  - Game.screenXPos < Zen.getMouseX()){
			if(this.y + 80   - Game.screenYPos> Zen.getMouseY() && this.y - 80  - Game.screenYPos < Zen.getMouseY()){
				if(getClick()){
					selected = !selected;
				}
				return true;
			}
		}
		return false;
	}
	
	public boolean getClick(){
		if(Zen.getMouseState() == MouseEvent.MOUSE_CLICKED){
			mouseReleased = true;
		}
		if(mouseReleased && Zen.getMouseState() == MouseEvent.MOUSE_PRESSED){
			mouseReleased = false;
			return true;
		}
		return false;
	}
}
