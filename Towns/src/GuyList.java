
public class GuyList {
	private Guy[] array;
	private int numberOfGuys;
	
	public GuyList(){
		array = new Guy[0];
		numberOfGuys = 6;
	}	
	
	public int getNumberOfGuys() {
		return numberOfGuys;
	}

	public void setNumberOfGuys(int numberOfGuys) {
		this.numberOfGuys = numberOfGuys;
	}
	
	public int length(){
		return array.length;
	}
	
	public Guy guyAt(int index){
		return array[index];
	}

	public void add(Guy g){
		Guy[] temp = new Guy[array.length + 1];
		for(int i = 0; i < array.length; i++){
			temp[i] = array[i];
		}
		temp[temp.length - 1] = g;
		array = temp;
	}
	
	public void removeAt(int index){
		Guy[] temp = new Guy[array.length - 1];
		for(int i = 0, count = 0; i < array.length; i++, count++){
			if(i != index){	
				temp[count] = array[i];
			}
			else{
				count--;
			}
		}
		array = temp;
	}
	
	public void drawAll(){
		for(int i = 0; i < array.length; i++){
			array[i].draw();
		}
		Zen.setFont("Helvetica-14");
		Zen.drawText("Num Guys: " + numberOfGuys, 100, 20);
	}
	
	public void doYourThingAll(){
		if(array.length < numberOfGuys){
			this.add(new Guy(Game.spawnX, Game.spawnY, 4, (int)(Math.random()*4)));	//11
		}
		
		for(int i = 0; i < array.length; i++){
			array[i].doYourThing();
			if(array[i].isDead()){
				this.removeAt(i);
				numberOfGuys--;
			}
		}
	}
	
	public void checkCollions(){
		for(int i = 0; i < array.length; i++){
			array[i].checkBarrelCollision();
			array[i].checkBedCollision();
			for(int j = 0; j < Game.appleList.length(); j++){
				array[i].checkAppleCollision(Game.appleList.appleAt(j));
			}
		}
	}
}
