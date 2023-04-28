package com.mygdx.panzerliedsurvivor.enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.panzerliedsurvivor.Player;
import com.mygdx.panzerliedsurvivor.utils.GameComponentProvider;

import static com.mygdx.panzerliedsurvivor.utils.Constants.PPM;

public class SimpleEnemy extends Enemy {


    float animationOffsetX,animationOffsetY;




    protected SimpleEnemy(int hitpoints, float movementSpeed, float width, float height,String animationName) {
        super(hitpoints, movementSpeed, width, height,animationName);

        currentAnimation = GameComponentProvider.getSpriteProcessor().getAnimations().get(animationName + "Down");

        animationOffsetX = currentAnimation.getKeyFrames()[0].getRegionWidth() / 2;
        animationOffsetY = currentAnimation.getKeyFrames()[0].getRegionHeight() / 2;

        animationTimer = 0;
    }

    @Override
    public void update(float delta) {
        Player player = GameComponentProvider.getPlayer();
        Vector2 playerPos = player.getPlayerBody().getPosition();
        Vector2 heading = playerPos.sub(body.getPosition());
        heading.nor();
        heading.scl(movementSpeed);

        body.setLinearVelocity(heading);


    }

    @Override
    public void render(float delta) {
        animationTimer += delta;
        GameComponentProvider.getSpriteBatch().draw(currentAnimation.getKeyFrame(animationTimer, true), (body.getPosition().x * PPM) - animationOffsetX, (body.getPosition().y * PPM) - animationOffsetY);

    }
}
