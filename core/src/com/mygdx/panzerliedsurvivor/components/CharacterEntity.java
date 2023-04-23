package com.mygdx.panzerliedsurvivor.components;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class CharacterEntity {

    TextureRegion characterTexReg;
    String interactionName;
    Rectangle boundingBox;

    public CharacterEntity(TextureRegion characterTexReg, String interactionName, Rectangle boundingBox) {
        this.characterTexReg = characterTexReg;
        this.interactionName = interactionName;
        this.boundingBox = boundingBox;
    }

    public TextureRegion getCharacterTexReg() {
        return characterTexReg;
    }

    public void setCharacterTexReg(TextureRegion characterTexReg) {
        this.characterTexReg = characterTexReg;
    }

    public String getInteractionName() {
        return interactionName;
    }

    public void setInteractionName(String interactionName) {
        this.interactionName = interactionName;
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(Rectangle boundingBox) {
        this.boundingBox = boundingBox;
    }

}
