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

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import Helpers.Font;
import Helpers.GameInfo;

public class Board extends Table {

    Table table;
    private Cell[] cells;
    int ladderStartPos[];
    int ladderEndPos[];
    int snakeStartPos[];
    int snakeEndPos[];
    Array<Integer> cellsOccupied= new Array<>();
    private boolean ladderInitialized = false;
    private boolean snakeInitialized = false;

    public Board() {
        cells = new Cell[100];

        setName("SnakeAndLadderBoard");
        Image bg = new Image(new Texture("bg.png"));
        bg.setColor(Color.valueOf("#fafca8"));

        stack(bg, createBoard()).expandX().fillX().height(GameInfo.HEIGHT * 0.61f);
        pack();
//        initLadder();

    }

    public void movePawnBy(int roll, Table t) {


        if (roll > 0) {
            Actor actor = cells[roll - 1].getActor();
            if (actor instanceof Stack) {
                Stack stack = (Stack) actor;
                if (stack.getChildren().size >= 2) {
                    final Actor pawn;
                    if (stack.getChildren().size == 2) {
//                        Image pawnImg = new Image(new Texture("blue.png"));
//                        Table t = new Table();
//                        t.add(pawnImg).height(cells[0].getMinHeight()*0.8f).width(cells[0].getMinWidth()*0.55f);
                        pawn = t;

                    } else {
                        pawn = stack.getChild(2);
                    }
                    final Stack nextCell = (Stack) cells[roll].getActor();
                    pawn.remove();
                    pawn.setPosition(stack.getX(), stack.getY());
                    addActor(pawn);

                    pawn.addAction(Actions.sequence(
                            Actions.moveTo(nextCell.getX(), nextCell.getY(), 0.3f),
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

//                Image pawnImg = new Image(new Texture("blue.png"));
//                Table t = new Table();
//                t.add(pawnImg).height(cells[0].getMinHeight()*0.8f).width(cells[0].getMinWidth()*0.55f);

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

    public void initLadder() {
        ladderInitialized = true;
        Random random = new Random();
        int x = random.nextInt(2) + 3;
        Array<Table> a = createLadder(x);
        for (Table y : a) {
            table.addActor(y);
        }

    }

    private Array<Table> createLadder(int laddersNumber) {
        Array<Table> a = new Array<>(laddersNumber);
        getStartAndEndOfLadders(laddersNumber);

        for (int i = 0; i < laddersNumber; i++) {
            System.out.print("ladder index => " + ladderStartPos[i] + "->" + ladderEndPos[i]);

            Stack stack = (Stack) cells[ladderStartPos[i]].getActor();
            Vector2 ladderStartPosition = new Vector2();
            stack.localToActorCoordinates(table, ladderStartPosition);

            Vector2 ladderEndPosition = new Vector2();

            Stack endStack = (Stack) cells[ladderEndPos[i]].getActor();
            endStack.localToActorCoordinates(table, ladderEndPosition);

            Vector2 midPoint = getMidPoint(ladderStartPosition.x + cells[0].getMinWidth() / 2f, ladderStartPosition.y + cells[0].getMinHeight() / 2f
                    , ladderEndPosition.x + cells[0].getMinWidth() / 2f, ladderEndPosition.y + cells[0].getMinHeight() / 2f);

            float ladderHeight = ladderEndPosition.dst(ladderStartPosition);

            Ladders ladders = new Ladders(ladderHeight);

            ladders.setPosition(midPoint.x, midPoint.y, Align.center);


            double theta = Math.atan2(ladderStartPosition.y - ladderEndPosition.y, ladderStartPosition.x - ladderEndPosition.x);
            theta += Math.PI / 2.0;
            double angle = Math.toDegrees(theta);//radian to degree
            if (angle < 0) {
                angle += 360;
            }
            ladders.rotateBy((float) angle);
            a.add(ladders);
        }
        return a;
    }

    private void getStartAndEndOfLadders(int laddersNumber) {
        Random random = new Random();
        ladderStartPos = new int[laddersNumber];
        ladderEndPos = new int[laddersNumber];

        for (int i = 0; i < laddersNumber; i++) {
            if(i==0){
                ladderStartPos[i] = random.nextInt(56) + 3;
                ladderEndPos[i] = random.nextInt(28) + 69;
            }
            else{
                ladderStartPos[i]=getNewRandomNoForStart();
                ladderEndPos[i]=getNewRandomNoForEnd();
            }
//
            cellsOccupied.add(ladderStartPos[i]);
            cellsOccupied.add(ladderEndPos[i]);
        }

    }
    public int getNewRandomNoForStart(){
        Random random= new Random();
        int x= random.nextInt(56) + 3;
        if(cellsOccupied.contains(x,true))
            return getNewRandomNoForStart();
        else{
            return x;
        }
    }
    public int getNewRandomNoForEnd(){
        Random random= new Random();
        int x= random.nextInt(28) + 69;
        if(cellsOccupied.contains(x,true))
            return getNewRandomNoForEnd();
        else{
            return x;
        }
    }

    public void initSnake() {
        snakeInitialized = true;
        Random random = new Random();
        int x = random.nextInt(5 - 3) + 3;
        Array<Table> a = createSnake(x);
        for (Table y : a) {
            table.addActor(y);
        }
    }

    private Array<Table> createSnake(int snakesNumber) {
        Array<Table> a = new Array<>(snakesNumber);
        getStartAndEndOfSnakes(snakesNumber);

        for (int i = 0; i < snakesNumber; i++) {
            System.out.print("Snake index => " + snakeStartPos[i] + "->" + snakeEndPos[i]);

            Stack stack = (Stack) cells[snakeStartPos[i]].getActor();
            Vector2 snakeStartPosition = new Vector2();
            stack.localToActorCoordinates(table, snakeStartPosition);

            Vector2 snakeEndPosition = new Vector2();

            Stack endStack = (Stack) cells[snakeEndPos[i]].getActor();
            endStack.localToActorCoordinates(table, snakeEndPosition);

            Vector2 midPoint = getMidPoint(snakeStartPosition.x + cells[0].getMinWidth() / 2f, snakeStartPosition.y + cells[0].getMinHeight() / 2f
                    , snakeEndPosition.x + cells[0].getMinWidth() / 2f, snakeEndPosition.y + cells[0].getMinHeight() / 2f);

            float snakeHeight = snakeEndPosition.dst(snakeStartPosition);

            Snakes snakes = new Snakes(snakeHeight);

            snakes.setPosition(midPoint.x, midPoint.y, Align.center);


            double theta = Math.atan2(snakeStartPosition.y - snakeEndPosition.y, snakeStartPosition.x - snakeEndPosition.x);
            theta += Math.PI / 2.0;
            double angle = Math.toDegrees(theta);//radian to degree
            if (angle < 0) {
                angle += 360;
            }
            snakes.rotateBy((float) angle);
            a.add(snakes);
        }
        return a;
    }

    private void getStartAndEndOfSnakes(int snakesNumber) {
        Random random = new Random();
        snakeStartPos = new int[snakesNumber];
        snakeEndPos = new int[snakesNumber];

        for (int i = 0; i < snakesNumber; i++) {
            if(i==0){
                snakeStartPos[i] = random.nextInt(56) + 3;
                snakeEndPos[i] = random.nextInt(28) + 69;
            }
            else{
                snakeStartPos[i]=getNewRandomNoForStart();
                snakeEndPos[i]=getNewRandomNoForEnd();
            }
//
            cellsOccupied.add(snakeStartPos[i]);
            cellsOccupied.add(snakeEndPos[i]);
        }
    }


    private Vector2 getMidPoint(float x1, float y1, float x2, float y2) {
        Vector2 vector2 = new Vector2();
        vector2.x = (x1 + x2) / 2;
        vector2.y = (y1 + y2) / 2;
        return vector2;
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

    public Cell[] getPosition() {
        return cells;
    }


    public boolean isLadderInitialized() {
        return ladderInitialized;
    }
    public boolean isSnakeInitialized() {
        return snakeInitialized;
    }
}

