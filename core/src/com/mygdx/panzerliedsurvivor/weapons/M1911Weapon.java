package com.mygdx.panzerliedsurvivor.weapons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.panzerliedsurvivor.Player;
import com.mygdx.panzerliedsurvivor.SpriteProcessor;
import com.mygdx.panzerliedsurvivor.enemies.Enemy;
import com.mygdx.panzerliedsurvivor.utils.EnemyUtils;
import com.mygdx.panzerliedsurvivor.utils.GameComponentProvider;

import static com.mygdx.panzerliedsurvivor.utils.Constants.PPM;
import static com.mygdx.panzerliedsurvivor.utils.GameComponentProvider.getSpriteProcessor;

public class M1911Weapon extends Weapon {

    //temp weapon fields

    float angle;
    SpriteProcessor spriteProcessor;

    Sprite weapon;

    Vector2 muzzleOffset;

    Vector2 muzzleLocation;

    SpriteBatch batch;

    ShapeRenderer shapeRenderer;

    public M1911Weapon(float attackSpeed, float projectileSpeed, TextureRegion sprite, int damage, int magSize,
                       float reloadSpeed, float range, float projectileDurability, Player player) {
        super(attackSpeed, projectileSpeed, sprite, damage, magSize, reloadSpeed, range, projectileDurability, player);

        //temp weapon stuff

        spriteProcessor = GameComponentProvider.getSpriteProcessor();
        batch = GameComponentProvider.getSpriteBatch();
        shapeRenderer = GameComponentProvider.getShapeRenderer();

        weapon = new Sprite(spriteProcessor.getMiscTextureRegions().get("kar98k"));
        weapon.setOriginCenter();
        weapon.scale(-.3f);
        weapon.setOrigin((weapon.getWidth() * .8f),(weapon.getHeight() / 2));
        weapon.setPosition(player.getPlayerBody().getPosition().x * PPM, player.getPlayerBody().getPosition().y * PPM);
        muzzleOffset = new Vector2(-25, 0);
        muzzleLocation = new Vector2();

    }

    @Override
    public void update(float delta) {

        //weapon sprite stuff

        Enemy enemy = EnemyUtils.getNearestEnemyWithinRange(player.getPlayerBody().getPosition(), range);

        if (enemy == null) {
            angle = 0;
        } else {
            angle = player.getPlayerBody().getPosition().sub(enemy.getBody().getPosition()).angleDeg();
        }

        weapon.setRotation(angle);

        if (weapon.getRotation() % 360 > 90 && weapon.getRotation() % 360 < 270) {
            weapon.setFlip(false, true);
            // The barrel isn't centered in the image so the y offset depends on whether the image is flipped or not
            muzzleOffset.y = -1;
        }else{
            weapon.setFlip(false, false);
            muzzleOffset.y = 1;
        }
        weapon.setCenter((player.getPlayerBody().getPosition().x * PPM) - 8,(player.getPlayerBody().getPosition().y * PPM) - 4);


        weapon.draw(batch);

        Vector2 weaponCenter = new Vector2(weapon.getX() + weapon.getOriginX(),weapon.getY() + weapon.getOriginY());


        muzzleLocation = new Vector2(weaponCenter).add(muzzleOffset).rotateAroundDeg(weaponCenter, angle);


//        shapeRenderer.setProjectionMatrix(GameComponentProvider.getCamera().combined);
////        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
////        shapeRenderer.setColor(Color.RED);
////        shapeRenderer.rect(weapon.getBoundingRectangle().x,weapon.getBoundingRectangle().y,weapon.getBoundingRectangle().width,weapon.getBoundingRectangle().height);
////        shapeRenderer.end();
//
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        shapeRenderer.setColor(Color.ORANGE);
//        shapeRenderer.circle(weapon.getX() + weapon.getOriginX(),weapon.getY() + weapon.getOriginY(),6f);
//        shapeRenderer.end();
//
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        shapeRenderer.setColor(Color.GREEN);
//        shapeRenderer.circle(weaponCenter.x, weaponCenter.y, 10f);
//        shapeRenderer.end();
//
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        shapeRenderer.setColor(Color.BLUE);
//        shapeRenderer.circle(muzzleLocation.x, muzzleLocation.y, 5f);
//        shapeRenderer.end();


        // -----

        if (currentAmmo == 0) {
            if (reloadTimer.updateTimerAndCheckCompletion(delta)) {
                currentAmmo = magSize;
                reloadTimer.reset();
            }
        }

        if (currentAmmo > 0 && attackTimer.updateTimerAndCheckCompletion(delta)) {
            fire();
            if (currentAmmo == 0)
                attackTimer.reset();
        }
    }

    private void fire() {
        Enemy target = EnemyUtils.getNearestEnemyWithinRange(this.player.getPlayerBody().getPosition(), range);
        if (target == null)
            return;
        Vector2 muzzleLocationMeters = new Vector2(muzzleLocation).scl(1/PPM);
        Vector2 direction = target.getBody().getPosition().sub(muzzleLocationMeters).nor().scl(projectileSpeed);
        Bullet bullet = new Bullet(damage, muzzleLocationMeters, direction, projectileSpeed, projectileDurability, getSpriteProcessor().getMiscTextureRegions().get("bullet"));
        GameComponentProvider.addBullet(bullet);
        currentAmmo--;
    }

}
