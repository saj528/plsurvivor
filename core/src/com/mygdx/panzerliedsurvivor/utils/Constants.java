package com.mygdx.panzerliedsurvivor.utils;

public class Constants {

    public static final float PPM = 32;

    public static final short PLAYER_CATEGORY_BITS = 0x0001;
    public static final short BULLET_CATEGORY_BITS = 0x0002;
    public static final short ENEMY_CATEGORY_BITS = 0x0004;
    public static final short TERRAIN_CATEGORY_BITS = 0x0008;

    public static final short PLAYER_MASK_BITS = ENEMY_CATEGORY_BITS | TERRAIN_CATEGORY_BITS;
    public static final short BULLET_MASK_BITS = ENEMY_CATEGORY_BITS;
    public static final short ENEMY_MASK_BITS = PLAYER_CATEGORY_BITS | BULLET_CATEGORY_BITS | TERRAIN_CATEGORY_BITS;
    public static final short TERRAIN_MASK_BITS = PLAYER_CATEGORY_BITS | ENEMY_CATEGORY_BITS;


}
