
public class GUI {
	static Button newGuyButton;
	static Button placeWallButton;
	static Button placeFloorButton;
	static Button placeDoorButton;
	static Button placeBedButton;
	static Button placeBarrelButton;
	static Button clearButton;
	static Button doneButton ;
	
	
	public static void init(){
		newGuyButton = new Button(Zen.getZenWidth() - 90, Zen.getZenHeight() - 30, 60, 20, "New Guy");
		placeWallButton = new Button(Zen.getZenWidth() - 90, Zen.getZenHeight() - 60, 80, 20, "Place Wall");
		placeFloorButton = new Button(Zen.getZenWidth() - 90, Zen.getZenHeight() - 90, 80, 20, "Place Floor");
		placeDoorButton = new Button(Zen.getZenWidth() - 90, Zen.getZenHeight() - 120, 80, 20, "Place Door");
		placeBedButton = new Button(Zen.getZenWidth() - 90, Zen.getZenHeight() - 150, 80, 20, "Place Bed");
		placeBarrelButton = new Button(Zen.getZenWidth() - 90, Zen.getZenHeight() - 180, 80, 20, "Place Barrel");
		clearButton = new Button(Zen.getZenWidth() - 90, Zen.getZenHeight() - 210, 80, 20, "Clear Land");
		doneButton = new Button(Zen.getZenWidth() - 90, Zen.getZenHeight() - 240, 80, 20, "Done Building");
	}
	
	public static void update(){
		if(newGuyButton.isClicked()){
			Game.guyList.setNumberOfGuys(Game.guyList.getNumberOfGuys() + 1);
		}
		else if(placeWallButton.isClicked()){
			Game.build.setEnabled(true);
			Game.build.setMaterial("woodwall");		
		}
		else if(placeFloorButton.isClicked()){
			Game.build.setEnabled(true);
			Game.build.setMaterial("woodfloor");
		}
		else if(placeDoorButton.isClicked()){
			Game.build.setEnabled(true);
			Game.build.setMaterial("door");
		}
		else if(placeBedButton.isClicked()){
			Game.build.setEnabled(true);
			Game.build.setMaterial("bed");
		}
		else if(placeBarrelButton.isClicked()){
			Game.build.setEnabled(true);
			Game.build.setMaterial("barrel");
		}
		else if(clearButton.isClicked()){
			Game.build.setEnabled(true);
			Game.build.setMaterial("grass");
		}
		else if(doneButton.isClicked()){
			Game.build.setEnabled(false);
		}	
	}
	
	public static void draw(){
		newGuyButton.draw();
		placeWallButton.draw();
		placeFloorButton.draw();
		placeDoorButton.draw();
		placeBedButton.draw();
		placeBarrelButton.draw();
		clearButton.draw();
		doneButton.draw();		
	}
}
