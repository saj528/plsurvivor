package com.mygdx.panzerliedsurvivor.weapons;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.panzerliedsurvivor.Player;
import com.mygdx.panzerliedsurvivor.enemies.Enemy;
import com.mygdx.panzerliedsurvivor.utils.GameComponentProvider;

import java.util.Set;

import static com.mygdx.panzerliedsurvivor.utils.GameComponentProvider.getSpriteProcessor;

public class PistolWeapon extends Weapon {


    public PistolWeapon(float attackSpeed, float projectileSpeed, TextureRegion sprite, int damage, float projectileDurability, Player player) {
        super(attackSpeed, projectileSpeed, sprite, damage, projectileDurability, player);
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
        Enemy target = getNearestEnemy();
        if (target == null)
            return;
        Vector2 direction = target.getBody().getPosition().sub(player.getPlayerBody().getPosition()).nor().scl(projectileSpeed);
        Bullet bullet = new Bullet(damage, player.getPlayerBody().getPosition(), direction, projectileSpeed, projectileDurability, getSpriteProcessor().getMiscTextureRegions().get("bullet"));
        GameComponentProvider.addBullet(bullet);
    }

    private Enemy getNearestEnemy() {
        Set<Enemy> enemies = GameComponentProvider.getEnemies();
        if(enemies.isEmpty())
            return null;

        Vector2 weaponPos = this.player.getPlayerBody().getPosition();
        float minDist = Float.MAX_VALUE;
        Enemy nearestEnemy = null;

        for (Enemy enemy : enemies)
        {
            float dist = Math.abs(enemy.getBody().getPosition().sub(weaponPos).len());
            if (dist < minDist) {
                nearestEnemy = enemy;
                minDist = dist;
            }
        }

        return nearestEnemy;
    }
}
