package com.project.snakeandladder;

import static com.project.snakeandladder.PawnAndPlayerType.BLUE;
import static com.project.snakeandladder.PawnAndPlayerType.GREEN;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;

import Helpers.GameInfo;
import Helpers.Huds;

public class GamePlay implements Screen {

    private Stage stage;
    private Board board;
    private Huds huds;
    private GameMain game;
    private Players player1;
    private Players player2;
    public GamePlay(GameMain game, Stage stage) {
        this.game=game;

        board = new Board();
        this.player1 = new Players("Vibhor",board,BLUE);
        this.player2 = new Players("Jai",board,GREEN);


        final Table container = new Table();
        container.setSize(GameInfo.WIDTH,GameInfo.HEIGHT);

        Stack stack= new Stack();
        stack.setSize(GameInfo.WIDTH,GameInfo.HEIGHT);

        Table hudsContainer = new Table();
        hudsContainer.setSize(GameInfo.WIDTH,GameInfo.HEIGHT);



        huds= new Huds(board, player1, player2,game.getPlayer1Turn());

        board.pack();
        board.setPosition(GameInfo.WIDTH/2,GameInfo.HEIGHT*0.6f, Align.center);
        container.addActor(board);

        hudsContainer.add(huds).top().expandX().fillX().expandY().fillY();
        stack.add(hudsContainer);
        container.add(stack).expand().fill();

        this.stage=stage;
        stage.addActor(container);
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
}
