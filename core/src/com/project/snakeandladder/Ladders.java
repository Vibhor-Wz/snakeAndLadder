package com.project.snakeandladder;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import java.util.HashMap;
import java.util.Map;

import Helpers.GameInfo;

public class Ladders extends Table {

    Board board;
    double height;
    Map<Integer, Vector2> ladder;
    public Ladders(double height){

        board= new Board();
        this.height=height;
        drawLadder();
        setTransform(true);
    }
    public Ladders() {
        initLadder();
    }

    private void initLadder() {
        ladder= new HashMap<>();
        ladder.put(0,new Vector2(28, 75));
        ladder.put(1,new Vector2(3, 97));
        ladder.put(2,new Vector2(46, 87));
        ladder.put(3,new Vector2(50, 88));
        ladder.put(4,new Vector2(13, 31));
        ladder.put(5,new Vector2(39, 81));
        ladder.put(6,new Vector2(17, 45));
        ladder.put(7,new Vector2(7, 48));
        ladder.put(8,new Vector2(5, 58));
        ladder.put(9,new Vector2(30, 72));
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
