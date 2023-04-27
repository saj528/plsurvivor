package com.mygdx.panzerliedsurvivor.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.panzerliedsurvivor.SpriteProcessor;
import com.mygdx.panzerliedsurvivor.weapons.Bullet;

import java.util.ArrayList;

/***
 * This class provides certain game components that are used across the application (e.g. game world),
 * to avoid having to pass them to every object that makes use of them
 */
public class GameComponentProvider {

    private static World gameWorld;
    private static SpriteBatch spriteBatch;
    private static SpriteProcessor spriteProcessor;

    private static ArrayList<Bullet> bullets = new ArrayList<>();

    public static World getGameWorld() {

        if (gameWorld == null) {
            gameWorld = new World(new Vector2(0, 0), false);
        }
        return gameWorld;
    }

    public static SpriteBatch getSpriteBatch() {

        if (spriteBatch == null) {
            spriteBatch = new SpriteBatch();
        }

        return spriteBatch;
    }

    public static SpriteProcessor getSpriteProcessor() {

        if (spriteProcessor == null) {
            spriteProcessor = new SpriteProcessor();
        }
        return spriteProcessor;
    }

    public static ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public static void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }
}
