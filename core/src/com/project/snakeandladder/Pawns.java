package com.project.snakeandladder;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import Helpers.GameInfo;

public class Pawns {
    public Table pawn;


    private int position;
    PawnAndPlayerType pawnType;



    public Pawns(PawnAndPlayerType pawnType){

        pawn= new Table();
        Image pawnImg = new Image(new Texture("SnakeAndLadder/"+pawnType+".png"));
        pawn.add(pawnImg).width(GameInfo.WIDTH * 0.05f).height(GameInfo.HEIGHT * 0.05185f);
        pawn.align(Align.center);
        this.position= 0;
        this.pawnType = pawnType;

    }

    public void updatePosition(int diceRollNumber){

        position += diceRollNumber;
    }
    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }



}
