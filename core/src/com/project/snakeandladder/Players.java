package com.project.snakeandladder;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
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

                int previousPos = pawns[0].getPosition();
                if (previousPos + game.getRoll() <= 100) {
                    if (playerTurnId == game.getPlayerTurn() && previousPos != 100) {
                        board.movePawnTo(previousPos + game.getRoll(), pawns[0], previousPos, p);
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
                            }
                            if (GameMain.movesLeft > 0) {
                                GameMain.movesLeft--;
                                Huds.numberOFMovesLeft.setText(GameMain.movesLeft);
                            } else {
                                System.out.print("GameOver");
                                Window.WindowStyle windowStyle = new Window.WindowStyle();
                                windowStyle.titleFont = Huds.getNormalFont(GameInfo.WIDTH * 0.05f);
                                windowStyle.titleFontColor = Color.BLACK;
                                if (Huds.bluePawnPlayer.playerScore > Huds.greenPawnPlayer.playerScore) {
                                    new GameOverDialog("Score Board",windowStyle,Huds.bluePawnPlayer.name,Huds.bluePawnPlayer.playerScore).show(GameMain.stage);
//
                                } else {

                                    new GameOverDialog("Score Board",windowStyle,Huds.greenPawnPlayer.name,Huds.greenPawnPlayer.playerScore).show(GameMain.stage);
//
                                }

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
                            }
                        }

                    } else {
                        System.out.println("Please wait for your turn");
                    }
                    if (pawns[0].getPosition() == 100) {
                        pawns[0].removeListener(this);
                    }
                    if (p.playerScore == 300) {
                        Window.WindowStyle windowStyle = new Window.WindowStyle();
                        windowStyle.titleFont = Huds.getNormalFont(GameInfo.WIDTH * 0.05f);
                        windowStyle.titleFontColor = Color.BLACK;

                        new GameOverDialog("Score Board",windowStyle,p.name,p.playerScore).show(GameMain.stage);
//
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
                        }
                        if (GameMain.movesLeft > 0) {
                            GameMain.movesLeft--;
                            Huds.numberOFMovesLeft.setText(GameMain.movesLeft);
                        } else {
                            System.out.print("GameOver");
                            Window.WindowStyle windowStyle = new Window.WindowStyle();
                            windowStyle.titleFont = Huds.getNormalFont(GameInfo.WIDTH * 0.05f);
                            windowStyle.titleFontColor = Color.BLACK;
                            if (Huds.bluePawnPlayer.playerScore > Huds.greenPawnPlayer.playerScore) {
                                new GameOverDialog("Score Board",windowStyle,Huds.bluePawnPlayer.name,Huds.bluePawnPlayer.playerScore).show(GameMain.stage);
//
                            } else {
                                new GameOverDialog("Score Board",windowStyle,Huds.greenPawnPlayer.name, Huds.greenPawnPlayer.playerScore).show(GameMain.stage);
//
                            }

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
                        }
                    }
            }

            }
        });
        pawns[1].addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                int previousPos = pawns[1].getPosition();
                if (previousPos + game.getRoll() <= 100) {
                    if (playerTurnId == game.getPlayerTurn() && previousPos != 100) {
                        board.movePawnTo(previousPos + game.getRoll(), pawns[1], previousPos, p);
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
                            }
                            if (GameMain.movesLeft > 0) {
                                GameMain.movesLeft--;
                                Huds.numberOFMovesLeft.setText(GameMain.movesLeft);
                            } else {
                                System.out.print("GameOver");
                                Window.WindowStyle windowStyle = new Window.WindowStyle();
                                windowStyle.titleFont = Huds.getNormalFont(GameInfo.WIDTH * 0.05f);
                                windowStyle.titleFontColor = Color.BLACK;
                                if (Huds.bluePawnPlayer.playerScore > Huds.greenPawnPlayer.playerScore) {
                                    new GameOverDialog("Score Board",windowStyle,Huds.bluePawnPlayer.name, Huds.bluePawnPlayer.playerScore).show(GameMain.stage);
//
                                } else {
                                    new GameOverDialog("Score Board",windowStyle,Huds.greenPawnPlayer.name, Huds.greenPawnPlayer.playerScore).show(GameMain.stage);
//
                                }
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
                            }
                        }
                    } else {
                        System.out.println("Please wait for your turn");
                    }
                    if (pawns[1].getPosition() == 100) {
                        pawns[1].removeListener(this);
                    }
                    if (p.playerScore == 300) {
                        Window.WindowStyle windowStyle = new Window.WindowStyle();
                        windowStyle.titleFont = Huds.getNormalFont(GameInfo.WIDTH * 0.05f);
                        windowStyle.titleFontColor = Color.BLACK;
                        new GameOverDialog("Score Board",windowStyle,p.name, p.playerScore).show(GameMain.stage);
//
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
                        }
                        if (GameMain.movesLeft > 0) {
                            GameMain.movesLeft--;
                            Huds.numberOFMovesLeft.setText(GameMain.movesLeft);
                        } else {
                            System.out.print("GameOver");
                            Window.WindowStyle windowStyle = new Window.WindowStyle();
                            windowStyle.titleFont = Huds.getNormalFont(GameInfo.WIDTH * 0.05f);
                            windowStyle.titleFontColor = Color.BLACK;
                            if (Huds.bluePawnPlayer.playerScore > Huds.greenPawnPlayer.playerScore) {
                                new GameOverDialog("Score Board",windowStyle,Huds.bluePawnPlayer.name, Huds.bluePawnPlayer.playerScore).show(GameMain.stage);

                            } else {
                                new GameOverDialog("Score Board",windowStyle,Huds.greenPawnPlayer.name, Huds.greenPawnPlayer.playerScore).show(GameMain.stage);

                            }
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
                        }
                    }
                }
            }
        });
        pawns[2].addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                int previousPos = pawns[2].getPosition();

                if (previousPos + game.getRoll() <= 100) {
                    if (playerTurnId == game.getPlayerTurn() && previousPos != 100) {
                        board.movePawnTo(previousPos + game.getRoll(), pawns[2], previousPos, p);
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
                            }
                            if (GameMain.movesLeft > 0) {
                                GameMain.movesLeft--;
                                Huds.numberOFMovesLeft.setText(GameMain.movesLeft);
                            } else {

                                Huds.numberOFMovesLeft.setText(GameMain.movesLeft);
                                System.out.print("GameOver");
                                Window.WindowStyle windowStyle = new Window.WindowStyle();
                                windowStyle.titleFont = Huds.getNormalFont(GameInfo.WIDTH * 0.05f);
                                windowStyle.titleFontColor = Color.BLACK;
                                if (Huds.bluePawnPlayer.playerScore > Huds.greenPawnPlayer.playerScore) {
                                    new GameOverDialog("Score Board",windowStyle,Huds.bluePawnPlayer.name, Huds.bluePawnPlayer.playerScore).show(GameMain.stage);

                                } else {
                                    new GameOverDialog("Score Board",windowStyle,Huds.greenPawnPlayer.name, Huds.greenPawnPlayer.playerScore).show(GameMain.stage);

                                }
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
                            }

                        }
                    } else {
                        System.out.println("Please wait for your turn");
                    }
                    if (pawns[2].getPosition() == 100) {
                        pawns[2].removeListener(this);
                    }
                    if (p.playerScore == 300) {
                        Window.WindowStyle windowStyle = new Window.WindowStyle();
                        windowStyle.titleFont = Huds.getNormalFont(GameInfo.WIDTH * 0.05f);
                        windowStyle.titleFontColor = Color.BLACK;

                        new GameOverDialog("Score Board",windowStyle,p.name, p.playerScore).show(GameMain.stage);
//
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
                        }
                        if (GameMain.movesLeft > 0) {
                            GameMain.movesLeft--;
                            Huds.numberOFMovesLeft.setText(GameMain.movesLeft);
                        } else {
//                            GameMain.movesLeft--;
                            Huds.numberOFMovesLeft.setText(GameMain.movesLeft);
                            System.out.print("GameOver");
                            Window.WindowStyle windowStyle = new Window.WindowStyle();
                            windowStyle.titleFont = Huds.getNormalFont(GameInfo.WIDTH * 0.05f);
                            windowStyle.titleFontColor = Color.BLACK;
                            if (Huds.bluePawnPlayer.playerScore > Huds.greenPawnPlayer.playerScore) {
                                new GameOverDialog("Score Board",windowStyle,Huds.bluePawnPlayer.name, Huds.bluePawnPlayer.playerScore).show(GameMain.stage);
//
                            } else {
                                new GameOverDialog("Score Board",windowStyle,Huds.greenPawnPlayer.name, Huds.greenPawnPlayer.playerScore).show(GameMain.stage);
//
                            }
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

                        }

                    }
                }
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


}
