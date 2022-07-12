package Helpers;

import static com.project.snakeandladder.PawnAndPlayerType.BLUE;
import static com.project.snakeandladder.PawnAndPlayerType.GREEN;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.DistanceFieldFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.project.snakeandladder.Board;
import com.project.snakeandladder.GameMain;
import com.project.snakeandladder.GamePlay;
import com.project.snakeandladder.PawnAndPlayerType;
import com.project.snakeandladder.Players;

public class Huds extends Table {
    public static Label numberOFMovesLeft;
    private Label movePawnLbl;
    private Label movesLeftLbl;
    private Board board;
    public static Table pawnBlueTbl;
    public static Table pawnGreenTbl;
    public static Label diceRollPlayer1;
    public static Label diceRollPlayer2;
    public static Players bluePawnPlayer;
    public static Players greenPawnPlayer;
    private int player1Turn;
    public static Label scorePlayer1;
    public static Label scorePlayer2;
    public static Stack movePawnStack1;
    public static Stack movePawnStack2;
    public static Table player2ScoreBoard;
    public static Table player1ScoreBoard;

    public Huds(Board board,Players bluePawnPlayer,Players greenPawnPlayer, int player1Turn){

        this.player1Turn=player1Turn;
        this.bluePawnPlayer = bluePawnPlayer;
        this.greenPawnPlayer =greenPawnPlayer;
        setSize(GameInfo.WIDTH,GameInfo.HEIGHT);
        this.board = board;
        pawnBlueTbl = new Table();
        pawnGreenTbl = new Table();
        Table moveLeftTbl= new Table();
        numberOFMovesLeft= new Label(String.valueOf(GameMain.movesLeft)
                , getLabelStyle(Color.WHITE,GameInfo.WIDTH*0.055f));
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

        mainTable.add(getDiceWithRollTbl(BLUE, 1)).expandX().fillX();
        mainTable.add(getDiceWithRollTbl(GREEN,2)).expandX().fillX().row();
        mainTable.add(getScoreBoardOfPlayer1(bluePawnPlayer.getName())).expandX().fillX();
        mainTable.add(getScoreBoardOfPlayer2(greenPawnPlayer.getName())).expandX().fillX();
        return mainTable;
    }
    private Table getDiceWithRollTbl(PawnAndPlayerType pawnColor, int playerTurn){
        Table table= new Table();
        table.add(getPawnAndDiceRollTbl(pawnColor));
        if(playerTurn ==1) {
            table.add(getMovePawnLblTable1())/*.padLeft(-GameInfo.WIDTH * 0.03f)*/;
        }
        else
            table.add(getMovePawnLblTable2())/*.padLeft(-GameInfo.WIDTH * 0.03f)*/;
        return table;
    }
    private Stack getMovePawnLblTable1(){

        Table movePawnTbl=new Table();
        movePawnStack1 = new Stack();

        Image t= new Image(new Texture("MovePawn.png"));
        t.setColor(Color.valueOf("#6de70a"));
        Table tTbl=new Table();
        tTbl.add(t).width(GameInfo.WIDTH*0.16f).height(GameInfo.HEIGHT*0.06f);

        movePawnLbl= new Label("MOVE PAWN", getLabelStyle(Color.BLACK, GameInfo.WIDTH*0.017f));
        movePawnLbl.setWrap(true);
        movePawnLbl.setAlignment(Align.center);
        movePawnStack1.add(tTbl);

        movePawnTbl.add(movePawnLbl).width(GameInfo.WIDTH*0.2f).padLeft(movePawnStack1.getMinWidth()*0.15f);
        movePawnTbl.align(Align.center);
        movePawnStack1.add(movePawnTbl);


        return movePawnStack1;
    }
    private Stack getMovePawnLblTable2(){
        Table movePawnTbl=new Table();

        movePawnStack2= new Stack();
        Image t= new Image(new Texture("MovePawn.png"));
        t.setColor(Color.valueOf("#6de70a"));
        Table tTbl=new Table();
        tTbl.add(t).width(GameInfo.WIDTH*0.16f).height(GameInfo.HEIGHT*0.06f);

        movePawnLbl= new Label("MOVE PAWN", getLabelStyle(Color.BLACK, GameInfo.WIDTH*0.017f));
        movePawnLbl.setWrap(true);
        movePawnLbl.setAlignment(Align.center);

        movePawnStack2.add(tTbl);
        movePawnTbl.add(movePawnLbl).width(GameInfo.WIDTH*0.2f).padLeft(movePawnStack1.getMinWidth()*0.15f);
        movePawnTbl.align(Align.center);

        movePawnStack2.add(movePawnTbl);
        movePawnStack2.setVisible(false);
        return movePawnStack2;
    }
    private Table getPawnAndDiceRollTbl(PawnAndPlayerType pawnColor){
        Table table= new Table();
        pawnBlueTbl.setTouchable(Touchable.enabled);
        pawnGreenTbl.setTouchable(Touchable.enabled);

            if(pawnColor.equals(BLUE)) {

                pawnBlueTbl.add(bluePawnPlayer.pawns[0]).width(GameInfo.WIDTH * 0.05f).height(GameInfo.HEIGHT * 0.05185f);

                pawnBlueTbl.add(bluePawnPlayer.pawns[1]).width(GameInfo.WIDTH * 0.05f).height(GameInfo.HEIGHT * 0.05185f).padLeft(-GameInfo.WIDTH * 0.025f);

                pawnBlueTbl.add(bluePawnPlayer.pawns[2]).width(GameInfo.WIDTH * 0.05f).height(GameInfo.HEIGHT * 0.05185f).padLeft(-GameInfo.WIDTH * 0.025f);

                table.add(pawnBlueTbl).width(GameInfo.WIDTH * 0.1f).padBottom(GameInfo.HEIGHT * 0.0129625f).row();

                diceRollPlayer1 = new Label(String.valueOf(GameMain.roll),getLabelStyle(Color.WHITE,GameInfo.WIDTH*0.03f));
                table.add(diceRollPlayer1);
            }
            else {

                    pawnGreenTbl.add(greenPawnPlayer.pawns[0]).width(GameInfo.WIDTH * 0.05f).height(GameInfo.HEIGHT * 0.05185f);

                    pawnGreenTbl.add(greenPawnPlayer.pawns[1]).width(GameInfo.WIDTH * 0.05f).height(GameInfo.HEIGHT * 0.05185f).padLeft(-GameInfo.WIDTH * 0.025f);

                    pawnGreenTbl.add(greenPawnPlayer.pawns[2]).width(GameInfo.WIDTH * 0.05f).height(GameInfo.HEIGHT * 0.05185f).padLeft(-GameInfo.WIDTH * 0.025f);

                    table.add(pawnGreenTbl).width(GameInfo.WIDTH * 0.1f).padBottom(GameInfo.HEIGHT * 0.0129625f).row();

                diceRollPlayer2 = new Label("",getLabelStyle(Color.WHITE,GameInfo.WIDTH*0.03f));
                table.add(diceRollPlayer2);
            }

        return table;
    }


    private Table getScoreBoardOfPlayer1(String playerName){
        player1ScoreBoard= new Table();
        Pixmap pixmap= new Pixmap((int) (GameInfo.WIDTH*0.4f), (int) (GameInfo.HEIGHT*0.14f), Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.valueOf("#4C9A2A"));
        pixmap.drawRectangle(1,1, (int) (GameInfo.WIDTH*0.38f), (int) (GameInfo.HEIGHT*0.12f));
        player1ScoreBoard.background(new TextureRegionDrawable(new Texture(pixmap)));
        pixmap.dispose();
        player1ScoreBoard.add(getPlayerNameAndPicture(playerName)).padRight(GameInfo.WIDTH*0.05f);

        player1ScoreBoard.add(getScoreDetailsOfPlayer1()).expandY().fill();

        return player1ScoreBoard;
    }
    private Table getScoreBoardOfPlayer2(String playerName){
        player2ScoreBoard= new Table();

        player2ScoreBoard.add(getPlayerNameAndPicture(playerName)).padRight(GameInfo.WIDTH*0.05f);

        player2ScoreBoard.add(getScoreDetailsOfPlayer2()).expandY().fill();

        return player2ScoreBoard;
    }
    private Table getScoreDetailsOfPlayer1(){
        final Table table = new Table();

        Table infoPlayer1= new Table();
        Image infoImg= new Image(new Texture("info.png"));
        infoPlayer1.add(infoImg).width(GameInfo.WIDTH*0.05f).height(GameInfo.WIDTH*0.05f);

        table.add(infoPlayer1).row();

        scorePlayer1 = new Label(String.valueOf(bluePawnPlayer.getPlayerScore()),getLabelStyle(Color.SKY,GameInfo.WIDTH*0.042f));
        table.add(scorePlayer1).row();

        Label scoreTxt = new Label("SCORE",getLabelStyle(Color.SKY, GameInfo.WIDTH*0.023f));
        table.add(scoreTxt);
        infoPlayer1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });
        return table;
    }
    private Table getScoreDetailsOfPlayer2(){
        Table table = new Table();

        Table infoPlayer2= new Table();
        Image infoImg= new Image(new Texture("info.png"));
        infoPlayer2.add(infoImg).width(GameInfo.WIDTH*0.05f).height(GameInfo.WIDTH*0.05f);

        table.add(infoPlayer2).top().row();

        scorePlayer2 = new Label(String.valueOf(greenPawnPlayer.getPlayerScore()),getLabelStyle(Color.GREEN,GameInfo.WIDTH*0.042f));
        table.add(scorePlayer2).row();

        Label scoreTxt = new Label("SCORE",getLabelStyle(Color.SKY, GameInfo.WIDTH*0.023f));
        table.add(scoreTxt);
        infoPlayer2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });
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
    public static BitmapFont getNormalFont(float size) {
        Texture texture = new Texture(Gdx.files.internal
                ("Fonts/MyFont.png"), true);

        DistanceFieldFont font = new Font(Gdx.files.internal("Fonts/MyFont.fnt"),
                new TextureRegion(texture), size);
        return font;

    }
    public Label.LabelStyle getLabelStyle(Color color, float fontSize){
        Label.LabelStyle style= new Label.LabelStyle();
        style.font=getNormalFont(fontSize);
        style.fontColor=color;

        return style;
    }


}
