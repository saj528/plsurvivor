package com.mygdx.panzerliedsurvivor.weapons;

import com.badlogic.gdx.graphics.Texture;
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

    TextureRegion bulletTexReg;

    float offsetX = .12f;
    float offsetY = .08f;

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

        this.body.applyLinearImpulse(direction.setLength(speed), this.body.getPosition(), true);
        angle = direction.angleDeg();
        rotatedTexture = new Sprite(bulletTexReg);
        rotatedTexture.flip(true, false);
        rotatedTexture.setOrigin(rotatedTexture.getWidth() / 2, rotatedTexture.getHeight() / 2);
        rotatedTexture.setRotation(angle);
        rotatedTexture.setPosition((startPosition.x - .1f) * Constants.PPM, startPosition.y * Constants.PPM);


    }

    public void update(float delta) {
        rotatedTexture.setPosition((body.getPosition().x - offsetX) * Constants.PPM, (body.getPosition().y - offsetY) * Constants.PPM);
    }

    public void render(float delta) {
        rotatedTexture.draw(GameComponentProvider.getSpriteBatch());
        //GameComponentProvider.getSpriteBatch().draw(bulletTexReg, (body.getPosition().x - offsetX) * Constants.PPM, (body.getPosition().y - offsetY) * Constants.PPM, bulletTexReg.getRegionWidth(), bulletTexReg.getRegionHeight());
    }
}
