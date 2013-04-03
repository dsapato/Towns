import java.awt.Color;
import java.awt.event.MouseEvent;



public class Button{
	private int x;
	private int y;
	private int width;
	private int height;
	private String title;
	private Color color;
	private boolean released = true;

	public Button(int xx, int yy, int w, int h, String title){
		this.x = xx;
		this.y = yy;
		this.width = w;
		this.height = h;
		this.title = title;
		color = Color.BLACK;
	}

	public boolean isHovered(){
		if(this.x + this.width > Zen.getMouseX() && this.x < Zen.getMouseX()){
			if(this.y + this.height > Zen.getMouseY() && this.y < Zen.getMouseY()){
				color = Color.DARK_GRAY;
				return true;
			}
		}
		color = Color.BLACK;
		return false;
	}	
	
	public boolean isClicked(){
		if(isHovered()){
			if(Zen.getMouseState() == MouseEvent.MOUSE_RELEASED || Zen.getMouseState() == MouseEvent.MOUSE_CLICKED){
				released = true;
			}
			if(released && Zen.getMouseState() == MouseEvent.MOUSE_PRESSED){
				released = false;
				return true;
			}
		}
		return false;
	}
	
	public void draw(){
		Zen.setColor(color);
		Zen.fillRect(x, y, width, height);
		Zen.setColor(Color.WHITE);
		Zen.drawText(title, x, y + height*3/4);
	}
}
