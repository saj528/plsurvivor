package com.mygdx.panzerliedsurvivor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.panzerliedsurvivor.utils.GameComponentProvider;
import com.mygdx.panzerliedsurvivor.weapons.Bullet;

import static com.mygdx.panzerliedsurvivor.utils.Box2DBodyIntializer.createPlayer;
import static com.mygdx.panzerliedsurvivor.utils.Constants.PPM;
import static com.mygdx.panzerliedsurvivor.utils.TiledObjectUtil.parseMap;

public class GameScreen implements Screen {

    private OrthographicCamera camera;
    private Viewport viewport;

    private SpriteBatch batch;

    SpriteProcessor spriteProcessor;

    ShapeRenderer shapeRenderer;

    private Box2DDebugRenderer box2DDebugRenderer;

    Player player;

    private final float playerSize = 16;
    private final float WORLD_WIDTH = playerSize * 15;
    private final float WORLD_HEIGHT = playerSize * 15;
    private static final float CAMERA_LERP_SPEED = 0.1f;

    MapObjects mapObjects;

    OrthogonalTiledMapRenderer tiledMapRenderer;



    private TiledMap map;

    private World world;

    public GameScreen() {

        shapeRenderer = new ShapeRenderer();

        box2DDebugRenderer = new Box2DDebugRenderer();

        camera = new OrthographicCamera();

        camera.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0);

        world = GameComponentProvider.getGameWorld();

        viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        spriteProcessor = new SpriteProcessor();

        batch = GameComponentProvider.getSpriteBatch();

        map = new TmxMapLoader().load("maps/map01.tmx");

        tiledMapRenderer = new OrthogonalTiledMapRenderer(map);



        player = new Player(batch, spriteProcessor, mapObjects, createPlayer(2,2,8,8));


        parseMap(map, world);

    }




    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(1 / 60f, 6, 2);

        camera.update();
        //System.out.println(player.getPlayerBody().getPosition().x * PPM + " " + player.getPlayerBody().getPosition().y * PPM);
        if (Math.abs((player.getPlayerBody().getPosition().x * PPM) - camera.position.x) > 2|| Math.abs((player.getPlayerBody().getPosition().y * PPM) - camera.position.y) > 2) {
            Vector3 targetPosition = new Vector3((player.getPlayerBody().getPosition().x * PPM), (player.getPlayerBody().getPosition().y * PPM), 0);
            camera.position.lerp(targetPosition, CAMERA_LERP_SPEED);
        }

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        batch.setProjectionMatrix(camera.combined);

        player.detectInput(delta);

        batch.begin();

        player.renderAndUpdate(delta);

        for(Bullet b : GameComponentProvider.getBullets()) {
            b.update(delta);
            b.render(delta);
        }

        batch.end();

        box2DDebugRenderer.render(world, camera.combined.scl(PPM));


    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
