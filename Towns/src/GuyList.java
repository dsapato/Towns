
public class GuyList {
	private Guy[] array;
	
	public GuyList(){
		array = new Guy[0];
	}
	
	public void add(Guy g){
		Guy[] temp = new Guy[array.length + 1];
		for(int i = 0; i < array.length; i++){
			temp[i] = array[i];
		}
		temp[temp.length - 1] = g;
		array = temp;
	}
	
	public void drawAll(){
		for(int i = 0; i < array.length; i++){
			array[i].draw();
		}
	}
	
	public void doYourThingAll(AppleList a, Barrel b, Home h, Map m){
		for(int i = 0; i < array.length; i++){
			array[i].doYourThing(a, b, h, m);
		}
	}
	
	public void checkCollions(AppleList a, Barrel b, Home h){
		for(int i = 0; i < array.length; i++){
			array[i].checkBarrelCollision(b);
			array[i].checkHomeCollision(h);
			for(int j = 0; j < a.length(); j++){
				array[i].checkAppleCollision(a.appleAt(j));
			}
		}
	}	
}
