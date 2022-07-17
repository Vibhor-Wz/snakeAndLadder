package com.project.snakeandladder;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;

import Helpers.GameInfo;

public class Pawns extends Image{


    private int position;
    PawnAndPlayerType pawnType;
    private final int pawnId;

//    private RepeatAction turnAction;



    public Pawns(PawnAndPlayerType pawnType, int pawnId){
        super(new Texture("SnakeAndLadder/"+pawnType+".png"));
        setSize(GameInfo.WIDTH * 0.05f,GameInfo.HEIGHT * 0.05185f);
        this.pawnId=pawnId;
        setAlign(Align.center);
//        turnAction = Actions.forever(Actions.sequence(Actions.scaleBy(0.1f, 0.1f,1),
//                                        Actions.scaleBy(-0.1f, -0.1f,1)));
        this.position= 0;
        this.pawnType = pawnType;

    }


    public int getPosition() {
        return position;
    }
    public void setPositionOnBoard(int position) {
        this.position = position;

    }

//    public RepeatAction getTurnAction() {
//        return turnAction;
//    }
//
//    public void setTurnAction(RepeatAction turnAction) {
//        this.turnAction = turnAction;
//    }


    public int getPawnId() {
        return pawnId;
    }

}
