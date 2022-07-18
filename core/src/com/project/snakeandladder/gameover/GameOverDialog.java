package com.project.snakeandladder.gameover;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.project.snakeandladder.Board;
import com.project.snakeandladder.GameMain;
import com.project.snakeandladder.MainMenuScreen;

import Helpers.GameInfo;
import Helpers.Huds;
import Helpers.ImageResize;

public class GameOverDialog extends Dialog {

    public GameOverDialog(String title, WindowStyle windowStyle, String winnerName, int WinnerScore, final Board board) {
        super(title, windowStyle);


        Label titleLbl= new Label("Score Board",  new Label.LabelStyle
                (Huds.getNormalFont(GameInfo.WIDTH * 0.05f), Color.WHITE));
        Table titleTable= new Table();
        titleTable.setBackground(ImageResize.getTextureInRespectOfWidth(new TextureRegion
                (new Texture("TitleBarBg.png")), GameInfo.WIDTH*0.8f));
        titleTable.add(titleLbl);
        Image image = new Image(new Texture("crossBtn.png"));
        titleTable.add(image).width(GameInfo.WIDTH*0.1f).height(GameInfo.WIDTH*0.1f)
                .padRight(-GameInfo.WIDTH*0.19f).padTop(-GameInfo.HEIGHT*0.06f);
        Table contentTbl= new Table();
        contentTbl.setBackground(ImageResize.getTextureInRespectOfWidth(new TextureRegion
                (new Texture("ScoreBoard.jpg")), GameInfo.WIDTH*0.8f));
        Label winnerDeclaration= new Label(winnerName + " Won With Score " + WinnerScore, new Label.LabelStyle
                (Huds.getNormalFont
                        (GameInfo.WIDTH * 0.05f), Color.valueOf("#619f9e")));
        winnerDeclaration.setWrap(true);

        winnerDeclaration.setAlignment(Align.center);
        contentTbl.add(winnerDeclaration).width(GameInfo.WIDTH*0.7f).left();
        getContentTable().add(titleTable).padTop(-GameInfo.HEIGHT*0.14f).row();
        getContentTable().add(contentTbl).padTop(-GameInfo.HEIGHT*0.14f);

        image.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameMain.game.setScreen(new MainMenuScreen(GameMain.stage,GameMain.game));
                board.remove();
            }
        });
    }
}
