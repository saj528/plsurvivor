package com.mygdx.panzerliedsurvivor.weapons;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.panzerliedsurvivor.Player;

public abstract class Weapon {
    float attackSpeed;

    float timeSinceLastAttack = 0f;

    TextureRegion sprite;

    int damage;

    float projectileDurability;

    Player player;

    public Weapon(float attackSpeed, TextureRegion sprite, int damage, float projectileDurability, Player player) {
        this.attackSpeed = attackSpeed;
        this.sprite = sprite;
        this.damage = damage;
        this.projectileDurability = projectileDurability;
        this.player = player;
    }

    public abstract void update(float delta);

}
