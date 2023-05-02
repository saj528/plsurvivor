package com.mygdx.panzerliedsurvivor.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
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

    protected String animationName;

    protected Body body;

    protected Animation<TextureRegion> currentAnimation;

    protected float animationTimer;

    protected boolean isDamaged = false;

    protected float timeDamaged = 0f;

    protected float sizeRadius;

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
        String animationName;
        float sizeRadius;

        switch (type) {
            default:
            case SimpleEnemy:
                health = 10;
                movementSpeed = 1f;
                width = 10;
                height = 10;
                animationName = "batFlying";
                sizeRadius = .3f;
                enemy = new SimpleEnemy(health, movementSpeed, width, height, animationName);
                break;
        }

        Body enemyBody = Box2DBodyIntializer.createEnemyCircleBody(x, y,sizeRadius);

        enemy.setBody(enemyBody);

        enemyBody.setUserData(enemy);

        GameComponentProvider.addEnemy(enemy);

        return enemy;
    }


    protected Enemy(int hitpoints, float movementSpeed, float width, float height, String animationName) {
        this.maxHitpoints = hitpoints;
        this.currentHitpoints = hitpoints;
        this.movementSpeed = movementSpeed;
        this.width = width;
        this.height = height;
        this.animationName = animationName;
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

    public String getAnimationName() {
        return animationName;
    }

    public void setAnimationName(String animationName) {
        this.animationName = animationName;
    }

    public Animation<TextureRegion> getCurrentAnimation() {
        return currentAnimation;
    }

    public void setCurrentAnimation(Animation<TextureRegion> currentAnimation) {
        this.currentAnimation = currentAnimation;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public boolean wasDamaged() {
        return isDamaged;
    }

    public void setDamaged(boolean damaged) {
        this.isDamaged = damaged;
    }
}
