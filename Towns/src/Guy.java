import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;


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
	private Bed myBed;
	private boolean passedOut = false;
	private BufferedImage image = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);
	
	//Constructors
	public Guy(int xx, int yy, int moveSpeed, int name){
		this.x = xx;
		this.y = yy;
		this.moveSpeed = moveSpeed;
		this.name = names[name];
		this.timestamp = System.currentTimeMillis();
		goalX = this.x;
		goalY = this.y;
		
		image = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);
		try {
			image = ImageIO.read(new File("C:\\Users\\Danny\\Towns\\Towns\\Resources\\People\\" + this.name + ".png"));
		}
		catch (IOException e) {
			try {
				image = ImageIO.read(new File("C:\\Users\\Danny\\Towns\\Towns\\Resources\\People\\" + "DannyTheGiraffe" + ".png"));
			}
			catch (IOException er) {}		
		}		
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
	public Bed getMyBed() {
		return myBed;
	}
	public void setMyBed(Bed myBed) {
		this.myBed = myBed;
	}
	public void setGoal(int xx, int yy){
		goalX = xx;
		goalY = yy;
	}

	//Functions	
	
	public void checkBarrelCollision(){
		for(int i = 0; i < Game.barrelList.length(); i ++){
			if(collided(Game.barrelList.barrelAt(i).getX() - 10, Game.barrelList.barrelAt(i).getY() - 10, SIZE + 20, SIZE + 20)){ //Extended Range
				goToBarrel = false;
				if(hunger <= 75){
					if(Game.barrelList.getTotalApples() > 0){
						hunger += 25;
						Game.barrelList.setTotalApples(Game.barrelList.getTotalApples() - 1);
					}
				}
			}
		}
	}
	
	public void checkBedCollision(){
		if(myBed != null && collided(myBed.getX() - SIZE, myBed.getY() - SIZE, 2 * SIZE, 2 * SIZE) && sleep < 100){
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
	
	public void doYourThing(){
		chooseGoal();
		move(moveSpeed);
	}	
	
	public void draw(){
		Zen.drawImage(image, x - Game.screenXPos, y - Game.screenYPos);

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
	private void chooseGoal(){
		
		if(hunger < 1){
			die(Game.map);
		}
		else if(hunger < 50){
			if(Game.barrelList.getTotalApples() > 0){	//Go to barrel
				state = "eating at barrel";
				if(!going){
					going = true;
					Barrel barrelToEat = targetNearestBarrel();
					path = AStar.Search(x,y, barrelToEat.getX(), barrelToEat.getY());
				}
			}
			else if(targetApple != null && (going || collided(targetApple.getX(), targetApple.getY(), targetApple.getSIZE(), targetApple.getSIZE()))){
				targetApple.setX(-30);
				going = false;
				hunger += 25;
			}
			else{
				state = "finding apple to eat";
				targetNearestApple();
				going = true;
				path = AStar.Search(x,y, targetApple.getX(), targetApple.getY());
			}
		}
		else if(myBed != null && (sleep < 25 || (sleep != 100 && collided(myBed.getX(), myBed.getY(), SIZE, SIZE)))){	//Go to bed
			state = "sleeping";
			if(!going){
				going = true;
				path = AStar.Search(x,y, myBed.getX(), myBed.getY());
			}
		}
		else if(sleep == 1 || passedOut){	//Sleep in place
			state = "passed out";
			passedOut = true;
			if(Math.random() > .95)sleep++;
			if(sleep > 50){
				passedOut = false;
			}
		}
		
		else if((Game.barrelList.getTotalApples() < 10 || targetApple != null) && Game.barrelList.length() > 0){	//Gather apples
			state = "gathering apples";
			if(goToBarrel){	//To barrel
				if(!going){
					going = true;
					Barrel nearest = targetNearestBarrel();
					if(nearest != null)path = AStar.Search(x,y, nearest.getX(), nearest.getY());
				}
			}
			else if(targetApple != null){	//Go to apple
				if(!going){
					going = true;
					path = AStar.Search(x,y, targetApple.getX(), targetApple.getY());
				}
			}
			else{
				targetNearestApple();
			}
		}
		else if(going){	//Have goal, get to it
			state = "going";
			if(Zen.isKeyPressed('g')){
				path = AStar.Search(x,y, Zen.getMouseX() + Game.screenXPos ,Zen.getMouseY() + Game.screenYPos);
			}
			
		}
		else{
			state = "idle";
			if(Zen.isKeyPressed('g')){	//Go to mouse
				path = AStar.Search(x,y, Zen.getMouseX() + Game.screenXPos ,Zen.getMouseY() + Game.screenYPos);
				going = true;
				return;
			}			
		
			wander(Game.map);
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
	
	private void move(int speed){
		if(path == null){
			Zen.setColor(Color.RED);
			Zen.drawText("NO PATH", x - 10 - Game.screenXPos, y - 60 - Game.screenYPos);
			return;
		}
		if(passedOut)return;
		if(path.size() > 0){
			if(Math.random() > .98 && sleep > 0)sleep--;
			if(Math.random() > .98)hunger--;
			goalX = path.get(path.size() - 1).getX() * SIZE;
			goalY = path.get(path.size() - 1).getY() * SIZE;
			if(Math.abs(goalX - x) < 10 && Math.abs(goalY - y) < 10 ){
				path.remove(path.size() - 1);
			}
		}
		else{
			going = false;
		}
		if(goalX > x){x += speed;}
		if(goalX < x){x -= speed;}
		if(goalY > y){y += speed;}
		if(goalY < y){y -= speed;}	
	}
	
	private void die(Map m){
		Zen.drawImage("explosion.png", x - Game.screenXPos - 50, y - Game.screenYPos - 50);
		m.setMapTile(new MapTile(x/SIZE, y/SIZE, "burntgrass", true));
		if(targetApple != null)targetApple.setTargetedBy(null);
		dead = true;
		
	}
	
	private boolean getClick(){
		if(Zen.getMouseState() == MouseEvent.MOUSE_RELEASED || Zen.getMouseState() == MouseEvent.MOUSE_CLICKED){
			mouseReleased = true;
		}
		if(mouseReleased && Zen.getMouseState() == MouseEvent.MOUSE_PRESSED){
			mouseReleased = false;
			return true;
		}
		return false;
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
	
	private void targetNearestApple(){
		int indexOfClosest = -1;
		int distOfClosest = 1000000;
		for(int i = 0; i < Game.appleList.length(); i++){
			if(Game.appleList.appleAt(i).isTargetedBy(null)){
				int newDist = Math.abs(Game.appleList.appleAt(i).getX() - x) + Math.abs(Game.appleList.appleAt(i).getY() - y);
				if(newDist < distOfClosest){
					indexOfClosest = i;
					distOfClosest = newDist;
				}
			}
		}
		if(indexOfClosest > -1){
			Game.appleList.appleAt(indexOfClosest).setTargetedBy(this);
			targetApple = Game.appleList.appleAt(indexOfClosest);
		}
	}
	
	private Barrel targetNearestBarrel(){
		int indexOfClosest = -1;
		int distOfClosest = 1000000;
		for(int i = 0; i < Game.barrelList.length(); i++){
			int newDist = Math.abs(Game.barrelList.barrelAt(i).getX() - x) + Math.abs(Game.barrelList.barrelAt(i).getY() - y);
			if(newDist < distOfClosest){
				indexOfClosest = i;
				distOfClosest = newDist;
			}
		}
		if(indexOfClosest > -1){
			return Game.barrelList.barrelAt(indexOfClosest);
		}	
		return null;
	}
	
	private void wander(Map m){
		if(System.currentTimeMillis() - timestamp > 1000){
			int tryX = this.x + (int)(Math.random() * 100 - 50);
			int tryY = this.y + (int)(Math.random() * 100 - 50);
			if(tryX > 0 && tryY > 0 && m.isLocationPassible(tryX, tryY)){
				path = AStar.Search(x,y, tryX, tryY);
			}
			this.timestamp = System.currentTimeMillis();
		}
	}
}
