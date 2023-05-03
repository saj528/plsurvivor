package com.mygdx.panzerliedsurvivor.weapons;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.panzerliedsurvivor.Player;

import java.util.Random;

import static com.mygdx.panzerliedsurvivor.utils.GameComponentProvider.*;

public class SpreadWeapon extends Weapon {


    public SpreadWeapon(float attackSpeed, float projectileSpeed, TextureRegion sprite, int damage, int magSize,
                        float reloadSpeed, float projectileDurability, Player player) {
        super(attackSpeed, projectileSpeed, sprite, damage, magSize, reloadSpeed, 0, projectileDurability, player);
    }

    @Override
    public void update(float delta) {
        if (currentAmmo == 0) {
            if (reloadTimer.updateTimerAndCheckCompletion(delta)) {
                currentAmmo = magSize;
                reloadTimer.reset();
            }
        }

        if (currentAmmo > 0 && attackTimer.updateTimerAndCheckCompletion(delta)) {
            fire();
            currentAmmo--;
            if (currentAmmo == 0)
                attackTimer.reset();
        }
    }

    private void fire() {
        Bullet bullet = new Bullet(damage, player.getPlayerBody().getPosition(), new Vector2(-1, 0), projectileSpeed, projectileDurability, getSpriteProcessor().getMiscTextureRegions().get("bullet"));
        Bullet bullet1 = new Bullet(damage, player.getPlayerBody().getPosition(), new Vector2(1, 0), projectileSpeed, projectileDurability, getSpriteProcessor().getMiscTextureRegions().get("bullet"));
        Bullet bullet2 = new Bullet(damage, player.getPlayerBody().getPosition(), new Vector2(0, 1), projectileSpeed, projectileDurability, getSpriteProcessor().getMiscTextureRegions().get("bullet"));
        Bullet bullet3 = new Bullet(damage, player.getPlayerBody().getPosition(), new Vector2(0, -1), projectileSpeed, projectileDurability, getSpriteProcessor().getMiscTextureRegions().get("bullet"));
        Bullet bullet4 = new Bullet(damage, player.getPlayerBody().getPosition(), new Vector2(-1, 1), projectileSpeed, projectileDurability, getSpriteProcessor().getMiscTextureRegions().get("bullet"));
        Bullet bullet5 = new Bullet(damage, player.getPlayerBody().getPosition(), new Vector2(1, -1), projectileSpeed, projectileDurability, getSpriteProcessor().getMiscTextureRegions().get("bullet"));
        Bullet bullet6 = new Bullet(damage, player.getPlayerBody().getPosition(), new Vector2(1, 1), projectileSpeed, projectileDurability, getSpriteProcessor().getMiscTextureRegions().get("bullet"));
        Bullet bullet7 = new Bullet(damage, player.getPlayerBody().getPosition(), new Vector2(-1, -1), projectileSpeed, projectileDurability, getSpriteProcessor().getMiscTextureRegions().get("bullet"));
        addBullet(bullet);
        addBullet(bullet1);
        addBullet(bullet2);
        addBullet(bullet3);
        addBullet(bullet4);
        addBullet(bullet5);
        addBullet(bullet6);
        addBullet(bullet7);

    }
}
