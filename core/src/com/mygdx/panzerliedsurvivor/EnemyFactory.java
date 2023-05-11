package com.mygdx.panzerliedsurvivor;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.panzerliedsurvivor.enemies.Enemy;
import com.mygdx.panzerliedsurvivor.utils.CameraUtils;
import com.mygdx.panzerliedsurvivor.utils.GameComponentProvider;

import java.util.HashSet;
import java.util.Random;

public class EnemyFactory {


    float spawnerX, spawnerY;
    float counter = 0;
    Enemy.EnemyType type;
    int enemyCount;
    float spawnRateSeconds;
    Random random;
    int currentRandomInt;

    float mapWidthPixels, mapHeightPixels;

    public EnemyFactory(Enemy.EnemyType type, int enemyCount, float spawnRateSeconds, float mapWidthPixels, float mapHeightPixels) {
        this.type = type;
        this.enemyCount = enemyCount;
        this.spawnRateSeconds = spawnRateSeconds;
        this.mapWidthPixels = mapWidthPixels;
        this.mapHeightPixels = mapHeightPixels;
        random = new Random();
    }


    public void update(float delta) {

        counter += delta;

        OrthographicCamera camera = GameComponentProvider.getCamera();

        currentRandomInt = random.nextInt(4);

        if (currentRandomInt == 0) {
            spawnerX = camera.position.x + camera.viewportWidth;
            spawnerY = camera.position.y + (random.nextFloat(camera.viewportHeight) - (camera.viewportHeight / 2));
        } else if (currentRandomInt == 1) {
            spawnerX = camera.position.x - camera.viewportWidth;
            spawnerY = camera.position.y + (random.nextFloat(camera.viewportHeight) - (camera.viewportHeight / 2));
        } else if (currentRandomInt == 2) {
            spawnerX = camera.position.x + (random.nextFloat(camera.viewportWidth) - (camera.viewportWidth / 2));
            spawnerY = camera.position.y + camera.viewportHeight;
        } else if (currentRandomInt == 3) {
            spawnerX = camera.position.x + (random.nextFloat(camera.viewportWidth) - (camera.viewportWidth / 2));
            spawnerY = camera.position.y - camera.viewportHeight;
        }

        spawnerX = Math.max(10, Math.min(spawnerX, mapWidthPixels - 10));
        spawnerY = Math.max(10, Math.min(spawnerY, mapHeightPixels - 10));

        if (counter >= spawnRateSeconds && enemyCount > 0 && !CameraUtils.isPointOnScreen(spawnerX, spawnerY)) {
            Enemy.createEnemy(type, spawnerX, spawnerY);
            enemyCount--;
            counter = 0;
        }

    }

}

