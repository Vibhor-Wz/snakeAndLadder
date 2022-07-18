package com.project.snakeandladder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.project.snakeandladder.gameover.GameOverDialog;

import Helpers.GameInfo;
import Helpers.Huds;
import Helpers.ImageResize;

public class MainMenuScreen implements Screen {
    private Label snakeAndLadderLbl;
    private TextureRegionDrawable mainMenuBg;
    private TextField player1Name;
    private TextField player2Name;
    private Label player1;
    private Label player2;
    private ImageButton playButton;
    private Stage stage;
    private GameMain game;
    private Table container;
    public  MainMenuScreen(final Stage stage , final GameMain game){
        this.stage=stage;
        this.game=game;
        container= new Table();
        container.top();
        mainMenuBg= new TextureRegionDrawable(new Texture("MainMenuBg.jpg"));
        TextField.TextFieldStyle style= new TextField.TextFieldStyle();
        style.font= Huds.getNormalFont(GameInfo.WIDTH*0.028f);
        style.fontColor= Color.valueOf("#A9A9A9");
        style.cursor=cursor();
        style.background=textFieldBg();


        player1Name= new TextField("", style);
        player1Name.setMessageText("Player 1");
        player1Name.setMaxLength(12);
        player1Name.setTextFieldFilter(new TextField.TextFieldFilter() {
            @SuppressWarnings("NewApi")
            @Override
            public boolean acceptChar(TextField textField, char c) {
                return Character.isAlphabetic(c);
            }
        });

        player2Name = new TextField("", style);
        player2Name.setMessageText("Player 2");
        player2Name.setMaxLength(12);
        player2Name.setTextFieldFilter(new TextField.TextFieldFilter() {
            @SuppressWarnings("NewApi")
            @Override
            public boolean acceptChar(TextField textField, char c) {
               return Character.isAlphabetic(c);
            }
        });
        Label.LabelStyle style1= new Label.LabelStyle();
        style1.font=Huds.getNormalFont(GameInfo.WIDTH*0.028f);
        style1.fontColor=Color.valueOf("#EEEBE8");

        player1= new Label("Player1 Name",style1);
        player1.setAlignment(Align.top);
        player2= new Label("Player2 Name",style1);
        player2.setAlignment(Align.top);
        playButton= new ImageButton(ImageResize.getTextureInRespectOfWidth
                (new TextureRegion(new Texture
                        ("play.png")),GameInfo.WIDTH*0.4f));
        Label.LabelStyle style2= new Label.LabelStyle();
        style2.font=Huds.getNormalFont(GameInfo.WIDTH*0.04f);
        style2.fontColor=Color.valueOf("#EEEBE8");
        snakeAndLadderLbl= new Label("Snake And Ladder", style2);
        snakeAndLadderLbl.setAlignment(Align.top);
        snakeAndLadderLbl.setWrap(true);
        container.background(mainMenuBg);
//        container.debugAll();
        container.add(snakeAndLadderLbl).top().colspan(2);
        container.row().padTop(GameInfo.HEIGHT*0.2f);
        container.add(player1).left().expandX().padLeft(GameInfo.WIDTH*0.05f);
        container.add(player1Name).width(GameInfo.WIDTH*0.35f).expandX();
        container.row().padTop(GameInfo.HEIGHT*0.1f);
        container.add(player2).left().padLeft(GameInfo.WIDTH*0.05f);
        container.add(player2Name).width(GameInfo.WIDTH*0.35f).row();
        container.add(playButton).colspan(2);

        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameMain.movesLeft = 18;
                game.setPlayerTurn(1);
                Gdx.input.setOnscreenKeyboardVisible(false);
                if (!player1Name.getText().equals("") && !player2Name.getText().equals(""))
                    game.setScreen(new GamePlay(game, stage, player1Name.getText(), player2Name.getText()));
                else if (player1Name.getText().equals("") && player2Name.getText().equals("")) {
                    game.setScreen(new GamePlay(game, stage, "Player 1", "Player 2"));
                }
                else{
                    if(player1Name.getText().equals(""))
                        game.setScreen(new GamePlay(game, stage, "Player 1", player2Name.getText()));
                    else
                        game.setScreen(new GamePlay(game, stage, player1Name.getText(), "Player 2"));
                    //

                }
            }

        });
        container.setFillParent(true);
        stage.addActor(container);
    }
    public Drawable cursor(){
        Pixmap pixmap= new Pixmap(2,2, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.valueOf("#A9A9A9"));
        pixmap.fill();
        Drawable drawable= new TextureRegionDrawable(new Texture(pixmap));
        pixmap.dispose();
        return drawable;
    }
    public Drawable textFieldBg() {
        Pixmap pixmap = new Pixmap((int) (GameInfo.WIDTH *0.2f), (int) (GameInfo.HEIGHT *0.03f), Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        Drawable drawable = new TextureRegionDrawable(new Texture(pixmap));
        pixmap.dispose();
        return drawable;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.act();
        stage.draw();

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
