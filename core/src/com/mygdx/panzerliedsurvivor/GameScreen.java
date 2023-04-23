package com.mygdx.panzerliedsurvivor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.panzerliedsurvivor.components.CharacterEntity;

public class GameScreen implements Screen {


    private OrthographicCamera camera;
    private OrthographicCamera hudCamera;
    private Viewport viewport;
    private Viewport hudViewport;

    private SpriteBatch batch;

    SpriteProcessor spriteProcessor;

    ShapeRenderer shapeRenderer;

    Player player;

    private final float playerSize = 16;
    private final float WORLD_WIDTH = playerSize * 15;
    private final float WORLD_HEIGHT = playerSize * 15;
    private static final float CAMERA_LERP_SPEED = 0.1f;

    MapObjects mapObjects;

    OrthogonalTiledMapRenderer tiledMapRenderer;

    private TiledMap map;

    private CharacterEntity oldMan;

    private TextureRegion textBoxTexReg;

    BitmapFont font = new BitmapFont();


    public GameScreen() {

        shapeRenderer = new ShapeRenderer();

        camera = new OrthographicCamera();
        hudCamera = new OrthographicCamera();

        camera.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0);
        hudCamera.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0);

        viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        hudViewport = new ScreenViewport(hudCamera);


        hudCamera.setToOrtho(false, 1920,1080);

        spriteProcessor = new SpriteProcessor();

        batch = new SpriteBatch();

        map = new TmxMapLoader().load("maps/map01.tmx");

        tiledMapRenderer = new OrthogonalTiledMapRenderer(map);

        mapObjects = map.getLayers().get("collision").getObjects();

        player = new Player(batch, spriteProcessor, mapObjects);

        float oldmanX = (float) mapObjects.get("oldMan").getProperties().get("x");
        float oldmanY = (float) mapObjects.get("oldMan").getProperties().get("y");

        oldMan = new CharacterEntity(spriteProcessor.getNpcTextureRegions().get("oldMan"),"oldMan", new Rectangle((float) mapObjects.get("oldMan").getProperties().get("x"),(float) mapObjects.get("oldMan").getProperties().get("y"),16,16));
        textBoxTexReg = spriteProcessor.getHudTextureRegions().get("textBox");
        font.getData().setScale(8);
        font.setColor(Color.BLACK);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();


        if (Math.abs(player.getPlayerBoundingBox().x - camera.position.x) > 2 || Math.abs(player.getPlayerBoundingBox().y - camera.position.y) > 2) {
            Vector3 targetPosition = new Vector3(player.getPlayerBoundingBox().x, player.getPlayerBoundingBox().y, 0);
            camera.position.lerp(targetPosition, CAMERA_LERP_SPEED);
        }

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);


        player.detectInput(delta);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 1, 0.1f);
        shapeRenderer.rect(player.getPlayerBoundingBox().x, player.getPlayerBoundingBox().y, player.getPlayerBoundingBox().width, player.getPlayerBoundingBox().height);

        shapeRenderer.setColor(0, 1, 0, 0.1f);
        shapeRenderer.rect(player.getPlayerUseBox().x, player.getPlayerUseBox().y, player.getPlayerUseBox().width, player.getPlayerUseBox().height);

        shapeRenderer.setColor(0, 0.5f, 0.5f, 0.1f);
        shapeRenderer.rect(oldMan.getBoundingBox().x, oldMan.getBoundingBox().y, oldMan.getBoundingBox().width, oldMan.getBoundingBox().height);
        shapeRenderer.end();

        batch.begin();

        batch.draw(oldMan.getCharacterTexReg(),oldMan.getBoundingBox().getX(),oldMan.getBoundingBox().getY());

        player.renderAndUpdate(delta);


        batch.end();

        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();
        if(player.isPlayerUsing() && player.getPlayerUseBox().overlaps(oldMan.getBoundingBox())){
            batch.draw(textBoxTexReg,textBoxTexReg.getRegionWidth() / 2,0,textBoxTexReg.getRegionWidth() * 7,textBoxTexReg.getRegionHeight() * 7);
            font.draw(batch, "Hello, world!",Gdx.graphics.getWidth() / 6 , Gdx.graphics.getHeight() / 6);

        }else{
            player.setPlayerUsing(false);
        }

        batch.end();

    }

    private void prepareHUD() {
        //Create a BitmapFont from our font file
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("EdgeOfTheGalaxyRegular-OVEa6.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.size = 72;
        fontParameter.borderWidth = 3.6f;
        fontParameter.color = new Color(1, 1, 1, 0.3f);
        fontParameter.borderColor = new Color(0, 0, 0, 0.3f);

        font = fontGenerator.generateFont(fontParameter);

        //scale the font to fit world
        font.getData().setScale(0.08f);

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
