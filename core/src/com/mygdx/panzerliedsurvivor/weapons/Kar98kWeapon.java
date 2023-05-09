package com.mygdx.panzerliedsurvivor.weapons;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.panzerliedsurvivor.Player;
import com.mygdx.panzerliedsurvivor.enemies.Enemy;
import com.mygdx.panzerliedsurvivor.utils.EnemyUtils;
import com.mygdx.panzerliedsurvivor.utils.GameComponentProvider;

import java.util.Arrays;

import static com.mygdx.panzerliedsurvivor.utils.Constants.PPM;
import static com.mygdx.panzerliedsurvivor.utils.GameComponentProvider.getSpriteProcessor;

public class Kar98kWeapon extends Weapon {


    float angle;

    Vector2 muzzleLocation;

    Animation<Sprite> kar98kfiring = GameComponentProvider.getSpriteProcessor().getSpriteAnimations().get("kar98kFiring");

    float animTimer;

    boolean firing = false;

    Sprite currentAnimSprite;

    public Kar98kWeapon(float attackSpeed, float projectileSpeed, Sprite weaponSprite, int damage, int magSize,
                        float reloadSpeed, float range, float projectileDurability, Player player,
                        float weaponTextureScale, float originX, float originY, Vector2 muzzleOffset, int weaponOffsetX, int weaponOffsetY) {
        super(attackSpeed, projectileSpeed, weaponSprite, damage, magSize, reloadSpeed, range, projectileDurability, player
                , weaponTextureScale, originX, originY, muzzleOffset, weaponOffsetX, weaponOffsetY);

        //weapon sprite initialization

        weaponSprite = initializeSprite(weaponSprite);

        for (Sprite tempSprite : kar98kfiring.getKeyFrames()) {
            tempSprite = initializeSprite(tempSprite);
        }

        currentAnimSprite = weaponSprite;

        animTimer = 0;

        muzzleLocation = new Vector2();

    }

    @Override
    public void render(float delta, SpriteBatch batch) {


        animTimer += delta;

        Enemy enemy = EnemyUtils.getNearestEnemyWithinRange(player.getPlayerBody().getPosition(), range);

        if (enemy == null) {
            angle = 0;
        } else {
            angle = player.getPlayerBody().getPosition().sub(enemy.getBody().getPosition()).angleDeg();
        }

        if (firing) {

            currentAnimSprite = kar98kfiring.getKeyFrame(animTimer);
            if (kar98kfiring.isAnimationFinished(animTimer)) {
                firing = false;
            }
        } else {
            currentAnimSprite = weaponSprite;
        }


        currentAnimSprite = updateSprite(currentAnimSprite);

        currentAnimSprite.draw(batch);

        Vector2 weaponCenter = new Vector2(weaponSprite.getX() + weaponSprite.getOriginX(), weaponSprite.getY() + weaponSprite.getOriginY());

        muzzleLocation = new Vector2(weaponCenter).add(muzzleOffset).rotateAroundDeg(weaponCenter, angle);

    }

    @Override
    public void update(float delta) {

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
        firing = true;
        Vector2 muzzleLocationMeters = new Vector2(muzzleLocation).scl(1 / PPM);
        Vector2 direction = target.getBody().getPosition().sub(muzzleLocationMeters).nor().scl(projectileSpeed);
        Bullet bullet = new Bullet(damage, muzzleLocationMeters, direction, projectileSpeed, projectileDurability, getSpriteProcessor().getMiscTextureRegions().get("bullet"));
        GameComponentProvider.addBullet(bullet);
        currentAmmo--;
    }

    private Sprite initializeSprite(Sprite sprite) {

        sprite.scale(weaponTextureScale);
        sprite.setOrigin((weaponSprite.getWidth() * originX), originY);
        sprite.setPosition(player.getPlayerBody().getPosition().x * PPM, player.getPlayerBody().getPosition().y * PPM);

        return sprite;
    }

    private Sprite updateSprite(Sprite sprite) {

        sprite.setRotation(angle);
        if (sprite.getRotation() % 360 > 90 && sprite.getRotation() % 360 < 270) {
            sprite.setFlip(false, true);
            // The barrel isn't centered in the image so the y offset depends on whether the image is flipped or not
            muzzleOffset.y = -1;
            weaponOffsetX = 15;
        } else {
            sprite.setFlip(false, false);
            muzzleOffset.y = 1;
            weaponOffsetX = 10;
        }
        sprite.setCenter((player.getPlayerBody().getPosition().x * PPM) - weaponOffsetX, (player.getPlayerBody().getPosition().y * PPM) - weaponOffsetY);

        return sprite;
    }

}
