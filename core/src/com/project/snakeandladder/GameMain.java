package com.project.snakeandladder;

import static com.project.snakeandladder.PawnAndPlayerType.BLUE;
import static com.project.snakeandladder.PawnAndPlayerType.GREEN;

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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.project.snakeandladder.interfaces.PlayerInterface;

import Helpers.GameInfo;
import Helpers.Huds;

public class GameMain extends Game{
	SpriteBatch batch;

	private Stage stage;
	private Board board;
	int r= 0;
	private Players bluePawnPlayer;
	private Players greenPawnPlayer;
	private Huds huds;
	Boolean player1Turn;

	@Override
	public void create () {
		batch = new SpriteBatch();


		createScreen();
		bluePawnPlayer = new Players("Vibhor",board,BLUE);
		greenPawnPlayer = new Players("Jai",board,GREEN);

		Gdx.input.setInputProcessor(stage);

	}
	void createScreen(){
		stage= new Stage();
		final Table container = new Table();
		container.setSize(GameInfo.WIDTH,GameInfo.HEIGHT);

		Stack stack= new Stack();
		stack.setSize(GameInfo.WIDTH,GameInfo.HEIGHT);

		Table hudsContainer = new Table();
		hudsContainer.setSize(GameInfo.WIDTH,GameInfo.HEIGHT);
		board = new Board();


		huds= new Huds(board,bluePawnPlayer,greenPawnPlayer);

		board.pack();
		board.setPosition(GameInfo.WIDTH/2,GameInfo.HEIGHT*0.6f, Align.center);
		container.addActor(board);

		hudsContainer.add(huds).top().expandX().fillX().expandY().fillY();
		stack.add(hudsContainer);
		container.add(stack).expand().fill();


		stage.addActor(container);
	}

	@Override
	public void render () {
		ScreenUtils.clear(Color.valueOf("#0d4345"));

		stage.act();
		stage.draw();
		if(!board.isLadderInitialized())
			board.initLadder();
		if (!board.isSnakeInitialized())
			board.initSnake();


	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
