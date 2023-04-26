package com.mygdx.panzerliedsurvivor.weapons;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.panzerliedsurvivor.Player;
import com.mygdx.panzerliedsurvivor.utils.GameComponentProvider;

import java.util.Random;

import static com.mygdx.panzerliedsurvivor.utils.GameComponentProvider.*;

public class SimpleWeapon extends Weapon {

    private float projectileSpeed = 0.25f;

    public SimpleWeapon(float attackSpeed, TextureRegion sprite, int damage, float projectileDurability, Player player) {
        super(attackSpeed, sprite, damage, projectileDurability, player);
    }

    @Override
    public void update(float delta) {
        timeSinceLastAttack += delta;
        if(timeSinceLastAttack >= attackSpeed) {
            fire();
            timeSinceLastAttack = timeSinceLastAttack - attackSpeed;
        }
    }

    private void fire() {
        Random random = new Random();
        Bullet bullet = new Bullet(damage, player.getPlayerBody().getPosition(), new Vector2(random.nextFloat(2) - 1, random.nextFloat(2) - 1), projectileSpeed, 10, getSpriteProcessor().getMiscTextureRegions().get("bullet"));
        addBullet(bullet);
    }
}
