package com.mygdx.panzerliedsurvivor.soldiers;

import com.mygdx.panzerliedsurvivor.Player;
import com.mygdx.panzerliedsurvivor.weapons.Weapon;

import java.util.Random;

public class Soldier {
    public enum Nationality {
        USA,
        Japan,
        Germany,
        Soviet
    }

    String firstName;
    String lastName;
    float moveSpeed;
    float accuracy;
    float reloadSpeed;
    float damageModifier;

    Nationality nationality;

    Weapon weapon;

    Player player;

    public static Soldier generateRandomSoldier() {
        Soldier soldier = new Soldier();
        Random random = new Random();
        soldier.firstName = random.nextInt(2) == 0 ? "John" : "Little Stevie";
        soldier.lastName = random.nextInt(2) == 0 ? "Ammirati" : "Joiner";

        soldier.moveSpeed = random.nextFloat(1) + 0.5f;
        soldier.accuracy = random.nextFloat(1) + 0.5f;
        soldier.reloadSpeed = random.nextFloat(1) + 0.5f;
        soldier.damageModifier = random.nextFloat(1) + 0.5f;

        soldier.nationality = Nationality.values()[random.nextInt(Nationality.values().length)];

        return soldier;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public float getReloadSpeed() {
        return reloadSpeed;
    }

    public void setReloadSpeed(float reloadSpeed) {
        this.reloadSpeed = reloadSpeed;
    }

    public float getDamageModifier() {
        return damageModifier;
    }

    public void setDamageModifier(float damageModifier) {
        this.damageModifier = damageModifier;
    }

    public Nationality getNationality() {
        return nationality;
    }

    public void setNationality(Nationality nationality) {
        this.nationality = nationality;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
        weapon.setSoldier(this);
    }

    public void removeWeapon() {
        weapon.setSoldier(null);
        this.weapon = null;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
