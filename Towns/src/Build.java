import java.awt.event.MouseEvent;


public class Build {
	private final int TILESIZE = 20;
	private boolean enabled = false;	
	private boolean released = false;
	private int x;
	private int y;
	
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
	
	//Functions
	public void update(Map m){
		if(enabled){
			x = Zen.getMouseX() / TILESIZE * TILESIZE;
			y = Zen.getMouseY() / TILESIZE * TILESIZE;
			
			if(getClick()){
				m.setMapTile(new MapTile((x + Game.screenXPos) / TILESIZE, (y + Game.screenYPos) /TILESIZE, "barrel", false));
			}
		}
	}
	
	public void draw(){
		if(enabled){
			Zen.drawImage("selectedtile.png", x - (Game.screenXPos % TILESIZE), y - (Game.screenYPos % TILESIZE), TILESIZE, TILESIZE);
		}
	}
	
	public boolean getClick(){
		if(Zen.getMouseState() == MouseEvent.MOUSE_CLICKED){
			released = true;
		}
		if(released && Zen.getMouseState() == MouseEvent.MOUSE_PRESSED){
			released = false;
			return true;
		}
		return false;
	}
}
