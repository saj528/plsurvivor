package com.mygdx.panzerliedsurvivor.enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.panzerliedsurvivor.Player;
import com.mygdx.panzerliedsurvivor.utils.GameComponentProvider;

public class SimpleEnemy extends Enemy {


    protected SimpleEnemy(int hitpoints, float movementSpeed, float width, float height, TextureRegion sprite) {
        super(hitpoints, movementSpeed, width, height, sprite);
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

    }

    @Override
    public void kill() {
        GameComponentProvider.deleteEnemy(this);
    }
}
