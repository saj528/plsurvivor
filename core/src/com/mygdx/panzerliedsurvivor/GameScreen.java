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
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.panzerliedsurvivor.components.CharacterEntity;

import static com.mygdx.panzerliedsurvivor.utils.Constants.PPM;

public class GameScreen implements Screen {

    private final float SCALE = 1.0f;
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
    private final int tileSize;

    MapObjects mapObjects;

    OrthogonalTiledMapRenderer tiledMapRenderer;



    private TiledMap map;

    private World world;

    public GameScreen() {

        shapeRenderer = new ShapeRenderer();

        box2DDebugRenderer = new Box2DDebugRenderer();

        camera = new OrthographicCamera();

        camera.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0);

        world = new World(new Vector2(0, 0), false);

        viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        spriteProcessor = new SpriteProcessor();

        batch = new SpriteBatch();

        map = new TmxMapLoader().load("maps/map01.tmx");
        tileSize = ((TiledMapTileLayer) map.getLayers().get(0)).getTileWidth();
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map);

        //mapObjects = map.getLayers().get("collision").getObjects();

        player = new Player(batch, spriteProcessor, mapObjects,createBox(2,2,8,8,false));


        parseMap();


        Rectangle rectangle = new Rectangle(0,0,32,32);


        BodyDef bodyDef = getBodyDef(1f,1f);

        Body body = world.createBody(bodyDef);
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(rectangle.getWidth() / 2.0f / PPM, rectangle.getHeight() / 2.0f / PPM);
        body.createFixture(polygonShape, 0.0f);
        polygonShape.dispose();


    }

    private void parseMap()
    {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        for (int x = 0; x < layer.getWidth(); x++)
        {
            for (int y = 0; y < layer.getHeight(); y++)
            {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell == null)
                    continue;

                MapObjects cellObjects = cell.getTile().getObjects();
                if (cellObjects.getCount() != 1)
                    continue;

                MapObject mapObject = cellObjects.get(0);

                if (mapObject instanceof RectangleMapObject)
                {
                    RectangleMapObject rectangleObject = (RectangleMapObject) mapObject;
                    Rectangle rectangle = rectangleObject.getRectangle();

                    BodyDef bodyDef = getBodyDef((x * tileSize + tileSize / 2f + rectangle.getX() - (tileSize - rectangle.getWidth()) / 2f) / PPM, (y * tileSize + tileSize / 2f + rectangle.getY() - (tileSize - rectangle.getHeight()) / 2f) / PPM);

                    Body body = world.createBody(bodyDef);
                    PolygonShape polygonShape = new PolygonShape();
                    polygonShape.setAsBox(rectangle.getWidth() / 2.0f / PPM, rectangle.getHeight() / 2.0f / PPM);
                    body.createFixture(polygonShape, 0.0f);
                    polygonShape.dispose();
                }
                else if (mapObject instanceof EllipseMapObject)
                {
                    EllipseMapObject circleMapObject = (EllipseMapObject) mapObject;
                    Ellipse ellipse = circleMapObject.getEllipse();

                    BodyDef bodyDef = getBodyDef(x * tileSize + tileSize / 2f + ellipse.x, y * tileSize + tileSize / 2f + ellipse.y);

                    if (ellipse.width != ellipse.height)
                        throw new IllegalArgumentException("Only circles are allowed.");

                    Body body = world.createBody(bodyDef);
                    CircleShape circleShape = new CircleShape();
                    circleShape.setRadius(ellipse.width / 2f);
                    body.createFixture(circleShape, 0.0f);
                    circleShape.dispose();
                }
                else if (mapObject instanceof PolygonMapObject)
                {
                    PolygonMapObject polygonMapObject = (PolygonMapObject) mapObject;
                    Polygon polygon = polygonMapObject.getPolygon();

                    BodyDef bodyDef = getBodyDef(x * tileSize + polygon.getX(), y * tileSize + polygon.getY());

                    Body body = world.createBody(bodyDef);
                    PolygonShape polygonShape = new PolygonShape();
                    polygonShape.set(polygon.getVertices());
                    body.createFixture(polygonShape, 0.0f);
                    polygonShape.dispose();
                }
            }
        }
    }

    private BodyDef getBodyDef(float x, float y)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);

        return bodyDef;
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

        batch.end();

        box2DDebugRenderer.render(world, camera.combined.scl(PPM));


    }

    public Body createBox(int x,int y,int width, int height, boolean isStatic) {
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
