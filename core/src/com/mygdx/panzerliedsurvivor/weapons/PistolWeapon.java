package com.mygdx.panzerliedsurvivor.weapons;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.panzerliedsurvivor.Player;
import com.mygdx.panzerliedsurvivor.enemies.Enemy;
import com.mygdx.panzerliedsurvivor.utils.GameComponentProvider;

import java.util.Set;

import static com.mygdx.panzerliedsurvivor.utils.GameComponentProvider.getSpriteProcessor;

public class PistolWeapon extends Weapon {

    private float projectileSpeed = 0.1f;

    public PistolWeapon(float attackSpeed, TextureRegion sprite, int damage, float projectileDurability, Player player) {
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
        Enemy target = getNearestEnemy();
        if (target == null)
            return;
        Vector2 direction = target.getBody().getPosition().sub(player.getPlayerBody().getPosition()).nor().scl(projectileSpeed);
        Bullet bullet = new Bullet(damage, player.getPlayerBody().getPosition(), direction, projectileSpeed, 5, getSpriteProcessor().getMiscTextureRegions().get("bullet"));
        GameComponentProvider.addBullet(bullet);
    }

    //TODO real implementation
    private Enemy getNearestEnemy() {
        Set<Enemy> enemies = GameComponentProvider.getEnemies();
        return enemies.isEmpty() ? null : enemies.iterator().next();
    }
}
