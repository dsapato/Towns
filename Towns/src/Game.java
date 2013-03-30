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

	public static void main(String[] args) {
		
		//Objects
		Map map = new Map(mapWidth, mapHeight);
		map.create();
		spawnX = map.getSpawn().getX() * TILESIZE; spawnY = map.getSpawn().getY() * TILESIZE;
		screenXPos = spawnX - Zen.getZenWidth()/2; screenYPos = spawnY - Zen.getZenHeight()/2;
		Build build = new Build();
		GuyList guyList = new GuyList();
		Home home = new Home(map.getHome().x * TILESIZE,map.getHome().y * TILESIZE);
		Barrel barrel = new Barrel(map.getBarrel().x * TILESIZE, map.getBarrel().y * TILESIZE);
		AppleList appleList = new AppleList();
		Button newGuyButton = new Button(Zen.getZenWidth() - 70, Zen.getZenHeight() - 30, 60, 20, "New Guy");
		Button placeWallButton = new Button(Zen.getZenWidth() - 70, Zen.getZenHeight() - 60, 80, 20, "Place Wall");
	
		//Variables
		int frames = 0;
		long now;
		long framesTimer = 0;
		int FPS = 0;
		boolean paused = false;
		
		while(Zen.isRunning()){	
			//Check for escape
			if(Zen.isKeyPressed((char)27)){
				Zen.closeWindow();
				System.exit(0);
			}	
			//Check for unpause
			if(Zen.isKeyPressed(' ')){
				paused = false;
			}
			
			while(!paused){
				//Check for escape key to quit
				if(Zen.isKeyPressed((char)27)){
					Zen.closeWindow();
					System.exit(0);
				}
				
				//Check keys
				CheckKeys();
				
				//Background		
				map.update();
				Zen.drawImage(map.getMapImg(), -screenXPos, -screenYPos);
				
				//Update
				guyList.doYourThingAll(appleList, barrel, home, map);
				appleList.update(map);
				build.update(map);
			
				//CheckCollisions
				guyList.checkCollions(appleList, barrel, home);
				appleList.checkBarrelCollision(barrel);
				
				//Buttons
				if(newGuyButton.isClicked()){
					guyList.setNumberOfGuys(guyList.getNumberOfGuys() + 1);
				}
				if(placeWallButton.isClicked()){
					build.setEnabled(true);
				}
					
				//Draw
				guyList.drawAll();
				home.draw();
				barrel.draw();
				appleList.drawAll();
				newGuyButton.draw();
				placeWallButton.draw();
				build.draw();
				
				
				//Pause
				if(Zen.isKeyPressed(' ')){
					Zen.setColor(Color.WHITE);
					Zen.setFont("Helvetica-40");
					Zen.drawText("Paused", Zen.getZenWidth()/2 - 90, Zen.getZenHeight()/2);
					Zen.setFont("Helvetica-14");
					paused = true;
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

	}
	
	static public void CheckKeys(){
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
