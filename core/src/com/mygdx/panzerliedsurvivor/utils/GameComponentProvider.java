package com.mygdx.panzerliedsurvivor.utils;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.panzerliedsurvivor.components.FloatingText;
import com.mygdx.panzerliedsurvivor.components.GameContactListener;
import com.mygdx.panzerliedsurvivor.enemies.Enemy;
import com.mygdx.panzerliedsurvivor.SpriteProcessor;
import com.mygdx.panzerliedsurvivor.weapons.Bullet;
import com.mygdx.panzerliedsurvivor.Player;

import java.util.HashSet;
import java.util.Set;

/***
 * This class provides certain game components that are used across the application (e.g. game world),
 * to avoid having to pass them to every object that makes use of them
 */
public class GameComponentProvider {

    private static World gameWorld;
    private static SpriteBatch spriteBatch;
    private static SpriteProcessor spriteProcessor;
    private static OrthographicCamera camera;


    private static Set<Bullet> bullets = new HashSet<>();

    private static Set<Bullet> bulletsToDelete = new HashSet<>();

    private static Set<Enemy> enemies = new HashSet<>();

    private static Set<Enemy> enemiesToDelete = new HashSet<>();

    private static Set<FloatingText> floatingTexts = new HashSet<>();

    private static Set<FloatingText> floatingTextsToDelete = new HashSet<>();

    private static Player player;

    private static TextWriter textWriter;

    private static ShapeRenderer shapeRenderer;

    public static World getGameWorld() {

        if (gameWorld == null) {
            gameWorld = new World(new Vector2(0, 0), false);
            gameWorld.setContactListener(new GameContactListener());
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

    public static OrthographicCamera getCamera() {

        if (camera == null) {
            camera = new OrthographicCamera();
        }
        return camera;
    }

    public static TextWriter getTextWriter() {
        if (textWriter == null) {
            textWriter = new TextWriter();
        }
        return textWriter;
    }

    public static ShapeRenderer getShapeRenderer() {
        if (shapeRenderer == null) {
            shapeRenderer = new ShapeRenderer();
        }
        return shapeRenderer;
    }

    public static Set<Bullet> getBullets() {
        return bullets;
    }

    public static void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    public static void deleteBullet(Bullet bullet) {
        bulletsToDelete.add(bullet);
    }

    public static Set<Enemy> getEnemies() {
        return enemies;
    }

    public static void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public static void deleteEnemy(Enemy enemy) {
        enemiesToDelete.add(enemy);
    }

    public static void setPlayer(Player player) {
        GameComponentProvider.player = player;
    }

    public static Player getPlayer() {
        return player;
    }

    public static Set<FloatingText> getFloatingTexts() {
        return floatingTexts;
    }

    public static void addFloatingText(FloatingText text) {
        floatingTexts.add(text);
    }

    public static void deleteFloatingText(FloatingText text) {
        floatingTextsToDelete.add(text);
    }

    public static void cleanObjects() {
        for(Enemy enemy : enemiesToDelete) {
            gameWorld.destroyBody(enemy.getBody());
        }
        enemies.removeAll(enemiesToDelete);
        enemiesToDelete.clear();

        for(Bullet bullet : bulletsToDelete) {
            gameWorld.destroyBody(bullet.getBody());
        }
        bullets.removeAll(bulletsToDelete);
        bulletsToDelete.clear();

        floatingTexts.removeAll(floatingTextsToDelete);
    }
}
