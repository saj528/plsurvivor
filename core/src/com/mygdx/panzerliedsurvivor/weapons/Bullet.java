package com.mygdx.panzerliedsurvivor.weapons;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.panzerliedsurvivor.utils.Box2DBodyIntializer;

public class Bullet {

    int damage;

    Body body;

    Vector2 direction;

    float speed;

    float durability;

    TextureRegion bulletTexReg;

    /***
     * This constructor creates a bullet at a specific location and applies an impulse to it to cause it to move
     * @param damage the damage of the bullet
     * @param startPosition where the bullet will be created
     * @param direction the direction that the bullet should travel
     * @param speed the speed of the bullet
     * @param durability the durability of the bullet
     * @param bulletTexReg the region of the sprite map for the bullet's sprite
     */
    public Bullet(int damage, Vector2 startPosition, Vector2 direction, float speed, float durability, TextureRegion bulletTexReg) {
        this.damage = damage;
        this.direction = direction;
        this.speed = speed;
        this.durability = durability;
        this.bulletTexReg = bulletTexReg;

        this.body = Box2DBodyIntializer.createBulletBody(startPosition);

        this.body.applyLinearImpulse(direction.setLength(speed), this.body.getPosition(), true);
    }

    public void update(float delta) {
//        this.body.applyLinearImpulse(direction.setLength(0.00001f), this.body.getPosition(), true);
    }

    public void render(float delta) {
//        batch.draw(currentAnimation.getKeyFrame(walkingTimer, true), playerBody.getPosition().x * PPM - 8, playerBody.getPosition().y * PPM - 16);
    }
}
