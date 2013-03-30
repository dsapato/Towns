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
	private MapTile spawn;
	
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
	
	public MapTile[][] getMap() {
		return map;
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
	
	public MapTile getSpawn() {
		return spawn;
	}

	public void setSpawn(MapTile spawn) {
		this.spawn = spawn;
	}

	public void setMapTile(MapTile m){
		map[m.getX()][m.getY()] = m;
		redrawTile(m);
	}

	//Functions
	public void create(){

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

		Zen.copyArrayToImage(mapColors, img, Game.screenXPos, Game.screenYPos);
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
					if(Math.random() > .1)
						map[i][j] = new MapTile(i,j, "grass", true);
					else{
						map[i][j] = new MapTile(i,j, "tree", false);
					}
				}
			}
		}					
		//Special tiles
		placeHome();
		placeBarrel();
		placeSpawn();
		
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
				home = new MapTile(tryX,tryY, "grass", true);
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
				barrel = new MapTile(tryX,tryY, "grass", true);
				map[tryX][tryY] = barrel;
				return;
			}
		}
	}
	private void placeSpawn(){
		while(true){
			int tryX =(int)(Math.random() * map.length);
			int tryY =(int)(Math.random() * map[0].length);
			if(map[tryX][tryY].isPassible()){
				spawn = new MapTile(tryX,tryY, "grass", true);
				map[tryX][tryY] = spawn;
				return;
			}
		}
	}
	
	public boolean isLocationPassible(int x, int y){
		if(x/TILESIZE >= map.length || y/TILESIZE >= map[0].length){
			return false;
		}
		return map[x/TILESIZE][y/TILESIZE].isPassible();
	}
	
	public void update(){
		
	}
	
	public void redrawTile(MapTile m){
		BufferedImage tile = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);
		try {
			tile = ImageIO.read(new File("C:\\Users\\Danny\\Towns\\Towns\\Resources\\First Tiles\\" + m.getType() + ".png"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		for(int k = 0; k < TILESIZE; k++){
			for(int l = 0; l < TILESIZE; l++){
				if(img != null)
					img.setRGB(m.getX() * TILESIZE + k, m.getY() * TILESIZE + l, tile.getRGB(k, l));
			}
		}
	}
}
