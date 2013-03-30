import java.util.ArrayList;
import java.util.List;

public class AStar {
	private static final int TILESIZE = 20;
	
	private static MapTile[][] map;
	
	public static List<MapTile> Search(int startX, int startY, int goalX, int goalY, Map m){
		map = m.getMap();
		List<MapTile> open = new ArrayList<MapTile>();
		List<MapTile> closed = new ArrayList<MapTile>();
		MapTile start = new MapTile(startX / TILESIZE, startY / TILESIZE, "grass", true);
		MapTile goal = new MapTile(goalX / TILESIZE, goalY / TILESIZE, "grass", true);
		goal.setPassible(map[goal.getX()][goal.getY()].isPassible());
		if(goal.isPassible()){
			open.add(start);
			
			while(open.size() > 0){
				//Find lowest f, this is the current MapTile
				int lowestIndex = 0;
				for(int i = 0; i < open.size(); i++){
					if(open.get(i).getF() < open.get(lowestIndex).getF()){
						lowestIndex = i;
					}
				}
				MapTile currentMapTile = open.get(lowestIndex);
				
				//End Case, Path has been found
				if(currentMapTile.x == goal.x && currentMapTile.y == goal.y){
					List<MapTile> path = new ArrayList<MapTile>();
					while(currentMapTile.getParentTile() != null){
						path.add(currentMapTile);
						currentMapTile = currentMapTile.getParentTile();
					}
					//End of function
					return path;
				}
				
				//Normal Case, Continue searching
				open.remove(currentMapTile);
				closed.add(currentMapTile);
				List<MapTile> neighbors = FindNeighbors(currentMapTile);
				for(int i = 0; i < neighbors.size(); i++){
					MapTile neighbor = neighbors.get(i);
					if(closed.contains(neighbor) || !neighbor.isPassible()){
						//This one fails, move on
						continue;
					}
					//Check g score, and make sure its the best so far
					int gScore = currentMapTile.getG() + 1;
					boolean gScoreIsBest = false;
					
					if(!open.contains(neighbor)){
						//First time found MapTile, compute h
						gScoreIsBest = true;
						neighbor.setH(FindH(neighbor.x, neighbor.y, goal.x, goal.y));
						//Zen.setColor(Color.RED);
						//Zen.fillOval(neighbor.getX() * 20, neighbor.getY() * 20, 20, 20);
						open.add(neighbor);
					}
					else if(gScore < neighbor.getG()){
						//Already saw MapTile, but g score was worse before
						gScoreIsBest = true;
					}
					
					if(gScoreIsBest){
						//Found optimal path so far. Store info
						neighbor.setParentTile(currentMapTile);
						neighbor.setG(gScore);
						neighbor.setF(neighbor.getG() + neighbor.getH());
						
					}
				}
			}
			//No path found
			Zen.drawText("No path found.",Zen.getZenWidth() - 200, 20);
			return null;
		}
		else{
		//Goal is not passible
		Zen.drawText("End point is not passible.",Zen.getZenWidth() - 200, 20);
		return null;
		}
	}
	
	public static List<MapTile> FindNeighbors(MapTile curMapTile){
		List<MapTile> curNeighbors = new ArrayList<MapTile>();
		
		int x = curMapTile.x;
		int y = curMapTile.y;
		
		if(x-1 > 0) {
			curNeighbors.add(map[x-1][y]);
		}
		if(x+1 < map.length) {
			curNeighbors.add(map[x+1][y]);
		}
		if(y-1 > 0) {
			curNeighbors.add(map[x][y-1]);
		}
		if(y+1 < map[0].length) {
			curNeighbors.add(map[x][y+1]);
		}
		
		return curNeighbors;
	}
	public static int FindH(int x1, int x2, int y1, int y2){
		//Manhattan Distance
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}
}
