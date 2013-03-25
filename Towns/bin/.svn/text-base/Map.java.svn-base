import java.awt.Color;
import java.awt.image.BufferedImage;


public class Map {
	private MapTile[][] map;
	private int[][] mapColors;
	
	//Constructors
	public Map(int w, int h){
		int width = w/10;
		int height = h/10;
		map = new MapTile[width][height];
		mapColors = new int[width][height];
		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map[0].length; j++)
				map[i][j] = new MapTile(i*10,j*10,Color.GREEN);
		}
	}
	
	public void draw(){
		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map[0].length; j++){
				mapColors[i][j] = map[i][j].color.getRGB();
			}
		}
		BufferedImage img = new BufferedImage(0, 0, 0);
		Zen.copyArrayToImage(mapColors, img);
		Zen.drawImage(img, 0, 0);
	}
}
