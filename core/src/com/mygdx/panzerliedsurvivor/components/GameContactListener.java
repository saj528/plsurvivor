package com.mygdx.panzerliedsurvivor.components;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.panzerliedsurvivor.Player;
import com.mygdx.panzerliedsurvivor.enemies.Enemy;
import com.mygdx.panzerliedsurvivor.weapons.Bullet;

public class GameContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Object objectA = contact.getFixtureA().getBody().getUserData();
        Object objectB = contact.getFixtureB().getBody().getUserData();
        if ((objectA instanceof Enemy && objectB instanceof Bullet)
                || (objectA instanceof Bullet && objectB instanceof Enemy)) {
            Enemy enemy = objectA instanceof Enemy ? (Enemy) objectA : (Enemy) objectB;
            Bullet bullet = objectA instanceof Bullet ? (Bullet) objectA : (Bullet) objectB;
            handleEnemyBulletContact(bullet, enemy);
        }

        if ((objectA instanceof Enemy && objectB instanceof Player)
                || (objectA instanceof Player && objectB instanceof Enemy)) {
            Player player = objectA instanceof Player ? (Player) objectA : (Player) objectB;
            Enemy enemy = objectA instanceof Enemy ? (Enemy) objectA : (Enemy) objectB;
            handlePlayerEnemyContact(player, enemy);
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private void handleEnemyBulletContact(Bullet bullet, Enemy enemy) {
        int enemyHealth = enemy.getCurrentHitpoints();
        int damage = bullet.getDamage();

        enemy.setCurrentHitpoints(enemyHealth - damage);
        if (enemy.getCurrentHitpoints() <= 0)
            enemy.kill();

        bullet.setDurability(bullet.getDurability() - 1);
        if (bullet.getDurability() <= 0) {
            bullet.kill();
        }
    }

    private void handlePlayerEnemyContact(Player player, Enemy enemy) {
        int playerHealth = player.getCurrentHitpoints();
        // TODO specific damage values per enemy?

        player.setCurrentHitpoints(playerHealth - 1);
        if (player.getCurrentHitpoints() <= 0)
            System.out.println("YOU DIED.");
    }
}
