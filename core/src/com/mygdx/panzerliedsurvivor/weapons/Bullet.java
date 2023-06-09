package com.mygdx.panzerliedsurvivor.weapons;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.panzerliedsurvivor.utils.Box2DBodyIntializer;
import com.mygdx.panzerliedsurvivor.utils.Constants;
import com.mygdx.panzerliedsurvivor.utils.GameComponentProvider;

public class Bullet {

    int damage;

    Body body;

    Vector2 direction;

    float speed;

    float durability;

    float timeToLive = 10f;

    float lifetime = 0f;

    TextureRegion bulletTexReg;

    float angle;
    Sprite rotatedTexture;


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

        this.body.setUserData(this);

        this.body.applyLinearImpulse(direction.setLength(speed), this.body.getPosition(), true);
        angle = direction.angleDeg();
        rotatedTexture = new Sprite(bulletTexReg);
        rotatedTexture.flip(true, false);
        rotatedTexture.setOriginCenter();
        rotatedTexture.setRotation(angle);
        rotatedTexture.setPosition(((startPosition.x) * Constants.PPM) - (rotatedTexture.getWidth() / 2), (startPosition.y * Constants.PPM) - (rotatedTexture.getHeight() / 2));


    }

    public void update(float delta) {
        rotatedTexture.setPosition(((body.getPosition().x) * Constants.PPM) - (rotatedTexture.getWidth() / 2), ((body.getPosition().y) * Constants.PPM) - (rotatedTexture.getHeight() / 2));
        lifetime += delta;
        if (lifetime >= timeToLive) {
            this.kill();
        }
    }

    public void render(float delta) {
        rotatedTexture.draw(GameComponentProvider.getSpriteBatch());
    }

    public void kill() {
        GameComponentProvider.deleteBullet(this);
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Vector2 getDirection() {
        return direction;
    }

    public void setDirection(Vector2 direction) {
        this.direction = direction;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getDurability() {
        return durability;
    }

    public void setDurability(float durability) {
        this.durability = durability;
    }
}
