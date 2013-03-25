import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.*;

public class Map {
	private final int TILESIZE = 20;
	int waterCount = 3;
	
	
	private MapTile[][] map;
	private int[][] mapColors;
	private BufferedImage img;
	
	private MapTile home;
	private MapTile barrel;
	
	//Constructors
	public Map(int width, int height){
		map = new MapTile[width / TILESIZE][height / TILESIZE];
		mapColors = new int[width][height];
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		generateMap();
	}
	
	//Getters, Setters
	public BufferedImage getMapImg() {
		return img;
	}
	
	public MapTile getHome() {
		return home;
	}

	public void setHome(MapTile home) {
		this.home = home;
	}

	public MapTile getBarrel() {
		return barrel;
	}

	public void setBarrel(MapTile barrel) {
		this.barrel = barrel;
	}

	//Functions
	public void draw(){

		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map[0].length; j++){
				BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
				try {
					img = ImageIO.read(new File("C:\\Users\\Danny\\Towns\\Towns\\Resources\\First Tiles\\" + map[i][j].getType() + ".png"));
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				for(int k = 0; k < TILESIZE; k++){
					for(int l = 0; l < TILESIZE; l++){
						if(img != null)
							mapColors[i*TILESIZE + k][j*TILESIZE + l] = img.getRGB(k, l);
					}
				}
			}
		}

		Zen.copyArrayToImage(mapColors, img);
		//Zen.drawImage(img, 0, 0);
	}
	


	private void generateMap(){
		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map[0].length; j++){
				
				//Water
				if(i == 0){
					changeWaterCount();
					for(int k = 0; k < waterCount; k++){
						map[i+k][j] = new MapTile(i+k,j, "water", false);
					}
				}
				else if(j == 0){
					changeWaterCount();
					for(int k = 0; k < waterCount; k++){
						map[i][j+k] = new MapTile(i,j+k, "water", false);
					}
				}
				else if(i == map.length - 1){
					changeWaterCount();
					for(int k = 0; k < waterCount; k++){
						map[i-k][j] = new MapTile(i-k,j, "water", false);
					}
				}
				else if(j == map[0].length - 1){
					changeWaterCount();
					for(int k = 0; k < waterCount; k++){
						map[i][j-k] = new MapTile(i,j-k, "water", false);
					}
				}	
				
				//Land
				else if(map[i][j] == null){
					//Grass
					map[i][j] = new MapTile(i,j, "grass", true);
				}
			}
		}					
		//Home
		placeHome();
		placeBarrel();
	}
	
	private void changeWaterCount(){
		double chance = Math.random(); 
		if(chance > .6 && waterCount < 5)waterCount++;
		else if(chance > .2 && waterCount > 1)waterCount--;
	}
	
	private void placeHome(){
		while(true){
			int tryX =(int)(Math.random() * map.length);
			int tryY =(int)(Math.random() * map[0].length);
			if(map[tryX][tryY].isPassible()){
				home = new MapTile(tryX,tryY, "grass", false);
				map[tryX][tryY] = home;
				return;
			}
		}
	}
	private void placeBarrel(){
		while(true){
			int tryX =(int)(Math.random() * map.length);
			int tryY =(int)(Math.random() * map[0].length);
			if(map[tryX][tryY].isPassible()){
				barrel = new MapTile(tryX,tryY, "grass", false);
				map[tryX][tryY] = barrel;
				return;
			}
		}
	}
	
	public boolean isLocationPassible(int x, int y){
		return map[x/TILESIZE][y/TILESIZE].isPassible();
	}
}
