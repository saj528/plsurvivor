package com.mygdx.panzerliedsurvivor.weapons;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.panzerliedsurvivor.Player;
import com.mygdx.panzerliedsurvivor.components.Timer;

public abstract class Weapon {

    public enum WeaponType {
        M1911,
        Mp40,
        SpreadWeapon
    }

    float projectileSpeed;

    Timer attackTimer;
    TextureRegion sprite;

    int damage;

    int magSize;

    int currentAmmo;

    float range;

    Timer reloadTimer;

    float projectileDurability;

    Player player;

    public static Weapon addWeapon(WeaponType weaponType, Player player) {
        Weapon weapon;

        float attackSpeed;
        int damage, magSize;
        float projectileDurability, projectileSpeed, reloadSpeed, range;
        TextureRegion sprite;


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
                sprite = null;
                weapon = new M1911Weapon(attackSpeed, projectileSpeed, sprite, damage, magSize, reloadSpeed, range, projectileDurability, player);
                break;
            case Mp40:
                attackSpeed = .2f;
                damage = 4;
                magSize = 32;
                reloadSpeed = 5f;
                projectileDurability = 1;
                projectileSpeed = 0.2f;
                range = 8f;
                sprite = null;
                weapon = new Mp40Weapon(attackSpeed, projectileSpeed, sprite, damage, magSize, reloadSpeed, range, projectileDurability, player);
                break;
            case SpreadWeapon:
                attackSpeed = 3f;
                damage = 10;
                magSize = 5;
                reloadSpeed = 7f;
                projectileDurability = 2;
                projectileSpeed = 0.1f;
                sprite = null;
                weapon = new SpreadWeapon(attackSpeed, projectileSpeed, sprite, damage, magSize, reloadSpeed, projectileDurability, player);
                break;
        }

        player.getWeapons().add(weapon);

        return weapon;
    }

    public Weapon(float attackSpeed, float projectileSpeed, TextureRegion sprite, int damage,
                  int magSize, float reloadSpeed, float range, float projectileDurability, Player player) {
        this.attackTimer = new Timer(attackSpeed);
        this.projectileSpeed = projectileSpeed;
        this.sprite = sprite;
        this.damage = damage;
        this.magSize = magSize;
        this.currentAmmo = magSize;
        this.reloadTimer = new Timer(reloadSpeed);
        this.range = range;
        this.projectileDurability = projectileDurability;
        this.player = player;
    }

    public abstract void update(float delta);

}
