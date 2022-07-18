package com.project.snakeandladder.custom;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import Helpers.GameInfo;

public class PawnImage extends Image {
    Texture t;
    public PawnImage(Texture texture){
        super(texture);
        t= new Texture("glow.png");

    }

    private boolean isPlayerTurn;

    public void setPlayerTurn(boolean playerTurn) {
        isPlayerTurn = playerTurn;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(isPlayerTurn){

            batch.draw(t,this.getX(),this.getY(), GameInfo.WIDTH * 0.05f,GameInfo.HEIGHT * 0.05185f);

        }


    }
}
