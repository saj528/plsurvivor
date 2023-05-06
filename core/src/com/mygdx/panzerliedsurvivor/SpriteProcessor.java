package com.mygdx.panzerliedsurvivor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
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
            batEnemyFlyingRightTexReg, batEnemyFlyingLeftTexReg;
    private float playerWalkingAnimSpeed = 0.20f;

    private Animation<TextureRegion> playerWalkingDownAnim, playerWalkingUpAnim,
            playerWalkingRightAnim, playerWalkingLeftAnim,
            playerIdleDownAnim, playerIdleUpAnim,
            playerIdleRightAnim, playerIdleLeftAnim,
            batEnemyFlyingDownAnim, batEnemyFlyingUpAnim,
            batEnemyFlyingRightAnim, batEnemyFlyingLeftAnim;

    private HashMap<String, Animation<TextureRegion>> animations;

    private HashMap<String, TextureRegion> miscTextureRegions;

    public SpriteProcessor() {

        animations = new HashMap<>();
        miscTextureRegions = new HashMap<>();

        textureAtlas = new TextureAtlas("pls_survivor.atlas");

        miscTextureRegions.put("bullet", textureAtlas.findRegion("bullet"));
        miscTextureRegions.put("m1garand_hands", textureAtlas.findRegion("m1garand_hands"));


        playerWalkingDownTexReg = createSpriteTextureRegion("walking_down", 1, 4);
        playerWalkingUpTexReg = createSpriteTextureRegion("walking_up", 1, 4);
        playerWalkingRightTexReg = createSpriteTextureRegion("walking_right", 1, 4);
        playerWalkingLeftTexReg = createSpriteTextureRegion("walking_left", 1, 4);


        playerIdleUpTexReg = new TextureRegion[1];
        playerIdleDownTexReg = new TextureRegion[1];
        playerIdleRightTexReg = new TextureRegion[1];
        playerIdleLeftTexReg = new TextureRegion[1];

        playerIdleUpTexReg[0] = textureAtlas.findRegion("walking_up4");
        playerIdleDownTexReg[0] = textureAtlas.findRegion("walking_down4");
        playerIdleRightTexReg[0] = textureAtlas.findRegion("walking_right4");
        playerIdleLeftTexReg[0] = textureAtlas.findRegion("walking_left4");

        playerWalkingDownAnim = new Animation<TextureRegion>(playerWalkingAnimSpeed, playerWalkingDownTexReg);
        playerWalkingUpAnim = new Animation<TextureRegion>(playerWalkingAnimSpeed, playerWalkingUpTexReg);
        playerWalkingRightAnim = new Animation<TextureRegion>(playerWalkingAnimSpeed, playerWalkingRightTexReg);
        playerWalkingLeftAnim = new Animation<TextureRegion>(playerWalkingAnimSpeed, playerWalkingLeftTexReg);

        playerIdleUpAnim = new Animation<TextureRegion>(0, playerIdleUpTexReg);
        playerIdleDownAnim = new Animation<TextureRegion>(0, playerIdleDownTexReg);
        playerIdleRightAnim = new Animation<TextureRegion>(0, playerIdleRightTexReg);
        playerIdleLeftAnim = new Animation<TextureRegion>(0, playerIdleLeftTexReg);

        batEnemyFlyingDownTexReg = createSpriteTextureRegion("bat_flying_down", 1, 3);
        batEnemyFlyingUpTexReg = createSpriteTextureRegion("bat_flying_up", 1, 3);
        batEnemyFlyingRightTexReg = createSpriteTextureRegion("bat_flying_right", 1, 3);
        batEnemyFlyingLeftTexReg = createSpriteTextureRegion("bat_flying_left", 1, 3);


        batEnemyFlyingDownAnim = new Animation<TextureRegion>(playerWalkingAnimSpeed,batEnemyFlyingDownTexReg);
        batEnemyFlyingUpAnim = new Animation<TextureRegion>(playerWalkingAnimSpeed,batEnemyFlyingUpTexReg);
        batEnemyFlyingRightAnim = new Animation<TextureRegion>(playerWalkingAnimSpeed,batEnemyFlyingRightTexReg);
        batEnemyFlyingLeftAnim = new Animation<TextureRegion>(playerWalkingAnimSpeed,batEnemyFlyingLeftTexReg);


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


    }

    public TextureRegion[] createSpriteTextureRegion(String spriteSheetName, int startNumber, int count) {
        TextureRegion[] tempTextureRegion = new TextureRegion[count];
        for (int i = 0; i < count; i++) {
            tempTextureRegion[i] = textureAtlas.findRegion(spriteSheetName + startNumber);
            startNumber++;
        }
        return tempTextureRegion;
    }

    public HashMap<String, Animation<TextureRegion>> getAnimations() {
        return animations;
    }

    public HashMap<String, TextureRegion> getMiscTextureRegions() {
        return miscTextureRegions;
    }
}
