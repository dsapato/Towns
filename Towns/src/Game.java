import java.awt.Color;


public class Game {
	
	static int screenXPos = 0;
	static int screenYPos = 0;
	private static final int scrollSpeed = 2;
	
	static final int mapWidth = 2000;
	static final int mapHeight = 2000;
	static final int TILESIZE = 20;
	static int spawnX;
	static int spawnY;
	private static boolean paused = false;
	
	static Map map = new Map(mapWidth, mapHeight);
	static Build build = new Build();
	static GuyList guyList = new GuyList();
	static BarrelList barrelList = new BarrelList();
	static AppleList appleList = new AppleList();
	static BedList bedList = new BedList();

	public static void main(String[] args) {
		
		//Objects
		map.create();
		spawnX = map.getSpawn().getX() * TILESIZE; spawnY = map.getSpawn().getY() * TILESIZE;
		screenXPos = spawnX - Zen.getZenWidth()/2; screenYPos = spawnY - Zen.getZenHeight()/2;
		GUI.init();
		
		//FPS Variables
		int frames = 0;
		long now;
		long framesTimer = 0;
		int FPS = 0;
		
		while(Zen.isRunning()){	
			//Check keys
			CheckKeys();
				
			//Background		
			map.update();
			Zen.drawImage(map.getMapImg(), -screenXPos, -screenYPos);
				
			//Update
			if(!paused)guyList.doYourThingAll();
			appleList.update();
			barrelList.update();
			build.update();
			
			//CheckCollisions
			guyList.checkCollions();
			appleList.checkBarrelCollision();
				
			//Buttons
			GUI.update();
					
			//Draw
			guyList.drawAll();
			appleList.drawAll();
			barrelList.drawAll();
			build.draw();
			GUI.draw();
			
			if(paused){
				Zen.setColor(Color.WHITE);
				Zen.setFont("Helvetica-40");
				Zen.drawText("Paused", Zen.getZenWidth()/2 - 90, Zen.getZenHeight()/2);
				Zen.setFont("Helvetica-14");				
			}
				
			//FPS Counter
			Zen.drawText("FPS: " + FPS, 10, 20);
			frames++;
			now = System.currentTimeMillis();
			if(now - framesTimer > 1000){
				framesTimer = System.currentTimeMillis();
				FPS = frames;
				frames = 0;
			}
				
			Zen.flipBuffer();	
		}
	}
	
	static public void CheckKeys(){
		//Check for escape
		if(Zen.isKeyPressed((char)27)){
			Zen.closeWindow();
			System.exit(0);
		}	
		
		//Pause
		if(Zen.isKeyPressed(' ')){
			paused = !paused;
		}		
		
		//Arrows
		if(Zen.isKeyPressed('a')){//Left
			screenXPos -= scrollSpeed;
		}
		else if(Zen.isKeyPressed('d')){//Right
			screenXPos += scrollSpeed;
		}
		if(Zen.isKeyPressed('w')){//Up
			screenYPos -= scrollSpeed;
		}
		else if(Zen.isKeyPressed('s')){//Down
			screenYPos += scrollSpeed;
		}
		//Bounds
		if(screenXPos < 0){
			screenXPos = 0;
		}
		else if(screenXPos + Zen.getZenWidth() > mapWidth){
			screenXPos = mapWidth - Zen.getZenWidth();
		}
		if(screenYPos < 0){
			screenYPos = 0;
		}
		else if(screenYPos + Zen.getZenHeight() > mapHeight){
			screenYPos = mapHeight - Zen.getZenHeight();
		}
	}

}
