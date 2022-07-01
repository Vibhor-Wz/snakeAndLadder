package com.project.snakeandladder;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;


public class Players{

    private String name;
    private String id;
    private int playerScore;
    private int noOfMovesLeft;
    private Boolean turn;
    private Map<Table,Integer> playerPawnMap;
    private Board board;
    private PawnAndPlayerType playerType;
    public Pawns[] pawns;
    public int timer;
    private int roll;

    public Players(String name, Board board, PawnAndPlayerType playerType){
        pawns= new Pawns[3];
        createPawns(playerType);
        playerScore=0;
        noOfMovesLeft=18;
        roll=1;
        this.board=board;
        turn=false;
        playerPawnMap= new HashMap<>();
        this.name = name;
        this.id = UUID.randomUUID().toString();
        this.board = board;
        this.playerType=playerType;
        initPlayerPawn();
        addListenersToPawns();
    }
    private void createRoll(){
        Random random= new Random();
        int x=random.nextInt(6)+1;

    }

    private void initPlayerPawn() {
        for(int i=0;i<3;i++){
            playerPawnMap.put(pawns[i].pawn,pawns[i].getPosition());
        }

    }

    void createPawns(PawnAndPlayerType pawnType){
        for(int i=0;i<3;i++){
            pawns[i]= new Pawns(pawnType);
        }


    }
    void addListenersToPawns(){
        pawns[0].pawn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int previousPos = pawns[0].getPosition();
                pawns[0].updatePosition(roll);
                    board.movePawnTo(previousPos + roll, pawns[0].pawn,previousPos,pawns[0]);
                    updatePlayerPawn(pawns[0].pawn,previousPos + roll);
            }
        });
        pawns[1].pawn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int previousPos = pawns[1].getPosition();
                pawns[1].updatePosition(roll);
                board.movePawnTo(previousPos + roll, pawns[1].pawn,previousPos,pawns[1]);
                updatePlayerPawn(pawns[1].pawn,previousPos + roll);
            }
        });
        pawns[2].pawn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int previousPos = pawns[2].getPosition();
                pawns[2].updatePosition(roll);

                board.movePawnTo(previousPos + 1, pawns[2].pawn, previousPos,pawns[2]);
                updatePlayerPawn(pawns[2].pawn, previousPos + roll);

            }
        });

    }

    public void updatePlayerPawn(Table pawn,int position) {
        playerPawnMap.put(pawn,position);
    }


    public String getId() {
        return id;
    }

    public void setPlayerScore() {
        playerScore=0;
        for (int i: playerPawnMap.values()) {
            playerScore+=i;
        }
    }

    public int getPlayerScore() {

        return playerScore;
    }

    public Boolean getTurn() {
        return turn;
    }

    public void setTurn(Boolean turn) {
        this.turn = turn;
    }


    public Map<Table, Integer> getPlayerPawnMap() {
        return playerPawnMap;
    }

    public PawnAndPlayerType getPlayerType() {
        return playerType;
    }
    public String getName() {
        return name;
    }



}
