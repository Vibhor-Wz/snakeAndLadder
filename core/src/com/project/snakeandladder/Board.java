package com.project.snakeandladder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.DistanceFieldFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

import Helpers.Font;
import Helpers.GameInfo;

public class Board extends Table {

    Table table;
    Cell[] cells;


//    Array<Actor> pawns= new Array<>();

    public Board() {
        cells = new Cell[100];

        setName("SnakeAndLadderBoard");
        Image bg = new Image(new Texture("bg.png"));
        bg.setColor(Color.valueOf("#fafca8"));

        stack(bg, createBoard()).expandX().fillX().height(GameInfo.HEIGHT * 0.61f);
//        layout();

    }

    public void movePawnBy(int roll) {

        if (roll > 0) {
            Actor actor = cells[roll - 1].getActor();
            if (actor instanceof Stack) {
                Stack stack = (Stack) actor;
                if (stack.getChildren().size >= 2) {
                    final Actor pawn;
                    if (stack.getChildren().size == 2) {
                        Image pawnImg = new Image(new Texture("blue.png"));
                        Table t = new Table();
                        t.add(pawnImg).width(getWidth() * 0.05f).height(getHeight() * 0.085f);
                        pawn = t;
//                        pawns.add(pawn);

                    } else {
                        pawn = stack.getChild(2);
//                        pawns.add(pawn);
                    }
                    final Stack nextCell = (Stack) cells[roll].getActor();
                    pawn.remove();
//                    pawns.removeValue(pawn,true);
                    pawn.setPosition(stack.getX(),stack.getY());
                    addActor(pawn);
//                    pawns.add(pawn);
                    pawn.addAction(Actions.sequence(
                            Actions.moveTo(nextCell.getX(),nextCell.getY(),0.3f),
                            Actions.run(new Runnable() {
                                @Override
                                public void run() {
                                    nextCell.add(pawn);
//                                    pawns.add(pawn);
                                }
                            })
                    ));

                }
            } else {

            }
        } else {
            Actor actor = cells[roll].getActor();
            if (actor instanceof Stack) {
                Stack stack = (Stack) actor;

                Image pawnImg = new Image(new Texture("blue.png"));
                Table t = new Table();
                t.add(pawnImg).width(getWidth() * 0.05f).height(getHeight() * 0.085f);

                stack.add(t);
//                pawns.add(t);
            }
        }

    }


    private Table createBoard() {
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = getNormalFont(GameInfo.WIDTH * 0.01f);
        style.fontColor = Color.valueOf("#709104");


        int t = 100;
        table = new Table();
        table.top().padTop(GameInfo.HEIGHT * 0.004f).padBottom(GameInfo.HEIGHT * 0.008f);


        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {

                Label numLbl = new Label(String.valueOf(t), style);
                numLbl.setAlignment(Align.topLeft);
                if (i % 2 != 0) {
                    if (j % 2 != 0) {
                        Image cellImgEven = new Image(new Texture("cell.png"));
                        cellImgEven.setColor(Color.valueOf("#dff964"));//odd
                        numLbl.setName(String.valueOf(t));
                        cells[t - 1] = table.stack(cellImgEven, numLbl).width(GameInfo.WIDTH * 0.093f).height(GameInfo.HEIGHT * 0.057f).pad(GameInfo.WIDTH * 0.003f);
                    } else {
                        Image cellImgOdd = new Image(new Texture("cell.png"));
                        cellImgOdd.setColor(Color.valueOf("#b5e72c"));//odd
                        cells[t - 1] = table.stack(cellImgOdd, numLbl).width(GameInfo.WIDTH * 0.093f).height(GameInfo.HEIGHT * 0.057f).pad(GameInfo.WIDTH * 0.003f);
                        numLbl.setName(String.valueOf(t));
                    }
                } else {
                    if (j % 2 == 0) {
                        Image cellImgEven = new Image(new Texture("cell.png"));
                        cellImgEven.setColor(Color.valueOf("#dff964"));//odd
                        cells[t - 1] = table.stack(cellImgEven, numLbl).width(GameInfo.WIDTH * 0.093f).height(GameInfo.HEIGHT * 0.056f).pad(GameInfo.WIDTH * 0.003f);

                        numLbl.setName(String.valueOf(t));

                    } else {
                        Image cellImgOdd = new Image(new Texture("cell.png"));
                        cellImgOdd.setColor(Color.valueOf("#b5e72c"));//odd
                        cells[t - 1] = table.stack(cellImgOdd, numLbl).width(GameInfo.WIDTH * 0.093f).height(GameInfo.HEIGHT * 0.056f).pad(GameInfo.WIDTH * 0.003f);
                        numLbl.setName(String.valueOf(t));
                    }
                }
                if (i % 2 == 0) {
                    t++;
                } else {
                    t--;
                }
//
            }
            if (i % 2 == 0) {
                t--;
            } else {
                t++;
            }
            t -= 10;

            table.row();
        }


        return table;
    }
    public void initLadder(){
        Random random = new Random();
        int x = random.nextInt(5-3)+3;
        Array<Image> a=createLadder(x);
        for (Image y : a){
            table.addActor(y);
        }
    }

    private Array<Image> createLadder(int laddersNumber) {
        Array <Image> a= new Array<>(laddersNumber);
        Random random= new Random();

        int cellPositionsOfLadder[] = new int[laddersNumber];
        for(int i=0;i<laddersNumber;i++) {
            if(!(i==0)) {
                cellPositionsOfLadder[i]= random.nextInt(69 - 3) + 3;
                for (int j = 0; j < i; j++) {

                    if(cellPositionsOfLadder[i]/10==cellPositionsOfLadder[j]/10 ) {
                        cellPositionsOfLadder[i] = random.nextInt(69 - 3) + 3;
                        j--;
                        continue;
                    }
                }
            }
            else {
                cellPositionsOfLadder[i] = random.nextInt(69 - 3) + 3;
            }
        }
        Vector2[] vector2s= new Vector2[laddersNumber];
        for(int i=0;i<laddersNumber;i++) {

            Stack stack = (Stack) cells[cellPositionsOfLadder[i]].getActor();
            vector2s[i]=new Vector2(stack.getX(),stack.getY()+cells[0].getMinWidth()/2f);
        }

        for(int i=0;i<laddersNumber;i++) {
            Image ladder = new Image(new Texture("ladder.png"));
            ladder.setSize(GameInfo.WIDTH * 0.1f, GameInfo.HEIGHT * 0.2f);
            if(-vector2s[i].y>getX()+getHeight()-ladder.getHeight()){
                vector2s[i].y=vector2s[i].y+ladder.getHeight();
            }
            ladder.setPosition(vector2s[i].x, vector2s[i].y);
            ladder.setAlign(Align.center);
            int x= random.nextInt(45 -(-45))+(-45);
            ladder.setRotation(x);

            a.add(ladder);
        }
        return a;
    }
    public void initSnake(){
        Random random = new Random();
        int x = random.nextInt(5-3)+3;
        Array<Image> a=createSnake(x);
        for (Image y : a){
            table.addActor(y);
        }
    }
    private Array<Image> createSnake(int snakesNumber) {
        Array <Image> a= new Array<>(snakesNumber);
        Random random= new Random();

        int cellPositionsOfSnake[] = new int[snakesNumber];
        for(int i=0;i<snakesNumber;i++) {
            if(!(i==0)) {
                cellPositionsOfSnake[i]= random.nextInt(69 - 3) + 3;
                for (int j = 0; j < i; j++) {

                    if(cellPositionsOfSnake[i]/10==cellPositionsOfSnake[j]/10 ) {
                        cellPositionsOfSnake[i] = random.nextInt(69 - 3) + 3;
                        j--;
                        continue;
                    }
                }
            }
            else {
                cellPositionsOfSnake[i] = random.nextInt(69 - 3) + 3;
            }
        }
        Vector2[] vector2s= new Vector2[snakesNumber];
        for(int i=0;i<snakesNumber;i++) {
            Stack stack = (Stack) cells[cellPositionsOfSnake[i]].getActor();
            vector2s[i]=new Vector2(stack.getX(),stack.getY()+cells[0].getMinWidth()/2f);
        }

        for(int i=0;i<snakesNumber;i++) {
            Image Snake = new Image(new Texture("Snake.png"));
            Snake.setSize(GameInfo.WIDTH * 0.1f, GameInfo.HEIGHT * 0.24f);
            if(-vector2s[i].y>getX()+getHeight()-Snake.getHeight()){
                vector2s[i].y=vector2s[i].y+Snake.getHeight();
            }
            Snake.setPosition(vector2s[i].x, vector2s[i].y);
            Snake.setAlign(Align.center);
            int x= random.nextInt(45 -(-45))+(-45);
            Snake.setRotation(x);

            a.add(Snake);
        }
        return a;
    }
    private double  calculateDistanceBetweenPoints(double  x1,double y1, double x2, double y2){
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    private BitmapFont getNormalFont(float size) {
        Texture texture = new Texture(Gdx.files.internal
                ("Fonts/MyFont.png"), true);

        DistanceFieldFont font = new Font(Gdx.files.internal("Fonts/MyFont.fnt"),
                new TextureRegion(texture), size);
        return font;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);


        }

    }

