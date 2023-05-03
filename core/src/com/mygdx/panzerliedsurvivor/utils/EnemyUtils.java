package com.mygdx.panzerliedsurvivor.utils;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.panzerliedsurvivor.enemies.Enemy;

import java.util.Set;

public class EnemyUtils {

    public static Enemy getNearestEnemy(Vector2 position) {
        return getNearestEnemyWithinRange(position, Float.MAX_VALUE);
    }
    public static Enemy getNearestEnemyWithinRange(Vector2 position, float maxRange) {
        Set<Enemy> enemies = GameComponentProvider.getEnemies();
        if(enemies.isEmpty())
            return null;

        float minDist = Float.MAX_VALUE;
        Enemy nearestEnemy = null;

        for (Enemy enemy : enemies)
        {
            Vector2 enemyPos = enemy.getBody().getPosition();
            float dist = Math.abs(enemyPos.sub(position).len());


            if (dist < minDist && dist < maxRange) {
                nearestEnemy = enemy;
                minDist = dist;
            }
        }

        return nearestEnemy;
    }
}
