import java.awt.Color;


public class MessAround {

	public static void main(String[] args) {
		
		//Map map = new Map(1370,770);
		//map.draw();
		GuyList guyList = new GuyList();
		for(int i = 0; i < 6; i++){
			guyList.add(new Guy((int)(Math.random()*200),(int)(Math.random()*700), 5, (int)(Math.random()*10)));
		}
		
		Home home = new Home(150,600);
		Barrel barrel = new Barrel(150,100);
		
		AppleList appleList = new AppleList();
		for(int i = 0; i < 15; i++){
			appleList.add(new Apple(500 + (int)(Math.random()*900), (int)(Math.random()*700)));
		}
		
		while(Zen.isRunning()){
			//Check for escape key to quit
			if(Zen.isKeyPressed((char)27)){
				Zen.closeWindow();
				System.exit(0);
			}
			
			//Background
			Zen.drawImage("grass.png", 0, 0, 1366, 768);
			
			//Move to mouse
			//int goalX = Zen.getMouseClickX();
			//int goalY = Zen.getMouseClickY();
			//dude.setGoal(goalX, goalY);
			
			//AI
			guyList.doYourThingAll(appleList, barrel, home);
			
			//Update AppleList
			appleList.update();
		
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
				Zen.waitForClick();
			}
			
			Zen.flipBuffer();
			Zen.sleep(10);
		}

	}

}
