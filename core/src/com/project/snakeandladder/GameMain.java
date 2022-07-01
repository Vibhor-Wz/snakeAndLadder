package com.project.snakeandladder;

import static com.project.snakeandladder.PawnAndPlayerType.BLUE;
import static com.project.snakeandladder.PawnAndPlayerType.GREEN;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;

import Helpers.GameInfo;
import Helpers.Huds;

public class GameMain extends Game{
	SpriteBatch batch;

	private Stage stage;

	Boolean player1Turn;


	@Override
	public void create () {
		batch = new SpriteBatch();
		stage= new Stage();
		player1Turn=true;
		setScreen(new GamePlay(this,stage));


		Gdx.input.setInputProcessor(stage);

	}

	@Override
	public void render () {
		super.render();


	}
	
	@Override
	public void dispose () {
		batch.dispose();
		stage.dispose();

	}

	public Boolean getPlayer1Turn() {
		return player1Turn;
	}

	public void setPlayer1Turn(Boolean player1Turn) {
		this.player1Turn = player1Turn;
	}

}
