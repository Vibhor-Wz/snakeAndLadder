package com.project.snakeandladder;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.utils.Timer;

import java.util.Random;

import Helpers.Huds;

public class GameMain extends Game{
	SpriteBatch batch;

	public static Stage stage;

	public static int roll;
	public static int movesLeft;


	private int playerTurn;
	public static final int MAX_PLAYERS = 2;
	public static GameMain game;
	RepeatAction action;

	@Override
	public void create () {
		game=this;
		roll=new Random().nextInt(6)+1;
		batch = new SpriteBatch();
		stage= new Stage();
		action = Actions.forever(Actions.sequence(Actions.scaleBy(0.3f, 0.3f,0.02f),
				Actions.scaleBy(-0.3f, -0.3f,0.02f)));


		setScreen(new MainMenuScreen(stage,this));
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

	public void changePlayerTurn() {
		playerTurn++;

		if (playerTurn > MAX_PLAYERS) {
			playerTurn = 1;
		}
					rollDice();
					if (playerTurn == 1) {
						Huds.diceRollPlayer1.setText(roll);
					} else {
						Huds.diceRollPlayer2.setText(roll);
					}
			}



	public int getPlayerTurn() {
		return playerTurn;
	}

	public void setPlayerTurn(int playerTurn) {
		this.playerTurn = playerTurn;

	}

	public int getRoll() {
		return roll;
	}
}
