package com.project.snakeandladder;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import Helpers.GameInfo;
import Helpers.Huds;


public class Players{

    private String name;
    private String id;
    private int playerScore;
    private int noOfMovesLeft;
    private Map<Integer,Integer> playerPawnMap;
    private Board board;
    private PawnAndPlayerType playerType;
    public Pawns[] pawns;

    private final int playerTurnId;
    private GameMain game;


    public Players(GameMain game, String name, Board board, PawnAndPlayerType playerType, int playerTurnId){
        this.game = game;
        this.playerTurnId = playerTurnId;
        pawns= new Pawns[3];
        createPawns(playerType);
        playerScore=0;
        noOfMovesLeft=18;
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
//                pawns[0].updatePosition(roll);
                    if(playerTurnId == game.getPlayerTurn()){
                        board.movePawnTo(previousPos + game.getRoll(), pawns[0],previousPos,p,1);
                        game.changePlayerTurn();
                        if(game.getPlayerTurn()==1) {
                            Huds.movePawnStack1.setVisible(true);
                            Huds.movePawnStack2.setVisible(false);
                            if(GameMain.movesLeft>0) {
                                GameMain.movesLeft--;
                                Huds.numberOFMovesLeft.setText(GameMain.movesLeft);
                            }
                            else{
                                System.out.print("GameOver");
                                Window.WindowStyle windowStyle= new Window.WindowStyle();
                                windowStyle.titleFont= Huds.getNormalFont(GameInfo.WIDTH*0.01f);
                                windowStyle.titleFontColor= Color.BLACK;
                                if(Huds.bluePawnPlayer.playerScore > Huds.greenPawnPlayer.playerScore){
                                    new Dialog(Huds.bluePawnPlayer.name+" Won With Score "+ Huds.bluePawnPlayer.playerScore
                                            ,windowStyle).show(GameMain.stage);
                                }
                                else{
                                    new Dialog(Huds.greenPawnPlayer.name+" Won With Score "+ Huds.greenPawnPlayer.playerScore
                                            ,windowStyle).show(GameMain.stage);
                                }

                            }
                        }
                        else{
                            Huds.movePawnStack1.setVisible(false);
                            Huds.movePawnStack2.setVisible(true);
                        }

                    }else{
                        System.out.println("Please wait for your turn");
                    }



            }
        });
        pawns[1].addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                int previousPos = pawns[1].getPosition();
//                pawns[1].updatePosition(roll);
                if(playerTurnId == game.getPlayerTurn()){
                    board.movePawnTo(previousPos + game.getRoll(), pawns[1],previousPos,p,2);
                    game.changePlayerTurn();
                    if(game.getPlayerTurn()==1) {
                        Huds.movePawnStack1.setVisible(true);
                        Huds.movePawnStack2.setVisible(false);
                        if(GameMain.movesLeft>0) {
                            GameMain.movesLeft--;
                            Huds.numberOFMovesLeft.setText(GameMain.movesLeft);
                        }
                        else{
                            System.out.print("GameOver");
                            Window.WindowStyle windowStyle= new Window.WindowStyle();
                            windowStyle.titleFont= Huds.getNormalFont(GameInfo.WIDTH*0.01f);
                            windowStyle.titleFontColor= Color.BLACK;
                            if(Huds.bluePawnPlayer.playerScore > Huds.greenPawnPlayer.playerScore){
                                new Dialog(Huds.bluePawnPlayer.name+" Won With Score "+ Huds.bluePawnPlayer.playerScore
                                        ,windowStyle).show(GameMain.stage);
                            }
                            else{
                                new Dialog(Huds.greenPawnPlayer.name+" Won With Score "+ Huds.greenPawnPlayer.playerScore
                                        ,windowStyle).show(GameMain.stage);
                            }
                        }
                    }
                    else{
                        Huds.movePawnStack1.setVisible(false);
                        Huds.movePawnStack2.setVisible(true);
                    }
                }else{
                    System.out.println("Please wait for your turn");
                }
            }
        });
        pawns[2].addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                int previousPos = pawns[2].getPosition();
//

                if(playerTurnId == game.getPlayerTurn()){
                    board.movePawnTo(previousPos + game.getRoll(), pawns[2],previousPos,p,3);
                    game.changePlayerTurn();
                    if(game.getPlayerTurn()==1) {
                        Huds.movePawnStack1.setVisible(true);
                        Huds.movePawnStack2.setVisible(false);
                        if(GameMain.movesLeft>0) {
                            GameMain.movesLeft--;
                            Huds.numberOFMovesLeft.setText(GameMain.movesLeft);
                        }
                        else{
                            System.out.print("GameOver");
                            Window.WindowStyle windowStyle= new Window.WindowStyle();
                            windowStyle.titleFont= Huds.getNormalFont(GameInfo.WIDTH*0.01f);
                            windowStyle.titleFontColor= Color.BLACK;
                            if(Huds.bluePawnPlayer.playerScore > Huds.greenPawnPlayer.playerScore){
                                new Dialog(Huds.bluePawnPlayer.name+" Won With Score "+ Huds.bluePawnPlayer.playerScore
                                        ,windowStyle).show(GameMain.stage);
                            }
                            else{
                                new Dialog(Huds.greenPawnPlayer.name+" Won With Score "+ Huds.greenPawnPlayer.playerScore
                                        ,windowStyle).show(GameMain.stage);
                            }
                        }
                    }
                    else{
                        Huds.movePawnStack1.setVisible(false);
                        Huds.movePawnStack2.setVisible(true);
                    }
                }else{
                    System.out.println("Please wait for your turn");
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
