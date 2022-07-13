package com.project.snakeandladder.gameover;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import Helpers.GameInfo;
import Helpers.Huds;
import Helpers.ImageResize;

public class GameOverDialog extends Dialog {

    public GameOverDialog(String title, WindowStyle windowStyle, String winnerName,int WinnerScore) {
        super(title, windowStyle);
        getTitleTable().top();
        getTitleTable().setBackground(ImageResize.getTextureInRespectOfWidth(new TextureRegion
                (new Texture("TitleBarBg.png")), GameInfo.WIDTH*0.8f));
//        getTitleTable().add(new Label("Score Board",
//                getTitleLabel().getStyle())).center().expand().top().padBottom(GameInfo.WIDTH * 0.1f);
        getContentTable().setBackground(ImageResize.getTextureInRespectOfWidth(new TextureRegion
                (new Texture("ScoreBoard.jpg")), GameInfo.WIDTH*0.8f));
        Label winnerDeclaration= new Label(winnerName + " Won With Score " + WinnerScore, new Label.LabelStyle
                (Huds.getNormalFont
                        (GameInfo.WIDTH * 0.05f), Color.BLACK));
        winnerDeclaration.setWrap(true);
//        winnerDeclaration.setWidth(GameInfo.WIDTH*0.7f);
        winnerDeclaration.setAlignment(Align.center);
        getContentTable().add(winnerDeclaration).width(GameInfo.WIDTH*0.7f).left();
    }
}
