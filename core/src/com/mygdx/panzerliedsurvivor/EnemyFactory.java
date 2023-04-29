package com.mygdx.panzerliedsurvivor;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.panzerliedsurvivor.enemies.Enemy;
import com.mygdx.panzerliedsurvivor.utils.GameComponentProvider;

import java.util.HashSet;

public class EnemyFactory {


    float spawnerX, spawnerY;
    float counter = 0;
    Enemy.EnemyType type;
    int enemyCount;
    int spawnRateSeconds;

    public EnemyFactory(Enemy.EnemyType type, int enemyCount, int spawnRateSeconds) {
        this.type = type;
        this.enemyCount = enemyCount;
        this.spawnRateSeconds = spawnRateSeconds;
    }


    public void update(float delta) {

        counter += delta;

        OrthographicCamera camera = GameComponentProvider.getCamera();
        spawnerX = camera.position.x - camera.viewportWidth;
        spawnerY = camera.position.y;

        if (counter >= spawnRateSeconds & enemyCount > 0) {
            Enemy.createEnemy(type, Math.max(0, spawnerX), spawnerY);
            enemyCount--;
            counter = 0;
        }

    }

}

