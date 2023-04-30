package com.mygdx.panzerliedsurvivor.weapons;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.panzerliedsurvivor.Player;

public abstract class Weapon {

    public enum WeaponType {
        PistolWeapon,
        SpreadWeapon
    }

    float attackSpeed;

    float projectileSpeed;

    float timeSinceLastAttack = 0f;

    TextureRegion sprite;

    int damage;

    float projectileDurability;

    Player player;

    public static Weapon addWeapon(WeaponType weaponType, Player player) {
        Weapon weapon;

        float attackSpeed;
        int damage;
        float projectileDurability, projectileSpeed;
        TextureRegion sprite;


        switch (weaponType) {
            default:
            case PistolWeapon:
                attackSpeed = 2f;
                damage = 5;
                projectileDurability = 2;
                projectileSpeed = 0.2f;
                sprite = null;
                weapon = new PistolWeapon(attackSpeed, projectileSpeed, sprite, damage, projectileDurability, player);
                break;
            case SpreadWeapon:
                attackSpeed = 3f;
                damage = 10;
                projectileDurability = 2;
                projectileSpeed = 0.1f;
                sprite = null;
                weapon = new SpreadWeapon(attackSpeed, projectileSpeed, sprite, damage, projectileDurability, player);
                break;
        }

        player.getWeapons().add(weapon);

        return weapon;
    }

    public Weapon(float attackSpeed, float projectileSpeed, TextureRegion sprite, int damage, float projectileDurability, Player player) {
        this.attackSpeed = attackSpeed;
        this.projectileSpeed = projectileSpeed;
        this.sprite = sprite;
        this.damage = damage;
        this.projectileDurability = projectileDurability;
        this.player = player;
    }

    public abstract void update(float delta);

}
