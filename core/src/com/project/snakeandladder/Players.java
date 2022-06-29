package com.project.snakeandladder;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

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
    public Pawns pawn1;
    public Pawns pawn2;
    public Pawns pawn3;
    private int[] rolls;

    public Players(String name, Board board, PawnAndPlayerType playerType){
        playerScore=0;
        noOfMovesLeft=18;
        rolls=new int[noOfMovesLeft];

        this.board=board;
        turn=false;
        playerPawnMap= new HashMap<>();
        this.name = name;
        this.id = UUID.randomUUID().toString();
        this.board = board;
        this.playerType=playerType;
        createPawns(playerType);
        initPlayerPawn();

    }
    private void createRoll(){
        Random random= new Random();
        int x=random.nextInt(6)+1;

    }

    private void initPlayerPawn() {
        playerPawnMap.put(pawn1.pawn,pawn1.getPosition());
        playerPawnMap.put(pawn2.pawn,pawn2.getPosition());
        playerPawnMap.put(pawn3.pawn,pawn3.getPosition());
    }

    void createPawns(PawnAndPlayerType pawnType){
        pawn1= new Pawns(pawnType);
        pawn2= new Pawns(pawnType);
        pawn3= new Pawns(pawnType);

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
