package com.mygdx.panzerliedsurvivor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.panzerliedsurvivor.utils.GameComponentProvider;
import com.mygdx.panzerliedsurvivor.weapons.Weapon;

import java.util.ArrayList;

import static com.mygdx.panzerliedsurvivor.utils.Constants.PPM;

public class Player {

    private int maxHitpoints;

    private int currentHitpoints;

    private SpriteBatch batch;
    private SpriteProcessor spriteProcessor;

    private float walkingTimer;

    private Animation<TextureRegion> currentAnimation;

    private String direction;

    private Body playerBody;

    private boolean isPlayerUsing = false;

    private ArrayList<Weapon> weapons;

    public Player(SpriteBatch batch, SpriteProcessor spriteProcessor, MapObjects mapObjects, Body playerBody) {

        this.batch = batch;

        this.spriteProcessor = spriteProcessor;

        this.playerBody = playerBody;

        currentAnimation = spriteProcessor.getAnimations().get("playerWalkingDown");
        direction = "down";

        walkingTimer = 0;

        weapons = new ArrayList<>();
        Weapon.addWeapon(Weapon.WeaponType.Kar98k, this);
        //Weapon.addWeapon(Weapon.WeaponType.SpreadWeapon, this);
        //Weapon.addWeapon(Weapon.WeaponType.Mp40, this);

        this.currentHitpoints = 10;
        this.maxHitpoints = 10;
    }

    public void renderAndUpdate(float delta) {
        walkingTimer += delta;
        batch.draw(currentAnimation.getKeyFrame(walkingTimer, true), playerBody.getPosition().x * PPM - 8, playerBody.getPosition().y * PPM - 16);

        for (Weapon weapon : weapons) {
            weapon.update(delta);
            weapon.render(delta, batch);
        }

    }

    public void detectInput(float delta) {


        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            isPlayerUsing = true;
        }

        float speed = 3f;

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
        playerBody.setLinearVelocity(new Vector2(horizontalForce * speed, verticalForce * speed));

    }

    public void kill() {

    }

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

    public boolean isPlayerUsing() {
        return isPlayerUsing;
    }

    public void setPlayerUsing(boolean playerUsing) {
        isPlayerUsing = playerUsing;
    }

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    public void setWeapons(ArrayList<Weapon> weapons) {
        this.weapons = weapons;
    }
}
