package Helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.DistanceFieldFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.project.snakeandladder.Board;

public class Huds extends Table {
    private Label numberOFMovesLeft;
    private Label movePawnLbl;
    private Label movesLeftLbl;
    private Stack pawnBlueStack;
    private Stack pawnGreenStack;
    private Array<Table> pawnBlueArray = new Array<>();
    private Array<Table> pawnGreenArray = new Array<>();
    private int numberOfBluePawnLeft;
    private int numberOfGreenPawnLeft;
    private Board board;
    public Huds(){
        setSize(GameInfo.WIDTH,GameInfo.HEIGHT);
        board= new Board();
        Table moveLeftTbl= new Table();
        numberOFMovesLeft= new Label("18", getLabelStyle(Color.WHITE,GameInfo.WIDTH*0.055f));
        movesLeftLbl = new Label("MOVES LEFT",getLabelStyle(Color.WHITE,GameInfo.WIDTH*0.02f));
        movesLeftLbl.setWrap(true);
        moveLeftTbl.add(numberOFMovesLeft).padRight(GameInfo.WIDTH*0.02f);
        moveLeftTbl.add(movesLeftLbl).width(GameInfo.WIDTH*0.15f).top().padTop(GameInfo.HEIGHT*0.01f);
        add(moveLeftTbl).top().expandX().fillX().row();
        add(createBottom()).bottom().expand().fill();
        addListenersToPawns();
//        System.out.print();
    }
    private void addListenersToPawns(){
        numberOfBluePawnLeft =3;
        pawnBlueStack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (numberOfBluePawnLeft >0) {
                    board.movePawnBy(1, pawnBlueArray.get(numberOfBluePawnLeft -1));


//                    pawnBlueStack.removeActor(pawnBlueArray.get(numberOfBluePawnLeft -1));
//                    pawnBlueStack.removeActorAt(numberOfBluePawnLeft -1,false);

                    numberOfBluePawnLeft--;
                }
            }
        });

//        numberOfGreenPawnLeft =3;
//        pawnGreenStack.addListener(new ClickListener(){
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                if (numberOfGreenPawnLeft >0) {
//
//                    board.movePawnBy(1, pawnGreenArray.get(numberOfGreenPawnLeft -1));
//                    pawnGreenStack.removeActor(pawnGreenArray.get(numberOfGreenPawnLeft -1));
////                    pawnGreenStack.removeActorAt(numberOfGreenPawnLeft -1,false);
//                    numberOfGreenPawnLeft--;
//                }
//
//            }
//        });
//        addListenerToSpecificPawn();
    }

    private void addListenerToSpecificPawn() {

        pawnGreenArray.get(0).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                board.movePawnBy(1,pawnGreenArray.get(0));
            }
        });

        pawnGreenArray.get(1).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                board.movePawnBy(1,pawnGreenArray.get(1));
            }
        });

        pawnGreenArray.get(2).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                board.movePawnBy(1,pawnGreenArray.get(2));
            }
        });

//        Blue Pawn

        pawnBlueArray.get(0).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                board.movePawnBy(1,pawnBlueArray.get(0));
            }
        });

        pawnBlueArray.get(1).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                board.movePawnBy(1,pawnBlueArray.get(1));
            }
        });

        pawnBlueArray.get(2).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                board.movePawnBy(1,pawnBlueArray.get(2));
            }
        });
    }

    private Table createBottom(){
        Table mainTable= new Table();
        mainTable.bottom();

        mainTable.add(getDiceWithRollTbl("blue",true)).expandX().fillX();
        mainTable.add(getDiceWithRollTbl("Green",false)).expandX().fillX().row();
        mainTable.add(getScoreBoardOfPlayers("Vibhor",true)).expandX().fillX();
        mainTable.add(getScoreBoardOfPlayers("Vibhor2",false)).expandX().fillX();
        return mainTable;
    }
    private Table getDiceWithRollTbl(String pawnColor,boolean playerTurn){
        Table table= new Table();
        table.add(getPawnAndDiceRollTbl(pawnColor));
        if(playerTurn) {

            table.add(getMovePawnLblTable()).padLeft(-GameInfo.WIDTH * 0.04f);
        }
        return table;
    }
    private Table getMovePawnLblTable(){
        Table table= new Table();
        Table movePawnTbl=new Table();
        Stack movePawnStack= new Stack();

        Image t= new Image(new Texture("MovePawn.png"));
        t.setColor(Color.valueOf("#6de70a"));
        Table tTbl=new Table();
        tTbl.add(t).width(GameInfo.WIDTH*0.16f).height(GameInfo.HEIGHT*0.06f);

        movePawnLbl= new Label("MOVE PAWN", getLabelStyle(Color.BLACK, GameInfo.WIDTH*0.017f));
        movePawnLbl.setWrap(true);
        movePawnLbl.setAlignment(Align.center);
        movePawnStack.add(tTbl);
        movePawnTbl.add(movePawnLbl).width(GameInfo.WIDTH*0.2f).padLeft(movePawnStack.getMinWidth()*0.15f);
        movePawnTbl.align(Align.center);
        movePawnStack.add(movePawnTbl);
        table.add(movePawnStack);
        return table;
    }

    private Table getPawnAndDiceRollTbl(String pawnColor){
        Table table= new Table();
         /*pawnBlueArray = new Array<>();
         pawnGreenArray= new Array<>();*/
        for (int i=0;i<3;i++){
            Image pawnImg = new Image(new Texture(pawnColor+".png"));
            Table t = new Table();
            t.add(pawnImg).width(GameInfo.WIDTH * 0.05f).height(GameInfo.HEIGHT * 0.05185f);
            if (i==0){
                t.align(Align.left);
            }
            else if (i==1){
                t.align(Align.center);
            }
            else {
                t.align(Align.right);
            }
            if(pawnColor.equals("blue")) {
                pawnBlueArray.add(t);
            }
            else{
                pawnGreenArray.add(t);
            }
        }
        if(pawnColor.equals("blue")) {
            pawnBlueStack = new Stack();
            for (Table b : pawnBlueArray) {
                pawnBlueStack.add(b);
            }
            table.add(pawnBlueStack).width(GameInfo.WIDTH * 0.1f).padBottom(GameInfo.HEIGHT * 0.0129625f).row();
        }
        else {
            pawnGreenStack = new Stack();
            for (Table b : pawnGreenArray) {
                pawnGreenStack.add(b);
            }
            table.add(pawnGreenStack).width(GameInfo.WIDTH * 0.1f).padBottom(GameInfo.HEIGHT * 0.0129625f).row();
        }
//        if(pawnColor.equals("blue")) {
//            table.add(pawnBlueStack).width(GameInfo.WIDTH * 0.1f).padBottom(GameInfo.HEIGHT * 0.0129625f).row();
//        }
//        else {
//            table.add(pawnGreenStack).width(GameInfo.WIDTH * 0.1f).padBottom(GameInfo.HEIGHT * 0.0129625f).row();

//        }
        Label diceRoll= new Label("5",getLabelStyle(Color.WHITE,GameInfo.WIDTH*0.03f));
        table.add(diceRoll);

        return table;
    }


    private Table getScoreBoardOfPlayers(String playerName, boolean player1){
        Table table= new Table();

       table.add(getPlayerNameAndPicture(playerName)).padRight(GameInfo.WIDTH*0.05f);
       if(player1){
           table.add(getScoreDetailsOfPlayer1()).top().expandY().fill();
       }
       else {
           table.add(getScoreDetailsOfPlayer2()).top().expandY().fill();
       }

        return table;
    }
    private Table getScoreDetailsOfPlayer1(){
        Table table = new Table();
        table.top();
        Table infoIcon= new Table();
        Image infoImg= new Image(new Texture("info.png"));
        infoIcon.add(infoImg).width(GameInfo.WIDTH*0.05f).height(GameInfo.WIDTH*0.05f);

        table.add(infoIcon).row();
        Label scorePlayer1 = new Label("84",getLabelStyle(Color.SKY,GameInfo.WIDTH*0.042f));
        table.add(scorePlayer1).row();

        Label scoreTxt = new Label("SCORE",getLabelStyle(Color.SKY, GameInfo.WIDTH*0.023f));
        table.add(scoreTxt);
        return table;
    }
    private Table getScoreDetailsOfPlayer2(){
        Table table = new Table();
        table.top();
        Table infoIcon= new Table();
        Image infoImg= new Image(new Texture("info.png"));
        infoIcon.add(infoImg).width(GameInfo.WIDTH*0.05f).height(GameInfo.WIDTH*0.05f);

        table.add(infoIcon).top().row();
        Label scorePlayer2 = new Label("10",getLabelStyle(Color.GREEN,GameInfo.WIDTH*0.042f));
        table.add(scorePlayer2).row();

        Label scoreTxt = new Label("SCORE",getLabelStyle(Color.SKY, GameInfo.WIDTH*0.023f));
        table.add(scoreTxt);
        return table;
    }
    private Table getPlayerNameAndPicture(String playerName){
        Table table= new Table();
        Label PlayerName= new Label(playerName,getLabelStyle(Color.YELLOW,GameInfo.WIDTH*0.02f));
        table.add(PlayerName).left().row();

        Table playerPicture=new Table();
        Image playerImg= new Image(new Texture("circle.png"));
        playerPicture.add(playerImg).width(GameInfo.WIDTH*0.2f).height(GameInfo.WIDTH*0.2f);
        table.add(playerPicture);
        return table;
    }
    private BitmapFont getNormalFont(float size) {
        Texture texture = new Texture(Gdx.files.internal
                ("Fonts/MyFont.png"), true);

        DistanceFieldFont font = new Font(Gdx.files.internal("Fonts/MyFont.fnt"),
                new TextureRegion(texture), size);
        return font;
    }
    private Label.LabelStyle getLabelStyle(Color color, float fontSize){
        Label.LabelStyle style= new Label.LabelStyle();
        style.font=getNormalFont(fontSize);
        style.fontColor=color;

        return style;
    }

}
