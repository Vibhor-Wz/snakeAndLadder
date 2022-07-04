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

import java.util.Random;

import Helpers.GameInfo;
import Helpers.Huds;

public class GameMain extends Game{
	SpriteBatch batch;

	public static Stage stage;

	public static int roll;
	public static int movesLeft;
	private int playerTurn;
	public static final int MAX_PLAYERS = 2;


	@Override
	public void create () {
		movesLeft=18;
		roll=new Random().nextInt(6)+1;
		batch = new SpriteBatch();
		stage= new Stage();

		playerTurn = 1;
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

	private void rollDice(){
		Random random= new Random();
		roll= random.nextInt(6)+1;
	}

	public void changePlayerTurn(){
		playerTurn++;
		if(playerTurn > MAX_PLAYERS){
			playerTurn = 1;
		}
		rollDice();
		if(playerTurn==1){
			Huds.diceRollPlayer1.setText(roll);
		}
		else {
			Huds.diceRollPlayer2.setText(roll);
		}
	}

	public int getPlayerTurn() {
		return playerTurn;
	}
	public int getRoll() {
		return roll;
	}
}
