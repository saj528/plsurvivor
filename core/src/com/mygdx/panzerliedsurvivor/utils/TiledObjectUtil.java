package com.mygdx.panzerliedsurvivor.utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.mygdx.panzerliedsurvivor.utils.Box2DBodyIntializer.getBodyDef;
import static com.mygdx.panzerliedsurvivor.utils.Constants.PPM;

public class TiledObjectUtil {


    public static void parseTiledObjectLayer(World world, MapObjects objects) {
        for (MapObject object : objects) {
            Shape shape;
            if (object instanceof PolylineMapObject) {
                shape = createPolyline((PolylineMapObject) object);
            } else {
                continue;
            }

            Body body;
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            body = world.createBody(bodyDef);
            body.createFixture(shape, 1.0f);
            shape.dispose();
        }
    }

    private static ChainShape createPolyline(PolylineMapObject polyline) {

        // getTransformedVertices gets a more accurate position
        float[] vertices = polyline.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < worldVertices.length; i++) {
            worldVertices[i] = new Vector2(vertices[i * 2] / Constants.PPM, vertices[i * 2 + 1] / Constants.PPM);
        }

        ChainShape chainShape = new ChainShape();

        chainShape.createChain(worldVertices);

        return chainShape;
    }

    public static void parseMapLayerCollision(TiledMap map, World world) {

        Filter filter = new Filter();
        filter.categoryBits = Constants.TERRAIN_CATEGORY_BITS;
        filter.maskBits = Constants.TERRAIN_MASK_BITS;

        MapObjects collisions = map.getLayers().get("collision").getObjects();
        for (int i = 0; i < collisions.getCount(); i++) {
            MapObject mapObject = collisions.get(i);

            if (mapObject instanceof RectangleMapObject) {
                RectangleMapObject rectangleObject = (RectangleMapObject) mapObject;
                Rectangle rectangle = rectangleObject.getRectangle();

                BodyDef bodyDef = getBodyDef((rectangle.getX() + rectangle.getWidth() / 2.0f) / PPM, (rectangle.getY() + rectangle.getHeight() / 2.0f) / PPM);

                Body body = world.createBody(bodyDef);
                PolygonShape polygonShape = new PolygonShape();
                polygonShape.setAsBox(rectangle.getWidth() / 2.0f / PPM, rectangle.getHeight() / 2.0f / PPM);
                body.createFixture(polygonShape, 0.0f);
                body.getFixtureList().get(0).setFilterData(filter);
                polygonShape.dispose();
            } else if (mapObject instanceof EllipseMapObject) {
                EllipseMapObject circleMapObject = (EllipseMapObject) mapObject;
                Ellipse ellipse = circleMapObject.getEllipse();

                BodyDef bodyDef = getBodyDef(ellipse.x, ellipse.y);

                if (ellipse.width != ellipse.height)
                    throw new IllegalArgumentException("Only circles are allowed.");

                Body body = world.createBody(bodyDef);
                CircleShape circleShape = new CircleShape();
                circleShape.setRadius(ellipse.width / 2f);
                body.createFixture(circleShape, 0.0f);
                body.getFixtureList().get(0).setFilterData(filter);
                circleShape.dispose();
            } else if (mapObject instanceof PolygonMapObject) {
                PolygonMapObject polygonMapObject = (PolygonMapObject) mapObject;
                Polygon polygon = polygonMapObject.getPolygon();

                BodyDef bodyDef = getBodyDef(polygon.getX(), polygon.getY());

                Body body = world.createBody(bodyDef);
                PolygonShape polygonShape = new PolygonShape();
                polygonShape.set(polygon.getVertices());
                body.createFixture(polygonShape, 0.0f);
                body.getFixtureList().get(0).setFilterData(filter);
                polygonShape.dispose();
            }else if (mapObject instanceof PolylineMapObject) {

                Shape shape = createPolyline((PolylineMapObject) mapObject);

                Body body;
                BodyDef bodyDef = new BodyDef();
                bodyDef.type = BodyDef.BodyType.StaticBody;
                body = world.createBody(bodyDef);
                body.createFixture(shape,1.0f);
                body.getFixtureList().get(0).setFilterData(filter);
                shape.dispose();
            }
        }
    }


    public static void parseMapTileCollision(TiledMap map, World world) {

        Filter filter = new Filter();
        filter.categoryBits = Constants.TERRAIN_CATEGORY_BITS;
        filter.maskBits = Constants.TERRAIN_MASK_BITS;

        int tileSize = ((TiledMapTileLayer) map.getLayers().get(0)).getTileWidth();

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell == null)
                    continue;

                MapObjects cellObjects = cell.getTile().getObjects();
                if (cellObjects.getCount() != 1)
                    continue;

                MapObject mapObject = cellObjects.get(0);

                if (mapObject instanceof RectangleMapObject) {
                    RectangleMapObject rectangleObject = (RectangleMapObject) mapObject;
                    Rectangle rectangle = rectangleObject.getRectangle();

                    BodyDef bodyDef = getBodyDef((x * tileSize + tileSize / 2f + rectangle.getX() - (tileSize - rectangle.getWidth()) / 2f) / PPM, (y * tileSize + tileSize / 2f + rectangle.getY() - (tileSize - rectangle.getHeight()) / 2f) / PPM);

                    Body body = world.createBody(bodyDef);
                    PolygonShape polygonShape = new PolygonShape();
                    polygonShape.setAsBox(rectangle.getWidth() / 2.0f / PPM, rectangle.getHeight() / 2.0f / PPM);
                    body.createFixture(polygonShape, 0.0f);
                    body.getFixtureList().get(0).setFilterData(filter);
                    polygonShape.dispose();
                } else if (mapObject instanceof EllipseMapObject) {
                    EllipseMapObject circleMapObject = (EllipseMapObject) mapObject;
                    Ellipse ellipse = circleMapObject.getEllipse();

                    BodyDef bodyDef = getBodyDef(x * tileSize + tileSize / 2f + ellipse.x, y * tileSize + tileSize / 2f + ellipse.y);

                    if (ellipse.width != ellipse.height)
                        throw new IllegalArgumentException("Only circles are allowed.");

                    Body body = world.createBody(bodyDef);
                    CircleShape circleShape = new CircleShape();
                    circleShape.setRadius(ellipse.width / 2f);
                    body.createFixture(circleShape, 0.0f);
                    body.getFixtureList().get(0).setFilterData(filter);
                    circleShape.dispose();
                } else if (mapObject instanceof PolygonMapObject) {
                    PolygonMapObject polygonMapObject = (PolygonMapObject) mapObject;
                    Polygon polygon = polygonMapObject.getPolygon();

                    BodyDef bodyDef = getBodyDef(x * tileSize + polygon.getX(), y * tileSize + polygon.getY());

                    Body body = world.createBody(bodyDef);
                    PolygonShape polygonShape = new PolygonShape();
                    polygonShape.set(polygon.getVertices());
                    body.createFixture(polygonShape, 0.0f);
                    body.getFixtureList().get(0).setFilterData(filter);
                    polygonShape.dispose();
                }
            }
        }
    }


}
