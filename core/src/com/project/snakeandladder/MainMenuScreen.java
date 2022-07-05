package com.project.snakeandladder;

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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import Helpers.GameInfo;
import Helpers.Huds;
import Helpers.ImageResize;

public class MainMenuScreen implements Screen {
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
        mainMenuBg= new TextureRegionDrawable(new Texture("MainMenuBg.jpeg"));
        TextField.TextFieldStyle style= new TextField.TextFieldStyle();
        style.font= Huds.getNormalFont(GameInfo.WIDTH*0.028f);
        style.fontColor= Color.BLACK;
        style.cursor=cursor();
        style.background=textFieldBg();

        player1Name= new TextField("", style);
        player1Name.setTextFieldFilter(new TextField.TextFieldFilter() {
            @SuppressWarnings("NewApi")
            @Override
            public boolean acceptChar(TextField textField, char c) {
                return Character.isAlphabetic(c);
            }
        });
        player2Name = new TextField("", style);
        player2Name.setTextFieldFilter(new TextField.TextFieldFilter() {
            @SuppressWarnings("NewApi")
            @Override
            public boolean acceptChar(TextField textField, char c) {
               return Character.isAlphabetic(c);
            }
        });
        Label.LabelStyle style1= new Label.LabelStyle();
        style1.font=Huds.getNormalFont(GameInfo.WIDTH*0.028f);
        style1.fontColor=Color.valueOf("347C2C");
        style1.background=labelBg();
        player1= new Label("Player1 Name",style1);
        player1.setAlignment(Align.center);
        player2= new Label("Player2 Name",style1);
        player2.setAlignment(Align.center);
        playButton= new ImageButton(ImageResize.getTextureInRespectOfWidth
                (new TextureRegion(new Texture
                        ("play.png")),GameInfo.WIDTH*0.2f));

        container.background(mainMenuBg);
        container.add(player1).left().expandX().padLeft(GameInfo.WIDTH*0.05f);
        container.add(player1Name).width(GameInfo.WIDTH*0.35f).expandX();
        container.row().expandX();
        container.add(player2).left().padLeft(GameInfo.WIDTH*0.05f).padTop(GameInfo.WIDTH*0.05f);
        container.add(player2Name).padTop(GameInfo.WIDTH*0.05f).width(GameInfo.WIDTH*0.35f).row();
        container.add(playButton).colspan(2);

        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GamePlay(game,stage, player1Name.getText(),player2Name.getText()));
            }
        });
        container.setFillParent(true);
        stage.addActor(container);
    }
    public Drawable cursor(){
        Pixmap pixmap= new Pixmap(2,2, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
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
    public Drawable labelBg(){
        Pixmap pixmap = new Pixmap((int) (GameInfo.WIDTH *0.35f), (int) (GameInfo.HEIGHT *0.03f), Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.valueOf("7E7E7E"));
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
