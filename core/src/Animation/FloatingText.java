package Animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.DistanceFieldFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import Helpers.Font;
import Helpers.GameInfo;

public class FloatingText extends Actor {
    private final String text;
    private final long animationDuration;
    private float deltaX;
    private float deltaY;
    private boolean animated = false;
    private long animationStart;

    private DistanceFieldFont font = new Font(Gdx.files.internal("Fonts/MyFont.fnt"),
            new TextureRegion(new Texture(Gdx.files.internal
                    ("Fonts/MyFont.png"), true))
            , GameInfo.WIDTH*0.02f);

    public FloatingText(String text, long animationDuration) {
        this.text = text;
        this.animationDuration = animationDuration;
    }


    public void setDeltaX(float deltaX) {
        this.deltaX = deltaX;
    }

    public void setDeltaY(float deltaY) {
        this.deltaY = deltaY;
    }


    public void animate() {
        animated = true;
        animationStart = System.currentTimeMillis();
    }

    public boolean isAnimated() {
        return animated;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (animated) {
            // The component will auto-destruct when animation is finished.

            float elapsed = System.currentTimeMillis() - animationStart;

            // The text will be fading.
            font.setColor(getColor().r, getColor().g, getColor().b, parentAlpha * (1 - elapsed / animationDuration));

            font.draw(batch, text, getX() + deltaX * elapsed / 1000f, getY() + deltaY * elapsed / 1000f);
        }
    }
}
