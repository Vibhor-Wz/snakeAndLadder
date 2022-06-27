package com.project.snakeandladder;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

public class Snakes extends Table {
    Board board;
    double height;
    public Snakes(double height){
        board= new Board();
        this.height=height;
        drawSnakes();
        setTransform(true);
//        debugAll();

    }

    private void drawSnakes() {
        float r= (float) Math.random();
        float g= (float) Math.random();
        float b= (float) Math.random();
        setOrigin(Align.center);
        Image head= new Image(new Texture("SnakeAndLadder/Snake-Face.png"));
        head.setColor(r,g,b,1);
        Table headTable= new Table();
        headTable.right();
        headTable.add(head).width(board.getPosition()[0].getMinWidth()*0.3f).height(board.getPosition()[0].getMinHeight()*0.2f);
        add(headTable).right().bottom().expand().fill().padTop(-board.getPosition()[0].getMinWidth()*0.15f).padRight(board.getPosition()[0].getMinWidth()*0.14f).width(board.getPosition()[0].getMinWidth())/*.height(board.getPosition()[0].getMinHeight())*/.row();


        height = height-(board.getPosition()[0].getMinHeight()*0.2f)-(board.getPosition()[0].getMinHeight()*0.5f);
        if(height<1){
            height=board.getPosition()[0].getMinHeight()*0.8f;
        }

        long NumOfMidRequired= Math.round(height/(board.getPosition()[0].getMinHeight()*0.8));
        for (int i= 0;i<NumOfMidRequired+1;i++){

            Image medial = new Image(new Texture("SnakeAndLadder/Snake-Body.png"));
            medial.setColor(r,g,b,1);
            Table medialTable= new Table();
            medialTable.add(medial).width(board.getPosition()[0].getMinWidth()*0.5f).height(board.getPosition()[0].getMinHeight()*0.8f);
            add(medialTable).expand().fillY().padTop(-board.getPosition()[0].getMinHeight()*0.07f).row();

        }



        Image bottom = new Image(new Texture("SnakeAndLadder/Snake-Tail.png"));
        bottom.setColor(r,g,b,1);
        Table bottomTable= new Table();
        bottomTable.add(bottom).width(board.getPosition()[0].getMinWidth()*0.3f).height(board.getPosition()[0].getMinHeight()*0.5f);
        add(bottomTable).left().expandY().fillY().padTop(-board.getPosition()[0].getMinHeight()*0.07f).padLeft(board.getPosition()[0].getMinWidth()*0.14f);


    }
}
