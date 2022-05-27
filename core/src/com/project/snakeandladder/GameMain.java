package com.project.snakeandladder;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;

import Helpers.GameInfo;

public class GameMain extends Game {
	SpriteBatch batch;
	Texture img;
	private Stage stage;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		stage= new Stage();
		Table container = new Table();
		container.setSize(GameInfo.WIDTH,GameInfo.HEIGHT);
		container.add(new Board());
		stage.addActor(container);
		Gdx.input.setInputProcessor(stage);
//		container.debugAll();
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();

		batch.end();
		stage.act();
		stage.draw();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
