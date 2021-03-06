package scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import utilities.Config;
import utilities.ClientRender;
import utilities.Resources;

public class HudScene {
	public Stage stage;
	private OrthographicCamera camara;
	public Viewport viewport;
	private Table table;
	private Skin skin;
	private Label score;
	private ProgressBar hpBar;
	private ProgressBar bulletBar;
	private float timer;

	public HudScene() {
		camara = new OrthographicCamera();
		viewport = new FitViewport(Config.WIDTH,Config.HEIGHT,camara);
	
		stage = new Stage(viewport);
		// set Skin, file that contains atlas from the sprites, styles and etc
		skin = new Skin(Gdx.files.internal(Resources.SCENESKIN));

		table = new Table();
		table.setFillParent(true);

		score = new Label("ScoreBoard: 0", skin);
		score.setAlignment(Align.topLeft);
		table.add(score).expandX().align(Align.topLeft);
		table.row();

		bulletBar = new ProgressBar(-5.0f, 100.0f, 1.0f, false, skin, "BulletBar");
		bulletBar.setValue(100.0f);
		
		table.add(hpBar).padBottom(16.0f).expandY().align(Align.bottom).maxWidth(70.0f);

		table.row();
		hpBar = new ProgressBar(0.0f, 100.0f, 1.0f, false, skin, "hpBar");
		hpBar.setValue(100.0f);
		
		table.add(bulletBar).expandX().align(Align.bottom);
		stage.addActor(table);
	}

	public void draw() {
		timer += Config.delta;
		ClientRender.batch.begin();
		try {
			stage.act();
		}catch(Exception e){
			
		}
		cdBullet();
		stage.draw();
		ClientRender.batch.end();
	}
	public void changeLife(float value) {
		hpBar.setValue(value);
	}
	public void cdBullet() {
		if(bulletBar.getValue()<100) {
			bulletBar.setValue(bulletBar.getValue()+1);
		}
	}
	public void cannonFired() {
		bulletBar.setValue(0);
	}
}
