package com.mygdx.panzerliedsurvivor.utils;

import com.badlogic.gdx.physics.box2d.*;

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

    public static Body createBox(World world, int x, int y, int width, int height, boolean isStatic) {
        Body boxBody;

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
}
