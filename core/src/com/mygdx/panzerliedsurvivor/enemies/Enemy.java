package com.mygdx.panzerliedsurvivor.enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.panzerliedsurvivor.utils.Box2DBodyIntializer;
import com.mygdx.panzerliedsurvivor.utils.GameComponentProvider;

public abstract class Enemy {

    public enum EnemyType {
        SimpleEnemy
    }
    protected int maxHitpoints;

    protected int currentHitpoints;

    protected float movementSpeed;

    protected float width;

    protected float height;

    protected TextureRegion sprite;

    protected Body body;

    /***
     * Create an Enemy of the given type at the given position
     * @param type The type of enemy to create. This is where type specific details (hp, speed etc.) will be defined
     * @param x x coord to place the enemy
     * @param y y coord to place the enemy
     * @return the Enemy that was created
     */
    public static Enemy createEnemy(EnemyType type, float x, float y) {
        Enemy enemy;

        int health;
        float movementSpeed, width, height;
        TextureRegion sprite = null;

        switch(type) {
            default:
            case SimpleEnemy:
                health = 10;
                movementSpeed = 1f;
                width = 10;
                height = 10;
                enemy = new SimpleEnemy(health, movementSpeed, width, height, sprite);
        }

        Body enemyBody = Box2DBodyIntializer.createEnemyBody(enemy, x, y);

        enemy.setBody(enemyBody);

        enemyBody.setUserData(enemy);

        GameComponentProvider.addEnemy(enemy);

        return enemy;
    }

    protected Enemy(int hitpoints, float movementSpeed, float width, float height, TextureRegion sprite) {
        this.maxHitpoints = hitpoints;
        this.currentHitpoints = hitpoints;
        this.movementSpeed = movementSpeed;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
    }

    public abstract void update(float delta);

    public abstract void render(float delta);

    public abstract void kill();

    public int getMaxHitpoints() {
        return maxHitpoints;
    }

    public void setMaxHitpoints(int maxHitpoints) {
        this.maxHitpoints = maxHitpoints;
    }

    public int getCurrentHitpoints() {
        return currentHitpoints;
    }

    public void setCurrentHitpoints(int currentHitpoints) {
        this.currentHitpoints = currentHitpoints;
    }

    public float getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(float movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public TextureRegion getSprite() {
        return sprite;
    }

    public void setSprite(TextureRegion sprite) {
        this.sprite = sprite;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }
}
