package com.mygdx.panzerliedsurvivor;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.panzerliedsurvivor.enemies.Enemy;
import com.mygdx.panzerliedsurvivor.utils.GameComponentProvider;

import java.util.HashSet;
import java.util.Random;

public class EnemyFactory {


    float spawnerX, spawnerY;
    float counter = 0;
    Enemy.EnemyType type;
    int enemyCount;
    int spawnRateSeconds;
    Random random;
    int currentRandomInt;

    float mapWidthPixels, mapHeightPixels;

    public EnemyFactory(Enemy.EnemyType type, int enemyCount, int spawnRateSeconds, float mapWidthPixels, float mapHeightPixels) {
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
            spawnerY = camera.position.y;
        } else if (currentRandomInt == 1) {
            spawnerX = camera.position.x - camera.viewportWidth;
            spawnerY = camera.position.y;
        } else if (currentRandomInt == 2) {
            spawnerX = camera.position.x;
            spawnerY = camera.position.y + camera.viewportHeight;
        } else if (currentRandomInt == 3) {
            spawnerX = camera.position.x;
            spawnerY = camera.position.y - camera.viewportHeight;
        }

        if (counter >= spawnRateSeconds & enemyCount > 0) {
            Enemy.createEnemy(type, Math.max(0, Math.min(spawnerX, mapWidthPixels)), Math.max(0, Math.min(spawnerY, mapHeightPixels)));
            enemyCount--;
            counter = 0;
        }

    }

}

