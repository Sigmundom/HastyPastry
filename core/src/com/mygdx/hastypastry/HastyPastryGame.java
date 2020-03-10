package com.mygdx.hastypastry;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pl.mk5.gdx.fireapp.GdxFIRApp;
import pl.mk5.gdx.fireapp.GdxFIRDatabase;
import pl.mk5.gdx.fireapp.functional.Consumer;

public class HastyPastryGame extends ApplicationAdapter implements Input {
	SpriteBatch batch;
	Texture img;

	InputProcessor touchInpputProcessor = new DrawingInputProcessor();

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		GdxFIRApp.inst().configure();
		GdxFIRDatabase.inst()
				.inReference("/test/field")
				.onDataChange(String.class)
				.then(new Consumer<String>() {
					@Override
					public void accept(String s) {
						System.out.println(s);
					}
				});
		System.out.println("Hei!!!!!!!!!!!!!!!!!!!!!!!!");


	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}


	@Override
	public float getAccelerometerX() {
		return 0;
	}

	@Override
	public float getAccelerometerY() {
		return 0;
	}

	@Override
	public float getAccelerometerZ() {
		return 0;
	}

	@Override
	public float getGyroscopeX() {
		return 0;
	}

	@Override
	public float getGyroscopeY() {
		return 0;
	}

	@Override
	public float getGyroscopeZ() {
		return 0;
	}

	@Override
	public int getMaxPointers() {
		return 0;
	}

	@Override
	public int getX() {
		return 0;
	}

	@Override
	public int getX(int pointer) {
		return 0;
	}

	@Override
	public int getDeltaX() {
		return 0;
	}

	@Override
	public int getDeltaX(int pointer) {
		return 0;
	}

	@Override
	public int getY() {
		return 0;
	}

	@Override
	public int getY(int pointer) {
		return 0;
	}

	@Override
	public int getDeltaY() {
		return 0;
	}

	@Override
	public int getDeltaY(int pointer) {
		return 0;
	}

	@Override
	public boolean isTouched() {
		return false;
	}

	@Override
	public boolean justTouched() {
		System.out.println("justTouched!");
		return false;
	}

	@Override
	public boolean isTouched(int pointer) {
		System.out.println("isTouched!");
		return false;
	}

	@Override
	public float getPressure() {
		return 0;
	}

	@Override
	public float getPressure(int pointer) {
		return 0;
	}

	@Override
	public boolean isButtonPressed(int button) {
		return false;
	}

	@Override
	public boolean isButtonJustPressed(int button) {
		return false;
	}

	@Override
	public boolean isKeyPressed(int key) {
		return false;
	}

	@Override
	public boolean isKeyJustPressed(int key) {
		return false;
	}

	@Override
	public void getTextInput(TextInputListener listener, String title, String text, String hint) {

	}

	@Override
	public void setOnscreenKeyboardVisible(boolean visible) {

	}

	@Override
	public void vibrate(int milliseconds) {

	}

	@Override
	public void vibrate(long[] pattern, int repeat) {

	}

	@Override
	public void cancelVibrate() {

	}

	@Override
	public float getAzimuth() {
		return 0;
	}

	@Override
	public float getPitch() {
		return 0;
	}

	@Override
	public float getRoll() {
		return 0;
	}

	@Override
	public void getRotationMatrix(float[] matrix) {

	}

	@Override
	public long getCurrentEventTime() {
		return 0;
	}

	@Override
	public void setCatchBackKey(boolean catchBack) {

	}

	@Override
	public boolean isCatchBackKey() {
		return false;
	}

	@Override
	public void setCatchMenuKey(boolean catchMenu) {

	}

	@Override
	public boolean isCatchMenuKey() {
		return false;
	}

	@Override
	public void setCatchKey(int keycode, boolean catchKey) {

	}

	@Override
	public boolean isCatchKey(int keycode) {
		return false;
	}

	@Override
	public void setInputProcessor(InputProcessor processor) {

	}

	@Override
	public InputProcessor getInputProcessor() {
		return null;
	}

	@Override
	public boolean isPeripheralAvailable(Peripheral peripheral) {
		return false;
	}

	@Override
	public int getRotation() {
		return 0;
	}

	@Override
	public Orientation getNativeOrientation() {
		return null;
	}

	@Override
	public void setCursorCatched(boolean catched) {

	}

	@Override
	public boolean isCursorCatched() {
		return false;
	}

	@Override
	public void setCursorPosition(int x, int y) {

	}
}