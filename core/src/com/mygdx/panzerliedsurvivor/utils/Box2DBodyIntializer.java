package com.mygdx.panzerliedsurvivor.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.panzerliedsurvivor.enemies.Enemy;

import static com.mygdx.panzerliedsurvivor.utils.Constants.PPM;

public class Box2DBodyIntializer {

    private static final float SCALE = 1.0f;

    public static BodyDef getBodyDef(float x, float y)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);

        return bodyDef;
    }

    /***
     * Create a Body using the createBox method but also apply the player category & mask for collision purposes
     * @param x x coord to create player
     * @param y y coord to create player
     * @param width width of the rectangle
     * @param height height of the rectangle
     * @return the Body representing the player that was created
     */
    public static Body createPlayer(int x, int y, int width, int height) {
        Filter filter = new Filter();
        filter.categoryBits = Constants.PLAYER_CATEGORY_BITS;
        filter.maskBits = Constants.PLAYER_MASK_BITS;

        Body playerBody = createBox(x, y, width, height, false);
        playerBody.getFixtureList().get(0).setFilterData(filter);

        return playerBody;
    }

    public static Body createEnemyBody(Enemy enemy, float x, float y) {
        Filter filter = new Filter();
        filter.categoryBits = Constants.ENEMY_CATEGORY_BITS;
        filter.maskBits = Constants.ENEMY_MASK_BITS;

        Body enemyBody = createBox(x, y, enemy.getWidth(), enemy.getHeight(), false);
        enemyBody.getFixtureList().get(0).setFilterData(filter);

        return enemyBody;
    }

    public static Body createBox(float x, float y, float width, float height, boolean isStatic) {
        Body boxBody;

        World world = GameComponentProvider.getGameWorld();

        BodyDef def = new BodyDef();
        if(isStatic){
            def.type = BodyDef.BodyType.StaticBody;
        }else{
            def.type = BodyDef.BodyType.DynamicBody;
        }
        def.position.set(x / PPM,y / PPM);
        def.fixedRotation = true;
        boxBody = world.createBody(def);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(width / SCALE / PPM, height / SCALE / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density =  1f;
        fixtureDef.friction = 1f;
        fixtureDef.shape = polygonShape;

        boxBody.createFixture(fixtureDef);
        polygonShape.dispose();

        return boxBody;
    }

    /***
     * create a Box2D bullet body based on a starting position
     * @param position the position to spawn the bullet
     * @return the Box2D Body representing the bullet
     */
    public static Body createBulletBody(Vector2 position) {
        Body bulletBody;
        World world = GameComponentProvider.getGameWorld();

        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(position.x, position.y);

        bulletBody = world.createBody(def);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(.125f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1f;
        fixtureDef.friction = 0f;
        fixtureDef.shape = circleShape;
        fixtureDef.filter.categoryBits = Constants.BULLET_CATEGORY_BITS;
        fixtureDef.filter.maskBits = Constants.BULLET_MASK_BITS;

        bulletBody.createFixture(fixtureDef);
        circleShape.dispose();

        return bulletBody;
    }
}
