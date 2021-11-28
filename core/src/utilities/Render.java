package utilities;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.bws.tanks.Tanks;

import elements.Buff;
import elements.Projectile;
import elements.Tank;
import input.Player;


public abstract class Render {
	public static SpriteBatch batch;
	public static Tanks app;
	public static World world;
	public static Player player;
	public static ArrayList<Tank> tanks = new ArrayList<Tank>();
	public static ArrayList<Sprite> renderList = new ArrayList<Sprite>();
	int renderID;

	public static void render(){ //Render everything in the renderList
		for(int i=0; i<renderList.size(); i++){
			if(renderList.get(i) != null) {
				renderList.get(i).draw(batch);
				try {
					//before it takes that the Sprite is intance of The class we want to ask, so it doesnt cast a buff as a projectile.
					// here it takes if a projectile is exploded or a buff picked so  it disappear, cause
					// we cant disappear it at the moment of the world listener cause the game crashes because the world2d is locked.
				
					if(renderList.get(i) instanceof Buff && ((Buff) renderList.get(i)).isPicked()) {
						
						((Buff) renderList.get(i)).disappear();;
						renderList.remove(i);
						
					}else if(renderList.get(i) instanceof Projectile && ((Projectile) renderList.get(i)).isExploded()) {
						((Projectile) renderList.get(i)).disappear();
						renderList.remove(i);
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
			}else  {
				renderList.remove(i);
			}
			
		}
	}
	public static void disposeList(){
		for(int i=0; i<renderList.size(); i++){
			if(renderList.get(i) != null) {
				renderList.get(i).getTexture().dispose();;
			}else {
				renderList.remove(i);
			}
		}
	}
	
	public static void cleanScreen() {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	public static void addSprite(Sprite sprite) {
		renderList.add(sprite);
	}
	
}
