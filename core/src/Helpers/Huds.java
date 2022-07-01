package Helpers;

import static com.project.snakeandladder.PawnAndPlayerType.BLUE;
import static com.project.snakeandladder.PawnAndPlayerType.GREEN;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.DistanceFieldFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.project.snakeandladder.Board;
import com.project.snakeandladder.PawnAndPlayerType;
import com.project.snakeandladder.Players;

public class Huds extends Table {
    private Label numberOFMovesLeft;
    private Label movePawnLbl;
    private Label movesLeftLbl;
    private Board board;
    private Table pawnBlueTbl;
    private Table pawnGreenTbl;
    public static Label diceRollPlayer1;
    public static Label diceRollPlayer2;
    private Players bluePawnPlayer;
    private Players greenPawnPlayer;
    private Boolean player1Turn;
    public static Label scorePlayer1;
    public static Label scorePlayer2;

    public Huds(Board board,Players bluePawnPlayer,Players greenPawnPlayer, Boolean player1Turn){
        this.player1Turn=player1Turn;
        this.bluePawnPlayer = bluePawnPlayer;
        this.greenPawnPlayer =greenPawnPlayer;
        setSize(GameInfo.WIDTH,GameInfo.HEIGHT);
        this.board = board;
        pawnBlueTbl = new Table();
        pawnGreenTbl = new Table();
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

        mainTable.add(getDiceWithRollTbl(BLUE,player1Turn)).expandX().fillX();
        mainTable.add(getDiceWithRollTbl(GREEN,(!player1Turn))).expandX().fillX().row();
        mainTable.add(getScoreBoardOfPlayer1(bluePawnPlayer.getName())).expandX().fillX();
        mainTable.add(getScoreBoardOfPlayer2(greenPawnPlayer.getName())).expandX().fillX();
        return mainTable;
    }
    private Table getDiceWithRollTbl(PawnAndPlayerType pawnColor, boolean playerTurn){
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

    private Table getPawnAndDiceRollTbl(PawnAndPlayerType pawnColor){
        Table table= new Table();
        pawnBlueTbl.setTouchable(Touchable.enabled);
        pawnGreenTbl.setTouchable(Touchable.enabled);

            if(pawnColor.equals(BLUE)) {

                pawnBlueTbl.add(bluePawnPlayer.pawns[0].pawn).width(GameInfo.WIDTH * 0.05f).height(GameInfo.HEIGHT * 0.05185f);

                pawnBlueTbl.add(bluePawnPlayer.pawns[1].pawn).width(GameInfo.WIDTH * 0.05f).height(GameInfo.HEIGHT * 0.05185f).padLeft(-GameInfo.WIDTH * 0.025f);

                pawnBlueTbl.add(bluePawnPlayer.pawns[2].pawn).width(GameInfo.WIDTH * 0.05f).height(GameInfo.HEIGHT * 0.05185f).padLeft(-GameInfo.WIDTH * 0.025f);

                table.add(pawnBlueTbl).width(GameInfo.WIDTH * 0.1f).padBottom(GameInfo.HEIGHT * 0.0129625f).row();

            }
            else {

                    pawnGreenTbl.add(greenPawnPlayer.pawns[0].pawn).width(GameInfo.WIDTH * 0.05f).height(GameInfo.HEIGHT * 0.05185f);

                    pawnGreenTbl.add(greenPawnPlayer.pawns[1].pawn).width(GameInfo.WIDTH * 0.05f).height(GameInfo.HEIGHT * 0.05185f).padLeft(-GameInfo.WIDTH * 0.025f);

                    pawnGreenTbl.add(greenPawnPlayer.pawns[2].pawn).width(GameInfo.WIDTH * 0.05f).height(GameInfo.HEIGHT * 0.05185f).padLeft(-GameInfo.WIDTH * 0.025f);

                    table.add(pawnGreenTbl).width(GameInfo.WIDTH * 0.1f).padBottom(GameInfo.HEIGHT * 0.0129625f).row();

            }


        diceRollPlayer1 = new Label("5",getLabelStyle(Color.WHITE,GameInfo.WIDTH*0.03f));
        table.add(diceRollPlayer1);

        return table;
    }

    private Table getScoreBoardOfPlayer1(String playerName){
        Table table= new Table();

        table.add(getPlayerNameAndPicture(playerName)).padRight(GameInfo.WIDTH*0.05f);

            table.add(getScoreDetailsOfPlayer1()).top().expandY().fill();

        return table;
    }
    private Table getScoreBoardOfPlayer2(String playerName){
        Table table= new Table();

        table.add(getPlayerNameAndPicture(playerName)).padRight(GameInfo.WIDTH*0.05f);

        table.add(getScoreDetailsOfPlayer2()).top().expandY().fill();

        return table;
    }
    private Table getScoreDetailsOfPlayer1(){
        Table table = new Table();
        table.top();
        Table infoPlayer1= new Table();
        Image infoImg= new Image(new Texture("info.png"));
        infoPlayer1.add(infoImg).width(GameInfo.WIDTH*0.05f).height(GameInfo.WIDTH*0.05f);

        table.add(infoPlayer1).row();

        scorePlayer1 = new Label(String.valueOf(bluePawnPlayer.getPlayerScore()),getLabelStyle(Color.SKY,GameInfo.WIDTH*0.042f));
        table.add(scorePlayer1).row();

        Label scoreTxt = new Label("SCORE",getLabelStyle(Color.SKY, GameInfo.WIDTH*0.023f));
        table.add(scoreTxt);
        return table;
    }
    private Table getScoreDetailsOfPlayer2(){
        Table table = new Table();
        table.top();
        Table infoPlayer2= new Table();
        Image infoImg= new Image(new Texture("info.png"));
        infoPlayer2.add(infoImg).width(GameInfo.WIDTH*0.05f).height(GameInfo.WIDTH*0.05f);

        table.add(infoPlayer2).top().row();

        scorePlayer2 = new Label(String.valueOf(greenPawnPlayer.getPlayerScore()),getLabelStyle(Color.GREEN,GameInfo.WIDTH*0.042f));
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
