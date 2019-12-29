package com.wizardstudios.hellfire;

import android.graphics.Rect;

public class GameObject {
	
	public double x;
	public double y;
	public int width;
	public int height;
	
	public GameObject(double x, double y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Rect getBounds() {
		return new Rect((int)x, (int)y, width, height);
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
}
