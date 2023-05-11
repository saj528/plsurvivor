package com.mygdx.panzerliedsurvivor.weapons;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.panzerliedsurvivor.Player;
import com.mygdx.panzerliedsurvivor.components.Timer;
import com.mygdx.panzerliedsurvivor.utils.GameComponentProvider;

public abstract class Weapon {

    public enum WeaponType {
        M1911,
        Mp40,
        SpreadWeapon,
        Kar98k
    }

    float projectileSpeed;

    Timer attackTimer;
    Sprite weaponSprite;

    int damage;

    int magSize;

    int currentAmmo;

    float range;

    Timer reloadTimer;

    float projectileDurability;

    Player player;

    float weaponTextureScale, originX, originY;
    int weaponOffsetX, weaponOffsetY;
    Vector2 muzzleOffset;

    public static Weapon addWeapon(WeaponType weaponType, Player player) {
        Weapon weapon;

        float attackSpeed;
        int damage, magSize;
        float projectileDurability, projectileSpeed, reloadSpeed, range;
        Sprite weaponSprite;
        float weaponTextureScale, originX, originY;
        int weaponOffsetX, weaponOffsetY;
        Vector2 muzzleOffset;


        switch (weaponType) {
            default:
            case M1911:
                attackSpeed = 2f;
                damage = 3;
                magSize = 7;
                reloadSpeed = 2.5f;
                projectileDurability = 2;
                projectileSpeed = 0.2f;
                range = 6f;
                weaponSprite = new Sprite(GameComponentProvider.getSpriteProcessor().getMiscTextureRegions().get("m1911"));
                weaponTextureScale = -.75f;
                originX = .8f;
                originY = weaponSprite.getHeight() / 2;
                muzzleOffset = new Vector2(-15, 0);
                weaponOffsetX = 0;
                weaponOffsetY = 4;
                weapon = new M1911Weapon(attackSpeed, projectileSpeed, weaponSprite, damage, magSize, reloadSpeed, range, projectileDurability, player,
                        weaponTextureScale, originX, originY, muzzleOffset, weaponOffsetX, weaponOffsetY);
                break;
            /*case Mp40:
                attackSpeed = .2f;
                damage = 4;
                magSize = 32;
                reloadSpeed = 5f;
                projectileDurability = 1;
                projectileSpeed = 0.2f;
                range = 8f;
                weaponSprite = null;
                weapon = new Mp40Weapon(attackSpeed, projectileSpeed, weaponSprite, damage, magSize, reloadSpeed, range, projectileDurability, player);
                break;
            case SpreadWeapon:
                attackSpeed = 3f;
                damage = 10;
                magSize = 5;
                reloadSpeed = 7f;
                projectileDurability = 2;
                projectileSpeed = 0.1f;
                weaponSprite = null;
                weapon = new SpreadWeapon(attackSpeed, projectileSpeed, weaponSprite, damage, magSize, reloadSpeed, projectileDurability, player);
                break;*/
            case Kar98k:
                attackSpeed = 2f;
                damage = 3;
                magSize = 7;
                reloadSpeed = 2.5f;
                projectileDurability = 2;
                projectileSpeed = 0.2f;
                range = 6f;
                weaponSprite = new Sprite(GameComponentProvider.getSpriteProcessor().getMiscTextureRegions().get("kar98k"));
                weaponTextureScale = -.3f;
                originX = .8f;
                originY = weaponSprite.getHeight() / 2;
                muzzleOffset = new Vector2(-25, 0);
                weaponOffsetX = 10;
                weaponOffsetY = 4;
                weapon = new Kar98kWeapon(attackSpeed, projectileSpeed, weaponSprite, damage, magSize, reloadSpeed, range, projectileDurability, player,
                        weaponTextureScale, originX, originY, muzzleOffset, weaponOffsetX, weaponOffsetY);
                break;
        }

        player.getWeapons().add(weapon);

        return weapon;
    }

    public Weapon(float attackSpeed, float projectileSpeed, Sprite weaponSprite, int damage,
                  int magSize, float reloadSpeed, float range, float projectileDurability, Player player,
                  float weaponTextureScale, float originX, float originY, Vector2 muzzleOffset, int weaponOffsetX, int weaponOffsetY) {
        this.attackTimer = new Timer(attackSpeed);
        this.projectileSpeed = projectileSpeed;
        this.weaponSprite = weaponSprite;
        this.damage = damage;
        this.magSize = magSize;
        this.currentAmmo = magSize;
        this.reloadTimer = new Timer(reloadSpeed);
        this.range = range;
        this.projectileDurability = projectileDurability;
        this.player = player;
        this.weaponTextureScale = weaponTextureScale;
        this.originX = originX;
        this.originY = originY;
        this.muzzleOffset = muzzleOffset;
        this.weaponOffsetX = weaponOffsetX;
        this.weaponOffsetY = weaponOffsetY;
    }

    public abstract void update(float delta);

    public abstract void render(float delta, SpriteBatch batch);



}
