public class BarrelList {
	//Variables
	private Barrel[] array;
	private int totalApples;
	
	//Constructors
	public BarrelList(){
		array = new Barrel[0];
	}
	
	//Setters and Getters
	public int length(){
		return array.length;
	}
	public int getTotalApples() {
		return totalApples;
	}
	public void setTotalApples(int totalApples) {
		this.totalApples = totalApples;
	}
	public Barrel barrelAt(int index){
		return array[index];
	}
	
	//Functions
	public void add(Barrel b){
		Barrel[] temp = new Barrel[array.length + 1];
		for(int i = 0; i < array.length; i++){
			temp[i] = array[i];
		}
		temp[temp.length - 1] = b;
		array = temp;
	}
	
	public void removeBarrelAt(int a){
		Barrel[] temp = new Barrel[array.length - 1];
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
	
	public void update(){
		//totalApples = 0;
		//for(int i = 0; i < array.length; i++){
		//	totalApples += array[i].getAppleCount();
		//}
	}
	
	public void drawAll(){
		Zen.drawText("Apples Stored: " + totalApples, Zen.getZenWidth() - 150, 20);
	}
}
