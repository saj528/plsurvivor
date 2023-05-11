package com.mygdx.panzerliedsurvivor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

public class SpriteProcessor {

    private TextureAtlas textureAtlas;
    private TextureRegion[] playerWalkingDownTexReg, playerWalkingUpTexReg,
            playerWalkingLeftTexReg, playerWalkingRightTexReg,
            playerIdleUpTexReg, playerIdleDownTexReg,
            playerIdleRightTexReg, playerIdleLeftTexReg,
            batEnemyFlyingDownTexReg, batEnemyFlyingUpTexReg,
            batEnemyFlyingRightTexReg, batEnemyFlyingLeftTexReg,
            kar98kFiringTexReg, muzzleFlashTexReg;
    private float playerWalkingAnimSpeed = 0.20f;

    private Animation<TextureRegion> playerWalkingDownAnim, playerWalkingUpAnim,
            playerWalkingRightAnim, playerWalkingLeftAnim,
            playerIdleDownAnim, playerIdleUpAnim,
            playerIdleRightAnim, playerIdleLeftAnim,
            batEnemyFlyingDownAnim, batEnemyFlyingUpAnim,
            batEnemyFlyingRightAnim, batEnemyFlyingLeftAnim;

    private Animation<Sprite> kar98kFiringAnim, muzzleFlashAnim;

    private HashMap<String, Animation<TextureRegion>> animations;

    private HashMap<String, TextureRegion> miscTextureRegions;

    private HashMap<String, Animation<Sprite>> spriteAnimations;

    public SpriteProcessor() {

        animations = new HashMap<>();
        miscTextureRegions = new HashMap<>();
        spriteAnimations = new HashMap<>();

        textureAtlas = new TextureAtlas("pls_survivor.atlas");

        miscTextureRegions.put("bullet", textureAtlas.findRegion("bullet"));
        miscTextureRegions.put("kar98k", textureAtlas.findRegion("kar98k"));
        miscTextureRegions.put("m1911", textureAtlas.findRegion("M1911A1"));


        playerWalkingDownTexReg = createSpriteTextureRegion("german_soldier_walking_down", 1, 4);
        playerWalkingUpTexReg = createSpriteTextureRegion("walking_up", 1, 4);
        playerWalkingRightTexReg = createSpriteTextureRegion("german_soldier_walking_right", 1, 4);
        playerWalkingLeftTexReg = createSpriteTextureRegion("german_soldier_walking_left", 1, 4);
        muzzleFlashTexReg = createSpriteTextureRegion("muzzle_flash", 1, 3);
        kar98kFiringTexReg = createSpriteTextureRegion("kar98k_firing", 1, 4);

        playerIdleUpTexReg = new TextureRegion[1];
        playerIdleDownTexReg = new TextureRegion[1];
        playerIdleRightTexReg = new TextureRegion[1];
        playerIdleLeftTexReg = new TextureRegion[1];

        playerIdleUpTexReg[0] = textureAtlas.findRegion("walking_up4");
        playerIdleDownTexReg[0] = textureAtlas.findRegion("german_soldier_walking_down4");
        playerIdleRightTexReg[0] = textureAtlas.findRegion("german_soldier_walking_right4");
        playerIdleLeftTexReg[0] = textureAtlas.findRegion("german_soldier_walking_left4");

        playerWalkingDownAnim = new Animation<TextureRegion>(playerWalkingAnimSpeed, playerWalkingDownTexReg);
        playerWalkingUpAnim = new Animation<TextureRegion>(playerWalkingAnimSpeed, playerWalkingUpTexReg);
        playerWalkingRightAnim = new Animation<TextureRegion>(playerWalkingAnimSpeed, playerWalkingRightTexReg);
        playerWalkingLeftAnim = new Animation<TextureRegion>(playerWalkingAnimSpeed, playerWalkingLeftTexReg);

        muzzleFlashAnim = new Animation<Sprite>(.025f, texRegArrToSpriteArr(muzzleFlashTexReg));
        kar98kFiringAnim = new Animation<Sprite>(.25f, texRegArrToSpriteArr(kar98kFiringTexReg));

        playerIdleUpAnim = new Animation<TextureRegion>(0, playerIdleUpTexReg);
        playerIdleDownAnim = new Animation<TextureRegion>(0, playerIdleDownTexReg);
        playerIdleRightAnim = new Animation<TextureRegion>(0, playerIdleRightTexReg);
        playerIdleLeftAnim = new Animation<TextureRegion>(0, playerIdleLeftTexReg);

        batEnemyFlyingDownTexReg = createSpriteTextureRegion("bat_flying_down", 1, 3);
        batEnemyFlyingUpTexReg = createSpriteTextureRegion("bat_flying_up", 1, 3);
        batEnemyFlyingRightTexReg = createSpriteTextureRegion("bat_flying_right", 1, 3);
        batEnemyFlyingLeftTexReg = createSpriteTextureRegion("bat_flying_left", 1, 3);


        batEnemyFlyingDownAnim = new Animation<TextureRegion>(playerWalkingAnimSpeed, batEnemyFlyingDownTexReg);
        batEnemyFlyingUpAnim = new Animation<TextureRegion>(playerWalkingAnimSpeed, batEnemyFlyingUpTexReg);
        batEnemyFlyingRightAnim = new Animation<TextureRegion>(playerWalkingAnimSpeed, batEnemyFlyingRightTexReg);
        batEnemyFlyingLeftAnim = new Animation<TextureRegion>(playerWalkingAnimSpeed, batEnemyFlyingLeftTexReg);


        //player
        animations.put("playerWalkingUp", playerWalkingDownAnim);
        animations.put("playerWalkingDown", playerWalkingDownAnim);
        animations.put("playerWalkingRight", playerWalkingRightAnim);
        animations.put("playerWalkingLeft", playerWalkingLeftAnim);
        animations.put("playerIdleUp", playerIdleDownAnim);
        animations.put("playerIdleDown", playerIdleDownAnim);
        animations.put("playerIdleRight", playerIdleRightAnim);
        animations.put("playerIdleLeft", playerIdleLeftAnim);

        //bat enemy
        animations.put("batFlyingDown", batEnemyFlyingDownAnim);
        animations.put("batFlyingUp", batEnemyFlyingUpAnim);
        animations.put("batFlyingRight", batEnemyFlyingRightAnim);
        animations.put("batFlyingLeft", batEnemyFlyingLeftAnim);

        //weapons
        //animations.put("muzzleFlash", muzzleFlashAnim);
        spriteAnimations.put("muzzleFlash", muzzleFlashAnim);
        spriteAnimations.put("kar98kFiring", kar98kFiringAnim);


    }

    public TextureRegion[] createSpriteTextureRegion(String spriteSheetName, int startNumber, int count) {
        TextureRegion[] tempTextureRegion = new TextureRegion[count];
        for (int i = 0; i < count; i++) {
            tempTextureRegion[i] = textureAtlas.findRegion(spriteSheetName + startNumber);
            startNumber++;
        }
        return tempTextureRegion;
    }

    public Sprite[] texRegArrToSpriteArr(TextureRegion[] texRegArray) {
        Sprite[] tempSprites = new Sprite[texRegArray.length];

        int count = 0;
        for (TextureRegion texReg : texRegArray) {
            tempSprites[count++] = new Sprite(texReg);
        }
        return tempSprites;
    }

    public HashMap<String, Animation<TextureRegion>> getAnimations() {
        return animations;
    }

    public HashMap<String, TextureRegion> getMiscTextureRegions() {
        return miscTextureRegions;
    }

    public HashMap<String, Animation<Sprite>> getSpriteAnimations() {
        return spriteAnimations;
    }
}
