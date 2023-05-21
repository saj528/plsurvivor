package com.mygdx.panzerliedsurvivor.weapons;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.panzerliedsurvivor.Player;
import com.mygdx.panzerliedsurvivor.enemies.Enemy;
import com.mygdx.panzerliedsurvivor.utils.EnemyUtils;
import com.mygdx.panzerliedsurvivor.utils.GameComponentProvider;

import java.util.Random;

import static com.mygdx.panzerliedsurvivor.utils.GameComponentProvider.getSpriteProcessor;

public class Mp40Weapon extends Weapon {

    float deviation = 8f;

    Random random = new Random();

    public Mp40Weapon(float attackSpeed, float projectileSpeed, Sprite weaponSprite, int damage, int magSize,
                      float reloadSpeed, float range, float projectileDurability, Player player,
                      float weaponTextureScale, float originX, float originY, Vector2 muzzleOffset, int weaponOffsetX, int weaponOffsetY) {
        super(attackSpeed, projectileSpeed, weaponSprite, damage, magSize, reloadSpeed, range, projectileDurability,
                weaponTextureScale, originX, originY, muzzleOffset, weaponOffsetX, weaponOffsetY);
    }

    @Override
    public void render(float delta, SpriteBatch batch) {

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
        Enemy target = EnemyUtils.getNearestEnemyWithinRange(this.soldier.getPlayer().getPlayerBody().getPosition(), range);
        if (target == null)
            return;
        Vector2 direction = target.getBody().getPosition().sub(soldier.getPlayer().getPlayerBody().getPosition()).nor().scl(projectileSpeed);

        float modifiedDeviation = this.deviation / soldier.getAccuracy();

        float deviation = modifiedDeviation == 0 ? 0 : random.nextFloat(2 * modifiedDeviation) - modifiedDeviation;

        direction = direction.rotateDeg(deviation);

        Bullet bullet = new Bullet(damage, soldier.getPlayer().getPlayerBody().getPosition(), direction, projectileSpeed,projectileDurability, getSpriteProcessor().getMiscTextureRegions().get("bullet"));
        GameComponentProvider.addBullet(bullet);
    }
}
