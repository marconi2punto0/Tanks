package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import elements.ButtonText;
import input.Player;
import scenes.HudScene;
import tiledMapObjects.World2D;
import utilities.ClientRender;
import utilities.Config;
import utilities.Resources;

public class MapScreen implements Screen {

	private SpriteBatch b;

	// camera
	private OrthographicCamera camera;
	private Viewport gamePort;
	// define tiledMap vars
	private TmxMapLoader mapLoader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	// world
	private World world;
	// we have to make the world2d and objects here
	private World2D world2d;

	// hud
	private HudScene hud;
	// hull testing
	private Player localPlayer;
	//warning
	
	float time;

	public MapScreen(Player player) {

		///// NETWORK TEST
		localPlayer = player;
//		localPlayer.connect(ip, port);///// NETWORK TES

		///// setting the PIM AS InputProcessor
		Gdx.input.setInputProcessor(localPlayer.PIM);

		// setting map
		camera = new OrthographicCamera();
		// load tiledMap
		mapLoader = new TmxMapLoader();
		map = mapLoader.load(Resources.MAP1);

		// order the render which map is going to draw
		renderer = new OrthogonalTiledMapRenderer(map, 1 / Config.PPM);

	}

	@Override
	public void show() {
		// set map properties
		ClientRender.world = new World(new Vector2(0, 0), true);
		world = ClientRender.world;

		// render which draws box2d Texture
		// then camera zoom
		gamePort = new FitViewport(((64 * 15) / Config.PPM), ((64 * 15) / Config.PPM), camera);
		// centers the camera to the new map
		camera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

		// creates 2dmap per layers
		world2d = new World2D(map);
		// working with tank
		b = ClientRender.batch;
		gamePort.getCamera().position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
		hud = new HudScene();
		hud.cannonFired();
	}

	@Override
	public void render(float delta) {

		update(delta);
		gamePort.apply();
		ClientRender.cleanScreen();
		b.setProjectionMatrix(camera.combined);
		// loads map
		renderer.render();
		// loads box2dDebugLines hitboxes
		// drawing

		
		ClientRender.render();
		
		// something happened when the hud was being drawd after the render.tank
		hud.draw();
	}

	private void update(float delta) {
		Config.delta = delta;
		camera.update();
		hud.viewport.apply();
		// 60 ticks in a second if im right
		world.step(1 / 60f, 6, 2);
		// set Camera on the players tank
//		camera.position.x = tank.hull.getX();
//		camera.position.y = tank.hull.getY();
		// sets whats the renderer gonna draw, that shows in camera
		renderer.setView(camera);
		time += delta;
		
	//testing
	}


	@Override
	public void resize(int width, int height) {
		gamePort.update(width, height);
		hud.stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		world.dispose();
		ClientRender.disposeList();
		hud.stage.dispose();

	}

}
