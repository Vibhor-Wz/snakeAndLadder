package com.project.snakeandladder;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import Helpers.GameInfo;

public class Ladders extends Table {

    Board board;
    double height;
    public Ladders(double height){
        board= new Board();
        this.height=height;
        drawLadder();
        setTransform(true);
    }
    void drawLadder(){

        setOrigin(Align.center);
        Image head= new Image(new Texture("SnakeAndLadder/Stairs-top.png"));
        Table headTable= new Table();
        headTable.add(head).width(board.getPosition()[0].getMinWidth()*0.8f).height(board.getPosition()[0].getMinHeight()*0.2f);
        add(headTable).row();


        height = height-(2*board.getPosition()[0].getMinHeight()*0.2f);
        if(height<1){
            height=board.getPosition()[0].getMinHeight()*0.5f;
        }

        long NumOfMidRequired= Math.round(height/(board.getPosition()[0].getMinHeight()*0.5f));
        for (int i= 0;i<NumOfMidRequired;i++){
            Image medial = new Image(new Texture("SnakeAndLadder/Stairs.png"));
            Table medialTable= new Table();
            medialTable.add(medial).width(board.getPosition()[0].getMinWidth()*0.8f).height(board.getPosition()[0].getMinHeight()*0.5f);
            add(medialTable).expandY().fillY().padTop(-board.getPosition()[0].getMinHeight()*0.018f).row();

        }


        TextureRegion textureRegion = new TextureRegion(new Texture("SnakeAndLadder/Stairs-top.png"));
        textureRegion.flip(false, true);

        Image bottom = new Image(textureRegion);
        Table bottomTable= new Table();
        bottomTable.add(bottom).width(board.getPosition()[0].getMinWidth()*0.8f).height(board.getPosition()[0].getMinHeight()*0.2f);
        add(bottomTable).expandY().fillY().padTop(-board.getPosition()[0].getMinHeight()*0.018f);


    }
}
