package com.mygdx.panzerliedsurvivor.components;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.panzerliedsurvivor.utils.GameComponentProvider;
import com.mygdx.panzerliedsurvivor.utils.TextWriter;

// TODO Add javadoc to everything

/***
 * This class represents a single instance of the floating text for e.g. damage
 */
public class FloatingText {

    /***
     * The text that is displayed
     */
    private String text;

    /***
     * The starting position of the text
     */
    private Vector2 basePosition;

    /***
     * A timer to track the lifetime of the text and delete when expired
     */
    private Timer lifetime;

//    /***
//     * The max Y offset of the text. Increasing this will cause the text to float higher
//     */
//    private final float maxYOffset = 15f;

    /***
     * The current Y offset of the text. Used to make it float upwards
     */
    private float currYOffset = 0f;

    /***
     * The textWriter that is used to draw the actual text on screen
     */
    private final TextWriter textWriter = GameComponentProvider.getTextWriter();

    /***
     * Creates a new FloatingText object and adds it to the tracked FloatingText objects in GameComponentProvider
     * @param text The text to display
     * @param lifetime Time in seconds that the text should live for
     * @param x x position to create the text
     * @param y y position to create the text
     * @return The floating text object for use if desired
     */
    public static FloatingText createFloatingText(String text, float lifetime, float x, float y) {
        FloatingText floatingText = new FloatingText(text, lifetime, x, y);

        GameComponentProvider.addFloatingText(floatingText);

        return floatingText;
    }

    /***
     * Convenience method to pass the position as a vector instead of x y floats.
     * @param text The text to display
     * @param lifetime Time in seconds that the text should live for
     * @param position The position to draw the text at
     * @return The floating text object for use if desired
     */
    public static FloatingText createFloatingText(String text, float lifetime, Vector2 position) {
        return createFloatingText(text, lifetime, position.x, position.y);
    }

    private FloatingText(String text, float lifetime, float x, float y) {
        this.text = text;
        this.lifetime = new Timer(lifetime);
        this.basePosition = new Vector2(x, y);
    }

    /***
     * Render an individual floating text. Every update, the Y offset is incremented by 1, causing the text to float upwards
     * Also set the transparency of the text so it fades out as the text approaches its max lifetime.
     * @param delta the delta time
     */
    public void render(float delta) {
        float x = basePosition.x;
        float y = basePosition.y + currYOffset;
        currYOffset++;
/* Old logic that causes the floating to look more choppy */
//        float y = basePosition.y + ((lifetime.getTimeSinceLastTrigger() / lifetime.getTriggerTime()) * maxYOffset);
//        if(lifetime.getNumberOfTriggers() > 0)
//            y = maxYOffset;
        float alpha = (1 - (lifetime.getTimeSinceLastTrigger() / lifetime.getTriggerTime()));
        if (lifetime.getNumberOfTriggers() > 0)
            alpha = 0f;
        textWriter.setColor(1, 1, 1, alpha);
        textWriter.drawText(text, x, y);
    }

    /***
     * Update method for FloatingText. Currently just increments the timer, and if it has finished, kills the text.
     * @param delta
     */
    public void update(float delta) {
        if (lifetime.updateTimerAndCheckCompletion(delta)) {
            this.kill();
        }
    }

    private void kill() {
        GameComponentProvider.deleteFloatingText(this);
    }
}
