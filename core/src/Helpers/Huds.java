package Helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.DistanceFieldFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import Helpers.Font;
import Helpers.GameInfo;

public class Huds extends Table {
    private Label numberOFMovesLeft;
    private Label movePawnLbl;
    private Label movesLeftLbl;
    private Label player1DiceNumber;
    private Label player2DiceNumber;
    private Table player1HighlightedTbl;
    private Table player2HighlightedTbl;
    public Huds(){
        setSize(GameInfo.WIDTH,GameInfo.HEIGHT);

        debugAll();
        Table moveLeftTbl= new Table();
        numberOFMovesLeft= new Label("18", getLabelStyle(Color.WHITE,GameInfo.WIDTH*0.055f));
        movesLeftLbl = new Label("MOVES LEFT",getLabelStyle(Color.WHITE,GameInfo.WIDTH*0.02f));
        movesLeftLbl.setWrap(true);
        moveLeftTbl.add(numberOFMovesLeft).padRight(GameInfo.WIDTH*0.02f);
        moveLeftTbl.add(movesLeftLbl).width(GameInfo.WIDTH*0.15f).top().padTop(GameInfo.HEIGHT*0.01f);
        add(moveLeftTbl).top().expandX().fillX().row();
        add(createBottom()).bottom().expand().fill();


    }
    private Table createBottom(){
        Table mainTable= new Table();
        mainTable.bottom();
        mainTable.debugAll();
        mainTable.add(getDiceWithRollTbl("blue",true));
        mainTable.add(getDiceWithRollTbl("Green",false)).row();
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
        Array<Table> a= new Array<>();
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
            a.add(t);
        }
        Stack stack= new Stack();
        for (Table b : a) {
            stack.add(b);
        }
        table.add(stack).width(GameInfo.WIDTH * 0.1f).padBottom(GameInfo.HEIGHT * 0.0129625f).row();

        Label diceRoll= new Label("5",getLabelStyle(Color.WHITE,GameInfo.WIDTH*0.03f));
        table.add(diceRoll);

        return table;
    }


    private Table getScoreBoardOfPlayer1(String playerName){
        Table table= new Table();
       table.add(getPlayerNameAndPicture(playerName));

        Table infoIcon= new Table();
        Image infoImg= new Image(new Texture("info.png"));
        infoIcon.add(infoImg).width(GameInfo.WIDTH*0.01f).height(GameInfo.WIDTH*0.01f);



        table.add(infoIcon).row();

        return table;
    }
    private Table getPlayerNameAndPicture(String playerName){
        Table table= new Table();
        Label PlayerName= new Label(playerName,getLabelStyle(Color.YELLOW,GameInfo.WIDTH*0.02f));
        table.add(PlayerName).left().row();

        Table playerPicture=new Table();
        Image playerImg= new Image(new Texture("circle.png"));
        playerPicture.add(playerImg).width(GameInfo.WIDTH*0.1f).height(GameInfo.WIDTH*0.1f);
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
