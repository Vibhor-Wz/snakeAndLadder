package com.project.snakeandladder;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.sun.tools.javac.util.Pair;

import java.util.HashMap;
import java.util.Map;




public class Snakes extends Table {
    private Board board;
    private double height;
    Map<Integer, Vector2> snake ;
    public Snakes(double height,Board board){

        this.board= board;
        this.height=height;
        drawSnakes();
        setTransform(true);


    }
    public Snakes(Board board) {
        this.board=board;
        initSnakes();
    }
    private void initSnakes(){

        snake= new HashMap<>();
        snake.put(0,new Vector2(99, 45));
        snake.put(1,new Vector2(70, 55));
        snake.put(2,new Vector2(52, 34));
        snake.put(3,new Vector2(36, 2));
        snake.put(4,new Vector2(95, 38));
        snake.put(5,new Vector2(49, 9));
        snake.put(6,new Vector2(79, 60));
        snake.put(7,new Vector2(56, 27));
        snake.put(8,new Vector2(40, 19));
        snake.put(9,new Vector2(44, 6));
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
        add(headTable).right().bottom().expand().fill().padTop(board.getPosition()[0].getMinWidth()*0.2f).padRight(board.getPosition()[0].getMinWidth()*0.25f).width(board.getPosition()[0].getMinWidth())/*.height(board.getPosition()[0].getMinHeight())*/.row();


        height = height-(board.getPosition()[0].getMinHeight()*0.2f)-(board.getPosition()[0].getMinHeight()*0.4f);
        if(height<1){
            height=board.getPosition()[0].getMinHeight()*0.8f;
        }

        long NumOfMidRequired= Math.round(height/(board.getPosition()[0].getMinHeight()*0.7));
        for (int i= 0;i<NumOfMidRequired+1;i++){

            Image medial = new Image(new Texture("SnakeAndLadder/Snake-Body.png"));
            medial.setColor(r,g,b,1);
            Table medialTable= new Table();
            medialTable.add(medial).width(board.getPosition()[0].getMinWidth()*0.5f).height(board.getPosition()[0].getMinHeight()*0.7f);
            add(medialTable).expand().fillY().padTop(-board.getPosition()[0].getMinHeight()*0.07f).row();

        }



        Image bottom = new Image(new Texture("SnakeAndLadder/Snake-Tail.png"));
        bottom.setColor(r,g,b,1);
        Table bottomTable= new Table();
        bottomTable.add(bottom).width(board.getPosition()[0].getMinWidth()*0.3f).height(board.getPosition()[0].getMinHeight()*0.4f);
        add(bottomTable).left().expandY().fillY().padTop(-board.getPosition()[0].getMinHeight()*0.15f).padLeft(board.getPosition()[0].getMinWidth()*0.2f);


    }
}
