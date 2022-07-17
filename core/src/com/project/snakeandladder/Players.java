package com.project.snakeandladder;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Timer;
import com.project.snakeandladder.gameover.GameOverDialog;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import Helpers.GameInfo;
import Helpers.Huds;


public class Players{

    private final String name;
    private final String id;
    private int playerScore;
    private Map<Integer,Integer> playerPawnMap;
    private Board board;
    private final PawnAndPlayerType playerType;
    public Pawns[] pawns;

    private final int playerTurnId;
    public GameMain game;
//    private RepeatAction action;


    public Players(GameMain game, String name, Board board, PawnAndPlayerType playerType, int playerTurnId){
        this.game = game;
        this.playerTurnId = playerTurnId;
        pawns= new Pawns[3];
        createPawns(playerType);
        playerScore=0;
        this.board=board;
        playerPawnMap= new HashMap<>();
        this.name = name;
        this.id = UUID.randomUUID().toString();
        this.board = board;
        this.playerType=playerType;
        initPlayerPawn();
//        action = Actions.forever(Actions.sequence(Actions.scaleBy(0.1f, 0.1f,1),
//                                        Actions.scaleBy(-0.1f, -0.1f,1)));
        addListenersToPawns();
    }

    private void initPlayerPawn() {
        for(int i=0;i<3;i++){
            playerPawnMap.put(i+1,pawns[i].getPosition());
        }

    }

    void createPawns(PawnAndPlayerType pawnType){
        for(int i=0;i<3;i++){
            pawns[i]= new Pawns(pawnType,i+1);
        }


    }
    public void addListenersToPawns(){
        final Players p=this;
        pawns[0].addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                listenerWork(p,pawns[0].getPawnId());


            }
        });
        pawns[1].addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                listenerWork(p,pawns[1].getPawnId());
            }
        });
        pawns[2].addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                listenerWork(p,pawns[2].getPawnId());
            }
        });
    }


    public void updatePlayerPawn(int id,int position) {

        playerPawnMap.put(id,position);
    }


    public String getId() {
        return id;
    }

    public void updatePlayerScore() {
        playerScore=0;
        for (int i: playerPawnMap.values()) {
            playerScore+=i;
        }
    }

    public int getPlayerScore() {

        return playerScore;
    }


    public Map<Integer, Integer> getPlayerPawnMap() {
        return playerPawnMap;
    }

    public PawnAndPlayerType getPlayerType() {
        return playerType;
    }
    public String getName() {
        return name;
    }

    public int getPlayerTurnId() {
        return playerTurnId;
    }
    private void listenerWork(Players player, int pawnId){
        int previousPos = pawns[pawnId-1].getPosition();

        if (previousPos + game.getRoll() <= 100) {
            if (playerTurnId == game.getPlayerTurn() && previousPos != 100) {
                board.movePawnTo(previousPos + game.getRoll(), pawns[pawnId-1], previousPos, player);
                game.changePlayerTurn();
                if (game.getPlayerTurn() == 2) {
                    Huds.movePawnStack2.setVisible(true);
                    Huds.movePawnStack1.setVisible(false);
                    Drawable d = Huds.player1ScoreBoard.getBackground();
                    Huds.player2ScoreBoard.setBackground(d);
                    d = null;
                    Huds.player1ScoreBoard.setBackground(d);
                    for (Pawns pawn : Huds.greenPawnPlayer.pawns) {
                        pawn.toFront();
//                        pawn.addAction(pawn.getTurnAction());

                    }

//                    for (Pawns p1 : Huds.bluePawnPlayer.pawns) {
//                        p1.removeAction(p1.getTurnAction());
//                        RepeatAction a = Actions.forever(Actions.sequence(Actions.scaleBy(0.1f, 0.1f,1),
//                                Actions.scaleBy(-0.1f, -0.1f,1)));
//                        p1.setTurnAction(a);
//
//                    }
                    if (GameMain.movesLeft > 0) {
                        GameMain.movesLeft--;
                        Huds.numberOFMovesLeft.setText(GameMain.movesLeft);
                        if(GameMain.movesLeft==1)
                        {
                            GameMain.movesLeft--;
                            Huds.numberOFMovesLeft.setText(GameMain.movesLeft);
                        }

                    } else {
                        Timer.schedule(new Timer.Task(){
                            @Override
                            public void run() {
                                System.out.print("GameOver");
                                Window.WindowStyle windowStyle = new Window.WindowStyle();
                                windowStyle.titleFont = Huds.getNormalFont(GameInfo.WIDTH * 0.05f);
                                windowStyle.titleFontColor = Color.BLACK;
                                if (Huds.bluePawnPlayer.playerScore > Huds.greenPawnPlayer.playerScore) {
                                    Huds.movePawnStack1.setVisible(false);
                                    Huds.movePawnStack2.setVisible(false);
                                    Huds.player1ScoreBoard.setBackground((Drawable) null);
                                    Huds.player2ScoreBoard.setBackground((Drawable) null);
                                    new GameOverDialog("",windowStyle,Huds.bluePawnPlayer.name, Huds.bluePawnPlayer.playerScore).show(GameMain.stage);
                                    for (Pawns pawns: Huds.bluePawnPlayer.pawns) {
                                        pawns.clearActions();
                                        pawns.clearListeners();
                                    }
                                    for (Pawns pawns: Huds.greenPawnPlayer.pawns) {
                                        pawns.clearActions();
                                        pawns.clearListeners();
                                    }

                                } else {
                                    Huds.movePawnStack1.setVisible(false);
                                    Huds.movePawnStack2.setVisible(false);
                                    Huds.player1ScoreBoard.setBackground((Drawable) null);
                                    Huds.player2ScoreBoard.setBackground((Drawable) null);
                                    new GameOverDialog("",windowStyle,Huds.greenPawnPlayer.name, Huds.greenPawnPlayer.playerScore).show(GameMain.stage);
                                    for (Pawns pawns: Huds.bluePawnPlayer.pawns) {
                                        pawns.clearActions();
                                        pawns.clearListeners();
                                    }
                                    for (Pawns pawns: Huds.greenPawnPlayer.pawns) {
                                        pawns.clearActions();
                                        pawns.clearListeners();
                                    }
                                }
                            }
                        }, board.getDelayReq()+0.2f);
                    }
                } else {
                    Huds.movePawnStack2.setVisible(false);
                    Huds.movePawnStack1.setVisible(true);
                    Drawable d = Huds.player2ScoreBoard.getBackground();
                    Huds.player1ScoreBoard.setBackground(d);
                    d = null;
                    Huds.player2ScoreBoard.setBackground(d);
                    for (Pawns p : Huds.bluePawnPlayer.pawns) {
                        p.toFront();
//                        p.addAction(p.getTurnAction());

                    }
//                    for (Pawns p1 : Huds.greenPawnPlayer.pawns) {
//                        p1.removeAction(p1.getTurnAction());
//                        RepeatAction a = Actions.forever(Actions.sequence(Actions.scaleBy(0.1f, 0.1f,1),
//                                Actions.scaleBy(-0.1f, -0.1f,1)));
//                        p1.setTurnAction(a);
//                    }

                }
            } else {
                System.out.println("Please wait for your turn");
            }
            if (pawns[pawnId-1].getPosition() == 100) {
                pawns[pawnId-1].clearListeners();
            }
            if (player.playerScore == 300) {
                Huds.movePawnStack1.setVisible(false);
                Huds.movePawnStack2.setVisible(false);
                Huds.player1ScoreBoard.setBackground((Drawable) null);
                Huds.player2ScoreBoard.setBackground((Drawable) null);
                Window.WindowStyle windowStyle = new Window.WindowStyle();
                windowStyle.titleFont = Huds.getNormalFont(GameInfo.WIDTH * 0.05f);
                windowStyle.titleFontColor = Color.BLACK;

                new GameOverDialog("",windowStyle,player.name, player.playerScore).show(GameMain.stage);
                for (Pawns pawns: Huds.bluePawnPlayer.pawns) {
                    pawns.clearActions();
                    pawns.clearListeners();
                }
                for (Pawns pawns: Huds.greenPawnPlayer.pawns) {
                    pawns.clearActions();
                    pawns.clearListeners();
                }
            }
        }
        else if(pawns[0].getPosition()+game.getRoll()>100 &&
                pawns[1].getPosition()+game.getRoll()>100 &&
                pawns[2].getPosition()+game.getRoll()>100 ){
            game.changePlayerTurn();
            if (game.getPlayerTurn() == 2) {
                Huds.movePawnStack2.setVisible(true);
                Huds.movePawnStack1.setVisible(false);
                Drawable d = Huds.player1ScoreBoard.getBackground();
                Huds.player2ScoreBoard.setBackground(d);
                d = null;
                Huds.player1ScoreBoard.setBackground(d);
                for (Pawns p : Huds.greenPawnPlayer.pawns) {
                    p.toFront();
//                    p.addAction(p.getTurnAction());
                }
//                for (Pawns p1 : Huds.bluePawnPlayer.pawns) {
//                    p1.removeAction(p1.getTurnAction());
//                    RepeatAction a = Actions.forever(Actions.sequence(Actions.scaleBy(0.1f, 0.1f,1),
//                            Actions.scaleBy(-0.1f, -0.1f,1)));
//                    p1.setTurnAction(a);
//                }
                if (GameMain.movesLeft > 0) {
                    GameMain.movesLeft--;
                    Huds.numberOFMovesLeft.setText(GameMain.movesLeft);
                    if(GameMain.movesLeft==1)
                    {
                        GameMain.movesLeft--;
                        Huds.numberOFMovesLeft.setText(GameMain.movesLeft);
                    }
                } else {
                    Timer.schedule(new Timer.Task(){
                        @Override
                        public void run() {
                            System.out.print("GameOver");
                            Window.WindowStyle windowStyle = new Window.WindowStyle();
                            windowStyle.titleFont = Huds.getNormalFont(GameInfo.WIDTH * 0.05f);
                            windowStyle.titleFontColor = Color.BLACK;
                            if (Huds.bluePawnPlayer.playerScore > Huds.greenPawnPlayer.playerScore) {
                                Huds.movePawnStack1.setVisible(false);
                                Huds.movePawnStack2.setVisible(false);
                                Huds.player1ScoreBoard.setBackground((Drawable) null);
                                Huds.player2ScoreBoard.setBackground((Drawable) null);
                                new GameOverDialog("",windowStyle,Huds.bluePawnPlayer.name, Huds.bluePawnPlayer.playerScore).show(GameMain.stage);
                                for (Pawns pawns: Huds.bluePawnPlayer.pawns) {
                                    pawns.clearActions();
                                    pawns.clearListeners();
                                }
                                for (Pawns pawns: Huds.greenPawnPlayer.pawns) {
                                    pawns.clearActions();
                                    pawns.clearListeners();
                                }
                            } else {
                                Huds.movePawnStack1.setVisible(false);
                                Huds.movePawnStack2.setVisible(false);
                                Huds.player1ScoreBoard.setBackground((Drawable) null);
                                Huds.player2ScoreBoard.setBackground((Drawable) null);
                                new GameOverDialog("",windowStyle,Huds.greenPawnPlayer.name, Huds.greenPawnPlayer.playerScore).show(GameMain.stage);
                                for (Pawns pawns: Huds.bluePawnPlayer.pawns) {
                                    pawns.clearActions();
                                    pawns.clearListeners();
                                }
                                for (Pawns pawns: Huds.greenPawnPlayer.pawns) {
                                    pawns.clearActions();
                                    pawns.clearListeners();
                                }
                            }
                        }
                    }, board.getDelayReq()+0.2f);
                }
            } else {
                Huds.movePawnStack2.setVisible(false);
                Huds.movePawnStack1.setVisible(true);
                Drawable d = Huds.player2ScoreBoard.getBackground();
                Huds.player1ScoreBoard.setBackground(d);
                d = null;
                Huds.player2ScoreBoard.setBackground(d);

                for (Pawns p : Huds.bluePawnPlayer.pawns) {
                    p.toFront();
//                    p.addAction(p.getTurnAction());

                }
//                for (Pawns p1 : Huds.greenPawnPlayer.pawns) {
//                    p1.removeAction(p1.getTurnAction());
//                    RepeatAction a = Actions.forever(Actions.sequence(Actions.scaleBy(0.1f, 0.1f,1),
//                            Actions.scaleBy(-0.1f, -0.1f,1)));
//                    p1.setTurnAction(a);
//
//                }

            }
        }
    }


}
