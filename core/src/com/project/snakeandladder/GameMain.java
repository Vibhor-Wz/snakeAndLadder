package com.project.snakeandladder;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;

import Helpers.GameInfo;
import Helpers.Huds;

public class GameMain extends Game {
	SpriteBatch batch;
	Texture img;
	private Stage stage;
	private Board board;
	int r= 0;
	int iterate=0;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		stage= new Stage();
		Table container = new Table();
		container.setSize(GameInfo.WIDTH,GameInfo.HEIGHT);
//		board = new Board();
//		container.add(board);
		Huds huds= new Huds();
		container.add(huds).top().expandX().fillX().expandY().fillY();
		stage.addActor(container);

		Gdx.input.setInputProcessor(stage);

//		Gdx.app.postRunnable(new Runnable() {
//			@Override
//			public void run() {
//				board.initLadder();
////				board.initSnake();
//			}
//		});





	}

	@Override
	public void render () {
		ScreenUtils.clear(Color.valueOf("#0d4345"));
		batch.begin();

		batch.end();
		stage.act();
		stage.draw();

//		if(Gdx.input.justTouched()){
//			board.movePawnBy(r);
//			r++;
//		}

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
