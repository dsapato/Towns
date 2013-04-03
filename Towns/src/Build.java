import java.awt.event.MouseEvent;


public class Build {
	private final int TILESIZE = 20;
	private boolean enabled = false;	
	private boolean released = false;
	private int x;
	private int y;
	private String material;
	//For dragging
	private int startX;
	private int startY;
	int minX;
	int minY;
	int maxX;
	int maxY;
	private boolean dragging = false;
	
	//Constructors
	public Build(){
		
	}
	
	//Setters/Getters
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	//Functions
	public void update(){
		if(enabled){
			x = Zen.getMouseX() / TILESIZE * TILESIZE;
			y = Zen.getMouseY() / TILESIZE * TILESIZE;
			
			if(Zen.getMouseX() < Zen.getZenWidth() - 90 || Zen.getMouseY() < Zen.getZenHeight() - 240){ //Doesn't apply to buttons
				if(getClick()){
					if(!dragging){ //First Click
						dragging = true;
						startX = x;
						startY = y;
					}
					else{			//Second Click
						dragging = false;
						for(int i = minX; i < maxX; i += TILESIZE){
							for(int j = minY; j < maxY; j += TILESIZE){
								if(Game.map.isLocationPassible(i + Game.screenXPos, j + Game.screenYPos) || material.equals("grass")){
									Game.map.setMapTile(new MapTile((i + Game.screenXPos) / TILESIZE, (j + Game.screenYPos) /TILESIZE, material, materialPassable()));
									if(material.equals("bed")){
										Game.bedList.add(new Bed(i + Game.screenXPos, j + Game.screenYPos));
										Game.bedList.assignBeds();
									}
									else if(material.equals("barrel")){
										Game.barrelList.add(new Barrel(i + Game.screenXPos, j + Game.screenYPos));
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void draw(){
		if(enabled){
			if(dragging){
				findMinMax();
				for(int i = minX; i < maxX; i += TILESIZE){
					for(int j = minY; j < maxY; j += TILESIZE){
						if(Game.map.isLocationPassible(i + Game.screenXPos, j + Game.screenYPos) || material.equals("grass")){
							Zen.drawImage("selectedtile.png", i - (Game.screenXPos % TILESIZE), j - (Game.screenYPos % TILESIZE), TILESIZE, TILESIZE);
						}
						else{
							Zen.drawImage("blockedtile.png", i - (Game.screenXPos % TILESIZE), j - (Game.screenYPos % TILESIZE), TILESIZE, TILESIZE);
						}
					}
				}
			}
			else{
				Zen.drawImage("selectedtile.png", x - (Game.screenXPos % TILESIZE), y - (Game.screenYPos % TILESIZE), TILESIZE, TILESIZE);
			}
		}
	}
	
	public boolean getClick(){
		if(Zen.getMouseState() == MouseEvent.MOUSE_RELEASED || Zen.getMouseState() == MouseEvent.MOUSE_CLICKED){
			released = true;
		}
		if(released && Zen.getMouseState() == MouseEvent.MOUSE_PRESSED){
			released = false;
			return true;
		}
		return false;
	}
	
	public boolean materialPassable(){
		if(material.equals("woodwall"))return false;
		else if(material.equals("woodfloor"))return true;
		else if(material.equals("door"))return true;
		else if(material.equals("bed"))return true;
		else if(material.equals("grass"))return true;
		else if(material.equals("barrel"))return true;
		
		return false;
	}
	
	public void findMinMax(){
		if(startX < x){minX = startX; maxX = x;}
		else if(x < startX){minX = x; maxX = startX + 1;}
		else{minX = x; maxX = x + 1;}
		if(startY < y){minY = startY; maxY = y;}
		else if(y < startY){minY = y; maxY = startY + 1;}
		else{minY = y; maxY = y + 1;}
	}
}
