

public class BedList {
	//Variables
	private Bed[] array;
	
	//Constructors
	public BedList(){
		array = new Bed[0];
	}
	
	//Setters and Getters
	public int length(){
		return array.length;
	}
	
	public Bed bedAt(int index){
		return array[index];
	}
	
	//Functions
	public void add(Bed b){
		Bed[] temp = new Bed[array.length + 1];
		for(int i = 0; i < array.length; i++){
			temp[i] = array[i];
		}
		temp[temp.length - 1] = b;
		array = temp;
	}
	
	public void removeBedAt(int a){
		Bed[] temp = new Bed[array.length - 1];
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
	
	public void assignBeds(){
		for(int i = 0; i < array.length && i < Game.guyList.length(); i++){
			Game.guyList.guyAt(i).setMyBed(array[i]);
		}
	}
}
