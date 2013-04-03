
public class AppleList {
	private Apple[] array;
	
	public AppleList(){
		array = new Apple[0];
	}
	
	public int length(){
		return array.length;
	}
	
	public Apple appleAt(int index){
		return array[index];
	}
	
	public void add(Apple a){
		Apple[] temp = new Apple[array.length + 1];
		for(int i = 0; i < array.length; i++){
			temp[i] = array[i];
		}
		temp[temp.length - 1] = a;
		array = temp;
	}
	
	public void removeAppleAt(int a){
		Apple[] temp = new Apple[array.length - 1];
		for(int i = 0, cnt = 0; i < array.length; i++, cnt++){
			if(i != a){
				temp[cnt] = array[i];
			}
			else{
				cnt--;
			}
		}
		array = temp;		
	}
	
	public void drawAll(){
		for(int i = 0; i < array.length; i++){
			array[i].draw();
		}
	}
	
	public void checkBarrelCollision(){
		for(int i = 0; i < array.length; i++){
			if(array[i].checkBarrelCollision()){
				removeAppleAt(i);
			}
		}
	}
	
	public void update(){
		if(array.length < 15){
			int tryX = (int)(Math.random()* Game.mapWidth);
			int tryY = (int)(Math.random()* Game.mapHeight);
			if(Game.map.isLocationPassible(tryX, tryY)){
				this.add(new Apple(tryX,tryY));
			}
		}
		for(int i = 0; i < array.length; i++){
			array[i].update();
			if(array[i].getX() < 0){removeAppleAt(i);}
		}
	}
}
