package com.project.snakeandladder;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Scaling;
import com.project.snakeandladder.gameover.GameOverDialog;

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
	public static GameMain game;
	RepeatAction action;

	@Override
	public void create () {
		game=this;
//		movesLeft=18;
		roll=new Random().nextInt(6)+1;
		batch = new SpriteBatch();
		stage= new Stage();
		action = Actions.forever(Actions.sequence(Actions.scaleBy(0.1f, 0.1f,1),
				Actions.scaleBy(-0.1f, -0.1f,1)));

//		playerTurn = 1;

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
		float scaling1=0.0f;
		float scaling2=0.0f;

		if (playerTurn > MAX_PLAYERS) {
			playerTurn = 1;
		}

		rollDice();
		if (playerTurn == 1) {
			Huds.diceRollPlayer1.setText(roll);
//			for (Pawns p1:Huds.greenPawnPlayer.pawns){
////				p.setSize(GameInfo.WIDTH * 0.05f,GameInfo.HEIGHT * 0.05185f);
//				if(p1.getHeight()>GameInfo.HEIGHT * 0.05185f ) {
//					scaling1 = (1 - (GameInfo.HEIGHT * 0.05185f) / (p1.getHeight())) ;
//					p1.scaleBy(-scaling1);
//
//				}
//			}
		} else {
			Huds.diceRollPlayer2.setText(roll);
//			for (Pawns p:Huds.bluePawnPlayer.pawns){
////				p.setSize(GameInfo.WIDTH * 0.05f,GameInfo.HEIGHT * 0.05185f);
//				if(p.getHeight() > GameInfo.HEIGHT * 0.05185f) {
//					scaling2 = (1 - (GameInfo.HEIGHT * 0.05185f) / (p.getHeight())) ;
//					p.scaleBy(-scaling2);
//				}
//			}
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
