package com.project.snakeandladder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
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

    Image pawn;
    Table table;
    Actor temp;
    Cell[] cells;

    public Board() {
        cells = new Cell[100];
        setName("SnakeAndLadderBoard");
        Image bg = new Image(new Texture("bg.png"));
        bg.setColor(Color.valueOf("#fafca8"));

        stack(bg, createBoard()).expandX().fillX().height(GameInfo.HEIGHT * 0.61f);
        layout();

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

                    } else {
                        pawn = stack.getChild(2);
                    }
                    final Stack nextCell = (Stack) cells[roll].getActor();
                    pawn.remove();
                    pawn.setPosition(stack.getX(),stack.getY());
                    addActor(pawn);
                    pawn.addAction(Actions.sequence(
                            Actions.moveTo(nextCell.getX(),nextCell.getY(),0.4f),
                            Actions.run(new Runnable() {
                                @Override
                                public void run() {
                                    nextCell.add(pawn);
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
        table.layout();
        Random random = new Random();
        int x = random.nextInt(5-3)+3;
        Array<Image> a=createLadder(x);
        for (Image y : a){
            table.addActor(y);
        }

        return table;
    }

    private Array<Image> createLadder(int laddersNumber) {
        Array <Image> a= new Array<>(laddersNumber);
        Random random= new Random();

        int cellPositionsOfLadder[] = new int[laddersNumber];
        for(int i=0;i<laddersNumber;i++) {
            cellPositionsOfLadder[i] = random.nextInt(69 - 3) + 3;
        }
        Vector2[] vector2s= new Vector2[laddersNumber];
        for(int i=0;i<laddersNumber;i++) {
            Stack stack = (Stack) cells[cellPositionsOfLadder[i]].getActor();
            vector2s[i]=new Vector2(stack.getX(),stack.getY());
        }

        for(int i=0;i<laddersNumber;i++) {
            Image ladder = new Image(new Texture("ladder.png"));
            ladder.setSize(GameInfo.WIDTH * 0.1f, GameInfo.HEIGHT * 0.2f);
            if(-vector2s[i].y>getX()+getHeight()-ladder.getHeight()){
                vector2s[i].y=vector2s[i].y+ladder.getHeight();
            }
            ladder.setPosition(vector2s[i].x, (-vector2s[i].y));
            ladder.setAlign(Align.center);

            a.add(ladder);
        }
        return a;
    }

    private BitmapFont getNormalFont(float size) {
        Texture texture = new Texture(Gdx.files.internal
                ("Fonts/MyFont.png"), true);

        DistanceFieldFont font = new Font(Gdx.files.internal("Fonts/MyFont.fnt"),
                new TextureRegion(texture), size);
        return font;
    }

}
