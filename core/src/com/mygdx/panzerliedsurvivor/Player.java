package com.mygdx.panzerliedsurvivor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.panzerliedsurvivor.weapons.PistolWeapon;
import com.mygdx.panzerliedsurvivor.weapons.SpreadWeapon;
import com.mygdx.panzerliedsurvivor.weapons.Weapon;

import java.util.ArrayList;

import static com.mygdx.panzerliedsurvivor.utils.Constants.PPM;

public class Player {

    private int maxHitpoints;

    private int currentHitpoints;

    private SpriteBatch batch;
    private SpriteProcessor spriteProcessor;

    //private Rectangle playerBoundingBox;

    private float walkingTimer;

    private Animation<TextureRegion> currentAnimation;

    private String direction;

    private MapObjects mapObjects;

    private Rectangle playerUseBox;

    private Body playerBody;

    private boolean isPlayerUsing = false;

    private ArrayList<Weapon> weapons;

    public Player(SpriteBatch batch, SpriteProcessor spriteProcessor, MapObjects mapObjects,Body playerBody) {

        this.batch = batch;

        this.spriteProcessor = spriteProcessor;

        this.mapObjects = mapObjects;

        this.playerBody = playerBody;

        currentAnimation = spriteProcessor.getAnimations().get("playerWalkingDown");
        direction = "down";

        walkingTimer = 0;

        weapons = new ArrayList<>();
        weapons.add(new SpreadWeapon(3f, null, 10, 10, this));
        weapons.add(new PistolWeapon(2f, null, 5, 5 , this));

        this.currentHitpoints = 10;
        this.maxHitpoints = 10;
    }

    public void renderAndUpdate(float delta) {
        walkingTimer += delta;
        batch.draw(currentAnimation.getKeyFrame(walkingTimer, true), playerBody.getPosition().x * PPM - 8, playerBody.getPosition().y * PPM - 16);

        for(Weapon weapon : weapons) {
            weapon.update(delta);
        }

    }

    public void detectInput(float delta) {


        if(Gdx.input.isKeyPressed(Input.Keys.E)){
            isPlayerUsing = true;
        }

        float speed = 10f;
/*        for (RectangleMapObject rectangleObject : mapObjects.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();
            if (Intersector.overlaps(rectangle, playerBoundingBox)) {
                if (direction.equals("up")) {
                    playerBoundingBox.y -= speed * delta;
                } else if (direction.equals("down")) {
                    playerBoundingBox.y += speed * delta;
                } else if (direction.equals("left")) {
                    playerBoundingBox.x += speed * delta;
                } else if (direction.equals("right")) {
                    playerBoundingBox.x -= speed * delta;
                }
            }
        }*/


        int horizontalForce = 0;
        int verticalForce = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            verticalForce = 1;
            direction = "up";
            currentAnimation = spriteProcessor.getAnimations().get("playerWalkingUp");
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            verticalForce = -1;
            direction = "down";
            currentAnimation = spriteProcessor.getAnimations().get("playerWalkingDown");
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            horizontalForce = -1;
            direction = "left";
            currentAnimation = spriteProcessor.getAnimations().get("playerWalkingLeft");
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            horizontalForce = 1;
            direction = "right";
            currentAnimation = spriteProcessor.getAnimations().get("playerWalkingRight");
        } else {
            if (direction.equals("up")) {
                currentAnimation = spriteProcessor.getAnimations().get("playerIdleUp");
            } else if (direction.equals("down")) {
                currentAnimation = spriteProcessor.getAnimations().get("playerIdleDown");
            } else if (direction.equals("right")) {
                currentAnimation = spriteProcessor.getAnimations().get("playerIdleRight");
            } else if (direction.equals("left")) {
                currentAnimation = spriteProcessor.getAnimations().get("playerIdleLeft");
            }
        }
        playerBody.setLinearVelocity(new Vector2(horizontalForce * speed,verticalForce * speed));

    }

    public void kill() {

    }


/*    public Rectangle getPlayerBoundingBox() {
        return playerBoundingBox;
    }*/

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

    public Body getPlayerBody() {
        return playerBody;
    }

    public Rectangle getPlayerUseBox() {
        return playerUseBox;
    }

    public boolean isPlayerUsing() {
        return isPlayerUsing;
    }

    public void setPlayerUsing(boolean playerUsing) {
        isPlayerUsing = playerUsing;
    }
}
