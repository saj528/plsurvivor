package com.mygdx.panzerliedsurvivor.utils;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/***
 * This class is responsible for drawing text to the screen, and provides various helper methods to do so.
 * I'm not sure if this class should even exist, it doesn't really do that much and might be more annoyance than help
 */
public class TextWriter {
    BitmapFont font;

    public TextWriter() {
        font = new BitmapFont();
        font.getData().setScale(0.5f);
    }

    /***
     * A convenience method to write text with a Vector2 instead of x y pos
     * @param text The text to write
     * @param vector2 The position to begin writing the text
     */
    public void drawText(String text, Vector2 vector2) {
        drawText(text, vector2.x, vector2.y);
    }

    /***
     * Draw the provided text to the screen
     * @param text The text to write
     * @param x The X pos to begin writing the text
     * @param y the Y pos to begin writing the text
     */
    public void drawText(String text, float x, float y) {
        SpriteBatch batch = GameComponentProvider.getSpriteBatch();

        font.draw(batch, text, x, y);
    }

    /**
     * This method changes the color of the text being written
     * @param r Red
     * @param g Green
     * @param b Blue
     * @param alpha Transparency (0-1, 0 = invisible, 1 = fully visible)
     */
    public void setColor(float r, float g, float b, float alpha) {
        font.setColor(r, g, b, alpha);
    }
}
