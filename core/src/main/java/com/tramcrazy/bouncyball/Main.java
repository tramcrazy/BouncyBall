package com.tramcrazy.bouncyball;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    final int originalSpeed = 3;
    int speed = originalSpeed;
    ShapeRenderer myRenderer;
    int circleRadius = 1;
    final int windowWidth = 640;
    final int windowHeight = 480;
    int circleXPos = windowWidth / 2;
    int circleYPos = windowHeight / 2;
    Random colourRand = new Random();
    int yMultiplier = 1;
    int xMultiplier = 1;
    float currentRedVal = 255;
    float currentGreenVal = 255;
    float currentBlueVal = 255;
    float currentBgRedVal = 0;
    float currentBgGreenVal = 0;
    float currentBgBlueVal = 0;
    boolean stateChange = false;
    boolean animating = true;
    Sound beepSound;

    @Override
    public void create() {
        myRenderer = new ShapeRenderer();
        beepSound = Gdx.audio.newSound(Gdx.files.internal("data/beep.wav"));
    }

    @Override
    public void render() {
        ScreenUtils.clear(currentBgRedVal/255, currentBgGreenVal/255, currentBgBlueVal/255, 1);
        myRenderer.begin(ShapeRenderer.ShapeType.Filled);
        myRenderer.setColor(currentRedVal/255, currentGreenVal/255, currentBlueVal/255, 1);
        myRenderer.circle(circleXPos, circleYPos, circleRadius);
        myRenderer.end();
        if (windowHeight - circleRadius == circleYPos) {
            yMultiplier = -1;
            stateChange = true;
        }
        else if (circleRadius == circleYPos) {
            yMultiplier = 1;
            stateChange = true;
        }
        if (windowWidth - circleRadius == circleXPos) {
            xMultiplier = -1;
            stateChange = true;
        } else if (circleRadius == circleXPos) {
            xMultiplier = 1;
            stateChange = true;
        }
        if (stateChange && animating) {
            beepSound.play(1);
            currentRedVal = (float) colourRand.nextInt(256);
            currentGreenVal = (float) colourRand.nextInt(256);
            currentBlueVal = (float) colourRand.nextInt(256);
            currentBgRedVal = (float) colourRand.nextInt(256);
            currentBgGreenVal = (float) colourRand.nextInt(256);
            currentBgBlueVal = (float) colourRand.nextInt(256);
            circleRadius += 1;
            stateChange = false;
        }
        if ((circleRadius == (windowHeight / 2) - 5 || circleRadius == (windowWidth / 2) - 5) && animating) {
            speed = 0;
            animating = false;
            circleXPos = windowWidth / 2;
            circleYPos = windowHeight / 2;
        }
        circleXPos += speed * xMultiplier;
        circleYPos += speed * yMultiplier;
        if (circleXPos > windowWidth - circleRadius) {
            circleXPos = windowWidth - circleRadius;
        } else if (circleXPos < circleRadius) {
            circleXPos = circleRadius;
        }
        if (circleYPos > windowHeight - circleRadius) {
            circleYPos = windowHeight - circleRadius;
        } else if (circleYPos < circleRadius) {
            circleYPos = circleRadius;
        }
    }

    @Override
    public void dispose() {
        beepSound.dispose();
        myRenderer.dispose();
    }
}
