package com.project.snakeandladder;

import static com.project.snakeandladder.PawnAndPlayerType.BLUE;
import static com.project.snakeandladder.PawnAndPlayerType.GREEN;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;


import Helpers.GameInfo;
import Helpers.Huds;

public class GamePlay implements Screen {

    public Stage getStage() {
        return stage;
    }

    private Stage stage;
    private Board board;


    private Huds huds;
    private GameMain game;


    public static Players player1;
    public static Players player2;


    public GamePlay(GameMain game, Stage stage, String player1Name,String player2Name ) {
        this.game=game;

        board = new Board(this);
        player1 = new Players(game, player1Name, board, BLUE, 1);
        player2 = new Players(game, player2Name, board, GREEN, 2);


        Table container = new Table();
        container.background(bg());
        container.setSize(GameInfo.WIDTH,GameInfo.HEIGHT);

        Stack stack= new Stack();
        stack.setSize(GameInfo.WIDTH,GameInfo.HEIGHT);

        Table hudsContainer = new Table();
        hudsContainer.setSize(GameInfo.WIDTH,GameInfo.HEIGHT);



        huds= new Huds(board, player1, player2, game.getPlayerTurn());

        board.pack();
        board.setPosition(GameInfo.WIDTH/2f,GameInfo.HEIGHT*0.6f, Align.center);
        container.addActor(board);

        hudsContainer.add(huds).top().expandX().fillX().expandY().fillY();
        stack.add(hudsContainer);
        container.add(stack).expand().fill();

        this.stage=stage;
        stage.addActor(container);
    }
    private Drawable bg(){
        Pixmap pixmap=new Pixmap(1,1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.valueOf("#0d4345"));
        pixmap.fill();

        TextureRegionDrawable drawable= new TextureRegionDrawable(new Texture(pixmap));
        pixmap.dispose();
        return drawable;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.valueOf("#0d4345"));

        stage.act();
        stage.draw();

        if(!board.isLadderInitialized())
            board.initLadder();
        if (!board.isSnakeInitialized())
            board.initSnake();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
    public Huds getHuds() {
        return huds;
    }




}
