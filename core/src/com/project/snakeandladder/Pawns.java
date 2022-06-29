package com.project.snakeandladder;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import Helpers.GameInfo;

public class Pawns {
    public Table pawnImgTbl;
    private int position;
    PawnAndPlayerType pawnType;
    Table pawn;

    public Pawns(PawnAndPlayerType pawnType){
        pawnImgTbl= new Table();
        Image pawnImg = new Image(new Texture(pawnType+".png"));
        pawnImgTbl.add(pawnImg).width(GameInfo.WIDTH * 0.05f).height(GameInfo.HEIGHT * 0.05185f);
        pawnImgTbl.align(Align.center);
        this.position= 0;
        this.pawnType = pawnType;

    }
    public void updatePosition(int diceRollNumber){
        position+=diceRollNumber;
    }
    public int getPosition() {
        return position;
    }


}
