package elements;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class ClientSprite extends Sprite {
	//Yes. We made an entire class just to add an int, because Marconi.
	int id;
	
	public ClientSprite() {
	}
	
	
	private void drawSprite(String sprite) { //sprite == rocketbuf
		
		//TODO: Do the texture here.
	}
	
	private void deleteSprite() {
		
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
}
