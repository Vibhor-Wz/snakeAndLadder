package com.project.snakeandladder;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import Helpers.GameInfo;

public class Pawns extends Table{
//    public Table pawn;


    private int position;
    PawnAndPlayerType pawnType;



    private final int pawnId;



    public Pawns(PawnAndPlayerType pawnType, int pawnId){

//        pawn= new Table();
        this.pawnId=pawnId;
        Image pawnImg = new Image(new Texture("SnakeAndLadder/"+pawnType+".png"));
        add(pawnImg).width(GameInfo.WIDTH * 0.05f).height(GameInfo.HEIGHT * 0.05185f);
        align(Align.center);
        this.position= 0;
        this.pawnType = pawnType;

    }


    public int getPosition() {
        return position;
    }
    public void setPositionOnBoard(int position) {
        this.position = position;

    }

    public int getPawnId() {
        return pawnId;
    }

}
