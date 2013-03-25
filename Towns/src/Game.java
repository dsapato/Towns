import java.awt.Color;

public class Game {

	public static void main(String[] args) {
		
		Map map = new Map(1370,770);
		map.draw();
		GuyList guyList = new GuyList();
		for(int i = 0; i < 6; i++){
			guyList.add(new Guy((int)(Math.random()*200),(int)(Math.random()*700), 5, (int)(Math.random()*10)));
		}
		
		Home home = new Home(map.getHome().x * 20,map.getHome().y * 20);
		Barrel barrel = new Barrel(map.getBarrel().x * 20, map.getBarrel().y * 20);
		AppleList appleList = new AppleList();
		
		while(Zen.isRunning()){
			
			//Check for escape key to quit
			if(Zen.isKeyPressed((char)27)){
				Zen.closeWindow();
				System.exit(0);
			}
			
			//Background			
			Zen.drawImage(map.getMapImg(), 0, 0);	
			
			//AI
			guyList.doYourThingAll(appleList, barrel, home, map);
			
			//Update AppleList
			appleList.update(map);
		
			//CheckCollisions
			guyList.checkCollions(appleList, barrel, home);
			appleList.checkBarrelCollision(barrel);
				
			//Draw
			guyList.drawAll();
			home.draw();
			barrel.draw();
			appleList.drawAll();
			
			
			//Pause
			if(Zen.isKeyPressed(' ')){
				Zen.setColor(Color.WHITE);
				Zen.setFont("Helvetica-40");
				Zen.drawText("Paused", Zen.getZenWidth()/2 - 30, Zen.getZenHeight()/2);
				Zen.waitForClick();
			}
			
			Zen.flipBuffer();
			Zen.sleep(10);
		}

	}

}
