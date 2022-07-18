package com.project.snakeandladder;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.DistanceFieldFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import Helpers.Font;
import Helpers.GameInfo;
import Helpers.Huds;

public class Board extends Table {


    Table table;
    private Cell[] cells;
    Array<Integer> ladderStartPos;
    Array<Integer> ladderEndPos;
    Array<Integer> snakeStartPos;
    Array<Integer> snakeEndPos;
    Array<Integer> ladderStartCellIndexes;
    Array<Integer> snakeBiteCellIndexes;
    private boolean ladderInitialized = false;
    private boolean snakeInitialized = false;
    private int noOfSnakes;
    private int noOfLadders;
    Map<Integer,Integer> ladderCoordinates;
    Map<Integer,Integer> snakeCoordinates;
    private GamePlay gamePlay;

    private boolean isPawnMoved;

    private float delayReq;



    public Board(GamePlay gamePlay) {


        this.gamePlay=gamePlay;
        cells = new Cell[100];
        setWidth(GameInfo.WIDTH);
        snakeBiteCellIndexes = new Array<>();
        ladderStartCellIndexes = new Array<>();
        ladderCoordinates= new HashMap<>();
        snakeCoordinates= new HashMap<>();
        setName("SnakeAndLadderBoard");
        Image bg = new Image(new Texture("bg.png"));
        bg.setColor(Color.valueOf("#fafca8"));
        isPawnMoved=true;

        stack(bg, createBoard()).width(GameInfo.WIDTH).height(GameInfo.HEIGHT * 0.61f);
        getRandomValuesForSnakeStart();
        getRandomValuesForLadderStart();
        pack();

    }

    public void movePawnTo(final int targetCellNo, final Pawns pawn,
                           int previousCellNo, final Players p) {


        if(targetCellNo > previousCellNo && targetCellNo <= 100
                && !ladderStartPos.contains(targetCellNo-1,true)
        && !snakeEndPos.contains(targetCellNo - 1, true)) {

                     if(p.getPlayerPawnMap().containsValue(targetCellNo)){

                         SequenceAction sequenceAction1 = new SequenceAction();
                         int t1=previousCellNo-1;
                         for(int j =1;j<=targetCellNo-previousCellNo;j++ ) {
                             t1++;
                             Cell previousCell = cells[t1];
                             Vector2 tmp1= new Vector2();
                             previousCell.getActor().localToActorCoordinates(this,tmp1);
                             sequenceAction1.addAction(Actions.moveTo(tmp1.x + (cells[0].getMinWidth()/2f)- GameInfo.WIDTH * 0.03f , tmp1.y + cells[0].getMinHeight() / 2f, (0.2f), Interpolation.linear));
                             sequenceAction1.addAction(Actions.moveTo(tmp1.x + GameInfo.WIDTH * 0.015f, tmp1.y, (0.2f), Interpolation.linear));
                             tmp1.crs(0,0);

                         }
                         delayReq=(targetCellNo-previousCellNo)*0.4f;
                         Timer.schedule(new Timer.Task() {
                             @Override
                             public void run() {
                                 isPawnMoved=true;
                             }
                         },delayReq);
                         pawn.addAction(Actions.sequence(sequenceAction1));

                     }
                     else {

                         SequenceAction sequenceAction = new SequenceAction();
                         int t=previousCellNo-1;
                         for(int j =1;j<=targetCellNo-previousCellNo;j++ ) {

                                 t++;
//                             Cell previousCell = cells[t];

                             Vector2 tmp1= new Vector2();
                             cells[t].getActor().localToActorCoordinates(this,tmp1);
                             sequenceAction.addAction(Actions.moveTo(tmp1.x + (cells[0].getMinWidth()/2f)- GameInfo.WIDTH * 0.03f , tmp1.y + cells[0].getMinHeight() / 2f, (0.2f), Interpolation.linear));
                             sequenceAction.addAction(Actions.moveTo(tmp1.x + GameInfo.WIDTH * 0.03f, tmp1.y, (0.2f), Interpolation.linear));
                             tmp1.crs(0,0);

                         }
                         delayReq=(targetCellNo-previousCellNo)*0.4f;
                         Timer.schedule(new Timer.Task() {
                             @Override
                             public void run() {
                                 isPawnMoved=true;
                             }
                         },delayReq);
                         pawn.addAction(Actions.sequence(sequenceAction));

                     }
                 if(p.getPlayerTurnId()==1){

                     Huds.pawnBlueTbl.removeActor(pawn);

                 }
                 else{

                     Huds.pawnGreenTbl.removeActor(pawn);

                 }
                 addActor(pawn);

            pawn.setPositionOnBoard(targetCellNo);
            p.updatePlayerPawn(pawn.getPawnId(),pawn.getPosition());
            p.updatePlayerScore();
            Timer.schedule(new Timer.Task(){
                @Override
                public void run() {

                    ifAnotherPlayerPawnPresent(p, targetCellNo);
                }
            }, delayReq);

        }
        else {

            if (ladderStartPos.contains(targetCellNo - 1, true)  ) {

                Cell targetCell = cells[ladderCoordinates.get(targetCellNo - 1)];
                Vector2 tmp = new Vector2();
                targetCell.getActor().localToActorCoordinates(this, tmp);

                if (p.getPlayerPawnMap().containsValue(ladderCoordinates.get(targetCellNo-1)+1)) {
                    SequenceAction sequenceAction1 = new SequenceAction();
                    int t1=previousCellNo-1;
                    for(int j =1;j<=targetCellNo-previousCellNo;j++ ) {
                        t1++;
                        Cell previousCell = cells[t1];
                        Vector2 tmp1= new Vector2();
                        previousCell.getActor().localToActorCoordinates(this,tmp1);
                        sequenceAction1.addAction(Actions.moveTo(tmp1.x + (cells[0].getMinWidth()/2f)- GameInfo.WIDTH * 0.03f , tmp1.y + cells[0].getMinHeight() / 2f, (0.2f), Interpolation.linear));
                        sequenceAction1.addAction(Actions.moveTo(tmp1.x + GameInfo.WIDTH * 0.015f, tmp1.y, (0.2f), Interpolation.linear));
                        tmp1.crs(0,0);

                    }
                    sequenceAction1.addAction(Actions.moveTo(tmp.x + GameInfo.WIDTH * 0.015f,tmp.y,0.2f,Interpolation.linear));
                    delayReq=((targetCellNo-previousCellNo)*0.4f)+0.2f;
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            isPawnMoved=true;
                        }
                    },delayReq);
                    pawn.addAction(Actions.sequence(sequenceAction1));

                } else {
                    SequenceAction sequenceAction = new SequenceAction();
                    int t=previousCellNo-1;
                    for(int j =1;j<=targetCellNo-previousCellNo;j++ ) {
                        t++;
                        Cell previousCell = cells[t];
                        Vector2 tmp1= new Vector2();
                        previousCell.getActor().localToActorCoordinates(this,tmp1);
                        sequenceAction.addAction(Actions.moveTo(tmp1.x + (cells[0].getMinWidth()/2f)- GameInfo.WIDTH * 0.03f , tmp1.y + cells[0].getMinHeight() / 2f, (0.2f), Interpolation.linear));
                        sequenceAction.addAction(Actions.moveTo(tmp1.x + GameInfo.WIDTH * 0.03f, tmp1.y, (0.2f), Interpolation.linear));
                        tmp1.crs(0,0);

                    }
                         sequenceAction.addAction(Actions.moveTo(tmp.x + GameInfo.WIDTH * 0.03f,tmp.y,0.2f,Interpolation.linear));
                    delayReq=((targetCellNo-previousCellNo)*0.4f)+0.2f;
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            isPawnMoved=true;
                        }
                    },delayReq);
                    pawn.addAction(Actions.sequence(sequenceAction));

                }

                if (p.getPlayerTurnId() == 1 ) {
                    Huds.pawnBlueTbl.removeActor(pawn);
                } else {
                    Huds.pawnGreenTbl.removeActor(pawn);
                }
                addActor(pawn);
                pawn.setPositionOnBoard(ladderCoordinates.get(targetCellNo - 1) + 1);
                p.updatePlayerPawn(pawn.getPawnId(), pawn.getPosition());
                p.updatePlayerScore();
                Timer.schedule(new Timer.Task(){
                    @Override
                    public void run() {

                        ifAnotherPlayerPawnPresent(p, ladderCoordinates.get(targetCellNo - 1) + 1);
                    }
                }, delayReq);


            }


            if (snakeEndPos.contains(targetCellNo - 1, true)) {

                Cell targetCell = cells[snakeCoordinates.get(targetCellNo - 1)];
                Vector2 tmp = new Vector2();
                targetCell.getActor().localToActorCoordinates(this, tmp);
                if (p.getPlayerPawnMap().containsValue(snakeCoordinates.get(targetCellNo-1)+1)) {
                    SequenceAction sequenceAction1 = new SequenceAction();
                    int t1=previousCellNo-1;
                    for(int j =1;j<=targetCellNo-previousCellNo;j++ ) {
                        t1++;
                        Cell previousCell = cells[t1];
                        Vector2 tmp1= new Vector2();
                        previousCell.getActor().localToActorCoordinates(this,tmp1);
                        sequenceAction1.addAction(Actions.moveTo(tmp1.x + (cells[0].getMinWidth()/2f)- GameInfo.WIDTH * 0.03f , tmp1.y + cells[0].getMinHeight() / 2f, (0.2f), Interpolation.linear));
                        sequenceAction1.addAction(Actions.moveTo(tmp1.x + GameInfo.WIDTH * 0.015f, tmp1.y, (0.2f), Interpolation.linear));
                        tmp1.crs(0,0);

                    }
                    sequenceAction1.addAction(Actions.moveTo(tmp.x + GameInfo.WIDTH * 0.015f,tmp.y,0.2f,Interpolation.linear));
                    delayReq=((targetCellNo-previousCellNo)*0.4f)+0.2f;
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            isPawnMoved=true;
                        }
                    },delayReq);
                    pawn.addAction(Actions.sequence(sequenceAction1));

                } else {
                    SequenceAction sequenceAction = new SequenceAction();
                    int t=previousCellNo-1;
                    for(int j =1;j<=targetCellNo-previousCellNo;j++ ) {
                        t++;
                        Cell previousCell = cells[t];
                        Vector2 tmp1= new Vector2();
                        previousCell.getActor().localToActorCoordinates(this,tmp1);
                        sequenceAction.addAction(Actions.moveTo(tmp1.x + (cells[0].getMinWidth()/2f)- GameInfo.WIDTH * 0.03f , tmp1.y + cells[0].getMinHeight() / 2f, (0.2f), Interpolation.linear));
                        sequenceAction.addAction(Actions.moveTo(tmp1.x + GameInfo.WIDTH * 0.03f, tmp1.y, (0.2f), Interpolation.linear));
                        tmp1.crs(0,0);

                    }
                    sequenceAction.addAction(Actions.moveTo(tmp.x + GameInfo.WIDTH * 0.03f,tmp.y,0.2f,Interpolation.linear));
                    delayReq=((targetCellNo-previousCellNo)*0.4f)+0.2f;
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            isPawnMoved=true;
                        }
                    },delayReq);
                    pawn.addAction(Actions.sequence(sequenceAction));
//                    pawn.addAction(Actions.moveTo(tmp.x + GameInfo.WIDTH * 0.03f, tmp.y, 0.2f, Interpolation.linear));

                }
                if (p.getPlayerTurnId() == 1) {
                    Huds.pawnBlueTbl.removeActor(pawn);
                } else {
                    Huds.pawnGreenTbl.removeActor(pawn);
                }
                addActor(pawn);
                pawn.setPositionOnBoard(snakeCoordinates.get(targetCellNo - 1) + 1);
                p.updatePlayerPawn(pawn.getPawnId(), pawn.getPosition());
                p.updatePlayerScore();
                Timer.schedule(new Timer.Task(){
                    @Override
                    public void run() {

                        ifAnotherPlayerPawnPresent(p, snakeCoordinates.get(targetCellNo - 1) + 1);
                    }
                }, delayReq);

            }

        }

                p.updatePlayerPawn(pawn.getPawnId(),pawn.getPosition());
                p.updatePlayerScore();
        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {

                if(p.getPlayerType().equals(PawnAndPlayerType.BLUE))
                    Huds.scorePlayer1.setText(p.getPlayerScore());
                else
                    Huds.scorePlayer2.setText(p.getPlayerScore());            }
        }, delayReq);


isPawnMoved=false;
            }


    void ifAnotherPlayerPawnPresent(Players player,int targetCellNo){
        if(!isMoreThanOneColorPawnPresent(GamePlay.player2,targetCellNo) &&
                player.getPlayerTurnId()==1 &&
                GamePlay.player2.getPlayerPawnMap().values().contains(targetCellNo) && targetCellNo!=100){

            int key = getKey(GamePlay.player2.getPlayerPawnMap(), targetCellNo);
            if(key==1){
                Huds.pawnGreenTbl.add(GamePlay.player2.pawns[0]).width(GameInfo.WIDTH * 0.05f).height(GameInfo.HEIGHT * 0.05185f);;
                GamePlay.player2.pawns[0].setPositionOnBoard(0);
            }
            else if(key==2){
                Huds.pawnGreenTbl.add(GamePlay.player2.pawns[1]).width(GameInfo.WIDTH * 0.05f).height(GameInfo.HEIGHT * 0.05185f).padLeft(-GameInfo.WIDTH * 0.025f);
                GamePlay.player2.pawns[1].setPositionOnBoard(0);
            }
            else{
                Huds.pawnGreenTbl.add(GamePlay.player2.pawns[2]).width(GameInfo.WIDTH * 0.05f).height(GameInfo.HEIGHT * 0.05185f).padLeft(-GameInfo.WIDTH * 0.025f);
                GamePlay.player2.pawns[2].setPositionOnBoard(0);
            }


            GamePlay.player2.updatePlayerPawn(key,0);
            GamePlay.player2.updatePlayerScore();

            Huds.scorePlayer2.setText(GamePlay.player2.getPlayerScore());

//            Huds.scorePlayer2.setText(GamePlay.player2.getPlayerScore());
            player.game.changePlayerTurn();
            Huds.movePawnStack2.setVisible(false);
            Huds.movePawnStack1.setVisible(true);
            Drawable d = Huds.player2ScoreBoard.getBackground();
            Huds.player1ScoreBoard.setBackground(d);
            d = null;
            Huds.player2ScoreBoard.setBackground(d);
            for (Pawns p : Huds.bluePawnPlayer.pawns) {
                p.toFront();
//                p.addAction(p.getTurnAction());

            }
//            for (Pawns p1 : Huds.greenPawnPlayer.pawns) {
//                p1.removeAction(p1.getTurnAction());
//                RepeatAction a = Actions.forever(Actions.sequence(Actions.scaleBy(0.1f, 0.1f,1),
//                        Actions.scaleBy(-0.1f, -0.1f,1)));
//                p1.setTurnAction(a);
//            }

        }
        else if(!isMoreThanOneColorPawnPresent(GamePlay.player1,targetCellNo) &&
                player.getPlayerTurnId()==2 &&
                GamePlay.player1.getPlayerPawnMap().values().contains(targetCellNo) && targetCellNo !=100) {
            int key = getKey(GamePlay.player1.getPlayerPawnMap(), targetCellNo);
            if(key==1){
                Huds.pawnBlueTbl.add(GamePlay.player1.pawns[0]).width(GameInfo.WIDTH * 0.05f).height(GameInfo.HEIGHT * 0.05185f);;
                GamePlay.player1.pawns[0].setPositionOnBoard(0);

            }
            else if(key==2){
                Huds.pawnBlueTbl.add(GamePlay.player1.pawns[1]).width(GameInfo.WIDTH * 0.05f).height(GameInfo.HEIGHT * 0.05185f).padLeft(-GameInfo.WIDTH * 0.025f);
                GamePlay.player1.pawns[1].setPositionOnBoard(0);
            }
            else{
                Huds.pawnBlueTbl.add(GamePlay.player1.pawns[2]).width(GameInfo.WIDTH * 0.05f).height(GameInfo.HEIGHT * 0.05185f).padLeft(-GameInfo.WIDTH * 0.025f);
                GamePlay.player1.pawns[2].setPositionOnBoard(0);
            }


            GamePlay.player1.updatePlayerPawn(key,0);
            GamePlay.player1.updatePlayerScore();
            Huds.scorePlayer1.setText(GamePlay.player1.getPlayerScore());

            player.game.changePlayerTurn();
            GameMain.movesLeft++;
            Huds.movePawnStack2.setVisible(true);
            Huds.movePawnStack1.setVisible(false);
            Drawable d = Huds.player1ScoreBoard.getBackground();
            Huds.player2ScoreBoard.setBackground(d);
            d = null;
            Huds.player1ScoreBoard.setBackground(d);
            for (Pawns pawn : Huds.greenPawnPlayer.pawns) {
                pawn.toFront();
//                pawn.addAction(pawn.getTurnAction());

            }

//            for (Pawns p1 : Huds.bluePawnPlayer.pawns) {
//                p1.removeAction(p1.getTurnAction());
//                RepeatAction a = Actions.forever(Actions.sequence(Actions.scaleBy(0.1f, 0.1f,1),
//                        Actions.scaleBy(-0.1f, -0.1f,1)));
//                p1.setTurnAction(a);
//
//            }
        }
    }
    public Boolean isMoreThanOneColorPawnPresent(Players player, int target){
        int temp =0;
        for (int i : player.getPlayerPawnMap().values()) {
            if (i== target){
                temp++;
            }
        }
        return temp >= 2;

    }
    public <K, V> K getKey(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
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
                        cells[t - 1] = table.stack( cellImgEven,numLbl).width(GameInfo.WIDTH * 0.093f).height(GameInfo.HEIGHT * 0.056f).pad(GameInfo.WIDTH * 0.003f);

                        numLbl.setName(String.valueOf(t));

                    } else {
                        Image cellImgOdd = new Image(new Texture("cell.png"));
                        cellImgOdd.setColor(Color.valueOf("#b5e72c"));//odd
                        cells[t - 1] = table.stack( cellImgOdd,numLbl).width(GameInfo.WIDTH * 0.093f).height(GameInfo.HEIGHT * 0.056f).pad(GameInfo.WIDTH * 0.003f);
                        numLbl.setName(String.valueOf(t));
                    }
                }
                if (i % 2 == 0) {
                    t++;
                } else {
                    t--;
                }
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

    public void getRandomValuesForLadderStart() {
        Random random = new Random();
        noOfLadders = random.nextInt(3) + 3;
        ladderStartPos =new Array<>();
        ladderEndPos = new Array<>();
        for (int i = 0; i < noOfLadders; i++) {
            int y = random.nextInt(9);
            if (!ladderStartCellIndexes.contains(y, true))
                ladderStartCellIndexes.add(y);
            else
                i--;
        }
    }

    public void initLadder() {
        ladderInitialized = true;
        Array<Table> a = createLadder(noOfLadders);
        for (Table y : a) {
            table.addActor(y);
        }

    }

    private Array<Table> createLadder(int laddersNumber) {
        Array<Table> a = new Array<>(laddersNumber);
        ladderPosition();
        for (int i = 0; i < laddersNumber; i++) {

            Stack stack = (Stack) cells[ladderStartPos.get(i)].getActor();
            Vector2 ladderStartPosition = new Vector2();
            stack.localToActorCoordinates(table, ladderStartPosition);

            Vector2 ladderEndPosition = new Vector2();

            Stack endStack = (Stack) cells[ladderEndPos.get(i)].getActor();
            endStack.localToActorCoordinates(table, ladderEndPosition);

            Vector2 midPoint = getMidPoint(ladderStartPosition.x + cells[0].getMinWidth() / 2f, ladderStartPosition.y + cells[0].getMinHeight() / 2f
                    , ladderEndPosition.x + cells[0].getMinWidth() / 2f, ladderEndPosition.y + cells[0].getMinHeight() / 2f);

            float ladderHeight = ladderEndPosition.dst(ladderStartPosition);

            Ladders ladders = new Ladders(ladderHeight,this);

            ladderCoordinates.put(ladderStartPos.get(i),ladderEndPos.get(i));
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

    private void ladderPosition() {
        Ladders ladders = new Ladders(this);
        Set<Integer> ladderStart = ladders.ladder.keySet();

        Iterator<Integer> iterator = ladderStart.iterator();


        int j = 0;
        while (iterator.hasNext() && j < ladderStartCellIndexes.size) {

            ladderStartPos.add((int) (ladders.ladder.get(ladderStartCellIndexes.get(j)).x) - 1) ;
            ladderEndPos.add((int) (ladders.ladder.get(ladderStartCellIndexes.get(j)).y) - 1);
            j++;


        }
    }


    public void getRandomValuesForSnakeStart() {

        Random random = new Random();
        noOfSnakes = random.nextInt(3) + 3;
        snakeStartPos = new Array<>();
        snakeEndPos = new Array<>();
        for (int i = 0; i < noOfSnakes; i++) {
            int y = random.nextInt(9);
            if (!snakeBiteCellIndexes.contains(y, true))
                snakeBiteCellIndexes.add(y);
            else
                i--;
        }
    }

    public void initSnake() {
        snakeInitialized = true;
        Array<Table> a = createSnake(noOfSnakes);
        for (Table y : a) {
            table.addActor(y);
        }
    }

    private Array<Table> createSnake(int snakesNumber) {
        Array<Table> a = new Array<>(snakesNumber);

        snakePosition();

        for (int i = 0; i < snakesNumber; i++) {

            Stack stack = (Stack) cells[snakeStartPos.get(i)].getActor();
            Vector2 snakeStartPosition = new Vector2();
            stack.localToActorCoordinates(table, snakeStartPosition);

            Vector2 snakeEndPosition = new Vector2();

            Stack endStack = (Stack) cells[snakeEndPos.get(i)].getActor();
            endStack.localToActorCoordinates(table, snakeEndPosition);

            Vector2 midPoint = getMidPoint(snakeStartPosition.x + cells[0].getMinWidth() / 2f, snakeStartPosition.y + cells[0].getMinHeight() / 2f
                    , snakeEndPosition.x + cells[0].getMinWidth() / 2f, snakeEndPosition.y + cells[0].getMinHeight() / 2f);

            float snakeHeight = snakeEndPosition.dst(snakeStartPosition);

            Snakes snake = new Snakes(snakeHeight,this);
            snakeCoordinates.put(snakeEndPos.get(i),snakeStartPos.get(i));
            snake.setPosition(midPoint.x, midPoint.y, Align.center);


            double theta = Math.atan2(snakeStartPosition.y - snakeEndPosition.y, snakeStartPosition.x - snakeEndPosition.x);
            theta += Math.PI / 2.0;
            double angle = Math.toDegrees(theta);//radian to degree
            if (angle < 0) {
                angle += 360;
            }
            snake.rotateBy((float) angle);
            a.add(snake);
        }
        return a;
    }

    private void snakePosition() {
        Snakes snakes = new Snakes(this);
        Set<Integer> snakebiteCells = snakes.snake.keySet();

        Iterator<Integer> iterator = snakebiteCells.iterator();


        int j = 0;
        while (iterator.hasNext() && j < snakeBiteCellIndexes.size) {

            snakeEndPos.add((int) (snakes.snake.get(snakeBiteCellIndexes.get(j)).x) - 1);
            snakeStartPos.add((int) (snakes.snake.get(snakeBiteCellIndexes.get(j)).y) - 1);
            j++;


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


    public float getDelayReq() {
        return delayReq;
    }



    public boolean isPawnMoved() {
        return isPawnMoved;
    }


}

