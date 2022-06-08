package com.project.snakeandladder;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import Helpers.GameInfo;
import Helpers.Huds;

public class GameMain extends Game {
	SpriteBatch batch;

	private Stage stage;
	private Board board;
	int r= 0;

	@Override
	public void create () {
		batch = new SpriteBatch();
		stage= new Stage(new FitViewport(GameInfo.WIDTH,GameInfo.HEIGHT));
		final Table container = new Table();
		container.setSize(GameInfo.WIDTH,GameInfo.HEIGHT);

		Stack stack= new Stack();
		stack.setSize(GameInfo.WIDTH,GameInfo.HEIGHT);

		Table hudsContainer = new Table();
		hudsContainer.setSize(GameInfo.WIDTH,GameInfo.HEIGHT);
		final Table boardTable= new Table();
		boardTable.top().padTop(GameInfo.HEIGHT * 0.1f);
		board = new Board();
		boardTable.add(board).expandX().fillX();

		Huds huds= new Huds();

		hudsContainer.add(huds).top().expandX().fillX().expandY().fillY();
		stack.add(hudsContainer);
		container.add(stack).expand().fill();
		stack.add(boardTable);
		boardTable.pack();

		stage.addActor(container);

		Gdx.input.setInputProcessor(stage);
		board.initLadder();

//		Gdx.app.postRunnable(new Runnable() {
//			@Override
//			public void run() {
//				container.invalidate();
//
////				board.initSnake();
//			}
//		});

//		Timer.schedule(new Timer.Task() {
//			@Override
//			public void run() {
//				Gdx.app.postRunnable(new Runnable() {
//					@Override
//					public void run() {
//						board.initLadder();
//					}
//				});
//			}
//		},2.0f);





	}

	@Override
	public void render () {
		ScreenUtils.clear(Color.valueOf("#0d4345"));

		stage.act();
		stage.draw();

//		if(Gdx.input.justTouched()){
//
//				board.movePawnBy(r);
//				r++;
//
//
//		}

	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
