package com.mygdx.panzerliedsurvivor.utils;

import com.badlogic.gdx.graphics.Camera;

/**
 * Class to hold various utility methods related to the camera
 */
public class CameraUtils {

    private static final Camera camera = GameComponentProvider.getCamera();

    /***
     * Given a point (x,y) in PIXELS, determine if it is currently visible on screen
     * @param x the x value of the point in pixels
     * @param y the Y value of the point in pixels
     * @return true if the point is within the camera's viewport, false otherwise
     */
    public static boolean isPointOnScreen(float x, float y) {
        float halfWidth = camera.viewportWidth / 2;
        float halfHeight = camera.viewportHeight / 2;
        boolean xAligned = x <= camera.position.x + halfWidth && x >= camera.position.x - halfWidth;
        boolean yAligned = y <= camera.position.y + halfHeight && y >= camera.position.y - halfHeight;

        return xAligned && yAligned;
    }
}
